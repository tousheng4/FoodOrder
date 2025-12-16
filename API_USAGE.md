# 支付功能 API 使用文档

## 概述

本文档描述前端如何与主服务交互实现支付宝支付功能。

- **前端只需调用主服务接口**，主服务会自动与外部支付服务通信
- 支付完成后会重定向到 `http://localhost:3000/order/{orderId}`（使用数字ID）

---

## 主服务 API 接口（前端调用）

### 基础信息

- **服务地址**: `http://localhost:8080`（本地开发）
- **基础路径**: `/api/orders`
- **Content-Type**: `application/json`
- **认证方式**: Bearer Token（JWT）

---

### 1. 发起支付宝支付

创建支付订单，返回支付宝支付表单HTML。

**接口地址**: `POST /api/orders/{id}/alipay`

**路径参数**:

| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| id | Long | 是 | 订单ID |

**请求头**:
```
Authorization: Bearer <token>
Content-Type: application/json
```

**成功响应示例**:
```json
{
  "code": 200,
  "message": "success",
  "data": {
    "paymentId": "1",
    "formHtml": "<form name='punchout_form' method='post' action='https://openapi-sandbox.dl.alipaydev.com/gateway.do'>...</form>"
  }
}
```

**前端使用示例**:
```javascript
async function initiateAlipay(orderId) {
  const response = await fetch(`/api/orders/${orderId}/alipay`, {
    method: 'POST',
    headers: {
      'Authorization': `Bearer ${token}`,
      'Content-Type': 'application/json'
    }
  });
  
  const result = await response.json();
  
  if (result.code === 200) {
    // 将支付表单渲染到页面
    document.getElementById('payment-form').innerHTML = result.data.formHtml;
    // 自动提交表单跳转到支付宝
    document.forms['punchout_form'].submit();
  } else {
    alert('发起支付失败: ' + result.message);
  }
}
```

---

### 2. 查询支付状态

查询订单的支付状态，主服务会自动同步支付服务的最新状态。

**接口地址**: `GET /api/orders/{id}/payment-status`

**路径参数**:

| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| id | Long | 是 | 订单ID |

**请求头**:
```
Authorization: Bearer <token>
```

**响应 data 对象结构**:

| 字段名 | 类型 | 说明 |
|--------|------|------|
| orderNo | String | 订单号 |
| status | String | 支付状态：PENDING(待支付)、PAID(已支付)、FAILED(支付失败/已取消) |
| paidAt | String | 支付时间，格式：yyyy-MM-ddTHH:mm:ss |

**成功响应示例**:
```json
{
  "code": 200,
  "message": "success",
  "data": {
    "orderNo": "FO17336123456781234abcd",
    "status": "PAID",
    "paidAt": "2023-12-07T14:30:00"
  }
}
```

---

### 3. 订单详情

查询订单详情，仅返回本地数据库中的订单状态。

> **注意**：此接口不会主动同步支付服务状态。支付完成后请使用 `/payment-status` 接口轮询。

**接口地址**: `GET /api/orders/{id}`

**路径参数**:

| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| id | Long | 是 | 订单ID |

**请求头**:
```
Authorization: Bearer <token>
```

**响应 data 对象结构**:

| 字段名 | 类型 | 说明 |
|--------|------|------|
| id | Long | 订单ID |
| orderNo | String | 订单号 |
| status | Integer | 订单状态：0=待支付，1=已支付，2=已取消，3=已完成 |
| totalAmount | BigDecimal | 商品总金额 |
| payAmount | BigDecimal | 实付金额 |
| payTime | String | 支付时间 |
| items | Array | 订单商品列表 |

---

### 前端轮询逻辑

支付完成后，支付宝会重定向到 `http://localhost:3000/order/{orderId}`，前端应轮询查询支付状态：

```javascript
// 从URL获取订单ID（数字）
const orderId = window.location.pathname.split('/').pop();

async function pollPaymentStatus(orderId) {
  const maxAttempts = 60;  // 最多轮询60次（5分钟）
  let attempts = 0;
  
  const poll = async () => {
    attempts++;
    
    try {
      const response = await fetch(`/api/orders/${orderId}/payment-status`, {
        headers: { 'Authorization': `Bearer ${token}` }
      });
      const result = await response.json();
      
      if (result.code === 200) {
        if (result.data.status === 'PAID') {
          showPaymentSuccess(result.data);
          return;
        }
        if (result.data.status === 'FAILED') {
          showPaymentFailed();
          return;
        }
      }
      
      if (attempts < maxAttempts) {
        setTimeout(poll, 5000);  // 5秒后继续
      } else {
        showTimeout();
      }
    } catch (error) {
      console.error('查询支付状态失败:', error);
      if (attempts < maxAttempts) {
        setTimeout(poll, 5000);
      }
    }
  };
  
  poll();
}

// 页面加载时开始轮询
pollPaymentStatus(orderId);
```

---

## 支付服务 API（内部使用，前端无需直接调用）

### 基础信息

- **服务地址**: `http://115.190.94.13:8080`
- **基础路径**: `/api/pay`
- **Content-Type**: `application/json`
- **字符编码**: `UTF-8`

> 以下接口由主服务内部调用，前端无需关心。

---

## 支付服务 API 接口列表

### 1. 创建支付订单

创建支付订单并返回支付宝支付表单。

**接口地址**: `POST /api/pay/create`

**请求头**:
```
Content-Type: application/json
```

**请求参数** (JSON):

| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| orderId | Long | 是 | 主服务的订单ID（用于支付完成后重定向到前端） |
| orderNo | String | 是 | 订单号，唯一标识（用于支付宝交易） |
| amount | BigDecimal | 是 | 支付金额，必须大于0 |
| subject | String | 是 | 订单标题/商品名称 |

**请求示例**:
```json
{
  "orderId": 12,
  "orderNo": "ORDER20231207123456",
  "amount": 99.99,
  "subject": "测试商品"
}
```

**响应格式**:

| 字段名 | 类型 | 说明 |
|--------|------|------|
| code | Integer | 响应码，200表示成功 |
| message | String | 响应消息 |
| data | Object | 响应数据 |

**data 对象结构**:

| 字段名 | 类型 | 说明 |
|--------|------|------|
| paymentId | String | 支付记录ID |
| formHtml | String | 支付宝支付表单HTML，可直接渲染到页面 |

**成功响应示例**:
```json
{
  "code": 200,
  "message": "success",
  "data": {
    "paymentId": "1",
    "formHtml": "<form name='punchout_form' method='post' action='https://openapi-sandbox.dl.alipaydev.com/gateway.do'>...</form>"
  }
}
```

**错误响应示例**:
```json
{
  "code": 500,
  "message": "创建支付订单失败: 订单号不能为空",
  "data": null
}
```

**使用说明**:
1. 调用接口创建支付订单
2. 获取返回的 `formHtml` 字段
3. 将 `formHtml` 渲染到前端页面，用户点击后会自动跳转到支付宝支付页面

---

### 2. 查询支付状态

根据订单号查询支付状态。

**接口地址**: `GET /api/pay/status/{orderNo}`

**路径参数**:

| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| orderNo | String | 是 | 订单号 |

**请求示例**:
```bash
GET http://localhost:8080/api/pay/status/ORDER20231207123456
```

**响应格式**:

**data 对象结构**:

| 字段名 | 类型 | 说明 |
|--------|------|------|
| orderNo | String | 订单号 |
| status | String | 支付状态：PENDING(待支付)、PAID(已支付)、FAILED(支付失败) |
| paidAt | LocalDateTime | 支付时间，格式：yyyy-MM-ddTHH:mm:ss |
| tradeNo | String | 支付宝交易号 |

**成功响应示例**:
```json
{
  "code": 200,
  "message": "success",
  "data": {
    "orderNo": "ORDER20231207123456",
    "status": "PAID",
    "paidAt": "2023-12-07T14:30:00",
    "tradeNo": "2023120722001234567890123456"
  }
}
```

**错误响应示例**:
```json
{
  "code": 500,
  "message": "订单不存在: ORDER20231207123456",
  "data": null
}
```

---

### 3. 支付宝异步回调（内部接口）

支付宝支付完成后的异步通知接口，由支付宝服务器调用。

**接口地址**: `POST /api/pay/notify`

**请求方式**: `POST` (form-data)

**说明**: 
- 此接口由支付宝服务器调用，无需前端调用
- 需要在支付宝商户平台配置回调地址：`https://your-domain.com/api/pay/notify`
- 接口会验证签名并更新支付状态

**响应**: 
- 成功返回: `success`
- 失败返回: `failure`

---

### 4. 支付宝同步返回（重定向）

用户支付完成后，支付宝会跳转到此接口，然后**自动重定向到前端订单页面**。

**接口地址**: `GET /api/pay/return`

**请求参数** (Query参数，由支付宝自动传递):

| 参数名 | 类型 | 说明 |
|--------|------|------|
| out_trade_no | String | 商户订单号 |
| trade_no | String | 支付宝交易号 |

**行为说明**:
- 此接口由支付宝自动调用，用户支付完成后会跳转到此接口
- 接口根据 `out_trade_no` 查询数据库，获取主服务的 `orderId`
- 然后将用户**重定向到前端订单页面**：`http://localhost:3000/order/{orderId}`
- 前端收到请求后，应轮询主服务查询订单状态

**重定向流程**:
```
支付宝 → 支付服务 /api/pay/return → 查询orderId → 302重定向 → 前端 http://localhost:3000/order/{orderId}
```

---

## 完整支付流程（推荐方案）

### 架构说明

```
┌────────┐      ┌────────────┐      ┌────────────┐      ┌─────────┐
│  前端   │      │ 主服务(本地) │      │ 支付服务(外网)│      │  支付宝  │
└───┬────┘      └─────┬──────┘      └─────┬──────┘      └────┬────┘
    │                 │                   │                  │
    │ ① 发起支付      │                   │                  │
    │────────────────>│ ② 创建支付        │                  │
    │                 │──────────────────>│ ③ 调用支付宝API   │
    │                 │                   │─────────────────>│
    │                 │ ④ 返回 formHtml   │                  │
    │ ⑤ 返回 formHtml │<──────────────────│                  │
    │<────────────────│                   │                  │
    │                 │                   │                  │
    │ ⑥ 用户跳转支付宝支付 ─────────────────────────────────────>│
    │                 │                   │                  │
    │                 │                   │ ⑦ 异步回调notify  │
    │                 │                   │<─────────────────│
    │                 │                   │ (更新DB状态为PAID) │
    │                 │                   │                  │
    │<───────────────────────────────────────────────────────│
    │          ⑧ 同步跳转 return_url → 重定向到前端             │
    │                 │                   │                  │
    │ ⑨ 轮询订单状态   │                   │                  │
    │────────────────>│ ⑩ 查询支付状态    │                  │
    │                 │──────────────────>│                  │
    │                 │ ⑪ 返回 PAID       │                  │
    │                 │<──────────────────│                  │
    │ ⑫ 返回已支付    │ (更新本地订单状态) │                  │
    │<────────────────│                   │                  │
```

### 数据一致性保障

主服务实现了以下数据一致性保障机制：

| 机制 | 说明 |
|------|------|
| **自动同步** | 查询订单详情和支付状态时，会自动同步支付服务的最新状态 |
| **幂等更新** | 使用乐观锁（WHERE status=0），保证订单状态只会被更新一次 |
| **故障隔离** | 支付服务查询失败不影响主服务正常返回，下次轮询会重试 |
| **状态检查** | 已支付/已取消的订单不会再次调用支付服务，避免无效请求 |

---

## 支付状态说明

| 状态值 | 说明 |
|--------|------|
| PENDING | 待支付，订单已创建但未支付 |
| PAID | 已支付，支付成功 |
| FAILED | 支付失败 |

---

## 错误码说明

| 错误码 | 说明 |
|--------|------|
| 200 | 请求成功 |
| 500 | 服务器内部错误 |

常见错误信息：
- `订单号不能为空`
- `支付金额不能为空`
- `支付金额必须大于0`
- `订单标题不能为空`
- `订单不存在: {orderNo}`
- `创建支付订单失败: {错误详情}`

---

## 完整使用示例

### 示例1：创建支付订单（使用 curl）

```bash
curl -X POST http://localhost:8080/api/pay/create \
  -H "Content-Type: application/json" \
  -d '{
    "orderNo": "ORDER20231207123456",
    "amount": 99.99,
    "subject": "测试商品"
  }'
```

### 示例2：查询支付状态（使用 curl）

```bash
curl http://localhost:8080/api/pay/status/ORDER20231207123456
```

### 示例3：前端 JavaScript 示例（调用主服务）

```javascript
// 发起支付宝支付
async function initiateAlipay(orderId, token) {
  const response = await fetch(`/api/orders/${orderId}/alipay`, {
    method: 'POST',
    headers: {
      'Authorization': `Bearer ${token}`,
      'Content-Type': 'application/json'
    }
  });
  
  const result = await response.json();
  
  if (result.code === 200) {
    // 将支付表单渲染到页面
    document.getElementById('payment-form').innerHTML = result.data.formHtml;
    // 自动提交表单跳转到支付宝
    document.forms['punchout_form'].submit();
  } else {
    alert('发起支付失败: ' + result.message);
  }
}

// 查询支付状态
async function checkPaymentStatus(orderId, token) {
  const response = await fetch(`/api/orders/${orderId}/payment-status`, {
    headers: {
      'Authorization': `Bearer ${token}`
    }
  });
  const result = await response.json();
  
  if (result.code === 200) {
    console.log('支付状态:', result.data.status);
    console.log('支付时间:', result.data.paidAt);
  } else {
    console.error('查询失败:', result.message);
  }
}

// 使用示例
initiateAlipay(12, 'your-jwt-token');
```

### 示例4：Java 客户端示例（调用支付服务，主服务内部使用）

```java
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import java.util.HashMap;
import java.util.Map;

public class PaymentClient {
    private static final String BASE_URL = "http://115.190.94.13:8080/api/pay";
    private RestTemplate restTemplate = new RestTemplate();
    
    // 创建支付订单
    public CreatePaymentResponse createPayment(Long orderId, String orderNo, BigDecimal amount, String subject) {
        String url = BASE_URL + "/create";
        
        Map<String, Object> request = new HashMap<>();
        request.put("orderId", orderId);    // 主服务订单ID，用于支付完成后重定向
        request.put("orderNo", orderNo);    // 订单号，用于支付宝交易
        request.put("amount", amount);
        request.put("subject", subject);
        
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(request, headers);
        
        ApiResponse response = restTemplate.postForObject(url, entity, ApiResponse.class);
        
        if (response.getCode() == 200) {
            return (CreatePaymentResponse) response.getData();
        } else {
            throw new RuntimeException("创建支付订单失败: " + response.getMessage());
        }
    }
    
    // 查询支付状态
    public PaymentStatusResponse getPaymentStatus(String orderNo) {
        String url = BASE_URL + "/status/" + orderNo;
        
        ApiResponse response = restTemplate.getForObject(url, ApiResponse.class);
        
        if (response.getCode() == 200) {
            return (PaymentStatusResponse) response.getData();
        } else {
            throw new RuntimeException("查询支付状态失败: " + response.getMessage());
        }
    }
}
```

---

## 注意事项

1. **订单号唯一性**: 订单号必须全局唯一，重复的订单号会导致创建失败
2. **金额精度**: 支付金额支持两位小数，例如：99.99
3. **支付环境**: 当前使用的是支付宝沙箱环境，正式环境需要修改配置
4. **回调地址**: 生产环境需要配置正确的回调地址和返回地址
5. **HTTPS**: 生产环境建议使用 HTTPS 协议
6. **签名验证**: 支付宝回调会自动验证签名，确保数据安全
7. **开发测试**: 
   - 支付服务部署在外网（115.190.94.13）用于接收支付宝回调
   - 前端可以在 localhost 运行，支付完成后会重定向回 `http://localhost:3000/order/{orderId}`（数字ID）
   - 主服务可以在本地运行，通过 HTTP 调用支付服务接口

---

## 测试建议

1. **创建订单测试**: 使用不同的订单号创建多个支付订单
2. **状态查询测试**: 创建订单后立即查询状态，应该返回 PENDING
3. **支付流程测试**: 完成支付后查询状态，应该返回 PAID
4. **异常情况测试**: 测试空参数、负数金额等异常情况

---

## 技术支持

如有问题，请查看：
- 服务日志：检查应用运行日志
- 数据库：检查 `payment_record` 表中的支付记录
- 支付宝沙箱：确认支付宝沙箱账号配置正确


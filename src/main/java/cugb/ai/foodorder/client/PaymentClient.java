package cugb.ai.foodorder.client;

import cugb.ai.foodorder.common.BusinessException;
import cugb.ai.foodorder.common.ErrorCode;
import cugb.ai.foodorder.dto.PaymentCreateRequest;
import cugb.ai.foodorder.dto.PaymentCreateResponse;
import cugb.ai.foodorder.dto.PaymentStatusResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.Map;

@Component
public class PaymentClient {

    private static final Logger log = LoggerFactory.getLogger(PaymentClient.class);

    @Value("${payment.service.base-url}")
    private String baseUrl;

    private final RestTemplate restTemplate;

    public PaymentClient() {
        this.restTemplate = new RestTemplate();
    }

    /**
     * 创建支付订单
     *
     * @param orderId 主服务订单ID（用于支付完成后重定向）
     * @param orderNo 订单号（用于支付宝交易）
     * @param amount  支付金额
     * @param subject 订单标题
     * @return 支付创建响应
     */
    public PaymentCreateResponse createPayment(Long orderId, String orderNo, BigDecimal amount, String subject) {
        String url = baseUrl + "/api/pay/create";

        PaymentCreateRequest request = new PaymentCreateRequest();
        request.setOrderId(orderId);
        request.setOrderNo(orderNo);
        request.setAmount(amount);
        request.setSubject(subject);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<PaymentCreateRequest> entity = new HttpEntity<>(request, headers);

        try {
            ResponseEntity<Map<String, Object>> response = restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    entity,
                    new ParameterizedTypeReference<Map<String, Object>>() {}
            );

            Map<String, Object> body = response.getBody();
            if (body == null) {
                throw new BusinessException(ErrorCode.SERVER_ERROR, "支付服务响应为空");
            }

            Integer code = (Integer) body.get("code");
            if (code == null || code != 200) {
                String message = (String) body.get("message");
                throw new BusinessException(ErrorCode.SERVER_ERROR, "创建支付失败: " + message);
            }

            @SuppressWarnings("unchecked")
            Map<String, Object> data = (Map<String, Object>) body.get("data");
            if (data == null) {
                throw new BusinessException(ErrorCode.SERVER_ERROR, "支付服务响应数据为空");
            }

            PaymentCreateResponse result = new PaymentCreateResponse();
            result.setPaymentId(String.valueOf(data.get("paymentId")));
            result.setFormHtml((String) data.get("formHtml"));
            return result;

        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            log.error("调用支付服务失败", e);
            throw new BusinessException(ErrorCode.SERVER_ERROR, "调用支付服务失败: " + e.getMessage());
        }
    }

    /**
     * 查询支付状态
     *
     * @param orderNo 订单号
     * @return 支付状态响应
     */
    public PaymentStatusResponse getPaymentStatus(String orderNo) {
        String url = baseUrl + "/api/pay/status/" + orderNo;

        try {
            ResponseEntity<Map<String, Object>> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<Map<String, Object>>() {}
            );

            Map<String, Object> body = response.getBody();
            if (body == null) {
                throw new BusinessException(ErrorCode.SERVER_ERROR, "支付服务响应为空");
            }

            Integer code = (Integer) body.get("code");
            if (code == null || code != 200) {
                String message = (String) body.get("message");
                throw new BusinessException(ErrorCode.SERVER_ERROR, "查询支付状态失败: " + message);
            }

            @SuppressWarnings("unchecked")
            Map<String, Object> data = (Map<String, Object>) body.get("data");
            if (data == null) {
                throw new BusinessException(ErrorCode.SERVER_ERROR, "支付服务响应数据为空");
            }

            PaymentStatusResponse result = new PaymentStatusResponse();
            result.setOrderNo((String) data.get("orderNo"));
            result.setStatus((String) data.get("status"));
            result.setTradeNo((String) data.get("tradeNo"));
            // paidAt 可能为null
            Object paidAtObj = data.get("paidAt");
            if (paidAtObj != null) {
                result.setPaidAt(java.time.LocalDateTime.parse((String) paidAtObj));
            }
            return result;

        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            log.error("调用支付服务失败", e);
            throw new BusinessException(ErrorCode.SERVER_ERROR, "调用支付服务失败: " + e.getMessage());
        }
    }
}

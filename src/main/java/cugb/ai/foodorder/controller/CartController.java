package cugb.ai.foodorder.controller;

import cugb.ai.foodorder.common.BusinessException;
import cugb.ai.foodorder.common.ErrorCode;
import cugb.ai.foodorder.common.Result;
import cugb.ai.foodorder.dto.AddCartItemRequest;
import cugb.ai.foodorder.dto.UpdateCartItemRequest;
import cugb.ai.foodorder.security.UserContext;
import cugb.ai.foodorder.service.CartService;
import cugb.ai.foodorder.vo.CartItemVO;
import javax.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    private Long currentUserId() {
        UserContext.LoginUser loginUser = UserContext.get();
        if (loginUser == null) {
            throw new BusinessException(ErrorCode.UNAUTHORIZED, "未登录");
        }
        return loginUser.getUserId();
    }

    /**
     * 获取当前用户购物车列表
     */
    @GetMapping
    public Result<List<CartItemVO>> list() {
        Long userId = currentUserId();
        List<CartItemVO> list = cartService.listCart(userId);
        return Result.success(list);
    }

    /**
     * 加入购物车
     */
    @PostMapping
    public Result<Void> add(@Valid @RequestBody AddCartItemRequest req) {
        Long userId = currentUserId();
        cartService.addToCart(userId, req);
        return Result.success();
    }

    /**
     * 更新购物车某个菜品（数量 / 选中状态）
     */
    @PutMapping("/{dishId}")
    public Result<Void> update(@PathVariable Long dishId,
                               @Valid @RequestBody UpdateCartItemRequest req) {
        Long userId = currentUserId();
        cartService.updateCartItem(userId, dishId, req);
        return Result.success();
    }

    /**
     * 删除购物车中的某个菜品
     */
    @DeleteMapping("/{dishId}")
    public Result<Void> remove(@PathVariable Long dishId) {
        Long userId = currentUserId();
        cartService.removeItem(userId, dishId);
        return Result.success();
    }

    /**
     * 清空购物车
     */
    @DeleteMapping
    public Result<Void> clear() {
        Long userId = currentUserId();
        cartService.clearCart(userId);
        return Result.success();
    }
}

package cugb.ai.foodorder.service;

import cugb.ai.foodorder.dto.AddCartItemRequest;
import cugb.ai.foodorder.dto.UpdateCartItemRequest;
import cugb.ai.foodorder.vo.CartItemVO;

import java.util.List;

public interface CartService {

    List<CartItemVO> listCart(Long userId);

    void addToCart(Long userId, AddCartItemRequest req);

    void updateCartItem(Long userId, Long dishId, UpdateCartItemRequest req);

    void removeItem(Long userId, Long dishId);

    void clearCart(Long userId);
}

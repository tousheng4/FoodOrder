package cugb.ai.foodorder.service.impl;

import cugb.ai.foodorder.common.BusinessException;
import cugb.ai.foodorder.common.ErrorCode;
import cugb.ai.foodorder.dto.AddCartItemRequest;
import cugb.ai.foodorder.dto.UpdateCartItemRequest;
import cugb.ai.foodorder.entity.CartItem;
import cugb.ai.foodorder.entity.Dish;
import cugb.ai.foodorder.mapper.CartItemMapper;
import cugb.ai.foodorder.mapper.DishMapper;
import cugb.ai.foodorder.service.CartService;
import cugb.ai.foodorder.vo.CartItemVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class CartServiceImpl implements CartService {

    private final CartItemMapper cartItemMapper;
    private final DishMapper dishMapper;

    public CartServiceImpl(CartItemMapper cartItemMapper,
                           DishMapper dishMapper) {
        this.cartItemMapper = cartItemMapper;
        this.dishMapper = dishMapper;
    }

    @Override
    public List<CartItemVO> listCart(Long userId) {
        List<CartItemVO> list = cartItemMapper.selectDetailByUserId(userId);
        // 计算小计
        for (CartItemVO vo : list) {
            BigDecimal subTotal = vo.getPrice()
                    .multiply(BigDecimal.valueOf(vo.getQuantity()));
            vo.setSubTotal(subTotal);
        }
        return list;
    }

    @Transactional
    @Override
    public void addToCart(Long userId, AddCartItemRequest req) {
        // 校验菜品存在且上架
        Dish dish = dishMapper.selectById(req.getDishId());
        if (dish == null || dish.getDeleted() != null && dish.getDeleted() == 1) {
            throw new BusinessException(ErrorCode.DISH_NOT_FOUND);
        }
        if (dish.getStatus() != null && dish.getStatus() == 0) {
            throw new BusinessException(ErrorCode.DISH_OFF_SHELF);
        }

        int addQty = (req.getQuantity() == null || req.getQuantity() <= 0)
                ? 1
                : req.getQuantity();

        CartItem exist = cartItemMapper.selectByUserIdAndDishId(userId, req.getDishId());
        if (exist == null) {
            CartItem item = new CartItem();
            item.setUserId(userId);
            item.setDishId(req.getDishId());
            item.setQuantity(addQty);
            item.setChecked(1); // 默认选中
            cartItemMapper.insert(item);
        } else {
            exist.setQuantity(exist.getQuantity() + addQty);
            // 保持选中状态不变
            cartItemMapper.update(exist);
        }
    }

    @Transactional
    @Override
    public void updateCartItem(Long userId, Long dishId, UpdateCartItemRequest req) {
        CartItem item = cartItemMapper.selectByUserIdAndDishId(userId, dishId);
        if (item == null) {
            throw new BusinessException(
                    ErrorCode.CART_ITEM_NOT_FOUND,
                    "购物车中不存在该菜品"
            );
        }

        // 数量为 0 或小于 0，直接删除
        if (req.getQuantity() != null && req.getQuantity() <= 0) {
            cartItemMapper.deleteByUserIdAndDishId(userId, dishId);
            return;
        }

        if (req.getQuantity() != null && req.getQuantity() > 0) {
            item.setQuantity(req.getQuantity());
        }
        if (req.getChecked() != null) {
            item.setChecked(req.getChecked());
        }

        cartItemMapper.update(item);
    }

    @Transactional
    @Override
    public void removeItem(Long userId, Long dishId) {
        cartItemMapper.deleteByUserIdAndDishId(userId, dishId);
    }

    @Transactional
    @Override
    public void clearCart(Long userId) {
        cartItemMapper.deleteByUserId(userId);
    }
}

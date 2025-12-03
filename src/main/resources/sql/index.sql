
ALTER TABLE dish
    ADD INDEX idx_dish_name (name),
    ADD INDEX idx_dish_category_status (category_id, status),
    ADD INDEX idx_dish_price (price);

ALTER TABLE order_info
    ADD INDEX idx_order_no (order_no),
    ADD INDEX idx_order_user (user_id, status, created_at),
    ADD INDEX idx_order_created_at (created_at);

ALTER TABLE sys_user
    ADD INDEX idx_user_username (username),
    ADD INDEX idx_user_phone (phone),
    ADD INDEX idx_user_nickname (nickname);

USE food_order;

INSERT INTO sys_user (username, password_hash, nickname, phone, avatar, role, status)
VALUES
-- 管理员账号：admin / admin123
('admin',
 '$2a$10$tcEV6C8A2y4jkOKZ1NaB0.qIhewUeD.J702zlyP1k5cce1BBvoy0q',
 '系统管理员',
 '13800000000',
 NULL,
 1,   -- role=1 管理员
 1),  -- status=1 正常

-- 普通用户：alice / 123456
('alice',
 '$2a$10$gxHsB4Cyt68Q4/g/dH1GCuQyu1TXppQWcGvmS8Si.zdUDs4Ik2BCO',
 '爱丽丝',
 '13800000001',
 NULL,
 0,   -- role=0 普通用户
 1),

-- 普通用户：bob / user123
('bob',
 '$2a$10$Eq.cJHX.88pxS/mVmF8FbeZOWpiO1Yn72zrcYASv4YG3xLMiOTzRi',
 '小波',
 '13800000002',
 NULL,
 0,
 1);
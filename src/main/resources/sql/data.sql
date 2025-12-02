USE food_order;

-- 可选：先删除之前同名的演示用户，避免主键或唯一约束冲突
DELETE FROM sys_user WHERE id BETWEEN 20001 AND 20060 OR username LIKE 'user0__';

INSERT INTO sys_user
(id, username, password_hash, nickname, phone, avatar, role, status, last_login_at, created_at, updated_at)
VALUES
    (20001, 'user001', '$2b$10$k1Pa.e2Ip/2z3E8uEcorY.P4E7O7fJ6S/6UEz1L7ZVqCClc1Ud9Vu', '用户001', '13980000001', NULL, 1, 1, NOW(), NOW(), NOW()),
    (20002, 'user002', '$2b$10$X0ePxaHeGrDJIzzEEHcL4ejtTnIGFdlWfzPPVQUNBO7y0cdnljZ7y', '用户002', '13980000002', NULL, 1, 1, NOW(), NOW(), NOW()),
    (20003, 'user003', '$2b$10$gjUq5NGwnePlRS7uAKJhX.CirDzfAGtfVubwdnQvkb9yZJty3BRsq', '用户003', '13980000003', NULL, 1, 1, NOW(), NOW(), NOW()),
    (20004, 'user004', '$2b$10$cflyYXntCB2xc6ClAnsbvOGGjAnOkV4pPHMIq67Uxi/Ink5ywx.76', '用户004', '13980000004', NULL, 0, 1, NOW(), NOW(), NOW()),
    (20005, 'user005', '$2b$10$znqa3gVU/o5K2SaYj0YPY.TrsrhftwHryruSYBS8CHmYW7jQZ3Po6', '用户005', '13980000005', NULL, 0, 1, NOW(), NOW(), NOW()),
    (20006, 'user006', '$2b$10$6yKBtSgEmta5i9osXwY88et0mIBSH.y4xeudAg9awlGYhiMCWcynK', '用户006', '13980000006', NULL, 0, 1, NOW(), NOW(), NOW()),
    (20007, 'user007', '$2b$10$ekt8tzZFBUT4RSZRaAwrYOmB8kCPiQKv2vsjdCANTGqnNsqcGi1T.', '用户007', '13980000007', NULL, 0, 1, NOW(), NOW(), NOW()),
    (20008, 'user008', '$2b$10$R4Ko6M.7VT8PWlPz3ubYsuFp.y0KMl9aEMz9W2b5vfbQM91YD/Nam', '用户008', '13980000008', NULL, 0, 1, NOW(), NOW(), NOW()),
    (20009, 'user009', '$2b$10$4h7i602znfCLHHNqSRqtSe.6DFnh3FJCZEkTPzzrRBOPwmVD0hU8C', '用户009', '13980000009', NULL, 0, 1, NOW(), NOW(), NOW()),
    (20010, 'user010', '$2b$10$EnPF..H2ZBnKgRbefS/.LeWBUcaF0bi1YbOngMg2l/FKnGtir7SB.', '用户010', '13980000010', NULL, 0, 0, NOW(), NOW(), NOW()),
    (20011, 'user011', '$2b$10$sTf8x9ynW1nSgoQNEFveAeEFoQP0QhYw75/s7d7kwLQFsANpSz9Z.', '用户011', '13980000011', NULL, 0, 1, NOW(), NOW(), NOW()),
    (20012, 'user012', '$2b$10$cFHifV.eGQEBvjQHiQoVre4tRTPZuGAP5Xot3FjnXryFW93.1ndb.', '用户012', '13980000012', NULL, 0, 1, NOW(), NOW(), NOW()),
    (20013, 'user013', '$2b$10$B9xM0BCglmmBKu3DWm3SYeGuTnAfecEVO0gWcGxbGhVjafWHErU4.', '用户013', '13980000013', NULL, 0, 1, NOW(), NOW(), NOW()),
    (20014, 'user014', '$2b$10$7xvkk03sEnyxIEvrZkt.Ee6y1n6hER6SeJTrxtAZ0ZFvzYAbFTVwC', '用户014', '13980000014', NULL, 0, 1, NOW(), NOW(), NOW()),
    (20015, 'user015', '$2b$10$aXGsNw40XuHf1HiieYMDxe/0kDlqYyIoUGLWFWuLNiQEEbOxlqx7a', '用户015', '13980000015', NULL, 0, 1, NOW(), NOW(), NOW()),
    (20016, 'user016', '$2b$10$B6Z.65X028lNmJISiYshwepRE6GuttnpC3CECOUixW1pmcoAo/yea', '用户016', '13980000016', NULL, 0, 1, NOW(), NOW(), NOW()),
    (20017, 'user017', '$2b$10$My5g/WbVs/8hB2b.dVny6ujK5Afod6pE.m56VNcfeo6rnL7iABe6O', '用户017', '13980000017', NULL, 0, 1, NOW(), NOW(), NOW()),
    (20018, 'user018', '$2b$10$q4u3ziOPpIQy73v1koS/veAt2x.VqfvrGh82kKpWJICYM9KO5kPsq', '用户018', '13980000018', NULL, 0, 1, NOW(), NOW(), NOW()),
    (20019, 'user019', '$2b$10$xLch2lcRSdEf18dtoG16qOdcDNeScYYd155zdub1F9DE8NKS.Uk.e', '用户019', '13980000019', NULL, 0, 1, NOW(), NOW(), NOW()),
    (20020, 'user020', '$2b$10$HMQ0T/oiSRfMZcV8mWB9DOGmBj5AMe/J6MkQKZW676TDXlDl6juNi', '用户020', '13980000020', NULL, 0, 0, NOW(), NOW(), NOW()),
    (20021, 'user021', '$2b$10$T.MnnD6kk/KoxyI4mZCKXOAUyZWbOPzS1I37YJamesnwZlL/oC5LW', '用户021', '13980000021', NULL, 0, 1, NOW(), NOW(), NOW()),
    (20022, 'user022', '$2b$10$KSIolF7YytJj12QraSR.D.CGtOOqTjMYEkGcjdq0uDQLVxSdbabDS', '用户022', '13980000022', NULL, 0, 1, NOW(), NOW(), NOW()),
    (20023, 'user023', '$2b$10$SZlcODb.WGna6Nf9o1kbA.AENDFb3ddlRwxKaNgaRJXHzYm7oscVi', '用户023', '13980000023', NULL, 0, 1, NOW(), NOW(), NOW()),
    (20024, 'user024', '$2b$10$Zz4tlWj4i8N1i0DzxnR2e.itI2k9PpZEcbcFZWDadZGYnrc0d6Jou', '用户024', '13980000024', NULL, 0, 1, NOW(), NOW(), NOW()),
    (20025, 'user025', '$2b$10$Cmo1XR1DEl4X/VgRxh6r/O8qEzKIc6.5YcwDxtmbrzYGFWaCN3foe', '用户025', '13980000025', NULL, 0, 1, NOW(), NOW(), NOW()),
    (20026, 'user026', '$2b$10$kdGcUfWW11bNfVsYYMUhpuKhN2/RjQNvwjYLuk2eq7IBG/y/8CrUS', '用户026', '13980000026', NULL, 0, 1, NOW(), NOW(), NOW()),
    (20027, 'user027', '$2b$10$OtUfepUMFNQt0ng7k/y7vewIyft1DPFbqg4DYzXdEtT0RNC8UbbSi', '用户027', '13980000027', NULL, 0, 1, NOW(), NOW(), NOW()),
    (20028, 'user028', '$2b$10$PPOv4jlWI2Y3HT1cghszFODcpazYvoaklY8OvRbOlTrRreZWF8HfO', '用户028', '13980000028', NULL, 0, 1, NOW(), NOW(), NOW()),
    (20029, 'user029', '$2b$10$cG1fOXhQBRum0O6RMbthR.Nz9ZCEuAnf/bb4GMUNVFoN.foKzoyTO', '用户029', '13980000029', NULL, 0, 1, NOW(), NOW(), NOW()),
    (20030, 'user030', '$2b$10$mXGMO97ygXg11Kz5XQDdqeSr9YdLhz4qF//otTdJHbqD4L1hx3a6e', '用户030', '13980000030', NULL, 0, 0, NOW(), NOW(), NOW()),
    (20031, 'user031', '$2b$10$4mtJdpKqTqEvv6NhgHkMHOzlSV9E0jkR/xjl0Yy/N9h.zebka9nM2', '用户031', '13980000031', NULL, 0, 1, NOW(), NOW(), NOW()),
    (20032, 'user032', '$2b$10$zRVQ.xLO9ufqqguilVb9WerlWxSyGtGo/F6BeNyF/4Zcz7VLsCiv2', '用户032', '13980000032', NULL, 0, 1, NOW(), NOW(), NOW()),
    (20033, 'user033', '$2b$10$MDqFaZlD83w25d52PUpUmis1vGs7dkvI3VphZd6eYa7LJWfKDAbFq', '用户033', '13980000033', NULL, 0, 1, NOW(), NOW(), NOW()),
    (20034, 'user034', '$2b$10$WSHpwbbyDEmueplOOpcJ4epbCWye4x54dVfmKmyglqeDeXzNbyHq6', '用户034', '13980000034', NULL, 0, 1, NOW(), NOW(), NOW()),
    (20035, 'user035', '$2b$10$H65pWF1ZOo4tgrBAkcx3Ze.1hQLhUZB/2I515vhbdPY.LCpXD4.5O', '用户035', '13980000035', NULL, 0, 1, NOW(), NOW(), NOW()),
    (20036, 'user036', '$2b$10$zJlRqDgljX.fKFos.fchqu4pcgMR6Zo7pU3piwkv4kYYrvqoJO7c6', '用户036', '13980000036', NULL, 0, 1, NOW(), NOW(), NOW()),
    (20037, 'user037', '$2b$10$BSEeCFgAcj1V4xct7JH3EuWQ81q3V14YKz3S.NzSixwxnKieFHgvi', '用户037', '13980000037', NULL, 0, 1, NOW(), NOW(), NOW()),
    (20038, 'user038', '$2b$10$vWXa1OVXqmihfBoIbHzMDOMuABn.InS9jXWH2tA4isnxEcZxpj4yG', '用户038', '13980000038', NULL, 0, 1, NOW(), NOW(), NOW()),
    (20039, 'user039', '$2b$10$3pmDr.x0v93dgVtYQ5z.4OyiapVxd4iuNkIc887vAxs9D.QKzMgra', '用户039', '13980000039', NULL, 0, 1, NOW(), NOW(), NOW()),
    (20040, 'user040', '$2b$10$e2F9D1D1pc6teDDoBItuPeSft2Q1W3nbPGKwqVE/do9AE5JSW.nHq', '用户040', '13980000040', NULL, 0, 0, NOW(), NOW(), NOW()),
    (20041, 'user041', '$2b$10$9.ouA1QGFaltsBfeBmr1ze6DivZ3CCffEYNcCpKq9z23EJLpJ7w1i', '用户041', '13980000041', NULL, 0, 1, NOW(), NOW(), NOW()),
    (20042, 'user042', '$2b$10$EPhnEvdNlUA4LUzEh8XO1.D1Gf.RaD4qYdCmtLX2hajTte9W0K.y2', '用户042', '13980000042', NULL, 0, 1, NOW(), NOW(), NOW()),
    (20043, 'user043', '$2b$10$pSn.jN5lHUhSbnt9SyL9Fef6yonYTO5Ro50W6tA7Eq4cfHU0X1vd2', '用户043', '13980000043', NULL, 0, 1, NOW(), NOW(), NOW()),
    (20044, 'user044', '$2b$10$gD3DkiAoIALikiwuORqJPeXW8S.R5sWSmky7IXEmE/osdp9H1qySq', '用户044', '13980000044', NULL, 0, 1, NOW(), NOW(), NOW()),
    (20045, 'user045', '$2b$10$s5YHkBTKUnn6WehB4b3ZSu4YGan5cphqMLz6u5GlzfhI3TXYczkeO', '用户045', '13980000045', NULL, 0, 1, NOW(), NOW(), NOW()),
    (20046, 'user046', '$2b$10$pbqy1qn4vFdfkOYksJz9B.W.xgFOOY30BvG7oM.fptV9ms0v1Cmuq', '用户046', '13980000046', NULL, 0, 1, NOW(), NOW(), NOW()),
    (20047, 'user047', '$2b$10$sdO8aZYzA1FoZ9vVMFAjZ.3CRcgsd4D8Ba1.58/n46i8dZug9B6fK', '用户047', '13980000047', NULL, 0, 1, NOW(), NOW(), NOW()),
    (20048, 'user048', '$2b$10$S.Gm/7I.G1pUv/xjqrQAM.TAou3sVWDpRe59dO96zWN0I3R3f.s2u', '用户048', '13980000048', NULL, 0, 1, NOW(), NOW(), NOW()),
    (20049, 'user049', '$2b$10$O5kow.U0w1DZYJnTFNl1ceGIVLbX3Uy3dVO3d6nDcO10ahACxzceq', '用户049', '13980000049', NULL, 0, 1, NOW(), NOW(), NOW()),
    (20050, 'user050', '$2b$10$MYwoz.gvCSUsfnlb3.OgUuQDMEWPhSqZSdwoG/JccIbj7/7K0rH4G', '用户050', '13980000050', NULL, 0, 0, NOW(), NOW(), NOW()),
    (20051, 'user051', '$2b$10$x.zTTJR5fzsazAfNcVlHqeJEMMRyfqMpXkHI8jlNkqBvdrPgw/7jq', '用户051', '13980000051', NULL, 0, 1, NOW(), NOW(), NOW()),
    (20052, 'user052', '$2b$10$B/K.Kfp2/6zdRbK8CdAzO.hcm0kWS8tPECqC3Nuuk13jBKOsMN8y.', '用户052', '13980000052', NULL, 0, 1, NOW(), NOW(), NOW()),
    (20053, 'user053', '$2b$10$s7SCXHCIQxVRgfD.jk4XM.m1hrdjohAHxzpInnQk5p8zSrlQmwJfm', '用户053', '13980000053', NULL, 0, 1, NOW(), NOW(), NOW()),
    (20054, 'user054', '$2b$10$v5GSPRn/TjJ5TxvgQmP7zujJIqcKQKE3m/oTKEmswucGNHZyIU/6q', '用户054', '13980000054', NULL, 0, 1, NOW(), NOW(), NOW()),
    (20055, 'user055', '$2b$10$G77qsRr3AFu3pxKwvd5iUebGsn7Zad4ttdY.U3ghluF84QPYvml2O', '用户055', '13980000055', NULL, 0, 1, NOW(), NOW(), NOW()),
    (20056, 'user056', '$2b$10$Fq8KiP5lQlyyLteUfuzFUeplR0vGTsFZR6AM6YzdtFeGFpfHfY21G', '用户056', '13980000056', NULL, 0, 1, NOW(), NOW(), NOW()),
    (20057, 'user057', '$2b$10$RGgtNZDiFW/FqgWbWXvlIOu4JYNImKJmM.9v7Ov9X7jwg4VAX5Dhe', '用户057', '13980000057', NULL, 0, 1, NOW(), NOW(), NOW()),
    (20058, 'user058', '$2b$10$gCkhkeTnUW2.PdiTiG70auOY3M6KNeBsZoQiM.6aATLUSmecLgaDq', '用户058', '13980000058', NULL, 0, 1, NOW(), NOW(), NOW()),
    (20059, 'user059', '$2b$10$7LTzh.GfAcPTero.3bfCQ.la.ma3BLpRWiV8doa1ezskmpvvRVNYi', '用户059', '13980000059', NULL, 0, 1, NOW(), NOW(), NOW()),
    (20060, 'user060', '$2b$10$uJA.WkYFx0u2f0YlGQRQDe3HdRXVJyVg4TUDwF.ehps4mPhGd/F7O', '用户060', '13980000060', NULL, 0, 0, NOW(), NOW(), NOW());


USE food_order;

-- 如需清理这批数据再重插，可以手动先执行：
-- DELETE FROM category WHERE id BETWEEN 20001 AND 20080;

INSERT INTO category
(id, name, sort, status, created_at, updated_at)
VALUES
    (20001, '川菜',            500, 1, NOW(), NOW()),
    (20002, '粤菜',            490, 1, NOW(), NOW()),
    (20003, '湘菜',            480, 1, NOW(), NOW()),
    (20004, '鲁菜',            470, 1, NOW(), NOW()),
    (20005, '浙菜',            460, 1, NOW(), NOW()),
    (20006, '苏菜',            450, 1, NOW(), NOW()),
    (20007, '闽菜',            440, 1, NOW(), NOW()),
    (20008, '徽菜',            430, 1, NOW(), NOW()),
    (20009, '东北菜',          420, 1, NOW(), NOW()),
    (20010, '西北菜',          410, 1, NOW(), NOW()),

    (20011, '家常小炒',        400, 1, NOW(), NOW()),
    (20012, '快餐简餐',        390, 1, NOW(), NOW()),
    (20013, '盖浇饭',          380, 1, NOW(), NOW()),
    (20014, '炒饭炒面',        370, 1, NOW(), NOW()),
    (20015, '粉面米线',        360, 1, NOW(), NOW()),
    (20016, '汉堡炸鸡',        350, 1, NOW(), NOW()),
    (20017, '披萨意面',        340, 1, NOW(), NOW()),
    (20018, '火锅冒菜',        330, 1, NOW(), NOW()),
    (20019, '烧烤烤肉',        320, 1, NOW(), NOW()),
    (20020, '卤味熟食',        310, 1, NOW(), NOW()),

    (20021, '汤羹炖品',        300, 1, NOW(), NOW()),
    (20022, '砂锅煲仔',        290, 1, NOW(), NOW()),
    (20023, '地方小吃-成都',   280, 1, NOW(), NOW()),
    (20024, '地方小吃-长沙',   270, 1, NOW(), NOW()),
    (20025, '地方小吃-西安',   260, 1, NOW(), NOW()),
    (20026, '地方小吃-北京',   250, 1, NOW(), NOW()),
    (20027, '日韩料理',        240, 1, NOW(), NOW()),
    (20028, '东南亚风味',      230, 1, NOW(), NOW()),
    (20029, '印度料理',        220, 1, NOW(), NOW()),
    (20030, '西式简餐',        210, 1, NOW(), NOW()),

    (20031, '牛排海鲜',        200, 1, NOW(), NOW()),
    (20032, '沙拉轻食',        190, 1, NOW(), NOW()),
    (20033, '素食专区',        180, 1, NOW(), NOW()),
    (20034, '健康低脂',        170, 1, NOW(), NOW()),
    (20035, '儿童套餐',        160, 1, NOW(), NOW()),
    (20036, '商务套餐',        150, 1, NOW(), NOW()),
    (20037, '双人套餐',        140, 1, NOW(), NOW()),
    (20038, '多人聚餐',        130, 1, NOW(), NOW()),
    (20039, '早餐粥粉',        120, 1, NOW(), NOW()),
    (20040, '下午茶点',        110, 1, NOW(), NOW()),

    (20041, '夜宵小吃',        100, 1, NOW(), NOW()),
    (20042, '甜品蛋糕',         90, 1, NOW(), NOW()),
    (20043, '面包糕点',         80, 1, NOW(), NOW()),
    (20044, '冰淇淋雪糕',       70, 1, NOW(), NOW()),
    (20045, '水果拼盘',         60, 1, NOW(), NOW()),
    (20046, '奶茶果茶',         50, 1, NOW(), NOW()),
    (20047, '咖啡饮品',         40, 1, NOW(), NOW()),
    (20048, '鲜榨果汁',         30, 1, NOW(), NOW()),
    (20049, '气泡饮料',         20, 1, NOW(), NOW()),
    (20050, '无糖饮品',         10, 1, NOW(), NOW()),

    (20051, '零食小吃',          9, 1, NOW(), NOW()),
    (20052, '主食套餐',          8, 1, NOW(), NOW()),
    (20053, '特色锅物',          7, 1, NOW(), NOW()),
    (20054, '便当盒饭',          6, 1, NOW(), NOW()),
    (20055, '校园特供',          5, 1, NOW(), NOW());


USE food_order;

-- 如需重复导入时避免冲突，可手动执行：
-- DELETE FROM dish WHERE id BETWEEN 30001 AND 30110;

INSERT INTO dish
(id, name, description, image, price, category_id, status, deleted, created_at, updated_at)
VALUES
    (30001, '水煮牛肉', '经典麻辣水煮牛肉，花椒香浓，配米饭绝佳', NULL, 38.00, 20001, 1, 0, NOW(), NOW()),
    (30002, '麻婆豆腐', '鲜嫩豆腐配牛肉末，麻辣鲜香，下饭神器', NULL, 22.00, 20001, 1, 0, NOW(), NOW()),

    (30003, '白切鸡', '肉质嫩滑的白切鸡，蘸料清爽不腻', NULL, 35.00, 20002, 1, 0, NOW(), NOW()),
    (30004, '清蒸石斑鱼', '保留鱼肉原汁原味，适合清淡口味', NULL, 68.00, 20002, 1, 0, NOW(), NOW()),

    (30005, '剁椒鱼头', '鲜辣剁椒覆盖大块鱼头，味道浓郁', NULL, 42.00, 20003, 1, 0, NOW(), NOW()),
    (30006, '小炒黄牛肉', '干香小炒牛肉，配大量辣椒与蒜片', NULL, 36.00, 20003, 1, 0, NOW(), NOW()),

    (30007, '葱烧海参', '海参口感软糯，葱香浓郁，风味独特', NULL, 88.00, 20004, 1, 0, NOW(), NOW()),
    (30008, '九转大肠', '酸甜适口的大肠，色泽红亮，入口弹牙', NULL, 52.00, 20004, 1, 0, NOW(), NOW()),

    (30009, '西湖醋鱼', '酸甜开胃，鱼肉细嫩，杭州名菜', NULL, 39.00, 20005, 1, 0, NOW(), NOW()),
    (30010, '东坡肉', '肥而不腻，入口即化，配米饭毫无压力', NULL, 45.00, 20005, 1, 0, NOW(), NOW()),

    (30011, '松鼠桂鱼', '外酥里嫩，酸甜口味，颜值与味道兼具', NULL, 58.00, 20006, 1, 0, NOW(), NOW()),
    (30012, '清炖狮子头', '肉质细腻软糯，大块肉丸配清汤', NULL, 36.00, 20006, 1, 0, NOW(), NOW()),

    (30013, '佛跳墙', '多种高档食材熬制，汤汁浓郁，鲜味十足', NULL, 98.00, 20007, 1, 0, NOW(), NOW()),
    (30014, '荔枝肉', '酸甜荔枝口感的肉块，开胃下饭', NULL, 32.00, 20007, 1, 0, NOW(), NOW()),

    (30015, '臭鳜鱼', '外皮焦香，鱼肉细嫩，闻起来重口吃起来上头', NULL, 58.00, 20008, 1, 0, NOW(), NOW()),
    (30016, '毛豆腐烧肉', '毛豆腐配五花肉，口味香浓独特', NULL, 32.00, 20008, 1, 0, NOW(), NOW()),

    (30017, '锅包肉', '酸甜酥脆的锅包肉，外焦里嫩', NULL, 32.00, 20009, 1, 0, NOW(), NOW()),
    (30018, '小鸡炖蘑菇', '鸡肉与蘑菇慢炖，汤浓肉香', NULL, 36.00, 20009, 1, 0, NOW(), NOW()),

    (30019, '兰州拉面', '清汤牛肉拉面，面条筋道有嚼劲', NULL, 18.00, 20010, 1, 0, NOW(), NOW()),
    (30020, '羊肉泡馍', '羊肉汤底浓郁，馍块吸饱汤汁', NULL, 26.00, 20010, 1, 0, NOW(), NOW()),

    (30021, '青椒土豆丝', '爽脆土豆丝配青椒丝，清爽下饭', NULL, 16.00, 20011, 1, 0, NOW(), NOW()),
    (30022, '西红柿炒鸡蛋', '酸甜适口，国民家常菜代表', NULL, 18.00, 20011, 1, 0, NOW(), NOW()),

    (30023, '双拼快餐', '两荤一素搭配米饭，工作日省心之选', NULL, 22.00, 20012, 1, 0, NOW(), NOW()),
    (30024, '三拼快餐', '三荤一素组合，荤菜分量十足', NULL, 26.00, 20012, 1, 0, NOW(), NOW()),

    (30025, '黑椒牛肉盖饭', '黑椒牛肉浇头配米饭，经典口味', NULL, 24.00, 20013, 1, 0, NOW(), NOW()),
    (30026, '咖喱鸡肉盖饭', '浓郁咖喱鸡块浇在米饭之上', NULL, 22.00, 20013, 1, 0, NOW(), NOW()),

    (30027, '扬州炒饭', '虾仁、火腿、鸡蛋等配料丰富，米粒分明', NULL, 20.00, 20014, 1, 0, NOW(), NOW()),
    (30028, '鸡蛋炒面', '鸡蛋与蔬菜炒制面条，简单实惠', NULL, 18.00, 20014, 1, 0, NOW(), NOW()),

    (30029, '过桥米线', '云南风味米线，汤底鲜美，配料多样', NULL, 23.00, 20015, 1, 0, NOW(), NOW()),
    (30030, '桂林米粉', '卤肉、酸豆角与花生粒配上米粉', NULL, 20.00, 20015, 1, 0, NOW(), NOW()),

    (30031, '厚牛肉汉堡', '厚切牛肉饼配生菜番茄与芝士', NULL, 24.00, 20016, 1, 0, NOW(), NOW()),
    (30032, '原味炸鸡块', '现炸鸡块，外皮酥脆，内里多汁', NULL, 22.00, 20016, 1, 0, NOW(), NOW()),

    (30033, '玛格丽特披萨', '番茄酱与芝士的经典组合，薄底披萨', NULL, 48.00, 20017, 1, 0, NOW(), NOW()),
    (30034, '培根蘑菇意面', '蘑菇与培根搭配奶油酱，口感顺滑', NULL, 32.00, 20017, 1, 0, NOW(), NOW()),

    (30035, '麻辣牛肉锅', '小锅麻辣汤底配牛肉与蔬菜', NULL, 49.00, 20018, 1, 0, NOW(), NOW()),
    (30036, '自选冒菜', '多种菜品可选，浸入麻辣汤底', NULL, 36.00, 20018, 1, 0, NOW(), NOW()),

    (30037, '孜然羊肉串', '孜然香浓的烤羊肉串，夜宵经典', NULL, 28.00, 20019, 1, 0, NOW(), NOW()),
    (30038, '烤五花肉拼盘', '烤五花肉配生菜与蒜片，包着吃更香', NULL, 39.00, 20019, 1, 0, NOW(), NOW()),

    (30039, '卤味拼盘', '包含鸡爪、豆腐干、海带结等多种卤味', NULL, 32.00, 20020, 1, 0, NOW(), NOW()),
    (30040, '酱牛肉切片', '酱香浓郁的牛腱肉，切片装盘', NULL, 36.00, 20020, 1, 0, NOW(), NOW()),

    (30041, '排骨莲藕汤', '排骨与莲藕慢炖，汤清味浓', NULL, 26.00, 20021, 1, 0, NOW(), NOW()),
    (30042, '老火靓汤', '多种食材久熬成汤，适合进补', NULL, 29.00, 20021, 1, 0, NOW(), NOW()),

    (30043, '肥肠砂锅', '肥肠搭配辣椒和酸菜，口味浓烈', NULL, 32.00, 20022, 1, 0, NOW(), NOW()),
    (30044, '腊味煲仔饭', '腊肠腊肉焖煮在米饭中，锅巴香脆', NULL, 28.00, 20022, 1, 0, NOW(), NOW()),

    (30045, '钵钵鸡', '冷锅串串，配以红油汤底，麻辣鲜香', NULL, 26.00, 20023, 1, 0, NOW(), NOW()),
    (30046, '龙抄手', '鲜香抄手配红油与花椒粉', NULL, 20.00, 20023, 1, 0, NOW(), NOW()),

    (30047, '炸臭豆腐', '外焦里嫩的臭豆腐，配蒜汁辣椒', NULL, 15.00, 20024, 1, 0, NOW(), NOW()),
    (30048, '糖油粑粑', '甜糯糯的糯米小点，裹上糖浆', NULL, 14.00, 20024, 1, 0, NOW(), NOW()),

    (30049, '肉夹馍', '腊汁肉夹入脆皮白吉馍，肉香四溢', NULL, 16.00, 20025, 1, 0, NOW(), NOW()),
    (30050, '凉皮组合套餐', '凉皮配卤蛋与豆奶，清凉解腻', NULL, 18.00, 20025, 1, 0, NOW(), NOW()),

    (30051, '炸酱面', '黄瓜丝、豆芽配上香浓炸酱', NULL, 22.00, 20026, 1, 0, NOW(), NOW()),
    (30052, '驴打滚', '豆面裹着糯米卷，甜而不腻', NULL, 16.00, 20026, 1, 0, NOW(), NOW()),

    (30053, '寿司拼盘', '多款寿司组合，含三文鱼和虾', NULL, 52.00, 20027, 1, 0, NOW(), NOW()),
    (30054, '石锅拌饭', '拌饭配多种蔬菜与煎蛋，石锅嗞嗞作响', NULL, 32.00, 20027, 1, 0, NOW(), NOW()),

    (30055, '泰式炒河粉', '酸甜微辣，配豆芽与花生碎', NULL, 28.00, 20028, 1, 0, NOW(), NOW()),
    (30056, '冬阴功海鲜汤', '酸辣香浓的海鲜汤，汤底层次丰富', NULL, 36.00, 20028, 1, 0, NOW(), NOW()),

    (30057, '咖喱鸡配馕', '印度香料咖喱鸡块搭配烤馕', NULL, 32.00, 20029, 1, 0, NOW(), NOW()),
    (30058, '坦都里烤鸡', '香料腌制后高温烤制，外焦里嫩', NULL, 39.00, 20029, 1, 0, NOW(), NOW()),

    (30059, '香煎鸡排套餐', '鸡排搭配土豆泥与沙拉', NULL, 42.00, 20030, 1, 0, NOW(), NOW()),
    (30060, '培根煎蛋三明治', '煎蛋和培根夹在吐司中，适合作为轻餐', NULL, 24.00, 20030, 1, 0, NOW(), NOW()),

    (30061, '西冷牛排', '西冷牛排煎制至五分熟，配黑椒汁', NULL, 68.00, 20031, 1, 0, NOW(), NOW()),
    (30062, '香煎三文鱼', '外皮微焦，鱼肉嫩滑多汁', NULL, 62.00, 20031, 1, 0, NOW(), NOW()),

    (30063, '凯撒鸡肉沙拉', '罗马生菜配烤鸡胸与帕玛森芝士', NULL, 26.00, 20032, 1, 0, NOW(), NOW()),
    (30064, '金枪鱼蔬菜沙拉', '金枪鱼配生菜、小番茄与玉米粒', NULL, 24.00, 20032, 1, 0, NOW(), NOW()),

    (30065, '香菇豆腐煲', '豆腐与香菇炖煮，汤汁浓郁而清淡', NULL, 22.00, 20033, 1, 0, NOW(), NOW()),
    (30066, '什锦蔬菜焖饭', '胡萝卜、玉米、豌豆与米饭一同焖煮', NULL, 21.00, 20033, 1, 0, NOW(), NOW()),

    (30067, '清蒸鸡胸配蔬菜', '低脂鸡胸肉配西兰花与胡萝卜', NULL, 28.00, 20034, 1, 0, NOW(), NOW()),
    (30068, '三文鱼藜麦碗', '藜麦、三文鱼与生菜组合的轻食碗', NULL, 36.00, 20034, 1, 0, NOW(), NOW()),

    (30069, '儿童炸鸡薯条', '减辣版炸鸡块配薯条与番茄酱', NULL, 26.00, 20035, 1, 0, NOW(), NOW()),
    (30070, '儿童水果意面', '奶油意面配上切块水果，口味温和', NULL, 24.00, 20035, 1, 0, NOW(), NOW()),

    (30071, '牛排午餐盒', '适合上班族的牛排便当搭配蔬菜沙拉', NULL, 58.00, 20036, 1, 0, NOW(), NOW()),
    (30072, '中式商务简餐', '两荤一素配米饭和例汤的商务套餐', NULL, 42.00, 20036, 1, 0, NOW(), NOW()),

    (30073, '双人火锅套餐', '含牛羊肉、蔬菜拼盘与蘸料，适合两人分享', NULL, 86.00, 20037, 1, 0, NOW(), NOW()),
    (30074, '双人西餐拼盘', '包括牛排、香肠和薯条的双人拼盘', NULL, 98.00, 20037, 1, 0, NOW(), NOW()),

    (30075, '四人家宴套餐', '多道家常菜组合，适合家庭聚餐', NULL, 168.00, 20038, 1, 0, NOW(), NOW()),
    (30076, '六人聚会套餐', '包含热菜与凉菜的多人聚会组合餐', NULL, 238.00, 20038, 1, 0, NOW(), NOW()),

    (30077, '皮蛋瘦肉粥', '米粥熬煮细腻，配上皮蛋和瘦肉', NULL, 18.00, 20039, 1, 0, NOW(), NOW()),
    (30078, '肉松鸡蛋肠粉', '肠粉皮软嫩，夹入鸡蛋与肉松', NULL, 19.00, 20039, 1, 0, NOW(), NOW()),

    (30079, '迷你三明治拼盘', '多种小三明治组成的下午茶小食', NULL, 26.00, 20040, 1, 0, NOW(), NOW()),
    (30080, '曲奇饼干礼盒', '多口味曲奇混合装，适合分享', NULL, 29.00, 20040, 1, 0, NOW(), NOW()),

    (30081, '小份串串香', '适合夜宵的一人份串串香组合', NULL, 24.00, 20041, 1, 0, NOW(), NOW()),
    (30082, '酸辣粉夜宵碗', '粉条爽滑，酸辣开胃，一碗解决夜宵', NULL, 18.00, 20041, 1, 0, NOW(), NOW()),

    (30083, '提拉米苏切块', '咖啡与奶油交织的甜品，口感绵密', NULL, 22.00, 20042, 1, 0, NOW(), NOW()),
    (30084, '芒果千层蛋糕', '多层薄饼与芒果奶油叠加的甜品', NULL, 26.00, 20042, 1, 0, NOW(), NOW()),

    (30085, '奶油菠萝包', '外层酥脆，内里松软，带有奶油香气', NULL, 10.00, 20043, 1, 0, NOW(), NOW()),
    (30086, '全麦吐司片', '高纤维全麦吐司，多片装', NULL, 12.00, 20043, 1, 0, NOW(), NOW()),

    (30087, '双球冰淇淋杯', '两球自选口味的冰淇淋，搭配脆筒碎', NULL, 18.00, 20044, 1, 0, NOW(), NOW()),
    (30088, '抹茶雪糕棒', '抹茶风味的雪糕，甜度适中', NULL, 12.00, 20044, 1, 0, NOW(), NOW()),

    (30089, '热带水果拼盘', '菠萝、芒果、火龙果等多种热带水果组合', NULL, 26.00, 20045, 1, 0, NOW(), NOW()),
    (30090, '时令水果杯', '当季水果切块装杯，方便即食', NULL, 18.00, 20045, 1, 0, NOW(), NOW()),

    (30091, '黑糖珍珠奶茶', '黑糖珍珠搭配浓郁奶茶，口感香甜', NULL, 15.00, 20046, 1, 0, NOW(), NOW()),
    (30092, '百香果果茶', '百香果果汁搭配茶底和果粒', NULL, 14.00, 20046, 1, 0, NOW(), NOW()),

    (30093, '拿铁咖啡', '浓缩咖啡与牛奶的顺滑搭配', NULL, 18.00, 20047, 1, 0, NOW(), NOW()),
    (30094, '冰美式咖啡', '无奶黑咖啡加冰块，清爽提神', NULL, 16.00, 20047, 1, 0, NOW(), NOW()),

    (30095, '鲜榨橙汁', '现榨橙子，维C丰富，酸甜适口', NULL, 16.00, 20048, 1, 0, NOW(), NOW()),
    (30096, '西瓜汁大杯', '冰镇西瓜汁，大杯解暑', NULL, 14.00, 20048, 1, 0, NOW(), NOW()),

    (30097, '柠檬苏打水', '柠檬片配苏打气泡水，低糖清爽', NULL, 12.00, 20049, 1, 0, NOW(), NOW()),
    (30098, '百香果气泡饮', '百香果风味的气泡饮料，酸甜解腻', NULL, 13.00, 20049, 1, 0, NOW(), NOW()),

    (30099, '无糖乌龙茶', '清香乌龙茶，不额外加糖', NULL, 10.00, 20050, 1, 0, NOW(), NOW()),
    (30100, '无糖豆浆', '现磨黄豆制成，不额外加糖', NULL, 9.00, 20050, 1, 0, NOW(), NOW()),

    (30101, '坚果海苔零食包', '混合坚果与海苔片的健康零食组合', NULL, 15.00, 20051, 1, 0, NOW(), NOW()),
    (30102, '辣味鸭脖小包', '小包装辣味鸭脖，适合追剧', NULL, 18.00, 20051, 1, 0, NOW(), NOW()),

    (30103, '红烧肉套餐饭', '红烧肉配两道时蔬与米饭', NULL, 26.00, 20052, 1, 0, NOW(), NOW()),
    (30104, '香煎鸡腿套餐', '鸡腿排配米饭和简单配菜', NULL, 24.00, 20052, 1, 0, NOW(), NOW()),

    (30105, '番茄牛腩锅', '番茄汤底搭配牛腩与土豆片', NULL, 42.00, 20053, 1, 0, NOW(), NOW()),
    (30106, '酸菜鱼锅', '鱼片与酸菜在暖锅中慢煮，酸爽开胃', NULL, 46.00, 20053, 1, 0, NOW(), NOW()),

    (30107, '照烧鸡腿便当', '照烧鸡腿配米饭、溏心蛋与配菜', NULL, 28.00, 20054, 1, 0, NOW(), NOW()),
    (30108, '卤肉饭便当', '卤肉盖浇在米饭上，附赠小菜', NULL, 26.00, 20054, 1, 0, NOW(), NOW()),

    (30109, '学生经济套餐A', '一荤一素配米饭，价格亲民', NULL, 16.00, 20055, 1, 0, NOW(), NOW()),
    (30110, '学生经济套餐B', '两荤一素组合，适合学生党', NULL, 18.00, 20055, 1, 0, NOW(), NOW());

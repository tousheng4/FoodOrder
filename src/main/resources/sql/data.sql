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




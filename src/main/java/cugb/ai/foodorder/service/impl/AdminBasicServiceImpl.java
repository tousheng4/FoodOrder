package cugb.ai.foodorder.service.impl;

import cugb.ai.foodorder.mapper.OrderInfoMapper;
import cugb.ai.foodorder.mapper.UserMapper;
import cugb.ai.foodorder.service.AdminBasicService;
import cugb.ai.foodorder.vo.ConsoleDetailVO;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class AdminBasicServiceImpl implements AdminBasicService {

    private final UserMapper userMapper;
    private final OrderInfoMapper orderInfoMapper;

    public AdminBasicServiceImpl(UserMapper userMapper, OrderInfoMapper orderInfoMapper) {
        this.userMapper = userMapper;
        this.orderInfoMapper = orderInfoMapper;
    }

    public ConsoleDetailVO getConsoleDetail() {
        ConsoleDetailVO vo = new ConsoleDetailVO();
        vo.setTotalUserCount(userMapper.countAll());
        vo.setTodayOrderCount(orderInfoMapper.countAdmin(null, null, null, LocalDate.now().atStartOfDay(), LocalDateTime.now()));
        vo.setTotalSales(orderInfoMapper.countTotalSales());
        vo.setToDoCount(orderInfoMapper.countAdmin(null, null, 0, null, null)
                + orderInfoMapper.countAdmin(null, null, 1, null, null)
                + orderInfoMapper.countAdmin(null, null, 2, null, null));
        return vo;
    }
}

package com.example.orderservice.service;

import com.example.orderservice.dto.OrderDto;
import com.example.orderservice.jpa.OrderEntity;
import com.example.orderservice.jpa.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

import java.util.ArrayList;
import java.util.List;

import static com.example.orderservice.global.fixture.TestInfoFixture.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderServiceImpl orderService;

    private OrderDto orderDto;
    private OrderEntity orderEntity;

    @BeforeEach
    void setUp() {
        orderDto = new OrderDto();
        orderDto.setOrderId(ORDER_ID);
        orderDto.setQty(QTY);
        orderDto.setUnitPrice(UNIT_PRICE);

        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        orderEntity = mapper.map(orderDto, OrderEntity.class);
    }

    @Test
    @DisplayName("주문정보 생성 성공")
    void createOrder() {
        // given
        given(orderRepository.save(any())).willReturn(orderEntity);

        // when
        final OrderDto result = orderService.createOrder(orderDto);

        // then
        verify(orderRepository).save(any());
        assertThat(result.getOrderId()).isNotEmpty();
        assertThat(result.getTotalPrice()).isEqualTo(result.getUnitPrice() * result.getQty());
    }

    @Test
    @DisplayName("주문정보 조회 성공")
    void getOrderByOrderId() {
        // given
        given(orderRepository.findByOrderId(anyString())).willReturn(orderEntity);

        // when
        final OrderDto result = orderService.getOrderByOrderId(ORDER_ID);

        // then
        verify(orderRepository).findByOrderId(ORDER_ID);
        assertThat(result.getQty()).isEqualTo(QTY);
        assertThat(result.getUnitPrice()).isEqualTo(UNIT_PRICE);
    }

    @Test
    @DisplayName("모든 주문정보 조회 성공")
    void getAllOrdersByUserId() {

        // given
        List<OrderEntity> orderEntities = new ArrayList<>();
        orderEntities.add(orderEntity);
        given(orderRepository.findByUserId(anyString())).willReturn(orderEntities);

        // when
        final List<OrderEntity> results = (List<OrderEntity>) orderService.getAllOrdersByUserId(USER_ID);

        // then
        verify(orderRepository).findByUserId(USER_ID);
        assertThat(results).contains(orderEntity);
    }
}
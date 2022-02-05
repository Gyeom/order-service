package com.example.orderservice.controller;

import com.example.orderservice.global.template.ControllerTestTemplate;
import com.example.orderservice.vo.RequestOrder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import static com.example.orderservice.global.fixture.TestInfoFixture.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class OrderControllerTest extends ControllerTestTemplate {

    @Test
    @DisplayName("사용자는 상품 주문에 성공한다.")
    void createOrder() throws Exception {

        // given
        final RequestOrder requestOrder = RequestOrder.builder()
                .productId(PRODUCT_ID)
                .qty(QTY)
                .unitPrice(UNIT_PRICE)
                .build();

        // when
        final ResultActions actions = mockMvc.perform(post("/{userId}/orders", USER_ID)
                        .content(objectMapper.writeValueAsString(requestOrder))
                        .contentType(MediaType.APPLICATION_JSON))
                        .andDo(print());

        // then
        actions.andExpect(status().isCreated())
                .andDo(print());
    }

    @Test
    @DisplayName("사용자는 주문정보 조회에 성공한다.")
    void getOrder() throws Exception{

        // given, when
        final ResultActions actions = mockMvc.perform(get("/{userId}/orders", USER_ID)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print());

        // then
        actions.andExpect(status().isOk())
                .andDo(print());
    }
}
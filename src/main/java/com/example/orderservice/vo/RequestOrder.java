package com.example.orderservice.vo;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RequestOrder {
    private String productId;
    private Integer qty;
    private Integer unitPrice;
}

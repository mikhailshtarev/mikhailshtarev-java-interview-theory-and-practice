package com.javainterview.shtarev.streamapi.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Order {
    private String orderId;
    private String customerName;
    private List<Product> products = new ArrayList<>();
}

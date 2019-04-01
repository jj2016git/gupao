package com.example.pattern.proxy;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    private String traceId;
    private String orderName;
    private Date createTime;
    private Integer price;
}

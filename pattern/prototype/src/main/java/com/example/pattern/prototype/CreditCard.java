package com.example.pattern.prototype;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreditCard implements Serializable {
    private String cardNo;
    private String name;
}

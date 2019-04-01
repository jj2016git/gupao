package com.example.adapter.old;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RespMsg {
    private Integer respCode;
    private String respMsg;
}

package com.example.observer.gperadvice;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Question {
    private String username;
    private String content;
    private String source;
}

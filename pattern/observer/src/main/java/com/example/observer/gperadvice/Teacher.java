package com.example.observer.gperadvice;

import com.google.common.eventbus.Subscribe;

/**
 * subscriber for questions
 */
public class Teacher {
    private String name;

    public Teacher(String name) {
        this.name = name;
    }

    @Subscribe
    public void subscribe(Question question) {
        System.out.println("===============================");
        System.out.println(this.name + "老师，你好！\n" +
                "您收到了一个来自“" + question.getSource() + "”的提问，希望您解答，问题内容如下：\n" +
                question.getContent() + "\n" +
                "提问者：" + question.getUsername());
    }
}

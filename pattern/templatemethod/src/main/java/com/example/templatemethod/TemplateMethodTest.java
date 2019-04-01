package com.example.templatemethod;

import java.util.List;

public class TemplateMethodTest {
    private static final MyJdbcTemplate myJdbcTemplate = new MyJdbcTemplate(null);

    public static void main(String[] args) {
        List<User> users = new UserDao(myJdbcTemplate).queryUsers();
        System.out.println("execute success");
    }
}

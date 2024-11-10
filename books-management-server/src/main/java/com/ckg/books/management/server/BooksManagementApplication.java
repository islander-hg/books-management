package com.ckg.books.management.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * 服务启动入口
 *
 * @author chenkaigui
 * @date 2024/11/7
 */
@SpringBootApplication(exclude = {
        DataSourceAutoConfiguration.class}, scanBasePackages = "com.ckg.books.management")
public class BooksManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(BooksManagementApplication.class, args);
        System.out.println("(♥◠‿◠)ﾉﾞ  图书管理服务启动成功   ლ(´ڡ`ლ)ﾞ");
    }

}

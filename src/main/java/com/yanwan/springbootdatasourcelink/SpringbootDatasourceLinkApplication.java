package com.yanwan.springbootdatasourcelink;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

@SpringBootApplication
public class SpringbootDatasourceLinkApplication implements CommandLineRunner {



    @Autowired
    private DataSource dataSource;


    public static void main(String[] args) {
        SpringApplication.run(SpringbootDatasourceLinkApplication.class, args);

    }
    @Override
    public void run(String... args) throws Exception {

        testMySQLConnection();
    }


    private void testMySQLConnection() throws SQLException {
        try (Connection conn = dataSource.getConnection();
             Statement stmt = conn.createStatement()) {
            System.out.println("✅ 数据库连接成功 | 连接信息: " + conn.getMetaData().getURL());
        } catch (Exception e) {
            System.err.println("❌ 数据库连接失败: " + e.getMessage());
        }
    }

}

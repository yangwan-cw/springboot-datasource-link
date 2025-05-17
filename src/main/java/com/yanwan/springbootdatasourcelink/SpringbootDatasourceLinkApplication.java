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
    // 注入数据库操作模板
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private MongoTemplate mongoTemplate;


    @Autowired
    private DataSource dataSource;


    public static void main(String[] args) {
        SpringApplication.run(SpringbootDatasourceLinkApplication.class, args);

    }
    @Override
    public void run(String... args) throws Exception {
        testRedisConnection();
        testMongoDBConnection();
        testMySQLConnection();
    }

    private void testRedisConnection() {
        try {
            redisTemplate.opsForValue().set("connection_test", "success");
            String value = redisTemplate.opsForValue().get("connection_test");
            System.out.println("✅ Redis 连接成功 | 测试值: " + value);
        } catch (Exception e) {
            System.err.println("❌ Redis 连接失败: " + e.getMessage());
        }
    }

    private void testMongoDBConnection() {
        try {
            // 执行 MongoDB 的 ping 命令
            mongoTemplate.getDb().runCommand(new org.bson.Document("ping", 1));
            System.out.println("✅ MongoDB 连接成功");
        } catch (Exception e) {
            System.err.println("❌ MongoDB 连接失败: " + e.getMessage());
        }
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

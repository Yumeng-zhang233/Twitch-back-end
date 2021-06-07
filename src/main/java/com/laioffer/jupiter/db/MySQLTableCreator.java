package com.laioffer.jupiter.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class MySQLTableCreator {
    public static void main(String[] args){
        try{
            // Step 1 Connect to MySQL.
            System.out.println("Connecting to " + MySQLDBUtil.getMySQLAddress());
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();

            Connection conn = DriverManager.getConnection(MySQLDBUtil.getMySQLAddress());

            if(conn == null){
                //与数据库的connection
                System.out.println("Driver manager get connection failed");
                return;
            }
            // Step 2 Drop tables in case they exist.
            Statement statement = conn.createStatement();
            String sql = "DROP TABLE IF EXISTS favorite_records";//关键词大写 用sql来解释这句话含义
            statement.executeUpdate(sql);//JDPC 往database传sql这句话

            sql = "DROP TABLE IF EXISTS items";
            statement.executeUpdate(sql);

            sql = "DROP TABLE IF EXISTS users";
            statement.executeUpdate(sql);

            // Step 3 Create new tables.
            sql = "CREATE TABLE items("
                    +"id VARCHAR (255) NOT NULL,"//不能为空
                    +"title VARCHAR (255) ,"
                    + "url VARCHAR(255),"
                    + "thumbnail_url VARCHAR(255),"
                    + "broadcaster_name VARCHAR(255),"
                    + "game_id VARCHAR(255),"
                    + "type VARCHAR(255) NOT NULL,"
                    + "PRIMARY KEY (id)"
                    + ")";

            statement.executeUpdate(sql);//执行一句没有返回值的sql


            sql = "CREATE TABLE users ("
                    + "id VARCHAR(255) NOT NULL,"
                    + "password VARCHAR(255) NOT NULL,"
                    + "first_name VARCHAR(255),"
                    + "last_name VARCHAR(255),"
                    + "PRIMARY KEY (id)"
                    + ")";
            statement.executeUpdate(sql);

            //Favorite records --- foreign keys
            sql = "CREATE TABLE favorite_records ("
                    + "user_id VARCHAR(255) NOT NULL,"
                    + "item_id VARCHAR(255) NOT NULL,"
                    + "last_favor_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,"//插入数据时。自动插入favor_time
                    + "PRIMARY KEY (user_id, item_id),"
                    + "FOREIGN KEY (user_id) REFERENCES users(id),"
                    + "FOREIGN KEY (item_id) REFERENCES items(id)"
                    + ")";
            statement.executeUpdate(sql);

            // Step 4: insert fake user 1111/3229c1097c00d497a0fd282d586be050.
            sql = "INSERT INTO users VALUES" +
                    "('1111', '3229c1097c00d497a0fd282d586be050', 'John', 'Smith')";
            //用户名，密码(FD5的哈希值）
            statement.executeUpdate(sql);

            conn.close();
            System.out.println("Import done successfully");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


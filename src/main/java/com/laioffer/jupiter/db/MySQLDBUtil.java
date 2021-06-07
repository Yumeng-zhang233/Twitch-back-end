package com.laioffer.jupiter.db;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class MySQLDBUtil {
    //Endpoint
    private static final String INSTANCE =
            "laiproject-instance.candizjqbrjk.us-east-2.rds.amazonaws.com";
    private static final String PORT_NUM = "3306";
    private static final String DB_NAME = "jupiter";

    public static String getMySQLAddress () throws IOException{
        Properties prop = new Properties();
        String propFileName = "config.properties";
        //通过getClassLoader 找到resource文件的路径
        InputStream inputStream = MySQLDBUtil.class.getClassLoader().getResourceAsStream(
                propFileName);
        prop.load(inputStream);
        String username = prop.getProperty("user");
        String password = prop.getProperty("password");


        //                                  INSTANCE:PORT/PORT_NUM      if disconnect 自动重连
         return String.format("jdbc:mysql://%s:%s/%s?user=%s&password=%s&autoReconnect=true&serverTimezone=UTC&createDatabaseIfNotExist=true",
                 INSTANCE, PORT_NUM, DB_NAME, username, password);
    }
}

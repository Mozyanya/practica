package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DB {

    public static Connection connectDb() {

        try {
            Class.forName("org.postgresql.Driver");
            Connection connect = DriverManager.getConnection("jdbc:postgresql://localhost:5432/practika", "postgres", "Ladygaga307");

            return connect;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }}

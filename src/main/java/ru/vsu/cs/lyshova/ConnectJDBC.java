package ru.vsu.cs.lyshova;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectJDBC {
    protected String dbHost = "localhost";
    protected String dbPort = "3306";
    protected String dbUsername = "root";
    protected String dbPassword = "0104Rctybz2004";
    protected String dbName = "mydb";
    private static ConnectJDBC instance;

    public Connection getDbConnection() throws ClassNotFoundException, SQLException {
        String connection = "jdbc:mysql://" + dbHost + ":" + dbPort + "/" + dbName;
        Class.forName("com.mysql.jdbc.Driver");
        return DriverManager.getConnection(connection, dbUsername, dbPassword);
    }

    public static synchronized ConnectJDBC getInstance() {
        if (instance == null) instance = new ConnectJDBC();
        return instance;
    }
}

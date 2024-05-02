package ru.saiev.technesistask.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DB {

    private final String HOST = "localhost";
    private final String PORT = "5432";
    private final String DB_NAME = "TechnesisDB";
    private final String USER = "user";
    private final String PASS = "user";
    private Connection dbConn = null;

    private Connection getDbConnection() throws ClassNotFoundException, SQLException {
        String connStr = "jdbc:postgresql://" + HOST + ":" + PORT + "/" + DB_NAME;
        Class.forName("org.postgresql.Driver");

        dbConn = DriverManager.getConnection(connStr, USER, PASS);
        return dbConn;
    }

    private Connection getCon() throws SQLException {
        String url = "jdbc:postgresql://" + HOST + "/" + DB_NAME;
        Properties props = new Properties();
        props.setProperty("user", USER);
        props.setProperty("password", PASS);
        props.setProperty("ssl", "true");
        return DriverManager.getConnection(url, props);
    }

    public void isConnected() throws SQLException, ClassNotFoundException {
        dbConn = getDbConnection();
        System.out.println(dbConn.isValid(1000));
    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        DB db = new DB();
        db.getCon();
        db.isConnected();
    }
}

package com.dhurd.club.login.sql;

import com.dhurd.club.login.coreutils.CoreMath;
import com.dhurd.club.login.coreutils.CorePaths;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SQLManager {
    private static final Logger logger = Logger.getLogger(SQLManager.class.getName());
    private static SQLManager instance;
    private String dbPath = CorePaths.DATABASE_PATH;
    
    private Connection connection = null;
    private Statement statement = null;
    
    /**
     * @return the default instance of SQLManager
     */
    public static SQLManager getDefault() {
        if (instance == null) {
            instance = new SQLManager();
        }
        return instance;
    }
    
    // Hidden constructor
    private SQLManager() {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException ex) {
            logger.log(Level.SEVERE, "Failed to load JDBC driver", ex);
        }
    }
    
    /**
     * Close the current statement and connection.
     */
    public void closeConnection() {
        try {
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException ex) {
            logger.log(Level.WARNING, "Failed to close the statement/connection", ex);
        }
    }
    
    /**
     * Deletes any existing database file and then creates a new database
     * with the ClubLogin structure.
     */
    public void create() {
        File db = new File(dbPath);
        if (db.exists()) {
            db.delete();
        }
        
        connection = null;
        statement = null;
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:" + dbPath);
            statement = connection.createStatement();
            
            statement.executeUpdate("CREATE TABLE employees ( "
                    + "id integer PRIMARY KEY AUTOINCREMENT, "
                    + "firstname VARCHAR(100), "
                    + "lastname VARCHAR(100), "
                    + "stagename VARCHAR(100) UNIQUE, "
                    + "address VARCHAR(100), "
                    + "dob DATE, "
                    + "ssn VARCHAR(100) UNIQUE, "
                    + "active INTEGER DEFAULT 1, "
                    + "loggedin INTEGER DEFAULT 0)");
            
            statement.executeUpdate("CREATE TABLE managers ( "
                    + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + "username VARCHAR(100) UNIQUE, "
                    + "password VARCHAR(100) )");
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, "Failed to create a new database", ex);
        } finally {
            closeConnection();
        }
    }
    
    /**
     * Add a manager to the database.
     * @param username manager username
     * @param password manager password
     * 
     * TODO: Remove concept of manager
     */
    public void addManager(String username, String password) {
        connection = null;
        statement = null;
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:" + dbPath);
            statement = connection.createStatement();
            
            statement.executeUpdate("INSERT INTO managers (username, password) "
                    + "VALUES ('" + username + "', "
                    + "'" + CoreMath.getMD5(password) + "')");
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, "Failed to connect to the database", ex);
        } finally {
            closeConnection();
        }
    }
    
    /**
     * Add an employee to the database.
     * @param first employee first name
     * @param last employee last name
     * @param address employee address
     * @param ssn employee social security number
     * 
     * TODO: Store ssn securely
     */
    public void addEmployee(String first, String last, String stage, String address, String ssn) {
        connection = null;
        statement = null;
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:" + dbPath);
            statement = connection.createStatement();
            
            statement.executeUpdate("INSERT INTO employees (firstname, lastname, stagename, address, ssn) "
                    + "VALUES ('" + first + "', "
                    + "'" + last + "', "
                    + "'" + stage + "', "
                    + "'" + address + "', "
                    + "'" + ssn + "')");
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, "Failed to connect to the database", ex);
        } finally {
            closeConnection();
        }
    }
    
    /**
     * Run a specific user-created query.
     * @param query query to run
     * @return ResultSet from the query
     */
    public ResultSet runQuery(String query) {
        connection = null;
        statement = null;
        ResultSet rs = null;
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:" + dbPath);
            statement = connection.createStatement();
            
            rs = statement.executeQuery(query);
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, "Failed to connect to the database", ex);
        }
        return rs;
    }
}

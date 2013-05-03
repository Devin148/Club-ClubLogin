package com.dhurd.club.login.sql;

import com.dhurd.club.login.coreutils.CorePaths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Employee Login/Logout database manager.
 * 
 * TODO: - Abstract common functions/vars to true SQLManager
 *       - Replace SQLManager with something like "EntryManager"
 *       - Prepared statements
 */
public class LoginManager {
    private static final Logger logger = Logger.getLogger(LoginManager.class.getName());
    private static LoginManager instance;
    private String dbPath = CorePaths.DATABASE_PATH;
    
    private Connection connection = null;
    private Statement statement = null;
    
    /**
     * @return the default instance of LoginManager
     */
    public static LoginManager getDefault() {
        if (instance == null) {
            instance = new LoginManager();
        }
        return instance;
    }
    
    // Hidden constructor
    private LoginManager() {
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
     * Is there an employee with the given SSN?
     * 
     * @param ssn employee SSN
     * @return true if the employee exists, false otherwise
     */
    public boolean employeeExists(String ssn) {
        connection = null;
        statement = null;
        ResultSet rs;
        int exists = 0;
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:" + dbPath);
            statement = connection.createStatement();
            rs = statement.executeQuery("SELECT COUNT(*) FROM employee WHERE active = 1 AND ssn = '" + ssn + "'");
            exists = rs.getInt(1);
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, "Failed to check if employee exists", ex);
        } finally {
            closeConnection();
        }
        return exists == 1 ? true : false;
    }
    
    /**
     * Is the employee with to the given SSN logged in?
     * 
     * @param ssn employee SSN
     * @return true if the employee is logged in, false if they are logged out or do not exist
     */
    public boolean employeeIsLoggedIn(String ssn) {
        connection = null;
        statement = null;
        ResultSet rs;
        int isLoggedIn = 0;
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:" + dbPath);
            statement = connection.createStatement();
            rs = statement.executeQuery("SELECT COUNT(*) FROM employee WHERE active = 1 AND ssn = '" + ssn + "' AND loggedin = 1");
            isLoggedIn = rs.getInt(1);
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, "Failed to check if employee is logged in", ex);
        } finally {
            closeConnection();
        }
        return isLoggedIn == 1 ? true : false;
    }
    
    /**
     * Login the employee with the given SSN.
     * 
     * @param ssn employee SSN
     * @return true if the employee is successfully logged in or already logged in, false otherwise
     */
    public boolean login(String ssn) {
        connection = null;
        statement = null;
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:" + dbPath);
            statement = connection.createStatement();
            statement.executeUpdate("UPDATE employee SET loggedin = 1 WHERE active = 1 AND ssn ='" + ssn + "'");
            return true;
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, "Failed to get list of employees for top component", ex);
        } finally {
            closeConnection();
        }
        return false;
    }
    
}

package main.java.util;

import main.java.exceptions.ExceptionsHandlingConstants;
import main.java.exceptions.NoDBPropertiesException;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtils {

    private final String url;
    private final String userName;
    private final String passWord;
    private Connection connection;

    private static final Logger logger = Logger.getLogger(ConnectionUtils.class);

    public ConnectionUtils(String url, String userName, String passWord) {
        if (url == null || url.isEmpty() || userName == null || userName.isEmpty()
        || passWord == null || passWord.isEmpty()){
            throw new IllegalArgumentException(ExceptionsHandlingConstants.NOT_VALID);
        }
        this.url = url;
        this.userName = userName;
        this.passWord = passWord;
    }

    public Connection getConnection() throws SQLException, NoDBPropertiesException {
        try{
            return connection == null || connection.isClosed() ? createNew() : connection;
        } catch (SQLException e){
            logger.error(ExceptionsHandlingConstants.NO_CONNECTION);
            throw new RuntimeException(e.getLocalizedMessage());
        }
    }

    private Connection createNew() {
        logger.info("createNew() -> new connection to DB creating ->");

        try{
            connection = DriverManager.getConnection(url, userName, passWord);
            logger.info("createNew() -> new connection successfully created::");
            return connection;
        } catch (SQLException e) {
            logger.error(ExceptionsHandlingConstants.NO_CONNECTION);
            throw new RuntimeException(e.getLocalizedMessage());
        }
    }


}

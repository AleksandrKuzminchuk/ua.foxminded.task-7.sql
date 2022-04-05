package ua.foxminded.task7.sql.config;

import ua.foxminded.task7.sql.exceptions.ExceptionsHandlingConstants;
import org.apache.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertiesManager {

    private final static Logger logger = Logger.getLogger(PropertiesManager.class);

    private final Properties properties;

    public PropertiesManager(String propertiesPath){
        if (propertiesPath == null || propertiesPath.isEmpty()){
            throw new IllegalArgumentException(ExceptionsHandlingConstants.ARGUMENT_IS_NULL_OR_EMPTY);
        }
        properties = new Properties();
        try{
            properties.load(new FileInputStream(propertiesPath));
        }catch (IOException e){
            logger.error(e);
            throw new RuntimeException(e.getLocalizedMessage());
        }
    }

    public String getProperty(String propertyKey){
        return properties.getProperty(propertyKey);
    }
}

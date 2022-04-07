package ua.foxminded.task7.sql.config;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class PropertiesManagerTest {

    private PropertiesManager propertiesManager;

    private String PROPERTIES_FILE = "src/main/test/ua/foxminded/task7/sql/resources/dbTest.properties";
    private String URL = "db.url";
    private String USER = "db.user";
    private String PASSWORD = "db.password";

    @BeforeEach
    void beforeEach(){
        propertiesManager = new PropertiesManager(PROPERTIES_FILE);
    }

    @Test
    void shouldReturnStringUrlUserPasswordWithFileDbProperties() {
        String expectedUrl = "jdbc:postgresql://localhost:5432/postgres";
        String expectedUser = "postgres";
        String expectedPassword = "1234";

        String resultUrl = propertiesManager.getProperty(URL);
        String resultUser = propertiesManager.getProperty(USER);
        String resultPassword = propertiesManager.getProperty(PASSWORD);

        assertEquals(expectedUrl, resultUrl);
        assertEquals(expectedUser, resultUser);
        assertEquals(expectedPassword, resultPassword);
    }

    @Test
    void shouldIfNullInParametersPropertiesManagerNeedThrowExceptionNotNull(){
        assertThrows(IllegalArgumentException.class, () -> {new PropertiesManager(null).getProperty(URL);});
    }

    @Test
    void shouldIfEmptyInParametersPropertiesManagerNeedThrowExceptionEmpty(){
        assertThrows(IllegalArgumentException.class, () -> {new PropertiesManager("").getProperty(URL);});
    }

    @Test
    void shouldIfAddressToFileNotCorrectNeedThrowExceptionRuntime(){
        assertThrows(RuntimeException.class,
                () -> {new PropertiesManager("src/main/test/ua/foxminded/task7/resources/dbTest.properties");});
    }
}
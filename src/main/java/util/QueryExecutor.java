package main.java.util;


import main.java.exceptions.ExceptionsHandlingConstants;
import main.java.exceptions.NoDBPropertiesException;
import org.apache.log4j.Logger;

import java.io.*;
import java.sql.SQLException;
import java.sql.Statement;

import static java.lang.String.format;

public class QueryExecutor {

    private static final String UTF8 = "UTF-8";
    private static final String DROP_TABLES = "src/main/resources/sql/drop-tables.sql";
    private static final String CREATE_GROUPS = "src/main/resources/sql/create-table-groups.sql";
    private static final String CREATE_COURSES = "src/main/resources/sql/create-table-courses.sql";
    private static final String CREATE_STUDENTS = "src/main/resources/sql/create-table-students.sql";
    private static final String CREATE_STUDENTS_COURSES = "src/main/resources/sql/create-table-students_courses.sql";

    private static final Logger logger = Logger.getLogger(QueryExecutor.class);

    private final ConnectionUtils connectionUtils;

    public QueryExecutor(ConnectionUtils connectionUtils) {
        this.connectionUtils = connectionUtils;
    }

    public void createTables(){
        logger.info("Create tables: students, courses, groups, students_courses");
        executorQuery(readQuery(DROP_TABLES));
        executorQuery(readQuery(CREATE_STUDENTS));
        executorQuery(readQuery(CREATE_COURSES));
        executorQuery(readQuery(CREATE_GROUPS));
        executorQuery(readQuery(CREATE_STUDENTS_COURSES));
        logger.info("Created tables: students, courses, groups, students_courses");
    }

    private void executorQuery(String text){
        requiredNonNullAndIsEmpty(text);
        logger.info(format("executorQuery('%s')", text.length()));
        try(Statement statement = connectionUtils.getConnection().createStatement()
                ){
            statement.execute(text);
            logger.info("Created statement from the the class QueryExecutor");
        }catch (SQLException e){
            logger.error("Can't execute query. Cause: " + e.getLocalizedMessage());
            throw new NoDBPropertiesException(e.getLocalizedMessage());
        }

    }

    private String readQuery(String file){
        requiredNonNullAndIsEmpty(file);
        logger.info(format("read %s file...", file));
        try(FileInputStream fileInputStream = new FileInputStream(file)
                ){
            return computeQueryFileContent(fileInputStream, UTF8);
        }catch (IOException e){
            logger.error("Cannot read the query text file: %s", e);
            throw new NoDBPropertiesException(e.getLocalizedMessage());
        }
    }

    private String computeQueryFileContent(FileInputStream inputStream, String encoding){
        requiredNonNullAndIsEmpty(inputStream, encoding);
        logger.info("Begin read inputStreamFile and encoding");
        String line;
        StringBuilder builder = new StringBuilder();
        try(InputStreamReader streamReader = new InputStreamReader(inputStream, encoding);
            BufferedReader reader = new BufferedReader(streamReader)
                ){
            logger.info("Read and convert file in StringBuilder");
            while ((line = reader.readLine()) != null){
                builder.append(line);
                builder.append("\n");
            }
            logger.info("return string from FileInputStream");
            return builder.toString();
        } catch (IOException e) {
            logger.error("Can't read file", e);
            throw new NoDBPropertiesException(e.getLocalizedMessage());
        }
    }

    private void requiredNonNullAndIsEmpty(String str){
        if (str == null || str.isEmpty()){
            throw new IllegalArgumentException(ExceptionsHandlingConstants.ARGUMENT_IS_NULL_OR_EMPTY);
        }
    }

    private void requiredNonNullAndIsEmpty(FileInputStream inputStream, String str){
        if (str == null || str.isEmpty() || inputStream == null){
            throw new IllegalArgumentException(ExceptionsHandlingConstants.ARGUMENT_IS_NULL_OR_EMPTY);
        }
    }
}

package ua.expo.model.dao.impl;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.log4j.Logger;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

class ConnectionPoolHolder {

    private final static Logger LOGGER = Logger.getLogger(ConnectionPoolHolder.class);

    private static final String DB_PROPERTIES = "/DBinfo.properties";
    private static final String DRIVER = "database.driver";
    private static final String URL = "database.url";
    private static final String USER = "database.user";
    private static final String PASSWORD = "database.password";
    private static volatile DataSource dataSource;

    /**
     * Create only one instance of DataSource class,
     * which contains pool of connection to the DB.
     * Parameters of DB connection contains in properties file.
     * Constant DB_PROPERTIES point to this file.
     * @return pool of connections
     */
    static DataSource getDataSource() {
        if (dataSource == null) {
            synchronized (ConnectionPoolHolder.class) {
                if (dataSource == null) {
                    try (InputStream stream = ConnectionPoolHolder.class.getResourceAsStream(DB_PROPERTIES)) {
                        Properties properties = new Properties();
                        properties.load(stream);
                        Class.forName(properties.getProperty(DRIVER));
                        BasicDataSource ds = new BasicDataSource();
                        ds.setUrl(properties.getProperty(URL));
                        ds.setUsername(properties.getProperty(USER));
                        ds.setPassword(properties.getProperty(PASSWORD));
                        ds.setMinIdle(5);
                        ds.setMaxIdle(10);
                        ds.setMaxOpenPreparedStatements(100);
                        dataSource = ds;
                    } catch (IOException e) {
                        LOGGER.error("IOException while trying to get property file " + DB_PROPERTIES, e);
                        throw new RuntimeException(e);
                    } catch (ClassNotFoundException e) {
                        LOGGER.error("ClassNowFoundException while creating data source", e);
                        e.printStackTrace();
                    }
                }
            }
        }
        LOGGER.info("Created new instance of DataSource class");
        return dataSource;
    }
}

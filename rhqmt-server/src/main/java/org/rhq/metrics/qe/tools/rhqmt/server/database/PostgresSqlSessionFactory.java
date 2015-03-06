package org.rhq.metrics.qe.tools.rhqmt.server.database;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.log4j.Logger;
import org.rhq.metrics.qe.tools.rhqmt.server.DatabaseConfiguration;
/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 */
public class PostgresSqlSessionFactory {
    private static Logger _logger = Logger.getLogger(PostgresSqlSessionFactory.class);

    protected static SqlSessionFactory FACTORY;

    private static DatabaseConfiguration configuration;


    public static void initSqlSessionFactory(){
        try {
            if(configuration == null){
                throw new Exception("Database Configuration file not loaded...");
            }
            
            if(configuration.getCleandb()){
                initializeDatabase();
            }
            
            
            String resource = "database-configuration.xml";
            InputStream inputStream = Resources.getResourceAsStream(resource);
            FACTORY = new SqlSessionFactoryBuilder().build(inputStream, configuration.getProperties());
            _logger.info("PostgresSqlSessionFactory loadded...");
            _logger.info("Paramater Map: "+FACTORY.getConfiguration().getParameterMaps());
        }catch (Exception ex){
            throw new RuntimeException("Fatal Error.  Cause: " + ex, ex);
        }
    }

    public static SqlSessionFactory getSqlSessionFactory() {
        if(FACTORY == null){
            _logger.info("Loading from getSqlSessionFactory....");
            initSqlSessionFactory();
        }
        return FACTORY;
    }

    public static void initializeDatabase(){

        try {
            Class.forName(configuration.getDriverClass());
            Connection connection = DriverManager.getConnection(configuration.getUrl(), configuration.getUsername(), configuration.getPassword());
            ScriptRunner runner=new ScriptRunner(connection);
            InputStream inputStream = Resources.getResourceAsStream("database-schema.sql");
            InputStreamReader reader = new InputStreamReader(inputStream);
            runner.runScript(reader);
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public static DatabaseConfiguration getConfiguration() {
        return configuration;
    }

    public static void setConfiguration(DatabaseConfiguration configuration) {
        PostgresSqlSessionFactory.configuration = configuration;
    }


}

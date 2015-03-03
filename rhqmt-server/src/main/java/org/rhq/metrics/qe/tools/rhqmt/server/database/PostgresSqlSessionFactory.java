package org.rhq.metrics.qe.tools.rhqmt.server.database;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.log4j.Logger;
/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 */
public class PostgresSqlSessionFactory {
    private static Logger _logger = Logger.getLogger(PostgresSqlSessionFactory.class);

    protected static SqlSessionFactory FACTORY;

    public static void initSqlSessionFactory(){
        try {
            String resource = "database-configuration.xml";
            InputStream inputStream = Resources.getResourceAsStream(resource);
            FACTORY = new SqlSessionFactoryBuilder().build(inputStream);
            _logger.info("PostgresSqlSessionFactory loadded...");
            _logger.info("Paramater Map: "+FACTORY.getConfiguration().getParameterMaps());
        }catch (Exception ex){
            throw new RuntimeException("Fatal Error.  Cause: " + ex, ex);
        }
    }

    public static SqlSessionFactory getSqlSessionFactory() {
        if(FACTORY == null){
            _logger.info("Loading from getSqlSessionFactory....");
            initializeDatabase();
            initSqlSessionFactory();
        }
        return FACTORY;
    }
    
    public static void initializeDatabase(){
        
        try {
            Class.forName("org.postgresql.Driver");
            Connection connection = DriverManager.getConnection("jdbc:postgresql://rhqmt.bc.jonqe.lab.eng.bos.redhat.com:5432/rhqmtserverdb?autoReconnect=true", "rhqmtadmin", "rhqmtadmin");
            ScriptRunner runner=new ScriptRunner(connection);
            InputStreamReader reader = new InputStreamReader(new FileInputStream("/storage/Work/development/projects/rhq-metrics-data-pump/rhqmt-server/src/main/resources/database-schema.sql"));
            runner.runScript(reader);
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
       
    }


}

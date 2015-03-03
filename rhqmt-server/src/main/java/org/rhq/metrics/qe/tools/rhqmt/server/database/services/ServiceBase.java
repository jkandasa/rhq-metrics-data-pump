package org.rhq.metrics.qe.tools.rhqmt.server.database.services;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.rhq.metrics.qe.tools.rhqmt.server.database.PostgresSqlSessionFactory;
/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 */
public class ServiceBase {
    protected Logger _logger = Logger.getLogger(ServiceBase.class);

    protected SqlSession getSession(){
        SqlSession session = null;
        try{
            session = PostgresSqlSessionFactory.getSqlSessionFactory().openSession();
            session.getConnection().setAutoCommit(true);            
        }catch(Exception ex){
            _logger.error("Exception while creating connection, ", ex);
        }       
        return session;
    }

    protected void closeSession(SqlSession session){
        session.close();
    }

    protected <T> T getMapper(Class<T> class1){
        return getSession().getMapper(class1);
    }
}

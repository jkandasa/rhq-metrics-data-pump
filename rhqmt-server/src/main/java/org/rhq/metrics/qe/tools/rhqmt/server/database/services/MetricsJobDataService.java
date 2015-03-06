package org.rhq.metrics.qe.tools.rhqmt.server.database.services;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.rhq.metrics.qe.tools.rhqmt.server.database.entities.MetricsJobData;
import org.rhq.metrics.qe.tools.rhqmt.server.database.mappers.MetricsJobDataMapper;
/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 */
public class MetricsJobDataService extends ServiceBase{

    
    public List<MetricsJobData> getAll() {
        SqlSession session = this.getSession();
        try {
            return session.getMapper(MetricsJobDataMapper.class).getAll();
        } finally {
            this.closeSession(session);
        }
    }
    
    public List<MetricsJobData> get(MetricsJobData metricsJobData) {
        SqlSession session = this.getSession();
        try {
            return session.getMapper(MetricsJobDataMapper.class).get(metricsJobData);
        } finally {
            this.closeSession(session);
        }
    }
    
    public MetricsJobData getOne(MetricsJobData metricsJobData) {
        List<MetricsJobData> jobDetails = get(metricsJobData);
        if(jobDetails.size() > 0){
            return jobDetails.get(0);
        }else{
            return null;
        }
    }
    
    public void add(MetricsJobData metricsJobData) {
        SqlSession session = this.getSession();
        try {
            session.getMapper(MetricsJobDataMapper.class).add(metricsJobData);
        } finally {
            this.closeSession(session);
        }
        
    }
    
   public void delete(MetricsJobData metricsJobData) {
        SqlSession session = this.getSession();
        try {
            session.getMapper(MetricsJobDataMapper.class).delete(metricsJobData);
        } finally {
            this.closeSession(session);
        }        
    }
   
  public void update(MetricsJobData metricsJobData) {
       SqlSession session = this.getSession();
       try {
           session.getMapper(MetricsJobDataMapper.class).update(metricsJobData);
       } finally {
           this.closeSession(session);
       }        
   }

}

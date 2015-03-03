package org.rhq.metrics.qe.tools.rhqmt.server.database.services;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.rhq.metrics.qe.tools.rhqmt.server.database.entities.MetricsTemplate;
import org.rhq.metrics.qe.tools.rhqmt.server.database.mappers.MetricsTemplateMapper;
/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 */
public class MetricsTemplateService extends ServiceBase{

    
    public List<MetricsTemplate> getAll() {
        SqlSession session = this.getSession();
        try {
            return session.getMapper(MetricsTemplateMapper.class).getAll();
        } finally {
            this.closeSession(session);
        }
    }
    
    public List<MetricsTemplate> get(MetricsTemplate metricsTemplate) {
        SqlSession session = this.getSession();
        try {
            return session.getMapper(MetricsTemplateMapper.class).get(metricsTemplate);
        } finally {
            this.closeSession(session);
        }
    }
    
    public MetricsTemplate getOne(MetricsTemplate metricsTemplate) {
        List<MetricsTemplate> jobDetails = get(metricsTemplate);
        if(jobDetails.size() > 0){
            return jobDetails.get(0);
        }else{
            return null;
        }
    }
    
    public void add(MetricsTemplate metricsTemplate) {
        SqlSession session = this.getSession();
        try {
            session.getMapper(MetricsTemplateMapper.class).add(metricsTemplate);
        } finally {
            this.closeSession(session);
        }
        
    }
    
   public void delete(MetricsTemplate metricsTemplate) {
        SqlSession session = this.getSession();
        try {
            session.getMapper(MetricsTemplateMapper.class).delete(metricsTemplate);
        } finally {
            this.closeSession(session);
        }        
    }
   
   public void update(MetricsTemplate metricsTemplate) {
       SqlSession session = this.getSession();
       try {
           session.getMapper(MetricsTemplateMapper.class).update(metricsTemplate);
       } finally {
           this.closeSession(session);
       }        
   }

}

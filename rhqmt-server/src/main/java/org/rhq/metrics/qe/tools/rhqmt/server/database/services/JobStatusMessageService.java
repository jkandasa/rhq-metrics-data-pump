package org.rhq.metrics.qe.tools.rhqmt.server.database.services;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.rhq.metrics.qe.tools.rhqmt.server.database.entities.JobStatusMessage;
import org.rhq.metrics.qe.tools.rhqmt.server.database.mappers.JobStatusMessageMapper;
/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 */
public class JobStatusMessageService extends ServiceBase{

    
    public List<JobStatusMessage> getAll() {
        SqlSession session = this.getSession();
        try {
            return session.getMapper(JobStatusMessageMapper.class).getAll();
        } finally {
            this.closeSession(session);
        }
    }
    
    public List<JobStatusMessage> get(JobStatusMessage jobStatusMessage) {
        SqlSession session = this.getSession();
        try {
            return session.getMapper(JobStatusMessageMapper.class).get(jobStatusMessage);
        } finally {
            this.closeSession(session);
        }
    }
    
    public JobStatusMessage getOne(JobStatusMessage jobStatusMessage) {
        List<JobStatusMessage> jobDetails = get(jobStatusMessage);
        if(jobDetails.size() > 0){
            return jobDetails.get(0);
        }else{
            return null;
        }
    }
    
    public void add(JobStatusMessage jobStatusMessage) {
        SqlSession session = this.getSession();
        try {
            session.getMapper(JobStatusMessageMapper.class).add(jobStatusMessage);
        } finally {
            this.closeSession(session);
        }
        
    }
    
   public void delete(JobStatusMessage jobStatusMessage) {
        SqlSession session = this.getSession();
        try {
            session.getMapper(JobStatusMessageMapper.class).delete(jobStatusMessage);
        } finally {
            this.closeSession(session);
        }        
    }
}

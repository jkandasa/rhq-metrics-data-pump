package org.rhq.metrics.qe.tools.rhqmt.server.database.services;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.quartz.SchedulerException;
import org.rhq.metrics.qe.tools.rhqmt.server.database.entities.JobDetail;
import org.rhq.metrics.qe.tools.rhqmt.server.database.mappers.JobDetailMapper;
import org.rhq.metrics.qe.tools.rhqmt.server.scheduler.ManageScheduler;
import org.rhq.metrics.qe.tools.rhqmt.server.scheduler.ScheduleDetail;
/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 */
public class JobDetailService extends ServiceBase{


    public List<JobDetail> getAllRealTimeMetrics() {
        SqlSession session = this.getSession();
        try {
            return session.getMapper(JobDetailMapper.class).getAll();
        } finally {
            this.closeSession(session);
        }
    }

    public List<JobDetail> getAll() {
        SqlSession session = this.getSession();
        try {
            return session.getMapper(JobDetailMapper.class).getAll();
        } finally {
            this.closeSession(session);
        }
    }

    public List<JobDetail> get(JobDetail jobDetail) {
        SqlSession session = this.getSession();
        try {
            return session.getMapper(JobDetailMapper.class).get(jobDetail);
        } finally {
            this.closeSession(session);
        }
    }

    public JobDetail getOne(JobDetail jobDetail) {
        List<JobDetail> jobDetails = get(jobDetail);
        if(jobDetails.size() > 0){
            return jobDetails.get(0);
        }else{
            return null;
        }
    }

    public void add(JobDetail jobDetail) {
        SqlSession session = this.getSession();
        try {
            session.getMapper(JobDetailMapper.class).add(jobDetail);
            JobDetail tmpJobDetail = new JobDetail();
            tmpJobDetail = this.getOne(jobDetail);
            jobDetail.getMetricsJobData().setJobId(tmpJobDetail.getId());
            if(jobDetail.getJobType().equals(JobDetail.JOB_TYPE.REAL_TIME_METRICS.toString())){
                new MetricsJobDataService().add(jobDetail.getMetricsJobData());
            }
            //Fetch Job details and update to scheduler
            jobDetail = this.getOne(jobDetail);
            ManageScheduler.addJob(new ScheduleDetail(jobDetail, ScheduleDetail.GROUP.USER));
        } finally {
            this.closeSession(session);
        }

    }

    public void enable(JobDetail jobDetail) {
        SqlSession session = this.getSession();
        try {
            session.getMapper(JobDetailMapper.class).enable(jobDetail);
        } finally {
            this.closeSession(session);
        }        
    }

    public void disable(JobDetail jobDetail) {
        SqlSession session = this.getSession();
        try {
            session.getMapper(JobDetailMapper.class).disable(jobDetail);
        } finally {
            this.closeSession(session);
        }

    }

    public void delete(JobDetail jobDetail) {
        SqlSession session = this.getSession();
        try {
            //Remove job from scheduler, and then from db
            jobDetail = this.getOne(jobDetail);
            ManageScheduler.removeJob(new ScheduleDetail(jobDetail, ScheduleDetail.GROUP.USER));
            session.getMapper(JobDetailMapper.class).delete(jobDetail);
        }catch(SchedulerException scEx){
            _logger.warn("Exception, ", scEx);
        }finally {
            this.closeSession(session);
        }        
    }

}

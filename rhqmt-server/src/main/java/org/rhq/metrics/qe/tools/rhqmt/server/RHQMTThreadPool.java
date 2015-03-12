package org.rhq.metrics.qe.tools.rhqmt.server;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 */
public class RHQMTThreadPool extends ThreadPoolExecutor{

    public RHQMTThreadPool(int corePoolSize, int maximumPoolSize,
            long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
    }
    
    @Override
    protected void beforeExecute(Thread t, Runnable r) {
        super.beforeExecute(t, r);
    }
 
    @Override
    protected void afterExecute(Runnable r, Throwable t) {
        super.afterExecute(r, t);
        if (t != null) {
            //TODO: Perform exception handler logic
        }
    }
}


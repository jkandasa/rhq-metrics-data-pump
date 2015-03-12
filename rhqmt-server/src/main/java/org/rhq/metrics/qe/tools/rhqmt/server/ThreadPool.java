package org.rhq.metrics.qe.tools.rhqmt.server;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 */
public class ThreadPool {
private static ExecutorService threadPoolExecutor = null;
    
    public static void load(ThreadPoolConfiguration configuration){
        if(threadPoolExecutor == null){
            threadPoolExecutor =
                    new RHQMTThreadPool(
                            configuration.getCorePoolSize(),
                            configuration.getMaxPoolSize(),
                            configuration.getKeepAliveTime(),
                            TimeUnit.MILLISECONDS,
                            new LinkedBlockingQueue<Runnable>(configuration.getWaitingQueueCapacity())
                            );
        }        
    }
    public static ExecutorService getExecutorService(){
        return threadPoolExecutor;        
    }
}

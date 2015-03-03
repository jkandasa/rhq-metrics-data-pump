package org.rhq.metrics.qe.tools.rhqmt.server;

import org.rhq.metrics.qe.tools.rhqmt.server.health.TemplateHealthCheck;
import org.rhq.metrics.qe.tools.rhqmt.server.resources.RHQMetricsRealTimeJob;
import org.rhq.metrics.qe.tools.rhqmt.server.resources.RHQMetricsResource;
import org.rhq.metrics.qe.tools.rhqmt.server.resources.RHQMetricsTemplate;
import org.rhq.metrics.qe.tools.rhqmt.server.resources.ServerData;
import org.rhq.metrics.qe.tools.rhqmt.server.scheduler.ManageScheduler;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 * Dec 03, 2014
 */
public class StartApplicaton extends Application<ServerConfiguration>{

	public static void main(String[] args) throws Exception {
		new StartApplicaton().run(args);
	}

	@Override
	public void initialize(Bootstrap<ServerConfiguration> bootstrap) {
		//nothing to do

	}
	
	private void startServices(){
	    //Start Scheduler
	    ManageScheduler.start();
	}
	
	private void stopServices(){
        //Stop Scheduler
        ManageScheduler.shutdown();
    }

	@Override
	public void run(ServerConfiguration configuration, Environment environment) throws Exception {

	    startServices();
	    
	    //Add resources
		final RHQMetricsResource pushMetrics = new RHQMetricsResource();
		final RHQMetricsTemplate rhqMetricsTemplate = new RHQMetricsTemplate();
		final RHQMetricsRealTimeJob rhqMetricsRealTimeJob = new RHQMetricsRealTimeJob();
		final ServerData serverData = new ServerData(); 

		final TemplateHealthCheck healthCheck = new TemplateHealthCheck(configuration.getTemplate());
		environment.healthChecks().register("template", healthCheck);

		environment.jersey().register(pushMetrics);
		environment.jersey().register(rhqMetricsTemplate);
		environment.jersey().register(rhqMetricsRealTimeJob);
		
		
		environment.jersey().register(serverData);
	}

}

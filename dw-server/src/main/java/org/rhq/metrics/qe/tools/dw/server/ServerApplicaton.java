package org.rhq.metrics.qe.tools.dw.server;

import org.rhq.metrics.qe.tools.dw.server.health.TemplateHealthCheck;
import org.rhq.metrics.qe.tools.dw.server.resources.PushMetrics;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 * Dec 03, 2014
 */
public class ServerApplicaton extends Application<ServerConfiguration>{

	public static void main(String[] args) throws Exception {
        new ServerApplicaton().run(args);
    }
	
	@Override
	public void initialize(Bootstrap<ServerConfiguration> bootstrap) {
		//nothing to do
		
	}

	@Override
	public void run(ServerConfiguration configuration, Environment environment) throws Exception {
		
		final PushMetrics pushMetrics = new PushMetrics();
		
		final TemplateHealthCheck healthCheck =
		        new TemplateHealthCheck(configuration.getTemplate());
		    environment.healthChecks().register("template", healthCheck);
		    environment.jersey().register(pushMetrics);
	}

}

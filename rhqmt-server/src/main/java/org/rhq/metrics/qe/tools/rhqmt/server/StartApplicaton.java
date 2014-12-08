package org.rhq.metrics.qe.tools.rhqmt.server;

import org.rhq.metrics.qe.tools.rhqmt.server.health.TemplateHealthCheck;
import org.rhq.metrics.qe.tools.rhqmt.server.resources.RHQMetricsResource;
import org.rhq.metrics.qe.tools.rhqmt.server.resources.ServerData;

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

	@Override
	public void run(ServerConfiguration configuration, Environment environment) throws Exception {

		final RHQMetricsResource pushMetrics = new RHQMetricsResource();
		final ServerData serverData = new ServerData(); 

		final TemplateHealthCheck healthCheck = new TemplateHealthCheck(configuration.getTemplate());
		environment.healthChecks().register("template", healthCheck);

		environment.jersey().register(pushMetrics);
		environment.jersey().register(serverData);
	}

}

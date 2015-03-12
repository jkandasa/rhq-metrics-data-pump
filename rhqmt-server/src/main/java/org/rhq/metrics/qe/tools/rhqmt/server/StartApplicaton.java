package org.rhq.metrics.qe.tools.rhqmt.server;


import java.net.InetSocketAddress;
import java.util.concurrent.TimeUnit;

import org.rhq.metrics.qe.tools.rhqmt.server.database.PostgresSqlSessionFactory;
import org.rhq.metrics.qe.tools.rhqmt.server.health.TemplateHealthCheck;
import org.rhq.metrics.qe.tools.rhqmt.server.resources.RHQMetricsRealTimeJob;
import org.rhq.metrics.qe.tools.rhqmt.server.resources.RHQMetricsResource;
import org.rhq.metrics.qe.tools.rhqmt.server.resources.RHQMetricsTemplate;
import org.rhq.metrics.qe.tools.rhqmt.server.resources.ServerData;
import org.rhq.metrics.qe.tools.rhqmt.server.scheduler.ManageScheduler;

import com.codahale.metrics.JmxReporter;
import com.codahale.metrics.MetricFilter;
import com.codahale.metrics.graphite.Graphite;
import com.codahale.metrics.graphite.GraphiteReporter;

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
        PostgresSqlSessionFactory.initSqlSessionFactory();
        //Start Scheduler
        ManageScheduler.start();
    }

    private void stopServices(){
        //Stop Scheduler
        ManageScheduler.shutdown();
    }

    private void configureReporter(ServerConfiguration configuration){
        GraphiteReporterConfiguration reporterConfiguration = configuration.getGraphiteReporterConfiguration();
        if(reporterConfiguration.getEnabled()){
            final Graphite graphite = new Graphite(new InetSocketAddress(reporterConfiguration.getHostname(), reporterConfiguration.getPort()));
            final GraphiteReporter graphiteReporter = 
                    GraphiteReporter.forRegistry(Metrics.getMetrics())
                    .prefixedWith(reporterConfiguration.getBaseReference())
                    .convertRatesTo(TimeUnit.SECONDS)
                    .convertDurationsTo(TimeUnit.MILLISECONDS)
                    .filter(MetricFilter.ALL)
                    .build(graphite);
            graphiteReporter.start(reporterConfiguration.getSendEverySeconds(), TimeUnit.SECONDS);
        }

        final JmxReporter jmxReporter = JmxReporter.forRegistry(Metrics.getMetrics()).build();
        jmxReporter.start();
        /*final ConsoleReporter consoleReporter = ConsoleReporter.forRegistry(Metrics.getMetrics())
        .convertRatesTo(TimeUnit.SECONDS)
        .convertDurationsTo(TimeUnit.MILLISECONDS)
        .build();
        consoleReporter.start(1, TimeUnit.MINUTES);
         */
    }

    @Override
    public void run(ServerConfiguration configuration, Environment environment) {
        PostgresSqlSessionFactory.setConfiguration(configuration.getDatabaseConfiguration());

        ThreadPool.load(configuration.getThreadPoolConfiguration());


        startServices();

        //Start reporters
        this.configureReporter(configuration);

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

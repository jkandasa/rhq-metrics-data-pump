package org.rhq.metrics.qe.tools.rhqmt.cli.commands;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.clamshellcli.api.Command;
import org.clamshellcli.api.Configurator;
import org.clamshellcli.api.Context;
import org.clamshellcli.api.IOConsole;
import org.rhq.metrics.qe.tools.rhqmt.cli.ConsoleCommands;
import org.rhq.metrics.qe.tools.rhqmt.cli.ConsoleMessage;
import org.rhq.metrics.qe.tools.rhqmt.cli.RHQMTServer;
import org.rhq.metrics.qe.tools.rhqmt.cli.converter.DateConverter;
import org.rhq.metrics.qe.tools.rhqmt.server.RESTUri;
import org.rhq.metrics.qe.tools.rhqmt.server.representations.core.ExecutionStatus;
import org.rhq.metrics.qe.tools.rhqmt.server.representations.core.RHQMetricInput;
import org.rhq.metrics.qe.tools.rhqmt.server.representations.hawkular.RHQMetrics;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterDescription;
import com.beust.jcommander.converters.DoubleConverter;
import com.beust.jcommander.internal.Lists;
import com.fasterxml.jackson.databind.ObjectMapper;
/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 * Dec 08, 2014
 */
public class RHQMetricsCommand implements Command{
	private RHQMetricsDescriptor descriptor = null;

	private class RHQMetricsParams {
		@Parameter
		public List<String> parameters = Lists.newArrayList();

		@Parameter(names = {"-g", "-generate"}, required=false, description = "Generate Metric JSON file")
		public boolean generate = false;

		@Parameter(names = {"-s", "-send"}, required=false, description = "Send Metric to RHQ server")
		public boolean send = false;

		@Parameter(names = {"-fl", "-file-location"}, required=false, description = "file localtion to store generated metrics, default location '.'")
		public String fileLocation = ".";

		@Parameter(names = {"-t", "-template"}, required=false, description = "Pre defined template")
		public String templateName = null;

		@Parameter(names = {"-rhqs","-rhq-server"}, required=false, description = "RHQ server connection URL as <hostname>:<port>, default localhost:8080")
		public String rhqServer = "localhost:8080";

		@Parameter(names = {"-mni","-metric-name-id"}, required=false, description = "RHQ Metric Name or Id")
		public String metricNameId = null; 

		@Parameter(names = {"-mc","-metric-count"}, required=false, description = "Metric Count")
		public Integer metricCount = null; 

		@Parameter(names = {"-mi","-metric-interval"}, required=false, description = "Metric Interval in seconds, default 60 seconds")
		public Integer metricInterval = 60; 

		@Parameter(names = {"-mvl","-metric-value-lowest"}, converter=DoubleConverter.class ,required=false, description = "Metric Value Lowest, default 0.0")
		public Double metricValueLowest = 0.0;

		@Parameter(names = {"-mvh","-metric-value-highest"}, converter=DoubleConverter.class, required=false, description = "Metric Value Highest, default 100.0")
		public Double metricValueHighest = 100.0;

		@Parameter(names = {"-mst","-metric-start-time"}, converter=DateConverter.class, required=false, description = "Metric Start Time <"+DateConverter.DATA_FORMAT+">")
		public Date metricStartTime = null;

		@Parameter(names = {"-met","-metric-end-time"}, converter=DateConverter.class, required=false, description = "Metric End Time <"+DateConverter.DATA_FORMAT+">")
		public Date metricEndTime = null;

		@Parameter(names = {"-ml","-metric-limit"}, required=false, description = "Metrics Limit per request, used only in send metrics, default 10000")
		public Integer metricLimit = 10000;

	}

	private class RHQMetricsDescriptor implements Command.Descriptor {
		private JCommander commander;
		RHQMetricsParams parameters;

		public void setCommandArgs(String[] args){
			commander = new JCommander((parameters=new RHQMetricsParams()), args);
		}

		@Override public String getNamespace() {
			return ConsoleCommands.NAMESPACE_RHQ_METRICS_CLI;
		}

		@Override
		public String getName() {
			return ConsoleCommands.COMMAND_METRICS;
		}

		@Override
		public String getDescription() {
			return "Send/Generate RHQ Metrics.";
		}

		@Override
		public String getUsage() {
			StringBuilder result = new StringBuilder();
			result
			.append(ConsoleCommands.COMMAND_METRICS+" [options]").append(Configurator.VALUE_LINE_SEP);

			/*for(Map.Entry<String,String> entry : getArguments().entrySet()){
				result.append(String.format("%n%1$15s %2$2s %3$s", entry.getKey(), " ", entry.getValue()));
			}*/

			return result.toString();
		}

		@Override
		public Map<String, String> getArguments() {
			if(commander == null) commander = new JCommander(new RHQMetricsParams());
			Map<String, String> result = new HashMap<String,String>();
			List<ParameterDescription> params = commander.getParameters();
			for(ParameterDescription param : params){
				result.put(param.getNames(), param.getDescription());
			}

			return result;
		}

	}

	@Override
	public Descriptor getDescriptor() {
		return (descriptor !=  null) ? descriptor : (descriptor = new RHQMetricsDescriptor());
	}

	@Override
	public void plug(Context arg0) {
		// no load-time setup needed
	}

	@Override
	public Object execute(Context ctx) {
		String[] args = (String[]) ctx.getValue(Context.KEY_COMMAND_LINE_ARGS);
		IOConsole console = ctx.getIoConsole();
		if(args != null){
			try{
				descriptor.setCommandArgs(args);
			}catch(RuntimeException ex){
				console.writeOutput(String.format("%nUnable execute command: %s%n%n", ex.getMessage()));
				return null;
			}

			// decipher args

			//>metrics -s

			if(descriptor!=null && descriptor.parameters.send){
				if(RHQMTServer.isConnected()){
					RHQMetricInput rhqMetricInput = getRHQMetricInput(descriptor, console, true);
					if(rhqMetricInput != null){
						try {
							ExecutionStatus executionStatus = (ExecutionStatus) RHQMTServer.getClient().post(RESTUri.RHQ_METRICS_SEND, rhqMetricInput, ExecutionStatus.class);
							if(executionStatus.getSuccess()){
								console.writeOutput(String.format("%nCommand executed successfully.."));
								console.writeOutput(String.format("%nOverall Execution Time(ms): "+executionStatus.getTimeTaken()));
								console.writeOutput(String.format("%nREST API Execution Time(ms): "+executionStatus.getRestRequestTimeTaken()));
							}else{
								console.writeOutput(String.format("%nCommand failed to execute!"));
								console.writeOutput(String.format("%nOverall Time taken(ms): "+executionStatus.getTimeTaken()));
								console.writeOutput(String.format("%nError Message: "+executionStatus.getErrorMessage()));
							}
						} catch (Exception ex) {
							console.writeOutput(String.format("%nException: %s", ex.getMessage()));
						}
					}

				}else{
					console.writeOutput(String.format("%n"+ConsoleMessage.NO_CONNECTION));
				}
			}else if(descriptor!=null && descriptor.parameters.generate){//>metrics -g

				if(RHQMTServer.isConnected()){
					RHQMetricInput rhqMetricInput = getRHQMetricInput(descriptor, console, false);
					if(rhqMetricInput != null){
						try {
							RHQMetrics[] rhqMetrics = (RHQMetrics[]) RHQMTServer.getClient().post(RESTUri.RHQ_METRICS_GENERATE, rhqMetricInput, RHQMetrics[].class);
							String fileName = descriptor.parameters.fileLocation+"/"+rhqMetricInput.getMetricNameId()+new SimpleDateFormat("dd_MMM_yyyy_HH_mm_ss").format(new Date())+".json".replaceAll("\\s+", "_");

							//Convert to JSON file.
							ObjectMapper objectMapper = new ObjectMapper();
							objectMapper.writeValue(new File(fileName), rhqMetrics);
							console.writeOutput(String.format("Created a JSON file: %s", fileName));
						} catch (Exception ex) {
							console.writeOutput(String.format("%nFailed!, Exception: %s", ex.getMessage()));
						}
					}

				}else{
					console.writeOutput(String.format("%n"+ConsoleMessage.NO_CONNECTION));
				}			
			}
			console.writeOutput(String.format("%n%n"));

		}
		return null;
	}

	private RHQMetricInput getRHQMetricInput(RHQMetricsDescriptor metricsDescriptor, IOConsole console, boolean sendMetrics){
		RHQMetricInput rhqMetricSimpleInput = new RHQMetricInput();

		if(sendMetrics){
			if(metricsDescriptor.parameters.rhqServer != null){
				rhqMetricSimpleInput.setRhqServer(metricsDescriptor.parameters.rhqServer);
			}else{
				console.writeOutput(String.format("%nError: RHQ server URL is mssing..recheck the entries"));
				return null; 
			}
		}

		if(metricsDescriptor.parameters.templateName != null){
			rhqMetricSimpleInput.setTemplate(metricsDescriptor.parameters.templateName);
			return rhqMetricSimpleInput;
		}else if(metricsDescriptor.parameters.metricNameId != null
				&& metricsDescriptor.parameters.metricCount != null
				&& metricsDescriptor.parameters.metricInterval != null 
				&& metricsDescriptor.parameters.metricValueLowest != null
				&& metricsDescriptor.parameters.metricValueHighest != null){
			rhqMetricSimpleInput.setMetricNameId(metricsDescriptor.parameters.metricNameId);
			rhqMetricSimpleInput.setMetricInterval(metricsDescriptor.parameters.metricInterval);
			rhqMetricSimpleInput.setMetricCount(metricsDescriptor.parameters.metricCount);
			rhqMetricSimpleInput.setMetricValueLowest(metricsDescriptor.parameters.metricValueLowest);
			rhqMetricSimpleInput.setMetricValueHighest(metricsDescriptor.parameters.metricValueHighest);
			if(metricsDescriptor.parameters.metricStartTime != null){
				rhqMetricSimpleInput.setMetricStartTime(metricsDescriptor.parameters.metricStartTime);
			}
			if(metricsDescriptor.parameters.metricEndTime != null){
				rhqMetricSimpleInput.setMetricEndTime(metricsDescriptor.parameters.metricEndTime);
			}
			if(metricsDescriptor.parameters.metricLimit != null){
				rhqMetricSimpleInput.setMetricLimit(metricsDescriptor.parameters.metricLimit);
			}
			return rhqMetricSimpleInput;
		}else{
			console.writeOutput(String.format("%nError: Some of the required parameters are mssing..recheck the entries"));
			return null;
		}
	}
}

package org.rhq.metrics.qe.tools.rhqmt.cli.commands;

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
import org.rhq.metrics.qe.tools.rhqmt.cli.RESTUri;
import org.rhq.metrics.qe.tools.rhqmt.cli.RHQMTServer;
import org.rhq.metrics.qe.tools.rhqmt.server.representations.core.ExecutionStatus;
import org.rhq.metrics.qe.tools.rhqmt.server.representations.core.RHQMetricSimpleInput;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterDescription;
import com.beust.jcommander.converters.DoubleConverter;
import com.beust.jcommander.internal.Lists;
/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 * Dec 08, 2014
 */
public class RHQMetricsCommand implements Command{
	private RHQMetricsDescriptor descriptor = null;

	private class RHQMetricsParams {
		@Parameter
		public List<String> parameters = Lists.newArrayList();
		
		@Parameter(names = {"-g", "-generate"}, required=false, description = "Generate Metric file")
		public boolean generate = false;
		
		@Parameter(names = {"-s", "-send"}, required=false, description = "Send Metric to RHQ server")
		public boolean send = false;
		
		@Parameter(names = {"-fl", "-file-location"}, required=false, description = "file localtion to store generated metrics")
		public String fileLocation = null;
		
		@Parameter(names = {"-t", "-template"}, required=false, description = "Disconnect from RHQMT-Server.")
		public String templateName = null;

		@Parameter(names = {"-rhqs","-rhq-server"}, required=false, description = "RHQ server connection URL as <hostname>:<port>")
		public String rhqServer = null;
		
		@Parameter(names = {"-mni","-metric-name-id"}, required=false, description = "RHQ Metric Name or Id")
		public String metricNameId = null; 
		
		@Parameter(names = {"-mc","-metric-count"}, required=false, description = "Metric Count")
		public Integer metricCount = null; 
		
		@Parameter(names = {"-mi","-metric-interval"}, required=false, description = "Metric Interval")
		public Integer metricInterval = null; 
		
		@Parameter(names = {"-mvl","-metric-value-lowest"}, converter=DoubleConverter.class ,required=false, description = "Metric Value Lowest")
		public Double metricValueLowest = null;
		
		@Parameter(names = {"-mvh","-metric-value-highest"}, converter=DoubleConverter.class, required=false, description = "Metric Value Highest")
		public Double metricValueHighest = null;
		
		@Parameter(names = {"-mst","-metric-start-time"}, required=false, description = "Metric Start Time")
		public Date metricStartTime = null;
		
		@Parameter(names = {"-met","-metric-end-time"}, required=false, description = "Metric End Time")
		public Date metricEndTime = null;
		
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
					RHQMetricSimpleInput rhqMetricSimpleInput = getRHQMetricSimpleInput(descriptor, console);
					if(rhqMetricSimpleInput != null){
						try {
							ExecutionStatus executionStatus = (ExecutionStatus) RHQMTServer.getClient().post(RESTUri.SEND_RHQ_METRICS_SIMPLE, rhqMetricSimpleInput, ExecutionStatus.class);
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
				
			}

			console.writeOutput(String.format("%n%n"));

		}
		return null;
	}
	
	private RHQMetricSimpleInput getRHQMetricSimpleInput(RHQMetricsDescriptor metricsDescriptor, IOConsole console){
		RHQMetricSimpleInput rhqMetricSimpleInput = new RHQMetricSimpleInput();
		if(metricsDescriptor.parameters.rhqServer != null){
			rhqMetricSimpleInput.setRhqServer(metricsDescriptor.parameters.rhqServer);
			if(metricsDescriptor.parameters.templateName != null){
				rhqMetricSimpleInput.setTemplate(metricsDescriptor.parameters.templateName);
				return rhqMetricSimpleInput;
			}else if(metricsDescriptor.parameters.metricNameId != null 
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
				return rhqMetricSimpleInput;
			}else{
				console.writeOutput(String.format("%nError: Some of the required parameters are mssing..recheck the entries"));
				return null;
			}
		}else{
			console.writeOutput(String.format("%nError: RHQ server URL is mssing..recheck the entries"));
			return null; 
		}
	}
}

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
import org.rhq.metrics.qe.tools.rhqmt.cli.RHQMTServer;
import org.rhq.metrics.qe.tools.rhqmt.server.representations.core.RHQMTServerDetail;
import org.rhq.metrics.qe.tools.rhqmt.server.uri.LocalUri;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterDescription;
import com.beust.jcommander.internal.Lists;
/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 * Dec 05, 2014
 */
public class ServerInfoCommand implements Command {
	private SystemInfoDescriptor descriptor;

	private class ServerInfoParams {
		@Parameter
		public List<String> parameters = Lists.newArrayList();

		@Parameter(names = {"-i","-information"}, required=false, description = "Displays the RHQMT-server information.")
		public boolean information = false;

	}


	private class SystemInfoDescriptor implements Command.Descriptor {
		private JCommander commander;
		ServerInfoParams parameters;

		public void setCommandArgs(String[] args){
			commander = new JCommander((parameters=new ServerInfoParams()), args);
		}

		@Override public String getNamespace() {
			return ConsoleCommands.NAMESPACE_RHQ_METRICS_CLI;
		}

		@Override
		public String getName() {
			return ConsoleCommands.COMMAND_SERVER;
		}

		@Override
		public String getDescription() {
			return "Displays RHQMT-server information.";
		}

		@Override
		public String getUsage() {
			StringBuilder result = new StringBuilder();
			result
			.append(ConsoleCommands.COMMAND_SERVER+" [options]").append(Configurator.VALUE_LINE_SEP);

			/*for(Map.Entry<String,String> entry : getArguments().entrySet()){
				result.append(String.format("%n%1$15s %2$2s %3$s", entry.getKey(), " ", entry.getValue()));
			}*/

			return result.toString();
		}

		@Override
		public Map<String, String> getArguments() {
			if(commander == null) commander = new JCommander(new ServerInfoParams());
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
		return (descriptor !=  null) ? 
				descriptor : 
					(descriptor = new SystemInfoDescriptor());
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

			// >server -information
			if(descriptor!=null && descriptor.parameters.information){
				RHQMTServerDetail serverDetail = null;
				if(!RHQMTServer.isConnected()){
					console.writeOutput(String.format("%n"+ConsoleMessage.NO_CONNECTION));
				}else{
					try {
						serverDetail = (RHQMTServerDetail) RHQMTServer.getClient().get(LocalUri.SERVER+LocalUri.SERVER_DETAIL, RHQMTServerDetail.class);
						console.writeOutput(String.format("%nRHQMT-Server Infomration"));
						console.writeOutput(String.format("%n------------------------------------------"));
						console.writeOutput(String.format("%nConnection URL: "+RHQMTServer.getClient().getServerUrl()));
						console.writeOutput(String.format("%nTime: %s", new Date(serverDetail.getServerTime())));
						console.writeOutput(String.format("%nVerson: %s", serverDetail.getVersion()));
						console.writeOutput(String.format("%n------------------------------------------"));
					} catch (Exception ex) {
						console.writeOutput(String.format("Exception!, "+ex.getMessage()));
					}
				}				
				console.writeOutput(String.format("%n%n"));

			}

		}

		return null;
	}
}

package org.rhq.metrics.qe.tools.rhqmt.cli.commands;

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

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterDescription;
import com.beust.jcommander.internal.Lists;
/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 * Dec 08, 2014
 */
public class ConnectCommand implements Command{
	private ConnectDescriptor descriptor = null;

	private class ConnectParams {
		@Parameter
		public List<String> parameters = Lists.newArrayList();

		@Parameter(names = {"-h","-host"}, required=false, description = "Connection URL as <hostname>:<port>")
		public String host = null;

		@Parameter(names = {"-u","-user"}, required=false, description = "Username for connection.")
		public String user = null; 

		@Parameter(names = {"-p","-password"}, required=false, description = "Password for connection.")
		public String password = null; 

		@Parameter(names = {"-d","-disconnect"}, required=false, description = "Disconnect from RHQMT-Server.")
		public boolean disconnect = false;
		
		@Parameter(names = {"-s","-status"}, required=false, description = "Display Connection status.")
		public boolean status = false;
	}

	private class ConnectDescriptor implements Command.Descriptor {
		private JCommander commander;
		ConnectParams parameters;

		public void setCommandArgs(String[] args){
			commander = new JCommander((parameters=new ConnectParams()), args);
		}

		@Override public String getNamespace() {
			return ConsoleCommands.NAMESPACE_RHQ_METRICS_CLI;
		}

		@Override
		public String getName() {
			return ConsoleCommands.COMMAND_CONNECT;
		}

		@Override
		public String getDescription() {
			return "Connect to RHQMT-server.";
		}

		@Override
		public String getUsage() {
			StringBuilder result = new StringBuilder();
			result
			.append(ConsoleCommands.COMMAND_CONNECT+" [options]").append(Configurator.VALUE_LINE_SEP);

			/*for(Map.Entry<String,String> entry : getArguments().entrySet()){
				result.append(String.format("%n%1$15s %2$2s %3$s", entry.getKey(), " ", entry.getValue()));
			}*/

			return result.toString();
		}

		@Override
		public Map<String, String> getArguments() {
			if(commander == null) commander = new JCommander(new ConnectParams());
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
		return (descriptor !=  null) ? descriptor : (descriptor = new ConnectDescriptor());
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
			
			//>connect -s
			
			if(descriptor!=null && descriptor.parameters.status){
				if(RHQMTServer.isConnected()){
					console.writeOutput(String.format("%nConnected with RHQMT-Server["+RHQMTServer.getClient().getServerUrl()+"]."));
				}else{
					console.writeOutput(String.format("%n"+ConsoleMessage.NO_CONNECTION));
				}
				console.writeOutput(String.format("%n%n"));
			}

			// >connect -d
			if(descriptor!=null && descriptor.parameters.disconnect){
				if(RHQMTServer.isConnected()){
					RHQMTServer.disconnect();
					console.writeOutput(String.format("%nDisconnected from RHQMT-Server."));
				}else{
					console.writeOutput(String.format("%n"+ConsoleMessage.NO_CONNECTION));
				}				
				console.writeOutput(String.format("%n%n"));
			}

			// >connect -h
			if(descriptor != null && descriptor.parameters.host != null){
				if(RHQMTServer.isConnected()){
					console.writeOutput(String.format("%nA connection found with RHQMT-Server["+RHQMTServer.getClient().getServerUrl()+"]. Disconnect it first, if you want to make new connection."));
				}else{
					try {
						RHQMTServerDetail rhqmtServerDetail = RHQMTServer.connect(descriptor.parameters.host);
						console.writeOutput(String.format("%nConnected with RHQMT-Server["+RHQMTServer.getClient().getServerUrl()+"]."));
						console.writeOutput(String.format("%nRHQMT-Server Version: "+rhqmtServerDetail.getVersion()));
					} catch (Exception ex) {
						console.writeOutput(String.format("%nException! While connecting to server["+RHQMTServer.getClient().getServerUrl()+"], "+ex.getMessage()));
					}
					
				}
				console.writeOutput(String.format("%n%n"));
			}

		}
		return null;
	}
}

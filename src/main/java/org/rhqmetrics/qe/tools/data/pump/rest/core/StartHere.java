package org.rhqmetrics.qe.tools.data.pump.rest.core;

import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;

public class StartHere {
	public static void main(String[] args) throws Exception{
		DataPumpInput dataPumpInput = new DataPumpInput();
		CmdLineParser parser = new CmdLineParser(dataPumpInput);

		try {
			// parse the arguments.
			parser.parseArgument(args);

			if( dataPumpInput.getMetricNameId() == null ){
				throw new CmdLineException(parser, "Metric Name or id is missing", null);
			}
			System.out.println("Running data pump..");
			PumpData.sendData(dataPumpInput);
			System.out.println("Completed..");
		} catch( CmdLineException e ) {
			// if there's a problem in the command line,
			// you'll get this exception. this will report
			// an error message.
			System.err.println(e.getMessage());
			System.out.println();
			System.out.println("Data Pump [options...] arguments...");
			// print the list of available options
			parser.printUsage(System.out);
			System.out.println();

			return;
		}
	}
}

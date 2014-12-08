package org.rhq.metrics.qe.tools.rhqmt.cli;

import java.io.OutputStream;
import java.io.PrintStream;

import org.clamshellcli.api.Configurator;
import org.clamshellcli.api.Context;
import org.clamshellcli.api.SplashScreen;
/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 * Dec 05, 2014
 */
public class RhqmtSplashScreen implements SplashScreen{
	private static StringBuilder screen;
	static{
		screen = new StringBuilder();
		screen
		.append(String.format("%n%n"))
		.append(" _______   __    __   ______   __       __  ________        ______   __        ______ ").append(Configurator.VALUE_LINE_SEP)
		.append("|       \\ |  \\  |  \\ /      \\ |  \\     /  \\|        \\      /      \\ |  \\      |      \\").append(Configurator.VALUE_LINE_SEP)
		.append("| $$$$$$$\\| $$  | $$|  $$$$$$\\| $$\\   /  $$ \\$$$$$$$$     |  $$$$$$\\| $$       \\$$$$$$").append(Configurator.VALUE_LINE_SEP)
		.append("| $$__| $$| $$__| $$| $$  | $$| $$$\\ /  $$$   | $$ ______ | $$   \\$$| $$        | $$  ").append(Configurator.VALUE_LINE_SEP)
		.append("| $$    $$| $$    $$| $$  | $$| $$$$\\  $$$$   | $$|      \\| $$      | $$        | $$  ").append(Configurator.VALUE_LINE_SEP)
		.append("| $$$$$$$\\| $$$$$$$$| $$ _| $$| $$\\$$ $$ $$   | $$ \\$$$$$$| $$   __ | $$        | $$  ").append(Configurator.VALUE_LINE_SEP)
		.append("| $$  | $$| $$  | $$| $$/ \\ $$| $$ \\$$$| $$   | $$        | $$__/  \\| $$_____  _| $$_ ").append(Configurator.VALUE_LINE_SEP)
		.append("| $$  | $$| $$  | $$ \\$$ $$ $$| $$  \\$ | $$   | $$         \\$$    $$| $$     \\|   $$ \\").append(Configurator.VALUE_LINE_SEP)
		.append(" \\$$   \\$$ \\$$   \\$$  \\$$$$$$\\ \\$$      \\$$    \\$$          \\$$$$$$  \\$$$$$$$$ \\$$$$$$").append(Configurator.VALUE_LINE_SEP)
		.append("                          \\$$$                                                        ").append(Configurator.VALUE_LINE_SEP)
		.append(Configurator.VALUE_LINE_SEP)
		.append("                                                  A command-line tool for RHQMT-Server").append(Configurator.VALUE_LINE_SEP)
		.append("Powered by Clamshell-Cli framework ").append(Configurator.VALUE_LINE_SEP)
		.append("https://github.com/jkandasa/rhq-metrics-data-pump/").append(String.format("%n%n"))
		.append("Java version: ").append(System.getProperty("java.version")).append(Configurator.VALUE_LINE_SEP)
		.append("Java Home: ").append(System.getProperty("java.home")).append(Configurator.VALUE_LINE_SEP)
		.append("OS: ").append(System.getProperty("os.name")).append(", Version: ").append(System.getProperty("os.version"))
		.append(Configurator.VALUE_LINE_SEP)
		.append(Configurator.VALUE_LINE_SEP)
		;
	}
	@Override
	public void plug(Context ctx) {
		PrintStream out = new PrintStream ((OutputStream)ctx.getValue(Context.KEY_OUTPUT_STREAM));
        out.println(screen);

	}

	@Override
	public void render(Context ctx) {
		//nothing to do
	}


}

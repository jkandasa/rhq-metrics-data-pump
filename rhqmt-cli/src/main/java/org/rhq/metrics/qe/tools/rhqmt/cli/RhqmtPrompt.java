package org.rhq.metrics.qe.tools.rhqmt.cli;

import org.clamshellcli.api.Context;
import org.clamshellcli.api.Prompt;
/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 * Dec 05, 2014
 */
public class RhqmtPrompt implements Prompt{
	private static final String PROMPT = "rhqmt-cli > ";
	@Override
	public void plug(Context ctx) {
		 //nothing to do
	}

	@Override
	public String getValue(Context arg0) {
		return PROMPT;
	}

}

package org.rhq.metrics.qe.tools.rhqmt.gui.main;

import java.io.File;
import java.net.URL;
import java.security.ProtectionDomain;

import org.mortbay.jetty.Server;
import org.mortbay.jetty.webapp.WebAppContext;
/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 */
public final class EmbeddedJettyServer
{
	public static void main(String[] args) throws Exception
	{
		int port = Integer.parseInt(System.getProperty("port", "8091"));
		Server server = new Server(port);

		ProtectionDomain domain = EmbeddedJettyServer.class.getProtectionDomain();
		URL location = domain.getCodeSource().getLocation();

		WebAppContext webapp = new WebAppContext();
		webapp.setContextPath("/");
		webapp.setDescriptor(location.toExternalForm() + "/WEB-INF/web.xml");
		webapp.setServer(server);
		webapp.setWar(location.toExternalForm());

		// (Optional) Set the directory the war will extract to.
		// If not set, java.io.tmpdir will be used, which can cause problems
		// if the temp directory gets cleaned periodically.
		// Your build scripts should remove this directory between deployments
		webapp.setTempDirectory(new File("/tmp/rhqmtgui"));

		server.setHandler(webapp);
		server.start();
		server.join();
	}
}
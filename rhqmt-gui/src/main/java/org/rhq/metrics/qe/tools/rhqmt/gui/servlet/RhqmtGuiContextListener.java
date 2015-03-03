package org.rhq.metrics.qe.tools.rhqmt.gui.servlet;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.apache.log4j.Logger;
/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 */
@WebListener
public class RhqmtGuiContextListener  implements ServletContextListener{
	static Logger _logger = Logger.getLogger(RhqmtGuiContextListener.class); 
	

	@Override
	public void contextInitialized(ServletContextEvent event) {
		_logger.info("Doing Initialization...");	
				
		//Load System Properties	
		_logger.info("Initialization : Done");
	}
	
	@Override
	public void contextDestroyed(ServletContextEvent event) {
		// TODO Auto-generated method stub
		
	}

}

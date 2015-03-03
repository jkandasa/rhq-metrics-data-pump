package org.rhq.metrics.qe.tools.rhqmt.gui.layout.metrics;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;
import org.rhq.metrics.qe.tools.rhqmt.gui.layout.BottomButtonLayout;
import org.rhq.metrics.qe.tools.rhqmt.gui.layout.HeaderLayout;
import org.rhq.metrics.qe.tools.rhqmt.gui.utils.GUINotification;
import org.rhq.metrics.qe.tools.rhqmt.gui.utils.rhqmtserver.RHQMTServer;
import org.rhq.metrics.qe.tools.rhqmt.gui.widgets.CustomButton;
import org.rhq.metrics.qe.tools.rhqmt.gui.widgets.CustomPopupDateField;
import org.rhq.metrics.qe.tools.rhqmt.gui.widgets.CustomTextField;
import org.rhq.metrics.qe.tools.rhqmt.server.representations.core.ExecutionStatus;
import org.rhq.metrics.qe.tools.rhqmt.server.representations.core.RHQMetricInput;
import org.rhq.metrics.qe.tools.rhqmt.server.representations.hawkular.RHQMetrics;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.porotype.iconfont.FontAwesome.Icon;
import com.vaadin.server.FileResource;
import com.vaadin.server.Page;
import com.vaadin.server.ResourceReference;
import com.vaadin.shared.ui.datefield.Resolution;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.VerticalLayout;
/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 */
public class GenerateSendLayout extends VerticalLayout{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5758274582478635283L;

	private CustomTextField rhqServerUrl = new CustomTextField("RHQ-Metrics Server[host:port]", true, "'RHQ-Metrics Server[host:port]' is a mandatory field");
	private CustomTextField metricName = new CustomTextField("Name or ID",true, "'Name or ID' is a mandatory field");
	private CustomTextField metricValueLowest = new CustomTextField("Value Lowest", true, "'Value Lowest' is a mandatory field");
	private CustomTextField metricValueHighest = new CustomTextField("Value Highest", true, "'Value Highest' is a mandatory field");
	private CustomTextField metricCount = new CustomTextField("Count",true, "'Count' is a mandatory field");
	private CustomTextField metricInterval = new CustomTextField("Interval(Sec)",true, "'Interval' is a mandatory field");
	private CustomTextField metricLimit = new CustomTextField("Limit",true, "'Limit' is a mandatory field");
	private CustomPopupDateField metricFromDate = new CustomPopupDateField("From Date", null, false, null, null);
	private CustomPopupDateField metricToDate = new CustomPopupDateField("To Date", null, false, null, null);

	private Logger _logger = Logger.getLogger(GenerateSendLayout.class.getName()); 

	@SuppressWarnings("serial")
	public GenerateSendLayout(){
		setSizeFull();
		setMargin(false);

		//Add title bar
		HorizontalLayout title = new HeaderLayout("Send/Generate Metrics", Icon.bar_chart);


		//Text boxes (body)
		HorizontalLayout formLayout = new HorizontalLayout();
		FormLayout leftLayout 	= new FormLayout();
		FormLayout rightLayout 	= new FormLayout();

		leftLayout.setMargin(true);
		rightLayout.setMargin(true);

		formLayout.addComponents(leftLayout, rightLayout);
		formLayout.setComponentAlignment(leftLayout, Alignment.TOP_LEFT);
		formLayout.setComponentAlignment(rightLayout, Alignment.TOP_RIGHT);

		metricFromDate.setResolution(Resolution.MINUTE);
		metricToDate.setResolution(Resolution.MINUTE);

		rhqServerUrl.setTabIndex(1);
		metricName.setTabIndex(2);
		metricValueLowest.setTabIndex(3);
		metricValueHighest.setTabIndex(4);
		metricCount.setTabIndex(5);
		metricInterval.setTabIndex(6);
		metricLimit.setTabIndex(7);
		metricFromDate.setTabIndex(8);
		metricToDate.setTabIndex(9);

		rhqServerUrl.setValue("localhost:8080");
		metricName.setValue("rhqmt-auto-metric-"+new Date().getTime());
		metricValueLowest.setValue("0.0");
		metricValueHighest.setValue("100.0");
		metricCount.setValue("1000");
		metricInterval.setValue("60");
		metricLimit.setValue("5000");


		leftLayout.addComponents(rhqServerUrl, metricValueLowest, metricCount, metricFromDate, metricToDate );
		rightLayout.addComponents(metricName, metricValueHighest, metricInterval, metricLimit);


		//Buttons
		BottomButtonLayout buttons = new BottomButtonLayout();

		//Send Metrics Button
		Button send = new CustomButton("Send", "Send Metrics to RHQ-Metrics Server", Icon.edit);

		//Send Metrics Button
		Button generate = new CustomButton("Generate", "Generate Metrics and download", Icon.download);


		//Add Buttons
		buttons.addLeftSideComponents(send, generate);

		// Adding all the components to page
		addComponents(title, formLayout, buttons);		
		setComponentAlignment(title, Alignment.TOP_LEFT);
		setComponentAlignment(formLayout, Alignment.TOP_LEFT);
		setComponentAlignment(buttons, Alignment.BOTTOM_LEFT);		
		setExpandRatio(formLayout, 1);

		send.addClickListener(new ClickListener() {			
			@Override
			public void buttonClick(ClickEvent event) {
				if(isValid(true)){
					sendMetricsData();
				}
			}
		});

		generate.addClickListener(new ClickListener() {			
			@Override
			public void buttonClick(ClickEvent event) {
				if(isValid(false)){
					generateMetricsData();
				}
			}
		});
	}

	private boolean isValid(boolean sendMetrics){
		try{
			if(sendMetrics){
				rhqServerUrl.validate();
			}
			metricName.validate();
			metricValueLowest.validate();
			metricValueHighest.validate();
			metricCount.validate();
			metricInterval.validate();
			metricLimit.validate();
			return true;
		}catch(Exception ex){
			Notification.show(ex.getMessage(), Type.ERROR_MESSAGE);
			return false;
		}
	}

	private void generateMetricsData(){
		RHQMetricInput rhqMetricSimpleInput = new RHQMetricInput();
		mapValues(rhqMetricSimpleInput);
		GUINotification.showInfoMessgae("Generate Metrics", "Generate Metrrics has been triggred...Status will be updated soon...");
		try {
			RHQMetrics[] rhqMetrics = RHQMTServer.generateMetrics(rhqMetricSimpleInput);
			String message = "";
			message+= "<br>Metric Name: "+rhqMetricSimpleInput.getMetricNameId();
			GUINotification.alwaysShowInfoMessgae("Generate Metrics - Success", "Details: "+message);

			String fileName = rhqMetricSimpleInput.getMetricNameId()+new SimpleDateFormat("dd_MMM_yyyy_HH_mm_ss").format(new Date())+".json".replaceAll("\\s+", "_");
			//Convert to JSON file.
			ObjectMapper objectMapper = new ObjectMapper();
			File rhqMetricsFile = new File(fileName);
			objectMapper.writeValue(rhqMetricsFile, rhqMetrics); 
			FileResource fileResource = new FileResource(rhqMetricsFile);			 
			setResource("download", fileResource);
			ResourceReference rr = ResourceReference.create(fileResource, this, "download");
			Page.getCurrent().open(rr.getURL(), null);

		} catch (Exception ex) {
			_logger.error("Failed, ", ex);
			GUINotification.alwaysShowErrorMessgae("Generate Metrics - Failed", "Error: "+ex.getMessage());
		}
	}

	private void sendMetricsData(){
		RHQMetricInput rhqMetricSimpleInput = new RHQMetricInput();
		mapValues(rhqMetricSimpleInput);
		GUINotification.showInfoMessgae("Send Metrics", "Send Metrics to RHQ server ("+rhqMetricSimpleInput.getRhqServer()+") has been triggred..<br>Status will be updated soon...");
		try {
			ExecutionStatus executionStatus = RHQMTServer.sendMetricsToServer(rhqMetricSimpleInput);
			String message = "";
			message+= "<br>Metric Name: "+rhqMetricSimpleInput.getMetricNameId();
			message+= "<br>RHQ Server: "+rhqMetricSimpleInput.getRhqServer();
			message+= "<br>Time Taken: "+executionStatus.getTimeTaken();
			message+= "<br>Time, REST Request(RHQMT-server <--> RHQ Server): "+executionStatus.getRestRequestTimeTaken();
			if(executionStatus.getErrorMessage() != null){
				message+= "<br>Error Message: "+executionStatus.getErrorMessage();
			}
			if(executionStatus.getSuccess()){
				GUINotification.alwaysShowInfoMessgae("Send Metrics - Success", "Details: "+message);
			}else{
				GUINotification.alwaysShowErrorMessgae("Send Metrics - Failed", "Details: "+message);
			}

		} catch (Exception ex) {
			_logger.error("Failed, ", ex);
			GUINotification.alwaysShowErrorMessgae("Send Metrics - Failed", "Error: "+ex.getMessage());
		}
	}

	private void mapValues(RHQMetricInput rhqMetricSimpleInput){
		rhqMetricSimpleInput.setRhqServer(rhqServerUrl.getValue());
		rhqMetricSimpleInput.setMetricNameId(metricName.getValue());
		rhqMetricSimpleInput.setMetricValueLowest(Double.valueOf(metricValueLowest.getValue()));
		rhqMetricSimpleInput.setMetricValueHighest(Double.valueOf(metricValueHighest.getValue()));
		rhqMetricSimpleInput.setMetricCount(Integer.valueOf(metricCount.getValue()));
		rhqMetricSimpleInput.setMetricInterval(Integer.valueOf(metricInterval.getValue()));
		rhqMetricSimpleInput.setMetricLimit(Integer.valueOf(metricLimit.getValue()));		
	}

}

package org.rhq.metrics.qe.tools.rhqmt.gui.view;

import org.rhq.metrics.qe.tools.rhqmt.gui.layout.metrics.AddEditMetricsTemplate;
import org.rhq.metrics.qe.tools.rhqmt.gui.layout.metrics.AddEditRealTimeMetricsJob;
import org.rhq.metrics.qe.tools.rhqmt.gui.layout.metrics.GenerateSendLayout;
import org.rhq.metrics.qe.tools.rhqmt.gui.layout.metrics.MetricsTemplateLayout;
import org.rhq.metrics.qe.tools.rhqmt.gui.layout.metrics.RealTimeMetricsJobLayout;
import org.rhq.metrics.qe.tools.rhqmt.gui.utils.StyleName;
import org.rhq.metrics.qe.tools.rhqmt.gui.utils.UrlActionMapper;
import org.rhq.metrics.qe.tools.rhqmt.gui.utils.ViewNavigator;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Accordion;
/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 */
public class MetricsView  extends BaseView implements View{


	/**
	 * 
	 */
    private static final long serialVersionUID = 2436759642218215535L;
	public MetricsView(){
		content.setMargin(false);
		setSizeFull();
		addStyleName(StyleName.LAYOUT_MAIN.toString());
		Accordion menuBar = this.getMenuBar(new ViewMenus().getMetricsMenu());
		menuBar.setHeight("100%");

		//--------------------------------------------------------------
		//Content Panel
		//--------------------------------------------------------------
		content.setSizeFull();
		//content.addStyleName(StyleName.LAYOUT_CONTENT_SETTINGS.toString());
		content.setCaption("Metrics");

		//Adding All the layout to root layout
		addComponent(menuBar);
		addComponent(content);

		setFirstComponent(menuBar);
		setSecondComponent(content);
		setSplitPosition(15,Unit.PERCENTAGE);
	}
	@Override
	public void enter(ViewChangeEvent event) {
		content.removeAllComponents();
		UrlActionMapper parameters = new UrlActionMapper(event.getParameters());
		if(parameters.getAction() != null){
			if(parameters.getAction().equals(ViewNavigator.SM_METRICS_PAST.toString())){
				content.addComponent(new GenerateSendLayout());
			}else if(parameters.getAction().equals(ViewNavigator.SM_METRICS_TEMPLATE.toString())){
			    if(parameters.getSubAction() == null){
	                content.addComponent(new MetricsTemplateLayout());
                    
                }else if(parameters.getSubAction().equals(ViewNavigator.SM_METRICS_TEMPLATE_ADD.toString())){
                    content.addComponent(new AddEditMetricsTemplate(null, ViewNavigator.VIEW_METRICS_TEMPLATE));
                    
                }else if(parameters.getSubAction().equals(ViewNavigator.SM_METRICS_TEMPLATE_EDIT.toString())){
                    content.addComponent(new AddEditMetricsTemplate(Long.valueOf(parameters.getValue()), ViewNavigator.VIEW_METRICS_TEMPLATE));
                    
                }else{
                    _logger.info("Wrong action specified, loading default...");
                    content.addComponent(new MetricsTemplateLayout());
                }
            }else if(parameters.getAction().equals(ViewNavigator.SM_METRICS_REALTIME.toString())){
                if(parameters.getSubAction() == null){
                    content.addComponent(new RealTimeMetricsJobLayout());
                    
                }else if(parameters.getSubAction().equals(ViewNavigator.SM_METRICS_REALTIME_ADD.toString())){
                    content.addComponent(new AddEditRealTimeMetricsJob(null, ViewNavigator.VIEW_METRICS_REALTIME));
                    
                }else if(parameters.getSubAction().equals(ViewNavigator.SM_METRICS_REALTIME_EDIT.toString())){
                    content.addComponent(new AddEditRealTimeMetricsJob(Long.valueOf(parameters.getValue()), ViewNavigator.VIEW_METRICS_REALTIME));
                    
                }else{
                    _logger.info("Wrong action specified, loading default...");
                    content.addComponent(new RealTimeMetricsJobLayout());
                }
            }
		}
	
		
	}

}
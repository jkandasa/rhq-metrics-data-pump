package org.rhq.metrics.qe.tools.rhqmt.server.hawkular;

import java.util.ArrayList;
import java.util.List;
/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 */
public class ParseInputParams {
    private static final String REGEX_INPUT_BASE = "(?![^)(]*\\([^)(]*?\\)\\)),(?![^\\[]*\\])";
    private static final String REGEX_INPUT_SQUARE_BRACKETS = "[\\[\\]]";
    //private static final String REGEX_INPUT_SQUARE_BRACKETS = "[\\[\\]']+";

    private static final String METRICS_TYPE_NUMERIC       = "N:";
    private static final String METRICS_TYPE_AVAILABILITY  = "A:";
    private static final String METRICS_TYPE_REPLACE_CHAR  = "";


    public List<InputTenantParams> getTenants(String tenantString){
        String[] tokensVal = tenantString.split(REGEX_INPUT_BASE);
        List<InputTenantParams> names = new ArrayList<>();

        for(String token: tokensVal){
            String[] subTokensVal = token.split(REGEX_INPUT_SQUARE_BRACKETS);
            if(subTokensVal.length == 2){

                if(subTokensVal[1].contains(",")){
                    String[] numbers = subTokensVal[1].split(",");
                    for(String number :numbers ){
                        this.setTenantsMinMaxParams(names, subTokensVal[0], number);
                    }
                }else{
                    this.setTenantsMinMaxParams(names, subTokensVal[0], subTokensVal[1]);
                }
            }else if(subTokensVal.length == 1){
                names.add(new InputTenantParams(token.trim()));
            }else{
                //TODO: throw exception
            }
        }
        return names;
    }

    private void setTenantsMinMaxParams(List<InputTenantParams> names, String name, String minMax){
        if(minMax.contains("-")){
            String[] minMaxArg = minMax.split("-");
            if(minMaxArg.length == 2){
                names.add(new InputTenantParams(name.trim(), Integer.valueOf(minMaxArg[0].trim()), Integer.valueOf(minMaxArg[1].trim())));
            }else{
                //TODO: throw Exception
            }
        }else{
            names.add(new InputTenantParams(name.trim()+minMax.trim()));
        }
    }

    public List<InputMetricParams> getMetrics(String metricString){
        String[] tokensVal = metricString.split(REGEX_INPUT_BASE);
        List<InputMetricParams> names = new ArrayList<>();

        for(String token: tokensVal){
            String[] subTokensVal = token.split(REGEX_INPUT_SQUARE_BRACKETS);
            if(subTokensVal.length == 2){

                if(subTokensVal[1].contains(",")){
                    String[] numbers = subTokensVal[1].split(",");
                    for(String number :numbers ){
                        this.setMetricsMinMaxParams(names, subTokensVal[0], number);
                    }
                }else{
                    this.setMetricsMinMaxParams(names, subTokensVal[0], subTokensVal[1]);
                }
            }else if(subTokensVal.length == 1){
                if(token.toUpperCase().startsWith(METRICS_TYPE_NUMERIC)){
                    names.add(new InputMetricParams(token.replaceFirst("(?i)"+METRICS_TYPE_NUMERIC, 
                            METRICS_TYPE_REPLACE_CHAR), InputMetricParams.METRICS_TYPE.NUMERIC));   
                }else if(token.toUpperCase().startsWith(METRICS_TYPE_AVAILABILITY)){
                    names.add(new InputMetricParams(token.replaceFirst("(?i)"+METRICS_TYPE_AVAILABILITY, 
                            METRICS_TYPE_REPLACE_CHAR), InputMetricParams.METRICS_TYPE.AVAILABILITY));
                }else{
                    //TODO: Throw exception...
                }
            }else{
                //TODO: throw exception
            }
        }
        return names;
    }

    private void setMetricsMinMaxParams(List<InputMetricParams> names, String name, String minMax){
        if(minMax.contains("-")){
            String[] minMaxArg = minMax.split("-");
            if(minMaxArg.length == 2){
                if(name.trim().toUpperCase().startsWith(METRICS_TYPE_NUMERIC)){
                    names.add(new InputMetricParams(name.trim().replaceFirst("(?i)"+METRICS_TYPE_NUMERIC, 
                            METRICS_TYPE_REPLACE_CHAR), Integer.valueOf(minMaxArg[0].trim()), 
                            Integer.valueOf(minMaxArg[1].trim()), InputMetricParams.METRICS_TYPE.NUMERIC));   
                }else if(name.trim().toUpperCase().startsWith(METRICS_TYPE_AVAILABILITY)){
                    names.add(new InputMetricParams(name.trim().replaceFirst("(?i)"+METRICS_TYPE_AVAILABILITY, 
                            METRICS_TYPE_REPLACE_CHAR), Integer.valueOf(minMaxArg[0].trim()), 
                            Integer.valueOf(minMaxArg[1].trim()), InputMetricParams.METRICS_TYPE.AVAILABILITY));   
                }else{
                    //TODO: Throw exception...
                }
            }else{
                //TODO: throw Exception
            }
        }else{
            if(name.toUpperCase().startsWith(METRICS_TYPE_NUMERIC)){
                names.add(new InputMetricParams(name.replaceFirst("(?i)"+METRICS_TYPE_NUMERIC, 
                        METRICS_TYPE_REPLACE_CHAR)+minMax.trim(), InputMetricParams.METRICS_TYPE.NUMERIC));   
            }else if(name.toUpperCase().startsWith(METRICS_TYPE_AVAILABILITY)){
                names.add(new InputMetricParams(name.replaceFirst("(?i)"+METRICS_TYPE_AVAILABILITY, 
                        METRICS_TYPE_REPLACE_CHAR)+minMax.trim(), InputMetricParams.METRICS_TYPE.AVAILABILITY));
            }else{
                //TODO: Throw exception...
            }
        }
    }
}

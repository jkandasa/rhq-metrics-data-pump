package org.rhq.metrics.qe.tools.rhqmt.server;

import java.util.Properties;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;
/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 */
public class DatabaseConfiguration {
    
    public Properties getProperties(){
        Properties properties = new Properties();
        properties.setProperty("driverClass", this.getDriverClass());
        properties.setProperty("url", this.getUrl());
        properties.setProperty("username", this.getUsername());
        properties.setProperty("password", this.getPassword());
        return properties;
    }
    
    @JsonProperty
    private Boolean cleandb = false; 
    
    @NotEmpty
    @JsonProperty
    private String driverClass = "org.postgresql.Driver";
    
    @NotEmpty
    @JsonProperty
    private String url;
    
    @NotEmpty
    @JsonProperty
    private String username;
    
    @NotEmpty
    @JsonProperty
    private String password;

    public String getDriverClass() {
        return driverClass;
    }
    
    public void setDriverClass(String driverClass) {
        this.driverClass = driverClass;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getCleandb() {
        return cleandb;
    }

    public void setCleandb(Boolean cleandb) {
        this.cleandb = cleandb;
    }
    
   
}

package org.rhq.metrics.qe.tools.dw.server;

import io.dropwizard.Configuration;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 * Dec 04, 2014
 */
public class ServerConfiguration extends Configuration{
	@NotEmpty
    @JsonProperty
    private String template;

    /*
    @Valid
    @NotNull
    @JsonProperty
    private DatabaseConfiguration databaseConfiguration = new DatabaseConfiguration();
    */

    @NotEmpty
    @JsonProperty
    private String defaultName = "Stranger";

    public String getTemplate() {
        return template;
    }

    public String getDefaultName() {
        return defaultName;
    }

    /*
    public DatabaseConfiguration getDatabaseConfiguration() {
        return databaseConfiguration;
    }
    */
}

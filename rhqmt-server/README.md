RHQ Metrics Tool (RHQMT) - Server
================================

Compile:
------

######Add `hawkular-metrics-api-jaxrs` in to local .m2 repository.,
`cd rhq-metrics-data-pump/rhqmt-server`

`mvn org.apache.maven.plugins:maven-install-plugin:2.5.2:install-file  \
    -Dfile=lib/hawkular-metric-rest.jar \
    -DgroupId=org.hawkular.metrics -DartifactId=hawkular-metrics-api-jaxrs \
    -Dversion=0.3.0-SNAPSHOT -Dpackaging=jar`
    
#####To compile and build,
`mvn package install`

######To run (from rhqmt-server project home),
`./start_rhqmt_server.sh`

If you want to change listening port, have a look in `${rhqmt-server.base}/rhqmt-server.yml`

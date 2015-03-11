#java -Xdebug -agentlib:jdwp=transport=dt_socket,address=9999,server=y,suspend=y -jar target/rhqmt-server-0.0.1-SNAPSHOT-single.jar server rhqmt-server.yml
#java -jar target/rhqmt-server-0.0.1-SNAPSHOT-single.jar server rhqmt-server.yml
java -Dcom.sun.management.jmxremote \
     -Dcom.sun.management.jmxremote.port=9010 \
     -Dcom.sun.management.jmxremote.local.only=false \
     -Dcom.sun.management.jmxremote.authenticate=false \
     -Dcom.sun.management.jmxremote.ssl=false \
     -jar target/rhqmt-server-0.0.1-SNAPSHOT-single.jar server rhqmt-server.yml

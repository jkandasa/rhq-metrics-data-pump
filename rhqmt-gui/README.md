# Build

Build the project with
    
    $ mvn clean vaadin:compile package install

# Run

Now you can run with your own webserver or you can run with embedded jetty server,

    $ mvn jetty:run -Djetty.port=8091

module wator {
    exports wator.main;
    opens wator.core;
    opens wator.config;
    
    opens wator.main to spring.core;
    exports wator.util to spring.beans;
    exports wator.simple to spring.beans;

    requires javafx.controls;
    requires spring.beans;
    requires spring.core;
    requires spring.context;
    requires org.apache.logging.log4j;
    requires java.sql;
    requires jakarta.inject;
}
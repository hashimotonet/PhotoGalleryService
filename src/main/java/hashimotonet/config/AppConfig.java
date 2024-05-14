package hashimotonet.config;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.apache.catalina.connector.Connector;
import org.apache.coyote.ajp.AbstractAjpProtocol;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
	
	Logger log = LogManager.getLogger(AppConfig.class);

    @Bean
    public WebServerFactoryCustomizer<TomcatServletWebServerFactory> servletContainer() {
        Connector connector = new Connector("AJP/1.3");
        connector.setPort(8009);
        connector.setRedirectPort(8443);

        ((AbstractAjpProtocol<?>) connector.getProtocolHandler()).setSecretRequired(false);

        InetAddress ip = null;
        try {
            ip = InetAddress.getByName("0.0.0.0");
        } catch (UnknownHostException e) {
            log.catching(e);
        }
        ((AbstractAjpProtocol<?>) connector.getProtocolHandler()).setAddress(ip);

        return factory -> factory.addAdditionalTomcatConnectors(connector);
    }
}

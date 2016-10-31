package re_lease;

import org.apache.log4j.PropertyConfigurator;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.webapp.WebAppContext;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import re_lease.config.ApplicationConfig;
import re_lease.config.WebConfig;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class StartServer {

    public static void main(String[] args) throws IOException {

        Properties properties = new Properties();
        InputStream stream = StartServer.class.getResourceAsStream("/application.properties");
        properties.load(stream);

        PropertyConfigurator.configure(properties);

        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
        context.register(WebConfig.class);
        context.getEnvironment().getPropertySources()
                .addLast(new PropertiesPropertySource("applicationEnvironment", properties));

        ServletHolder servletHolder = new ServletHolder("test-dispatcher", new DispatcherServlet(context));
        servletHolder.setAsyncSupported(true);
        servletHolder.setInitOrder(1);

        WebAppContext webAppContext = new WebAppContext();
        webAppContext.addEventListener(new ContextLoaderListener(context));
        webAppContext.setInitParameter("contextConfigLocation", ApplicationConfig.class.getName());
        webAppContext.setResourceBase("web");
        webAppContext.setContextPath(properties.getProperty("app.url"));
        webAppContext.addServlet(servletHolder, "/");

        Server server = new Server(Integer.parseInt(properties.getProperty("app.port")));
        server.setHandler(webAppContext);

        try {
            server.start();
            server.join();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            server.destroy();
        }

    }

}

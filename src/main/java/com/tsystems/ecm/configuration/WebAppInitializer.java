package com.tsystems.ecm.configuration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;

public class WebAppInitializer implements WebApplicationInitializer {

    private static final Logger log = LogManager.getLogger(WebAppInitializer.class);

    @Override
    public void onStartup(ServletContext container) {
        log.debug("Creating Spring root context");
        AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
        log.debug("Created context: {}", rootContext);

        log.debug("Register application, JMS and security configs to root context");
        rootContext.register(AppConfig.class, SecurityConfig.class, JmsConfig.class);

        log.debug("Adding listener for managing the lifecycle of the spring root context");
        container.addListener(new ContextLoaderListener(rootContext));

        log.debug("Creating DispatcherServlet context");
        AnnotationConfigWebApplicationContext dispatcherContext = new AnnotationConfigWebApplicationContext();
        log.debug("Created context: {}", dispatcherContext);

        log.debug("Register WebMvc config to DispatcherServlet context");
        dispatcherContext.register(WebMvcConfig.class);

        log.debug("Registering Spring MVC DispatcherServlet");
        ServletRegistration.Dynamic dispatcher = container.addServlet("dispatcher", new DispatcherServlet(dispatcherContext));
        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping("/");

        FilterRegistration.Dynamic encodingFilter = container.addFilter("encoding-filter", new CharacterEncodingFilter());
        encodingFilter.setInitParameter("encoding", "UTF-8");
        encodingFilter.setInitParameter("forceEncoding", "true");
        encodingFilter.addMappingForUrlPatterns(null, true, "/*");
    }
}
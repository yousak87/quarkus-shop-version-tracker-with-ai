package com.skrill.config;

import freemarker.template.Configuration;
import freemarker.template.TemplateExceptionHandler;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class FreeMarkerConfigProvider {

    public static Configuration getFreeMarkerConfig() {
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_32);

        // Atur lokasi template ke folder 'freemarker' di resources
        cfg.setClassForTemplateLoading(FreeMarkerConfigProvider.class, "/freemarker");

        cfg.setDefaultEncoding("UTF-8");
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);

        return cfg;
    }
}

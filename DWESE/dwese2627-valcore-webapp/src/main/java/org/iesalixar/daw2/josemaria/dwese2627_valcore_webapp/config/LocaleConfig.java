package org.iesalixar.daw2.josemaria.dwese2627_valcore_webapp.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.Locale;

/**
 * Gestiona el idioma de la interfaz. El parámetro {@code lang} permite cambiarlo
 * y el resolver lo guarda en la sesión para las siguientes peticiones.
 */
@Configuration
public class LocaleConfig implements WebMvcConfigurer {

    private static final Logger logger = LoggerFactory.getLogger(LocaleConfig.class);

    @Bean
    public LocaleResolver localeResolver() {
        SessionLocaleResolver resolver = new SessionLocaleResolver();
        // La primera visita usa español si la sesión aún no tiene un idioma elegido.
        resolver.setDefaultLocale(Locale.of("es"));
        logger.info("LocaleResolver configurado con el idioma predeterminado: es");
        return resolver;
    }

    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor interceptor = new LocaleChangeInterceptor();
        interceptor.setParamName("lang");
        logger.info("LocaleChangeInterceptor configurado con el parámetro 'lang'");
        return interceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor());
        logger.info("LocaleChangeInterceptor registrado en el InterceptorRegistry");
    }
}


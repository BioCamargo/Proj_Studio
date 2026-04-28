package com.beautysalon.config;

import com.beautysalon.converter.AgendamentoConverter;
import com.beautysalon.converter.ClienteConverter;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class WebConfig implements WebMvcConfigurer
{

    private final AgendamentoConverter agendamentoConverter ;
    private final ClienteConverter clienteConverter;
    //private final ClienteDTOConverter clienteDTOConverter;

    public WebConfig(AgendamentoConverter agendamentoConverter, ClienteConverter clienteConverter) {
        this.agendamentoConverter = agendamentoConverter;
        this.clienteConverter=clienteConverter;
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter((Converter<?, ?>) agendamentoConverter);
        registry.addConverter(clienteConverter);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //WebMvcConfigurer.super.addResourceHandlers(registry);
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:///C:/beautysalon/uploads/");
                  //.addResourceLocations("file:uploads/");

        registry.addResourceHandler("/css/**")
                .addResourceLocations("classpath:/static/css/");
        registry.addResourceHandler("/js/**")
                .addResourceLocations("classpath:/static/js/");
        registry.addResourceHandler("/images/**")
                .addResourceLocations("classpath:/static/images/");



    }

}

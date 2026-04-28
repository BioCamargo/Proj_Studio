package com.beautysalon.Implementacao;

import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class StaticResourceConfig  implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Serve arquivos da pasta /static
//        registry.addResourceHandler("/css/**")
//                .addResourceLocations("classpath:/static/css/");

//         Serve uploads de imagens, perfil, etc
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:uploads/");

        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/static/");
    }
}

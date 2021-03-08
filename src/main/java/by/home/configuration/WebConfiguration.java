package by.home.configuration;

import by.home.interceptor.AdminInterceptor;
import by.home.interceptor.UserInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class WebConfiguration implements WebMvcConfigurer {

    @Autowired
    private AdminInterceptor adminInterceptor;
    @Autowired
    private UserInterceptor userInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry
                .addInterceptor(adminInterceptor)
                .addPathPatterns("/pet/{petId}")
                .addPathPatterns("/pet/findByStatus")
                .addPathPatterns("/pet")
                .addPathPatterns("/tag")
                .addPathPatterns("/category")
                .addPathPatterns("/store/inventory");

        registry.addInterceptor(userInterceptor)
                .addPathPatterns("/store/order");
    }
}

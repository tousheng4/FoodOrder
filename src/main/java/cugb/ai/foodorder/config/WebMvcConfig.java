package cugb.ai.foodorder.config;

import cugb.ai.foodorder.security.AuthInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Bean
    public AuthInterceptor authInterceptor() {
        return new AuthInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(authInterceptor())
                .addPathPatterns(
                        "/api/cart/**",
                        "/api/orders/**",
                        "/api/user/**",
                        "/api/admin/**",
                        "/api/address/**",
                        "/api/reviews/**"
                )
                .excludePathPatterns(
                        "/api/auth/**",
                        "/api/categories/**",
                        "/api/dishes/**",
                        "/api/reviews/dish/**"   // 菜品评价列表允许游客访问
                );
    }
}

package al.brain.saleforce.restsaleforce.configs;

import al.brain.saleforce.restsaleforce.filters.FirebaseFilter;
import al.brain.saleforce.restsaleforce.security.FirebaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

@Configuration
public class FirebaseConfig {

    @Autowired
    private FirebaseService firebaseService;
    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public FilterRegistrationBean<FirebaseFilter> firebaseFilter() {
        FilterRegistrationBean<FirebaseFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new FirebaseFilter(firebaseService));
        registrationBean.addUrlPatterns("/*");
        return registrationBean;
    }
}
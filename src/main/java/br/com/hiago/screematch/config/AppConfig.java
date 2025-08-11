package br.com.hiago.screematch.config;

import br.com.hiago.screematch.util.ConsumoApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Bean
    public ConsumoApi consumoApi(){
        return new ConsumoApi();
    }
}

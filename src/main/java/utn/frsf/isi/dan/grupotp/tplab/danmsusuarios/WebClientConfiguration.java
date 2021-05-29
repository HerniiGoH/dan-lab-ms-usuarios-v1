package utn.frsf.isi.dan.grupotp.tplab.danmsusuarios;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfiguration {
    @Bean
    public WebClient webClient(){
        return WebClient.create("http://localhost:4041/api/pedido/obra/");
    }
}

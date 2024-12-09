package medilux.aquabe.config;

import io.swagger.annotations.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .addServersItem(new Server().url("/"))
                .components(new Components())
                .info(new io.swagger.v3.oas.models.info.Info()
                        .title("Aqua API")
                        .description("아쿠아 api 명세서")
                        .version("1.0.0"));
    }
}
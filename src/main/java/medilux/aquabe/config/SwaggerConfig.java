package medilux.aquabe.config;

import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.Arrays;

@EnableWebMvc
@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI openAPI() {

        SecurityScheme bearerAuthScheme = new io.swagger.v3.oas.models.security.SecurityScheme()
                .type(io.swagger.v3.oas.models.security.SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT")
                .in(SecurityScheme.In.HEADER)
                .name("Authorization");

        SecurityRequirement bearerAuthRequirement = new SecurityRequirement().addList("bearerAuth");





        return new OpenAPI()
                .addServersItem(new Server().url("/"))
                .components(new Components()
                        .addSecuritySchemes("bearerAuth", bearerAuthScheme))
                .info(new Info()
                        .title("Aqua API")
                        .description("아쿠아 api 명세서")
                        .version("1.0.0"))
                .security(Arrays.asList(bearerAuthRequirement));
    }
}
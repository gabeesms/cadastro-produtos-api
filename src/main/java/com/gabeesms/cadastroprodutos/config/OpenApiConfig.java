package com.gabeesms.cadastroprodutos.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI cadastroProdutosOpenApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("Cadastro de Produtos API")
                        .description("API REST para cadastro, consulta, atualização e exclusão de produtos.")
                        .version("v1"));
    }
}

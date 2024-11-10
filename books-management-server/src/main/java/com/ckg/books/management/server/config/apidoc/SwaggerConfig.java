package com.ckg.books.management.server.config.apidoc;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Swagger3的接口配置
 *
 * @author chenkaigui
 * @date 2024/11/7
 */
@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI initOpenAPI() {
        return new OpenAPI().info(
                new Info().title("Books-management API").description("OpenAPI").version("v1.0.0")
        ) // 添加安全需求项，指定使用"BearerAuth"安全方案
                .addSecurityItem(new SecurityRequirement().addList("BearerAuth"))
                // 定义组件，包括安全方案等
                .components(new Components()
                        // 在安全方案中添加"BearerAuth"，用于认证
                        .addSecuritySchemes("BearerAuth",
                                // 创建一个新的HTTP安全方案
                                new SecurityScheme()
                                        // 设置安全方案的名称为"BearerAuth"
                                        .name("BearerAuth")
                                        // 指定安全方案的类型为HTTP
                                        .type(SecurityScheme.Type.HTTP)
                                        // 设置认证方式为"bearer"
                                        .scheme("bearer")
                                        // 指定bearer token的格式为JWT
                                        .bearerFormat("JWT")));
    }
}

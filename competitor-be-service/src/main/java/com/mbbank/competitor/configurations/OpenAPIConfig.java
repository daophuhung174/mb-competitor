package com.mbbank.competitor.configurations;

import com.mbbank.competitor.dto.BaseResponse;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.media.*;
import io.swagger.v3.oas.models.parameters.Parameter;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.HandlerMethod;

@Configuration
@RequiredArgsConstructor
//public class OpenAPIConfig{
public class OpenAPIConfig implements OperationCustomizer {

    @Value("${info.app.name}")
    private String appName;

    @Value("${info.app.version}")
    private String appVersion;

    @Bean
    public OpenAPI customOpenAPI() {
        String securitySchemeName = "OAuth2";
        String apiTitle = String.format("%s API", appName);

        var components = new Components()
                .addSecuritySchemes(
                        securitySchemeName,
                        new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT"));

        return new OpenAPI()
                .addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
                .components(components)
                .info(new Info().title(apiTitle).version(appVersion));
    }


    @Override
    public Operation customize(Operation operation, HandlerMethod handlerMethod) {
        ApiResponses responses = operation.getResponses();
        if (responses != null && responses.containsKey("200")) {
            final ApiResponse response200 = responses.get("200");
            final Content content = response200.getContent();
            if (content != null) {
                content.keySet().forEach(mediaTypeKey -> {
                    final MediaType mediaType = content.get(mediaTypeKey);
                    mediaType.schema(this.customizeSchema(mediaType.getSchema()));
                });
            }
        }

        operation.addParametersItem(
                new Parameter().in("header").required(true).description("Client Message ID Mandatory").name("clientMessageId"));
        return operation;
    }



    private Schema<?> customizeSchema(final Schema<?> objSchema) {
        final Schema<?> wrapperSchema = new Schema<>();
        wrapperSchema.addProperties(BaseResponse.Fields.status, new IntegerSchema()._default(200));
        wrapperSchema.addProperties(BaseResponse.Fields.error, new StringSchema()._default("OK"));
        wrapperSchema.addProperties(BaseResponse.Fields.clientMessageId, new StringSchema()._default("string"));
        wrapperSchema.addProperties(BaseResponse.Fields.path, new StringSchema()._default("string"));
        wrapperSchema.addProperties(BaseResponse.Fields.soaErrorCode, new StringSchema()._default("string"));
        wrapperSchema.addProperties(BaseResponse.Fields.soaErrorDesc, new StringSchema()._default("string"));
        wrapperSchema.addProperties(BaseResponse.Fields.data, objSchema);
        return wrapperSchema;
    }
}


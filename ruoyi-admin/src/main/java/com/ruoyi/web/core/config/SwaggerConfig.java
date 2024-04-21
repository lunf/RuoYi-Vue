package com.ruoyi.web.core.config;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.ruoyi.common.config.RuoYiConfig;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.service.SecurityScheme;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * Swagger2The interface configuration.
 * 
 * @author ruoyi
 */
@Configuration
public class SwaggerConfig
{
    /** System Basic Configuration */
    @Autowired
    private RuoYiConfig ruoyiConfig;

    /** is opened.swagger */
    @Value("${swagger.enabled}")
    private boolean enabled;

    /** Set up the unified forecast. */
    @Value("${swagger.pathMapping}")
    private String pathMapping;

    /**
     * CreatedAPI
     */
    @Bean
    public Docket createRestApi()
    {
        return new Docket(DocumentationType.OAS_30)
                // is activated.Swagger
                .enable(enabled)
                // used to create it.APIThe Basic Information，Showing on the document page.（Customized displayed information）
                .apiInfo(apiInfo())
                // Which interfaces are exposed toSwaggerShowing
                .select()
                // Scanning all the notes.api，More flexible in this way.
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                // Scanning in the specified package.swaggerNotes
                // .apis(RequestHandlerSelectors.basePackage("com.ruoyi.project.tool.swagger"))
                // Scanning all. .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build()
                /* Establish a safety model.，swaggerYou can set access.token */
                .securitySchemes(securitySchemes())
                .securityContexts(securityContexts())
                .pathMapping(pathMapping);
    }

    /**
     * The safety model.，indicated here.tokenThroughAuthorizationRequest for transmission.
     */
    private List<SecurityScheme> securitySchemes()
    {
        List<SecurityScheme> apiKeyList = new ArrayList<SecurityScheme>();
        apiKeyList.add(new ApiKey("Authorization", "Authorization", In.HEADER.toValue()));
        return apiKeyList;
    }

    /**
     * Safety to the bottom.
     */
    private List<SecurityContext> securityContexts()
    {
        List<SecurityContext> securityContexts = new ArrayList<>();
        securityContexts.add(
                SecurityContext.builder()
                        .securityReferences(defaultAuth())
                        .operationSelector(o -> o.requestMappingPattern().matches("/.*"))
                        .build());
        return securityContexts;
    }

    /**
     * Secure reference.
     */
    private List<SecurityReference> defaultAuth()
    {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        List<SecurityReference> securityReferences = new ArrayList<>();
        securityReferences.add(new SecurityReference("Authorization", authorizationScopes));
        return securityReferences;
    }

    /**
     * Add a summary information.
     */
    private ApiInfo apiInfo()
    {
        // useApiInfoBuilderTo be customized.
        return new ApiInfoBuilder()
                // Set the title.
                .title("The title：If the management system_Interface Documents")
                // described
                .description("described：Personal information for managing the Group's company.,Specifically includesXXX,XXXThe Module...")
                // Author Information
                .contact(new Contact(ruoyiConfig.getName(), null, null))
                // The version
                .version("The version number:" + ruoyiConfig.getVersion())
                .build();
    }
}

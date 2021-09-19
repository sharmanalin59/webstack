package com.imanage.webstack.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Min;

/**
 * @author nalin.sharma on 20/09/21
 */
@Component
@ConfigurationProperties("app")
@Validated
public class ApplicationPropValidation {
    @Min(0)
    private int stackSize;
}

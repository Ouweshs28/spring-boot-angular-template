package com.project.template.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * @author Ouweshs28
 */
@Configuration
@ComponentScan({"com.project.template"})
@EnableJpaRepositories("com.project.template.persistence.repository")
@EntityScan("com.project.template.persistence.entity")
@EnableJpaAuditing
public class TemplateConfig {

}
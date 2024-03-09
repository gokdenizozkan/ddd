package com.gokdenizozkan.ddd.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.domain.Specification;

@Configuration
public class Specifications {

    @Bean(name = "SpecificationActives")
    // an active entity should be ENABLED and NOT DELETED
    public <T> Specification<T> isActive(Class<T> clazz) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.and(
                criteriaBuilder.isTrue(root.get("enabled")),
                criteriaBuilder.isFalse(root.get("deleted"))
        );
    }
}

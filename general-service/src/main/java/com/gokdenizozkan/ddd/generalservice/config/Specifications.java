package com.gokdenizozkan.ddd.generalservice.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.domain.Specification;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collection;

@Configuration
public class Specifications {

    public static <T> Specification<T> isActive(Class<T> clazz) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.and(
                criteriaBuilder.isTrue(root.get("enabled")),
                criteriaBuilder.isFalse(root.get("deleted"))
        );
    }

    public static class Helper {
        public static Collection<String> toProperties(Class<?> clazz) {
            return Arrays.stream(clazz.getDeclaredFields()).map(Field::getName).toList();
        }
    }
}

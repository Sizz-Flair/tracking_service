package com.giant.tracking.config;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * packageName    : com.giant.tracking.config
 * fileName       : QuerydslConfig
 * author         : akfur
 * date           : 2024-04-02
 * description    :
 * ======================================================================
 * DATE             AUTHOR            NOTE
 * ----------------------------------------------------------------------
 * 2024-04-02         akfur
 */
@Configuration
public class QuerydslConfig {
    @PersistenceContext
    protected EntityManager em;

    @Bean
    public JPAQueryFactory jpaQueryFactory() {
        return new JPAQueryFactory(em);
    }
}

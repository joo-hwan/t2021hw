package com.ssg.homework.t2021hw.config;

import org.hibernate.HibernateException;
import org.hibernate.MappingException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.Configurable;
import org.hibernate.id.IdentifierGenerator;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.type.Type;

import java.io.Serializable;
import java.util.Properties;
import java.util.stream.Stream;

/*
custom generator 구현 클래스
해당 되는 entity를 조회하여 max 값에서 +1 을 해주어 pk 값을 증가 시킴
형식은 스키마에 정의된 것과 같이 맞추었음
 */
public class CustomIdGenerator implements IdentifierGenerator, Configurable {
    @Override
    public void configure(Type type, Properties properties, ServiceRegistry serviceRegistry) throws MappingException {

    }

    @Override
    public Serializable generate(SharedSessionContractImplementor sharedSessionContractImplementor, Object o) throws HibernateException {
        String query = String.format("select %s from %s",
                sharedSessionContractImplementor.getEntityPersister(o.getClass().getName(), o)
                .getIdentifierPropertyName(),
                o.getClass().getSimpleName()
        );

        Stream<String> ids = sharedSessionContractImplementor.createQuery(query).stream();

        Long max = ids.mapToLong(Long::parseLong)
                .max()
                .orElse(0L);

        return String.format("%010d", max+1);
    }
}

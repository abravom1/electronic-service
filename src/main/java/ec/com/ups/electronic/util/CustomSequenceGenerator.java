package ec.com.ups.electronic.util;

import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import java.io.Serializable;

public class CustomSequenceGenerator implements IdentifierGenerator {

    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object) {
        String sql = "SELECT nextval('GENERADORID')";
        Long nextValue = (Long) session.createNativeQuery(sql).uniqueResult();
        return nextValue.toString();
    }
}

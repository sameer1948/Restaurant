package org.app.restaurant.generator;

import org.hibernate.Session;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import java.io.Serializable;
import java.text.DecimalFormat;

public class TaxIdGenerator implements IdentifierGenerator {

    private static final DecimalFormat ID_FORMAT = new DecimalFormat("TAX00000");

    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object) {
        // Get the current session
        Session currentSession = (Session) session.getSession();

        // Correct query to get the current count of rows in the Tax entity
        Long currentCount = (Long) currentSession.createQuery("SELECT COUNT(t) FROM Tax t") // Use the entity name "Tax"
                .uniqueResult();

        if (currentCount == null) {
            throw new IllegalStateException("Failed to get the row count from the Tax table.");
        }

        // Increment the count to generate the new ID
        int newCounter = currentCount.intValue() + 1;

        // Format the ID with zero-padding and return it
        return ID_FORMAT.format(newCounter);
    }
}


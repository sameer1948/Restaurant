package org.app.restaurant.generator;

import org.hibernate.Session;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import java.io.Serializable;
import java.text.DecimalFormat;

public class MenuListIdGenerator implements IdentifierGenerator {

    private static final DecimalFormat ID_FORMAT = new DecimalFormat("MENU00000");

    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object) {
        Session currentSession = (Session) session.getSession();

        // Query to get the current count from the MenuList table
        Long currentCount = (Long) currentSession.createQuery("SELECT COUNT(ml) FROM MenuList ml")
                .uniqueResult();

        if (currentCount == null) {
            throw new IllegalStateException("Failed to get the row count from the MenuList table.");
        }

        // Increment the count to generate the new ID
        int newCounter = currentCount.intValue() + 1;

        // Format the ID with zero-padding and return it
        return ID_FORMAT.format(newCounter);
    }
}

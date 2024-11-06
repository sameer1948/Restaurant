package org.app.restaurant.generator;

import org.hibernate.Session;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;
import java.io.Serializable;
import java.text.DecimalFormat;

public class CouponIdGenerator implements IdentifierGenerator {

    private static final DecimalFormat ID_FORMAT = new DecimalFormat("COUPON00000");

    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object) {
        // Get the current session
        Session currentSession = (Session) session.getSession();

        // Query to get the current count of rows in the Coupon entity
        Long currentCount = (Long) currentSession.createQuery("SELECT COUNT(c) FROM Coupon c") // Use the entity name "Coupon"
                .uniqueResult();

        if (currentCount == null) {
            throw new IllegalStateException("Failed to get the row count from the Coupon table.");
        }

        // Increment the count to generate the new ID
        int newCounter = currentCount.intValue() + 1;

        // Format the ID with zero-padding and return it
        return ID_FORMAT.format(newCounter);
    }
}

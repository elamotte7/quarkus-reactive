package org.acme.api.versioning.core.strategy;

import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.boot.model.naming.ImplicitForeignKeyNameSource;
import org.hibernate.boot.model.naming.ImplicitNamingStrategyJpaCompliantImpl;

public class FKNamingStrategy extends ImplicitNamingStrategyJpaCompliantImpl {

    private static final String FK_PREFIX = "FK_";

    @Override
    public Identifier determineForeignKeyName(ImplicitForeignKeyNameSource source) {
        return toIdentifier(FK_PREFIX + source.getTableName().getCanonicalName().toUpperCase() + "_"
                + source.getReferencedTableName().getCanonicalName().toUpperCase(), source.getBuildingContext());
    }
}

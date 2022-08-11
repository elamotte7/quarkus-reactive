package org.acme.api.versioning.core.strategy;

import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.boot.model.naming.PhysicalNamingStrategy;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;

public class CustomPhysicalNamingStrategy implements PhysicalNamingStrategy {

    @Override
    public Identifier toPhysicalCatalogName(final Identifier identifier, final JdbcEnvironment jdbcEnv) {
        return convertToSnakeCase(identifier);
    }

    @Override
    public Identifier toPhysicalColumnName(final Identifier identifier, final JdbcEnvironment jdbcEnv) {
        return convertToSnakeCase(stripentity(identifier));
    }

    @Override
    public Identifier toPhysicalSchemaName(final Identifier identifier, final JdbcEnvironment jdbcEnv) {
        return convertToSnakeCase(identifier);
    }

    @Override
    public Identifier toPhysicalSequenceName(final Identifier identifier, final JdbcEnvironment jdbcEnv) {
        return convertToSnakeCase(identifier);
    }

    @Override
    public Identifier toPhysicalTableName(final Identifier identifier, final JdbcEnvironment jdbcEnv) {
        return convertToSnakeCase(stripentity(identifier));
    }

    private Identifier convertToSnakeCase(final Identifier identifier) {
        if (identifier == null) return null;
        var regex = "([a-z])([A-Z])";
        var replacement = "$1_$2";
        var newName = identifier.getText()
                .replaceAll(regex, replacement)
                .toUpperCase();
        return Identifier.toIdentifier(newName);
    }

    private Identifier stripentity(Identifier identifier) {
        var regex = "Entity";
        var replacement = "";
        return Identifier.toIdentifier(identifier.getText().replaceAll(regex, replacement));

    }
}

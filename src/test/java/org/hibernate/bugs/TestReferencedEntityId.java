package org.hibernate.bugs;

import java.sql.Types;
import java.util.UUID;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import org.hibernate.annotations.JdbcTypeCode;

@Embeddable
public record TestReferencedEntityId(
        @Column(name = "id", updatable = false, nullable = false, columnDefinition = "CHAR(36)")
        @Basic
        @JdbcTypeCode(Types.CHAR)
        UUID id) {
}

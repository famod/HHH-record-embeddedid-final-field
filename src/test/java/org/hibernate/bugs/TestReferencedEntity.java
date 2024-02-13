package org.hibernate.bugs;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;

@Entity
public class TestReferencedEntity {

    @EmbeddedId
    private TestReferencedEntityId id;

    public TestReferencedEntityId getId() {
        return id;
    }

    public void setId(TestReferencedEntityId id) {
        this.id = id;
    }
}

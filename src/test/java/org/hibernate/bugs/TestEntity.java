package org.hibernate.bugs;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;

@Entity
public class TestEntity {

    @EmbeddedId
    private TestEntityId id;

    public TestEntityId getId() {
        return id;
    }

    public void setId(TestEntityId id) {
        this.id = id;
    }
}

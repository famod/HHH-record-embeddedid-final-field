package org.hibernate.bugs;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;

@Entity
public class TestEntity {

    @EmbeddedId
    private TestEntityId id;

    @OneToOne(cascade = CascadeType.ALL)
    private TestReferencedEntity referenced;

    public TestEntityId getId() {
        return id;
    }

    public void setId(TestEntityId id) {
        this.id = id;
    }

    public TestReferencedEntity getReferenced() {
        return referenced;
    }

    public void setReferenced(TestReferencedEntity sub) {
        this.referenced = sub;
    }
}

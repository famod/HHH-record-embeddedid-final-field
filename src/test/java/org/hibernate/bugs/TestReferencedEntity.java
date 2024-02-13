package org.hibernate.bugs;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;

@Entity
public class TestReferencedEntity {

    @EmbeddedId
    private TestReferencedEntityId id;

    @Column
    private String bar;

    public TestReferencedEntityId getId() {
        return id;
    }

    public void setId(TestReferencedEntityId id) {
        this.id = id;
    }

    public String getBar() {
        return bar;
    }

    public void setBar(String foo) {
        this.bar = foo;
    }
}

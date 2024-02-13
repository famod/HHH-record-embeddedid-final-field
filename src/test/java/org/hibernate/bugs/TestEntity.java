package org.hibernate.bugs;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;

@Entity
public class TestEntity {

    @EmbeddedId
    private TestEntityId id;

    @Column
    private String foo;

    public TestEntityId getId() {
        return id;
    }

    public void setId(TestEntityId id) {
        this.id = id;
    }

    public String getFoo() {
        return foo;
    }

    public void setFoo(String foo) {
        this.foo = foo;
    }
}

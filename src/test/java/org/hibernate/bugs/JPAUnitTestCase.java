package org.hibernate.bugs;

import java.util.UUID;
import java.util.function.Function;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class JPAUnitTestCase {

	private EntityManagerFactory entityManagerFactory;

	@Before
	public void init() {
		entityManagerFactory = Persistence.createEntityManagerFactory( "templatePU" );
	}

	@After
	public void destroy() {
		entityManagerFactory.close();
	}
	
	@Test
	public void multiTx() throws Exception {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		try {
		    TestEntityId id = new TestEntityId(UUID.randomUUID());
		    TestReferencedEntityId referencedId = new TestReferencedEntityId(UUID.randomUUID());
		    withTx(entityManager, em -> {
		        TestEntity entity = new TestEntity();
		        entity.setId(id);
		        em.persist(entity);
		        return null;
		    });
		    TestEntity loadedEntity = withTx(entityManager, em -> em.find(TestEntity.class, id));
            TestReferencedEntity referenced = new TestReferencedEntity();
            referenced.setId(referencedId);
            loadedEntity.setReferenced(referenced);
		    TestEntity mergedEntity = withTx(entityManager, em -> em.merge(loadedEntity));
		} finally {
	        entityManager.close();
		}
	}

    @Test
    public void oneTx() throws Exception {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            TestEntityId id = new TestEntityId(UUID.randomUUID());
            TestReferencedEntityId referencedId = new TestReferencedEntityId(UUID.randomUUID());
            withTx(entityManager, em -> {
                TestEntity entity = new TestEntity();
                entity.setId(id);
                em.persist(entity);
                TestReferencedEntity referenced = new TestReferencedEntity();
                referenced.setId(referencedId);
                entity.setReferenced(referenced);
                em.merge(entity);
                return null;
            });
        } finally {
            entityManager.close();
        }
    }

    private <T> T withTx(EntityManager entityManager, Function<EntityManager, T> func) {
        entityManager.getTransaction().begin();
        try {
            T result = func.apply(entityManager);
            entityManager.getTransaction().commit();
            return result;
        } catch (RuntimeException re) {
            entityManager.getTransaction().rollback();
            throw re;
        }
     }
}

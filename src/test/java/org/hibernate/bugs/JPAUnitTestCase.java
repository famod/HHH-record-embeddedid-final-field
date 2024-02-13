package org.hibernate.bugs;

import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Function;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * This template demonstrates how to develop a test case for Hibernate ORM, using the Java Persistence API.
 */
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

	// Entities are auto-discovered, so just add them anywhere on class-path
	// Add your tests, using standard JUnit.
	@Test
	public void hhh123Test() throws Exception {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		try {
		    TestEntityId id = new TestEntityId(UUID.randomUUID());
		    withTx(entityManager, em -> {
		        TestEntity entity = new TestEntity();
		        entity.setId(id);
		        em.persist(entity);
		        return null;
		    });
		    TestEntity loadedEntity = withTx(entityManager, em -> em.find(TestEntity.class, id));
		    loadedEntity.setFoo("modified");
		    TestEntity mergedEntity = withTx(entityManager, em -> em.merge(loadedEntity));
		} finally {
	        entityManager.close();
		}
	}

//	private void withTx(EntityManager entityManager, Consumer<EntityManager> cons) {
//       entityManager.getTransaction().begin();
//       try {
//           cons.accept(entityManager);
//           entityManager.getTransaction().commit();
//       } catch (RuntimeException re) {
//           entityManager.getTransaction().rollback();
//           throw re;
//       }
//	}

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

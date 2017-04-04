package io.robusta.funko.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Optional;

import javax.persistence.EntityManager;

import org.hibernate.engine.transaction.jta.platform.internal.SynchronizationRegistryBasedSynchronizationStrategy;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import io.robusta.funko.EmFactory;
import io.robusta.funko.entities.Universe;

public class UniverseDaoTest {

	Universe u = new Universe("Maths");
	EntityManager em;
	UniverseDao dao;

	@Before
	public void setUp() {
		em = EmFactory.createEntityManager();
		dao = new UniverseDao(em);
	}

	@After
	public void tearDown() {
		em.close();
	}

	@AfterClass
	public static void close() {
		EmFactory.getInstance().close();
	}

	@Test
	public void createUniverse() {

		em.getTransaction().begin();

		dao.createUniverse(u);
		assertTrue(u.getId() > 0);

		dao.deleteUniverse(u);
		assertEquals(Optional.empty(), dao.findById(u.getId()));

		em.getTransaction().commit();
	}

	@Test
	public void updateUniverse() {

		em.getTransaction().begin();
		em.persist(u);

		if (!em.contains(u)) {
			System.out.println("need to merge u");
			em.merge(u);
		}

		u.setName("Litterature");
		dao.updateUniverse(u);
		em.getTransaction().commit();

		em.getTransaction().begin();
		boolean found = dao.findById(u.getId()).map(universe -> universe.getName())
				.filter(name -> name.equals("Litterature")).isPresent();

		assertTrue(found);

		em.getTransaction().commit();
	}

	@Test
	public void findById() {
		em.getTransaction().begin();
		em.persist(u);
		em.getTransaction().commit();

		em.getTransaction().begin();
		assertTrue(dao.findById(u.getId()).isPresent());
		em.getTransaction().commit();
	}

	@Test
	public void findUniverse() {

	}

	@Test
	public void findPops() {

	}
}

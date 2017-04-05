package io.robusta.funko.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import io.robusta.funko.EmFactory;
import io.robusta.funko.IntegrationTest;
import io.robusta.funko.entities.FunkoPop;
import io.robusta.funko.entities.Universe;

public class FunkoPopDaoTest implements IntegrationTest{

	EntityManager em;
	FunkoPopDao dao;

	Universe starWars = new Universe("Star Wars");
	FunkoPop jabba = new FunkoPop("Jabba", starWars);
	FunkoPop boba = new FunkoPop("Boba Fett", starWars);

	@Before
	public void setUp() {
		em = EmFactory.createEntityManager();
		dao = new FunkoPopDao(em);
		em.persist(starWars);
	}

	@After
	public void tearDown() {
		if (em.isOpen())
			em.close();
	}

	@AfterClass
	public static void close() {
		EmFactory.getInstance().close();
	}

	@Test
	public void create() {
		em.getTransaction().begin();

		dao.create(boba);
		assertTrue(boba.getId() > 0);

		dao.delete(boba);
		assertEquals(Optional.empty(), dao.findById(boba.getId()));

		em.getTransaction().commit();
	}

	@Test
	public void update() {

		em.getTransaction().begin();
		em.persist(jabba);

		if (!em.contains(jabba)) {
			System.out.println("need to merge jabba");
			jabba = em.merge(jabba);
		}

		jabba.setName("Beurk!");
		dao.update(jabba);
		em.getTransaction().commit();

		em.getTransaction().begin();

		boolean found = dao.findById(jabba.getId()).map(funkoPop -> funkoPop.getName())
				.filter(name -> name.equals("Beurk!")).isPresent();

		assertTrue(found);
		em.getTransaction().commit();
	}

	@Test
	public void findById() {
		em.getTransaction().begin();
		em.persist(boba);
		em.getTransaction().commit();

		em.getTransaction().begin();
		assertTrue(dao.findById(boba.getId()).isPresent());
		em.getTransaction().commit();
	}

	@Test
	public void findFunkoPop() {
		FunkoPop jabba = new FunkoPop("Jabba", starWars);
		FunkoPop jabba3 = new FunkoPop("Jabba3", starWars);
		FunkoPop jabba1 = new FunkoPop("Jabba1", starWars);
		FunkoPop jabba2 = new FunkoPop("Jabba2", starWars);
		
		em.getTransaction().begin();
		
		em.persist(jabba);
		em.persist(jabba1);
		em.persist(jabba2);
		em.persist(jabba3);
		
		em.getTransaction().commit();
		
em.getTransaction().begin();
		
List<FunkoPop> selection = dao.findFunkoPop("Jab", 0, 3);
		assertTrue(selection.contains(jabba1));
		assertFalse(selection.contains(jabba3));
		
		em.getTransaction().commit();
		
	}
}

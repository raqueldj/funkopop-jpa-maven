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
import io.robusta.funko.entities.FunkoPop;
import io.robusta.funko.entities.Universe;

public class UniverseDaoTest {

	Universe u = new Universe("Maths");
	EntityManager em;
	UniverseDao dao;

	Universe starWars = new Universe("Star Wars");
	FunkoPop jabba = new FunkoPop("Jabba", starWars);
	FunkoPop boba = new FunkoPop("Boba Fett", starWars);

	@Before
	public void setUp() {
		em = EmFactory.createEntityManager();
		dao = new UniverseDao(em);
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
		Universe physics = new Universe("Physics");
		Universe phylosophy = new Universe("Phylosophy");
		Universe physio = new Universe("Physio");
		Universe biophylosophy = new Universe("Biophylosophy");

		em.getTransaction().begin();

		dao.createUniverse(physics);
		dao.createUniverse(phylosophy);
		dao.createUniverse(physio);
		dao.createUniverse(biophylosophy);

		em.getTransaction().commit();
		em.close();

		em = EmFactory.createEntityManager();
		dao = new UniverseDao(em);
		em.getTransaction().begin();
		List<Universe> selection = dao.findUniverse("Phy", 0, 3);

		assertTrue(selection.contains(biophylosophy));
		assertFalse(selection.contains(physio));

		em.getTransaction().commit();
	}

	@Test
	public void findPops() {

		em.getTransaction().begin();

		dao.createUniverse(starWars);
		em.persist(jabba);
		em.persist(boba);

		em.getTransaction().commit();
		em.close();

		em = EmFactory.createEntityManager();
		dao = new UniverseDao(em);

		em.getTransaction().begin();
		List<FunkoPop> pops = dao.findPops(starWars);
		em.getTransaction();
		em.close();

		assertTrue(pops.size() == 2);
		assertTrue(pops.contains(jabba));
	}
}

package io.robusta.funko.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;

import io.robusta.funko.entities.FunkoPop;
import io.robusta.funko.entities.Universe;

public class UniverseDao {

	EntityManager em;

	public UniverseDao(EntityManager em) {
		super();
		this.em = em;
	}

	public void createUniverse(Universe universe) {
		// il fait la requete SQL tout seul
		em.persist(universe);
	}

	public void updateUniverse(Universe universe) {

	}

	public void deleteUniverse(Universe universe) {
		em.remove(universe);
	}

	public Optional<Universe> findById(int id) {
		Universe u = em.find(Universe.class, id);

		return Optional.ofNullable(u);
	}

	public List<Universe> findUniverse(String name, int start, int quantity) {
		String query = "SELECT u FROM Universe u WHERE u.name LIKE :name ORDER BY u.name ASC";

		return em.createQuery(query, Universe.class).setParameter("name", "%" + name + "%").setFirstResult(start)
				.setMaxResults(quantity).getResultList();
	}

	public List<FunkoPop> findPops(Universe universe) {

		String jpql = "SELECT p FROM FunkoPop p WHERE p.universe = :universe";

		return em.createQuery(jpql, FunkoPop.class).setParameter("universe", universe).getResultList();
	}

	public List<Universe> findAll() {

		String jpql = "SELECT u FROM Universe u ORDER BY u.name ASC";

		return em.createQuery(jpql, Universe.class).getResultList();
	}

	public HashMap<Universe, List<FunkoPop>> findUniverseAndPops(String name) {
		String jpql = "SELECT p FROM FunkoPop p JOIN FETCH p.universe WHERE p.universe.name LIKE :name";

		List<FunkoPop> pops = em.createQuery(jpql, FunkoPop.class).setParameter("name", "%" + name + "%").getResultList();
		System.out.println(pops);

		HashMap<Universe, List<FunkoPop>> map = new HashMap<>();
		for (FunkoPop pop : pops) {
			List<FunkoPop> resultPops = map.get(pop.getUniverse());
			if (resultPops == null) {
				resultPops = new ArrayList<>();
			}
			resultPops.add(pop);
			map.put(pop.getUniverse(), resultPops);
		}

		return map;
	}
}

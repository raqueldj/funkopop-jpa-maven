package io.robusta.funko.dao;

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

		return null;
	}
}

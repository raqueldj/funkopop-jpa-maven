package io.robusta.funko.dao;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;

import io.robusta.funko.entities.FunkoPop;

public class FunkoPopDao {

	EntityManager em;

	public FunkoPopDao(EntityManager em) {
		super();
		this.em = em;
	}

	public FunkoPop create(FunkoPop funkoPop) {
		em.persist(funkoPop);
		return funkoPop;
	}

	public void update(FunkoPop funkoPop) {

	}

	public void delete(FunkoPop funkoPop) {
		em.remove(funkoPop);
	}

	public Optional<FunkoPop> findById(int id) {
		FunkoPop fp = em.find(FunkoPop.class, id);

		return Optional.ofNullable(fp);
	}

	public List<FunkoPop> findFunkoPop(String name, int start, int quantity) {
		String query = "SELECT fp FROM FunkoPop fp WHERE fp.name LIKE :name ORDER BY fp.name ASC";

		return em.createQuery(query, FunkoPop.class).setParameter("name", "%" + name + "%").setFirstResult(start)
				.setMaxResults(quantity).getResultList();
	}
	
	public List<FunkoPop> findAll() {

		String jpql = "SELECT p FROM FunkoPop p ORDER BY p.name ASC";

		return em.createQuery(jpql, FunkoPop.class).getResultList();
	}
}

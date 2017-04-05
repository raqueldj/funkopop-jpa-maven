package io.robusta.funko.service;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import io.robusta.funko.dao.UniverseDao;

@Stateless
public class UniverseService {

	UniverseDao universeDao;
	
	@PersistenceContext
	EntityManager em;

	public UniverseService() {
		super();
	}
	
}

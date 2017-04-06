package io.robusta.funko.service;

import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import io.robusta.funko.dao.FunkoPopDao;
import io.robusta.funko.entities.FunkoPop;

@Stateless
@Named
public class FunkoPopService {

	FunkoPopDao funkoPopDao;

	@PersistenceContext
	EntityManager em;

	@PostConstruct
	void after() {
		System.out.println("----------PostConstruct in FunkoPopService----------");
		this.funkoPopDao = new FunkoPopDao(em);
	}

	public FunkoPop create(FunkoPop funkoPop) {
		
		return funkoPopDao.create(funkoPop);
	}

	public void update(FunkoPop funkoPop) {
		funkoPopDao.update(funkoPop);
	}

	public void delete(FunkoPop funkoPop) {
		FunkoPop p = funkoPopDao.findById(funkoPop.getId()).orElseThrow(IllegalArgumentException::new);
		funkoPopDao.delete(p);
	}

	public Optional<FunkoPop> findById(int id) {
		return funkoPopDao.findById(id);
	}

	public List<FunkoPop> findFunkoPop(String name, int start, int quantity) {
		return funkoPopDao.findFunkoPop(name, start, quantity);
	}

	public List<FunkoPop> findAll() {
		return funkoPopDao.findAll();
	}
}

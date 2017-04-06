package io.robusta.funko.service;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import io.robusta.funko.dao.UniverseDao;
import io.robusta.funko.entities.FunkoPop;
import io.robusta.funko.entities.Universe;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Stateless
@Named
public class UniverseService {

    UniverseDao universeDao;

    @PersistenceContext
    EntityManager em;

    @PostConstruct
    void after(){
        System.out.println("----------PostConstruct in UniverseService----------");
        this.universeDao = new UniverseDao(em);
    }
    
    public List<Universe> findUniverse(String name, int start, int quantity) {
    	return universeDao.findUniverse(name, start, quantity);
    }

    public Optional<Universe> findById(int id) {
        return universeDao.findById(id);
    }
    
    public List<Universe> findAll() {
        return universeDao.findAll();
    }
    
    public List<FunkoPop> findPops(Universe u){
    	return universeDao.findPops(u);
    }
    
    public HashMap<Universe, List<FunkoPop>> findUniverseAndPops(String name) {
    	return universeDao.findUniverseAndPops(name);
    }
}

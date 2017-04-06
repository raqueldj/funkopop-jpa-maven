package io.robusta.funko.cdi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import io.robusta.funko.entities.FunkoPop;
import io.robusta.funko.entities.Universe;
import io.robusta.funko.service.UniverseService;

@Named
@RequestScoped
public class FavoritesBean {
	String test = "Testing";
	int index = 0;

	@EJB
	UniverseService universeService;

	public String getTest() {
		index++;
		return test + index;
	}

	public void setTest(String test) {
		this.test = test;
	}

	public List<Universe> getFavorites() {
		return universeService.findUniverse("ar", 0, 5);
	}
	
	HashMap<Universe, List<FunkoPop>> cache;
	public HashMap<Universe, List<FunkoPop>> getFavoritesFaster() {
		System.out.println("------FAST------");
		if(this.cache == null){
			this.cache = universeService.findUniverseAndPops("ar");
		}
		return this.cache;
	}
	public ArrayList<Universe> getUniversesFaster() {
		return new ArrayList<Universe>(getFavoritesFaster().keySet());
	}
	public List<FunkoPop> getPopsFaster(Universe universe) {
		return this.cache.get(universe);
	}

}

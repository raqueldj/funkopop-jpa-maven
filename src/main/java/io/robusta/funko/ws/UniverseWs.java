package io.robusta.funko.ws;

import io.robusta.funko.cdi.FavoritesBean;
import io.robusta.funko.entities.FunkoPop;
import io.robusta.funko.entities.Universe;
import io.robusta.funko.service.UniverseService;

import java.util.List;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * Created by AELION on 05/04/2017.
 */

@Path("universes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes("application/json")
public class UniverseWs {

    @EJB //object superpowered I must say it!!
    UniverseService service;
    
    @Inject //object not superpowered...
    FavoritesBean favoritesBean;

    @GET
    @Path("{id}")
    public Universe findById(@PathParam("id") int id) {

        return service.findById(id).orElseThrow(NotFoundException::new);
    }
    
    @GET
    @Path("{id}")
    public List<FunkoPop> findPops(@PathParam("id") int id) {

    	Universe u = findById(id);
    	
        return service.findPops(u);
    }
    
    @GET
    public List<Universe> findAll() {

    	System.out.println("%%%%%%%%%%%%%%%%%% : "+favoritesBean.getTest());
        return service.findAll();
    }
}

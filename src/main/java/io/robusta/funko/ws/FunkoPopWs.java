package io.robusta.funko.ws;

import java.util.List;
import java.util.Optional;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import io.robusta.funko.entities.FunkoPop;
import io.robusta.funko.service.FunkoPopService;

@Path("pops")
@Produces(MediaType.APPLICATION_JSON)
@Consumes("application/json")
public class FunkoPopWs {

	@EJB
	FunkoPopService service;
	
	@GET
	@Path("{id}")
	public FunkoPop findById(@PathParam("id") int id){
		return service.findById(id).orElseThrow(NotFoundException::new);
	}
	
	@GET
	public List<FunkoPop> findAll(){
		return service.findAll();
	}
	
	@POST
	public FunkoPop create(FunkoPop funkoPop){
		/*if(!funkoPop.getName().isEmpty()){
			return null;
		}*/
		return service.create(funkoPop);
	}
	
	@PUT
	public void update(FunkoPop funkoPop){
		service.update(funkoPop);
	}
	
	@DELETE
	@Path("{id}")
	public void delete(@PathParam("id") int id){
		FunkoPop p = service.findById(id).orElseThrow(IllegalArgumentException::new);
		
		service.delete(p);
	}
	
}

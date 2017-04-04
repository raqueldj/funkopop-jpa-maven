package io.robusta.funko.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;



@Entity
public class FunkoPop implements HasId
{
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id = null;
	
	@ManyToOne
	//def universe ici evite d'avoir des tables de jointure
	Universe universe;

	private String name;
	//@ManyToOne
	//private Universe universe;
	private boolean waterproof = false;

	public FunkoPop()
	{
	}

	public FunkoPop( String name, Universe universe )
	{
		
		this.name = name;
		this.universe = universe;
	}

	public String getName()
	{
		return name;
	}

	public void setName( String name )
	{
		this.name = name;
	}


	
	/*public Universe getUniverse() {
		return universe;
	}

	public void setUniverse(Universe universe) {
		this.universe = universe;
	}*/

	public boolean isWaterproof()
	{
		return waterproof;
	}

	public void setWaterproof( boolean waterproof )
	{
		this.waterproof = waterproof;
	}

	public Integer getId()
	{
		return id;
	}

	public void setId( Integer id )
	{
		this.id = id;
	}

	
	
	public Universe getUniverse() {
		return universe;
	}

	public void setUniverse(Universe universe) {
		this.universe = universe;
	}

	@Override
	public String toString() {
		
		return this.name + " ("+this.universe+")";
	}
}

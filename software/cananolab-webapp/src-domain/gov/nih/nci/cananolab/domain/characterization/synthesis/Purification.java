package gov.nih.nci.cananolab.domain.characterization.synthesis;

import gov.nih.nci.cananolab.domain.particle.Characterization;

import java.io.Serializable;
/**
	* The act of describing distinctive characteristics or essential features of the assay which is conducted in an artificial environment, such as in a test tube, under a defined and controlled set of solvent and solute conditions.
	**/

public class Purification extends SynthesisCharacterization implements Serializable
{
	/**
	* An attribute to allow serialization of the domain objects
	*/
	private static final long serialVersionUID = 1234567890L;
	
	/**
	* Compares <code>obj</code> to it self and returns true if they both are same
	*
	* @param obj
	**/
	public boolean equals(Object obj)
	{
		if(obj instanceof Purification) 
		{
			Purification c =(Purification)obj; 			 
			if(getId() != null && getId().equals(c.getId()))
				return true;
		}
		return false;
	}
		
	/**
	* Returns hash code for the primary key of the object
	**/
	public int hashCode()
	{
		if(getId() != null)
			return getId().hashCode();
		return 0;
	}
	
}
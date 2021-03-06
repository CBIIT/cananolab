package gov.nih.nci.cananolab.domain.characterization.invitro;


import java.io.Serializable;
/**
	* A disturbance in the prooxidant-antioxidant balance in favor of the former, leading to potential damage. Indicators of oxidative stress include damaged DNA bases, protein oxidation products, and lipid peroxidation products.  The damage to biological tissues is caused by superoxide and other free radicals generated by many factors, including exposure to alcohol, medications, trauma, cold, toxins, and radiation or by antimicrobial cellular immunity, metabolic abnormality, or "normal" aging; not synonymous with hypoxia or hyperoxia.  Oxidative stress promotes a range of degenerative disorders, including cancer, diabetes, premature aging, Alzheimer's, and many others.
	**/

public class OxidativeStress extends InvitroCharacterization implements Serializable
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
		if(obj instanceof OxidativeStress) 
		{
			OxidativeStress c =(OxidativeStress)obj;
            return getId() != null && getId().equals(c.getId());
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
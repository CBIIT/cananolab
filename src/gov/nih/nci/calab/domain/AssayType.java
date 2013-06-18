/*L
 *  Copyright SAIC
 *  Copyright SAIC-Frederick
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/cananolab/LICENSE.txt for details.
 */


package gov.nih.nci.calab.domain;

/**
 * <!-- LICENSE_TEXT_START -->
 * <!-- LICENSE_TEXT_END -->
 */
 
  /**

   */

public  class AssayType 
	implements java.io.Serializable 
{
	private static final long serialVersionUID = 1234567890L;

	
	   
	   private java.lang.Long id;
	   public  java.lang.Long getId(){
	      return id;
	   }
	   public void setId( java.lang.Long id){
	      this.id = id;
	   }
	
	   
	   private java.lang.String name;
	   public  java.lang.String getName(){
	      return name;
	   }
	   public void setName( java.lang.String name){
	      this.name = name;
	   }
	
	   
	   private java.lang.String description;
	   public  java.lang.String getDescription(){
	      return description;
	   }
	   public void setDescription( java.lang.String description){
	      this.description = description;
	   }
	
	   private java.lang.String executeOrder;
	   public  java.lang.String getExecuteOrder(){
	      return executeOrder;
	   }
	   public void setExecuteOrder( java.lang.String executeOrder){
	      this.executeOrder = executeOrder;
	   }
	

		public boolean equals(Object obj){
			boolean eq = false;
			if(obj instanceof AssayType) {
				AssayType c =(AssayType)obj; 			 
				Long thisId = getId();		
				
					if(thisId != null && thisId.equals(c.getId())) {
					   eq = true;
				    }		
				
			}
			return eq;
		}
		
		public int hashCode(){
			int h = 0;
			
			if(getId() != null) {
				h += getId().hashCode();
			}
			
			return h;
	}
	
	
}
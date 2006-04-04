

package gov.nih.nci.calab.domain;
import gov.nih.nci.calab.domain.*;
import gov.nih.nci.system.applicationservice.*;

import java.util.*;

/**
 * <!-- LICENSE_TEXT_START -->
 * <!-- LICENSE_TEXT_END -->
 */
 
  /**

   */

public  abstract class LabFile 
	implements java.io.Serializable 
{
	   private java.lang.Long id;
	   public  java.lang.Long getId(){
	      return id;
	   }
	   public void setId( java.lang.Long id){
	      this.id = id;
	   }
	
	   
	
	   
	   private java.lang.String extension;
	   public  java.lang.String getExtension(){
	      return extension;
	   }
	   public void setExtension( java.lang.String extension){
	      this.extension = extension;
	   }
	
	   
	   private java.lang.String createdBy;
	   public  java.lang.String getCreatedBy(){
	      return createdBy;
	   }
	   public void setCreatedBy( java.lang.String createdBy){
	      this.createdBy = createdBy;
	   }
	
	   
	   private java.util.Date createdDate;
	   public  java.util.Date getCreatedDate(){
	      return createdDate;
	   }
	   public void setCreatedDate( java.util.Date createdDate){
	      this.createdDate = createdDate;
	   }
	
	   
	   private java.lang.String version;
	   public  java.lang.String getVersion(){
	      return version;
	   }
	   public void setVersion( java.lang.String version){
	      this.version = version;
	   }
	

	   private java.lang.String path;
	   public  java.lang.String getPath(){
	      return path;
	   }
	   public void setPath( java.lang.String path){
	      this.path = path;
	   }

		public boolean equals(Object obj){
			boolean eq = false;
			if(obj instanceof InputFile) {
				InputFile c =(InputFile)obj; 			 
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

//	   public  java.lang.Long getId();
//	   public void setId( java.lang.Long id);
//
//	   public  java.lang.String getPath();
//	   public void setPath( java.lang.String path);
//
//	   public  java.lang.String getExtension();
//	   public void setExtension( java.lang.String extension);
//	   
//	   public  java.lang.String getCreatedBy();
//	   public void setCreatedBy( java.lang.String createdBy);
//	   
//	   public  java.util.Date getCreatedDate();
//	   public void setCreatedDate( java.util.Date createdDate);
//
//	   public  java.lang.String getVersion();
//	   public void setVersion( java.lang.String version);
//	   
//
//	   public boolean equals(Object obj);
//		
//	   public int hashCode();
	
	
}
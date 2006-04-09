package gov.nih.nci.calab.service.workflow;

import gov.nih.nci.calab.db.DataAccessProxy;
import gov.nih.nci.calab.db.IDataAccess;
import gov.nih.nci.calab.domain.Aliquot;
import gov.nih.nci.calab.domain.DataStatus;
import gov.nih.nci.calab.service.util.CalabConstants;

import java.util.List;

/**
 * Generalizes Mask functionality for masking Aliquot, File, etc.  
 * @author doswellj
 * @param strType Type of Mask (e.g., aliquot, file, run, etc.)
 * @param strId	  The id associated to the type
 * @param strDescription The mask description associated to the mask type and Id. 
 *
 */
public class MaskService 
{
    
	//This functionality is pending the completed Object Model
	public void setMask(String strType, String strId, String strDescription) throws Exception
    {
        IDataAccess ida = (new DataAccessProxy()).getInstance(IDataAccess.HIBERNATE);

		try {
	        ida.open();
	        
	        if (strType.equals("aliquot"))
	        {
	            //TODO Find Aliquot record based on the strID
				List aliquots = ida.search("from Aliquot aliquot where aliquot.name='" + strId + "'");
				Aliquot aliquot = (Aliquot)aliquots.get(0);
				DataStatus maskStatus = new DataStatus();
				maskStatus.setReason(strDescription);
				maskStatus.setStatus(CalabConstants.MASK_STATUS);
				
				aliquot.setDataStatus(maskStatus);
				ida.store(aliquot); 	        	
	        }
	        if (strType.equals("file"))
	        {
	        	//TODO Find File record based on the its strID
	        	
	        	//TODO Set File Status record to "Masked". 
	        }
			ida.close();
		}catch (Exception e) {
			
			ida.rollback();
			ida.close();
			
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
        	
        	 
    }
    
}

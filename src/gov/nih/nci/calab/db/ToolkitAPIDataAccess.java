package gov.nih.nci.calab.db;

import gov.nih.nci.common.util.HQLCriteria;
import gov.nih.nci.system.applicationservice.ApplicationService;

import java.io.Serializable;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

public class ToolkitAPIDataAccess implements IDataAccess {

	private ApplicationService appService = null;
//	private boolean useLocal;
//	
//	public ToolkitAPIDataAccess(boolean useLocal)
//	{
//	   this.useLocal = useLocal;	
//	}

	public void open() throws Exception
	 {
//		if(useLocal)
//		{
			appService = ApplicationService.getLocalInstance();			
//		}
//		else
//		{
//			appService = ApplicationService.getRemoteInstance(
//												NCIAConfig.getSearchApiUrl() );
//		}
//		appService = ApplicationServiceProvider.getApplicationService();
	}
	public void close() throws Exception {
		appService = null;
	}
	
	public List search(String HQL) throws Exception {
		
		throw new Exception("Not supported yet");
	}

	public List search(Class className, Object o) throws Exception {
		
		return appService.search( className, o );
	}
	
	public List search( Class className, List l ) throws Exception {
		return appService.search( className, l );
	}

	public List search(Class className, Object o, String url)
			throws Exception {
		
		throw new Exception( "Not supported yet");
	}

	public List query(Object criteria, String targetClassName)
			throws Exception {
				
        if(criteria instanceof String)
            return appService.query( new HQLCriteria((String)criteria), targetClassName );
        else if(criteria instanceof DetachedCriteria)
            return appService.query( (DetachedCriteria)criteria, targetClassName );
        else
            return null;
	}

	public void store(Object o) {
		
	}
	
	public Object createObject(Object obj) throws Exception
	{
		return appService.createObject(obj);
	}
	
	public Object updateObject(Object obj) throws Exception
	{
		return appService.updateObject(obj);
	}

	public void removeObject(Object obj)  throws Exception
	{
		appService.removeObject(obj);
	}
	

	public Object load(Class klass, Serializable id) throws Exception
	{
		throw new Exception( "Not supported yet");
	}
}

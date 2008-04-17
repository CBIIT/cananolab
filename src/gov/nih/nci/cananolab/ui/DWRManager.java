package gov.nih.nci.cananolab.ui;

import gov.nih.nci.cananolab.exception.CaNanoLabException;
import gov.nih.nci.cananolab.ui.core.InitSetup;
import gov.nih.nci.cananolab.ui.particle.InitNanoparticleSetup;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.directwebremoting.WebContext;
import org.directwebremoting.WebContextFactory;
import org.directwebremoting.impl.DefaultWebContextBuilder;

public class DWRManager {
	public DWRManager() {
	}

	public String getEntityIncludePage(String entityType)
			throws ServletException, IOException, CaNanoLabException {
		try {
			WebContext wctx = WebContextFactory.get();
			ServletContext appContext = wctx.getServletContext();
			String entityClassName = InitSetup.getInstance().getObjectName(
					entityType, appContext);
			String page = "/particle/composition/body" + entityClassName
					+ "Info.jsp";
			return wctx.forwardToString(page);
		} catch (Exception e) {
			return "";
		}
	}
	
	public String [] getComposingElementTypeOptions(String nanoparticleEntityType) {
		
		DefaultWebContextBuilder dwcb = new DefaultWebContextBuilder();
		org.directwebremoting.WebContext webContext = dwcb.get();
		HttpServletRequest request = webContext.getHttpServletRequest();
	     
        if(nanoparticleEntityType.equals("emulsion")) {
        	try {
        		List<String> emulsionCompList = InitNanoparticleSetup.getInstance().getEmulsionComposingElementTypes(request);
        		String [] eleArray = new String [ emulsionCompList.size()];
        		return emulsionCompList.toArray(eleArray);
        	} catch (Exception e) {
        		System.out.println("getEmulsionComposingElementTypes exception.");
        		e.printStackTrace();
        	}
        } else {
        	try {
        		List<String> compList = InitNanoparticleSetup.getInstance().getComposingElementTypes(request);
        		String [] eleArray = new String [ compList.size()];
        		return compList.toArray(eleArray);
        	} catch (Exception e) {
        		System.out.println("getComposingElementTypes exception.");
        		e.printStackTrace();
        	}
        }
        return null;
    }
}

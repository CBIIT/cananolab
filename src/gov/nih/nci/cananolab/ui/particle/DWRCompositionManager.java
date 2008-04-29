package gov.nih.nci.cananolab.ui.particle;

import gov.nih.nci.cananolab.exception.CaNanoLabException;
import gov.nih.nci.cananolab.ui.core.InitSetup;

import java.io.IOException;
import java.util.SortedSet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.directwebremoting.WebContext;
import org.directwebremoting.WebContextFactory;
import org.directwebremoting.impl.DefaultWebContextBuilder;

/**
 * Work with DWR to set up drop-downs required in the composition pages
 * 
 * @author pansu, cais
 * 
 */
public class DWRCompositionManager {
	public DWRCompositionManager() {
	}

	public String getEntityIncludePage(String entityType, String pagePath)
			throws ServletException, IOException, CaNanoLabException {
		try {
			WebContext wctx = WebContextFactory.get();
			ServletContext appContext = wctx.getServletContext();
			String entityClassName = InitSetup.getInstance().getObjectName(
					entityType, appContext);
			String page = pagePath + "/bodyNew" + entityClassName + "Info.jsp";
			return wctx.forwardToString(page);
		} catch (Exception e) {
			return "";
		}
	}

	public String[] getComposingElementTypeOptions(String nanoparticleEntityType) {

		DefaultWebContextBuilder dwcb = new DefaultWebContextBuilder();
		org.directwebremoting.WebContext webContext = dwcb.get();
		HttpServletRequest request = webContext.getHttpServletRequest();

		if (nanoparticleEntityType.equals("emulsion")) {
			try {
				SortedSet<String> emulsionCompList = InitCompositionSetup
						.getInstance()
						.getEmulsionComposingElementTypes(request);
				String[] eleArray = new String[emulsionCompList.size()];
				return emulsionCompList.toArray(eleArray);
			} catch (Exception e) {
				System.out
						.println("getEmulsionComposingElementTypes exception.");
				e.printStackTrace();
			}
		} else {
			try {
				SortedSet<String> compList = InitSetup.getInstance()
						.getDefaultAndOtherLookupTypes(request,
								"composingElementTypes", "ComposingElement",
								"type", "otherType", false);
				String[] eleArray = new String[compList.size()];
				return compList.toArray(eleArray);
			} catch (Exception e) {
				System.out.println("getComposingElementTypes exception.");
				e.printStackTrace();
			}
		}
		return null;
	}

	public String[] getBiopolymerTypeOptions(String nanoparticleEntityType) {
		if (nanoparticleEntityType.equals("biopolymer")) {
			DefaultWebContextBuilder dwcb = new DefaultWebContextBuilder();
			org.directwebremoting.WebContext webContext = dwcb.get();
			HttpServletRequest request = webContext.getHttpServletRequest();
			try {
				SortedSet<String> typeList = InitSetup.getInstance()
						.getDefaultAndOtherLookupTypes(request,
								"biopolymerTypes", "Biopolymer", "type",
								"otherType", false);
				String[] eleArray = new String[typeList.size()];
				return typeList.toArray(eleArray);

			} catch (Exception e) {
				System.out.println("getBiopolymerTypeOptions exception.");
				e.printStackTrace();
			}
		}
		return new String[] { "" };
	}

	public String[] getWallTypeOptions(String nanoparticleEntityType) {
		if (nanoparticleEntityType.equals("carbon nanotube")) {

			DefaultWebContextBuilder dwcb = new DefaultWebContextBuilder();
			org.directwebremoting.WebContext webContext = dwcb.get();
			HttpServletRequest request = webContext.getHttpServletRequest();
			ServletContext sc = request.getSession().getServletContext();
			try {
				SortedSet<String> typeList = InitSetup.getInstance()
						.getServletContextDefaultLookupTypes(sc, "wallTypes",
								"CarbonNanotube", "wallType");
				String[] eleArray = new String[typeList.size()];
				return typeList.toArray(eleArray);

			} catch (Exception e) {
				System.out.println("getWallTypeOptions exception.");
				e.printStackTrace();
			}
		}
		return new String[] { "" };
	}

	public String[] getAntibodyTypeOptions(String nanoparticleEntityType) {
		if (nanoparticleEntityType.equals("antibody")) {
			DefaultWebContextBuilder dwcb = new DefaultWebContextBuilder();
			org.directwebremoting.WebContext webContext = dwcb.get();
			HttpServletRequest request = webContext.getHttpServletRequest();
			try {
				SortedSet<String> typeList = InitSetup.getInstance()
						.getDefaultAndOtherLookupTypes(request,
								"antibodyTypes", "Antibody", "type",
								"otherType", false);
				String[] eleArray = new String[typeList.size()];
				return typeList.toArray(eleArray);

			} catch (Exception e) {
				System.out.println("getAntibodyTypeOptions exception.");
				e.printStackTrace();
			}
		}
		return new String[] { "" };
	}

	public String[] getAntibodyIsotypeOptions(String nanoparticleEntityType) {
		if (nanoparticleEntityType.equals("antibody")) {
			DefaultWebContextBuilder dwcb = new DefaultWebContextBuilder();
			org.directwebremoting.WebContext webContext = dwcb.get();
			HttpServletRequest request = webContext.getHttpServletRequest();
			try {
				SortedSet<String> typeList = InitSetup.getInstance()
						.getDefaultAndOtherLookupTypes(request,
								"antibodyIsotypes", "Antibody", "isotype",
								"otherIsotype", false);
				String[] eleArray = new String[typeList.size()];
				return typeList.toArray(eleArray);

			} catch (Exception e) {
				System.out.println("getAntibodyIsotypeOptions exception.");
				e.printStackTrace();
			}
		}
		return new String[] { "" };
	}

	public String[] getAntibodySpeciesOptions(String nanoparticleEntityType) {
		if (nanoparticleEntityType.equals("antibody")) {
			DefaultWebContextBuilder dwcb = new DefaultWebContextBuilder();
			org.directwebremoting.WebContext webContext = dwcb.get();
			HttpServletRequest request = webContext.getHttpServletRequest();
			ServletContext sc = request.getSession().getServletContext();
			try {
				SortedSet<String> typeList = InitSetup.getInstance()
						.getServletContextDefaultLookupTypes(sc,
								"antibodySpecies", "Antibody", "species");
				String[] eleArray = new String[typeList.size()];
				return typeList.toArray(eleArray);

			} catch (Exception e) {
				System.out.println("getAntibodySpeciesOptions exception.");
				e.printStackTrace();
			}
		}
		return new String[] { "" };
	}

	public String[] getModalityTypeOptions(String functionType) {
		if (functionType.equals("imaging")) {
			DefaultWebContextBuilder dwcb = new DefaultWebContextBuilder();
			org.directwebremoting.WebContext webContext = dwcb.get();
			HttpServletRequest request = webContext.getHttpServletRequest();
			try {
				SortedSet<String> typeList = InitSetup.getInstance()
						.getDefaultAndOtherLookupTypes(request,
								"modalityTypes", "ImagingFunction", "modality",
								"otherModality", true);
				String[] eleArray = new String[typeList.size()];
				return typeList.toArray(eleArray);
			} catch (Exception e) {
				System.out.println("getModalityTypeOptions exception.");
				e.printStackTrace();
			}
		}
		return new String[] { "" };
	}
}

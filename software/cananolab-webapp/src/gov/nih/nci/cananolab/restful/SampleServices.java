package gov.nih.nci.cananolab.restful;

import gov.nih.nci.cananolab.dto.particle.SampleBean;
import gov.nih.nci.cananolab.restful.sample.SampleBO;
import gov.nih.nci.cananolab.restful.sample.SearchSampleBO;
import gov.nih.nci.cananolab.restful.view.SampleSearchResult;
import gov.nih.nci.cananolab.restful.view.SimpleSampleBean;
import gov.nih.nci.cananolab.ui.form.SearchSampleForm;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;

@Path("/sample")
public class SampleServices {
	private Logger logger = Logger.getLogger(SampleServices.class);
	
	@Inject
	ApplicationContext applicationContext;

	@GET
	@Path("/setup")
	@Produces ("application/json")
    public Response setup(@Context HttpServletRequest httpRequest) {
		System.out.println("In initSetup");		
		
		try { 
			SearchSampleBO searchSampleBO = 
					(SearchSampleBO) applicationContext.getBean("searchSampleBO");
			Map<String, List<String>> dropdownTypeLists = searchSampleBO.setup(httpRequest);

			return Response.ok(dropdownTypeLists).build();
		} catch (Exception e) {
			return Response.ok("Error while setting up drop down lists").build();
		}
	}
	
	
	
	@GET
	@Path("/getCharacterizationByType")
	@Produces ("application/json")
    public Response getCharacterizationByType(@Context HttpServletRequest httpRequest, 
    		@DefaultValue("") @QueryParam("type") String type) {
		
		SearchSampleBO searchSampleBO = 
				(SearchSampleBO) applicationContext.getBean("searchSampleBO");
		
		try {
			List<String> characterizations = searchSampleBO.getCharacterizationByType(httpRequest, type);
			return Response.ok(characterizations).build();
		} catch (Exception e) {
			return Response.ok("Error while getting characterization by type").build();
		}
	}
	
	@POST
	@Path("/searchSample")
	@Produces ("application/json")
	public Response searchSample(@Context HttpServletRequest httpRequest, SearchSampleForm searchForm ) {
		
		try {
			SearchSampleBO searchSampleBO = 
					(SearchSampleBO) applicationContext.getBean("searchSampleBO");
			
			List results = searchSampleBO.search(searchForm, httpRequest);
			//SampleSearchResult resultWrapper = searchSampleBO.createSampleSearchResult(httpRequest, searchForm, results);
			//return (resultWrapper == null) ? Response.ok(results).build() : Response.ok(resultWrapper).build();
			return Response.ok(results).build();

		} catch (Exception e) {
			logger.error(e.getMessage());
			return Response.ok("Error while searching for samples").build();
		}
	}
	
	
	
	@GET
	@Path("/view")
	@Produces ("application/json")
	 public Response view(@Context HttpServletRequest httpRequest, 
	    		@DefaultValue("") @QueryParam("sampleId") String sampleId){
		
		try { 

			SampleBO sampleBO = 
					(SampleBO) applicationContext.getBean("sampleBO");

			SampleBean sampleBean = sampleBO.summaryView(sampleId,httpRequest);
			SimpleSampleBean view = new SimpleSampleBean();
			view.transferSampleBeanForSummaryView(sampleBean);
			
			return Response.ok(view).build();
			
		} catch (Exception e) {
			return Response.ok("Error while viewing the search results").build();
		}
	}
	
	
	@GET
	@Path("/viewDataAvailability")
	@Produces ("application/json")
	 public Response viewDataAvailability(@Context HttpServletRequest httpRequest, 
	    		@DefaultValue("") @QueryParam("sampleId") String sampleId){
		
		try { 

			SampleBO sampleBO = 
					(SampleBO) applicationContext.getBean("sampleBO");

			SampleBean sampleBean = sampleBO.dataAvailabilityView(sampleId,httpRequest);
			
			//SimpleSampleBean view = new SimpleSampleBean();
			//view.transferSampleBeanForSummaryView(sampleBean);
			
			return Response.ok(sampleBean).build();
			
		} catch (Exception e) {
			return Response.ok(e.getMessage()).build();
		}
	}
}
	



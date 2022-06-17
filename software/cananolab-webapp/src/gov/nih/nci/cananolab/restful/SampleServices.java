package gov.nih.nci.cananolab.restful;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import gov.nih.nci.cananolab.dto.common.DataReviewStatusBean;
import gov.nih.nci.cananolab.dto.common.ProtocolBean;
import gov.nih.nci.cananolab.dto.common.PublicationBean;
import gov.nih.nci.cananolab.dto.common.PublicationSummaryViewBean;
import gov.nih.nci.cananolab.dto.particle.AdvancedSampleSearchBean;
import gov.nih.nci.cananolab.dto.particle.characterization.CharacterizationSummaryViewBean;
import gov.nih.nci.cananolab.dto.particle.composition.CompositionBean;
import gov.nih.nci.cananolab.dto.particle.synthesis.SynthesisBean;
import gov.nih.nci.cananolab.restful.bean.LabelValueBean;
import gov.nih.nci.cananolab.restful.publication.PublicationBO;
import gov.nih.nci.cananolab.restful.publication.PublicationManager;
import gov.nih.nci.cananolab.restful.sample.AdvancedSampleSearchBO;
import gov.nih.nci.cananolab.restful.sample.CharacterizationBO;
import gov.nih.nci.cananolab.restful.sample.CharacterizationManager;
import gov.nih.nci.cananolab.restful.sample.CharacterizationResultManager;
import gov.nih.nci.cananolab.restful.sample.CompositionBO;
import gov.nih.nci.cananolab.restful.sample.SampleBO;
import gov.nih.nci.cananolab.restful.sample.SearchSampleBO;
import gov.nih.nci.cananolab.restful.synthesis.SynthesisBO;
import gov.nih.nci.cananolab.restful.util.CommonUtil;
import gov.nih.nci.cananolab.restful.view.SimpleAdvancedSearchResultView;
import gov.nih.nci.cananolab.restful.view.SimpleCharacterizationSummaryViewBean;
import gov.nih.nci.cananolab.restful.view.SimpleCharacterizationsByTypeBean;
import gov.nih.nci.cananolab.restful.view.SimpleCompositionBean;
import gov.nih.nci.cananolab.restful.view.SimplePublicationSummaryViewBean;
import gov.nih.nci.cananolab.restful.view.SimpleSampleBean;
import gov.nih.nci.cananolab.restful.view.SimpleSynthesisBean;
import gov.nih.nci.cananolab.restful.view.edit.SampleEditGeneralBean;
import gov.nih.nci.cananolab.restful.view.edit.SimplePointOfContactBean;
import gov.nih.nci.cananolab.security.utils.SpringSecurityUtil;
import gov.nih.nci.cananolab.service.protocol.ProtocolService;
import gov.nih.nci.cananolab.ui.form.CompositionForm;
import gov.nih.nci.cananolab.ui.form.PublicationForm;
import gov.nih.nci.cananolab.ui.form.SearchSampleForm;
import gov.nih.nci.cananolab.ui.form.SynthesisForm;
import gov.nih.nci.cananolab.util.Constants;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import javax.json.Json;
import javax.json.JsonBuilderFactory;
import javax.json.JsonObject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.beans.factory.annotation.Autowired;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

@Path("/sample")
public class SampleServices {
    private static final Logger logger = Logger.getLogger(SampleServices.class);
    private String spc = "                                                                                                                                            ";

    @Autowired
    private ProtocolService protocolService;

    @GET
	@Path("/setup")
	@Produces ("application/json")
    public Response setup(@Context HttpServletRequest httpRequest)
	{
		logger.debug("In initSetup");		
		
		try { 
			SearchSampleBO searchSampleBO = (SearchSampleBO) SpringApplicationContext.getBean(httpRequest, "searchSampleBO");
			Map<String, List<String>> dropdownTypeLists = searchSampleBO.setup(httpRequest);

			return Response.ok(dropdownTypeLists).build();
		} catch (Exception e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity(CommonUtil.wrapErrorMessageInList("Error while setting up drop down lists")).build();
		}
	}
	
	@GET
	@Path("/getCharacterizationByType")
	@Produces ("application/json")
    public Response getCharacterizationByType(@Context HttpServletRequest httpRequest, 
    		@DefaultValue("") @QueryParam("type") String type)
	{	
		SearchSampleBO searchSampleBO = (SearchSampleBO) SpringApplicationContext.getBean(httpRequest, "searchSampleBO");
		
		try {
			List<String> characterizations = searchSampleBO.getCharacterizationByType(httpRequest, type);
			return Response.ok(characterizations).build();
		} catch (Exception e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity(CommonUtil.wrapErrorMessageInList("Error while getting characterization by type")).build();
		}
	}
	
	@POST
	@Path("/searchSample")
	@Produces ("application/json")
	public Response searchSample(@Context HttpServletRequest httpRequest, SearchSampleForm searchForm )
	{
		try
		{
			SearchSampleBO searchSampleBO = (SearchSampleBO) SpringApplicationContext.getBean(httpRequest, "searchSampleBO");
			
			List results = searchSampleBO.search(searchForm, httpRequest);
			
			Object result = results.get(0);
			if (result instanceof String) {
				logger.debug("Search sample has error: " + results.get(0));
				return Response.status(Response.Status.NOT_FOUND).entity(result).build();
			} else {
				logger.debug("Sample search successful");
				return Response.ok(results).build();
			}

		} catch (Exception e) {
			logger.error(e.getMessage());
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity(CommonUtil.wrapErrorMessageInList("Error while searching for samples: " + e.getMessage())).build();
		}
	}


    @GET
	@Path("/view")
	@Produces ("application/json")
	 public Response view(@Context HttpServletRequest httpRequest,
	    		@DefaultValue("") @QueryParam("sampleId") String sampleId)
	{
		try
		{
			SampleBO sampleBO = (SampleBO) SpringApplicationContext.getBean(httpRequest, "sampleBO");

			SimpleSampleBean sampleBean = sampleBO.summaryView(sampleId,httpRequest);

			return (sampleBean.getErrors().size() == 0) ?
					Response.ok(sampleBean).header("Access-Control-Allow-Credentials", "true")
                        .header("Access-Control-Allow-Origin", "*")
                        .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS")
					.header("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, Authorization").build()
					:Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(sampleBean.getErrors()).build();

		} catch (Exception e) {
			//return Response.ok("Error while viewing the search results").build();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity(CommonUtil.wrapErrorMessageInList("Error while viewing the search results")).build();
		}
	}
	
	
	@GET
	@Path("/viewDataAvailability")
	@Produces ("application/json")
	 public Response viewDataAvailability(@Context HttpServletRequest httpRequest, 
	    		@DefaultValue("") @QueryParam("sampleId") String sampleId)
	{
		try { 

			SampleBO sampleBO = (SampleBO) SpringApplicationContext.getBean(httpRequest, "sampleBO");

			SimpleSampleBean sampleBean = sampleBO.dataAvailabilityView(sampleId,httpRequest);
			
			//SimpleSampleBean view = new SimpleSampleBean();
			//view.transferSampleBeanForSummaryView(sampleBean);
			
			return Response.ok(sampleBean).build();
			
		} catch (Exception e) {
			logger.error(e.getMessage());
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity(CommonUtil.wrapErrorMessageInList("Error while getting data availability data")).build();
		}
	}
	
	@GET
	@Path("/characterizationView")
	@Produces ("application/json")
	 public Response characterizationView(@Context HttpServletRequest httpRequest, 
	    		@DefaultValue("") @QueryParam("sampleId") String sampleId)
	{
		try { 
			CharacterizationBO characterizationBO = (CharacterizationBO) SpringApplicationContext.getBean(httpRequest, "characterizationBO");

			CharacterizationSummaryViewBean charView = characterizationBO.summaryView(sampleId,httpRequest);
			SimpleCharacterizationSummaryViewBean viewBean = new SimpleCharacterizationSummaryViewBean();
			
			List<SimpleCharacterizationsByTypeBean> finalBean = viewBean.transferData(httpRequest, charView, sampleId);
			
			logger.debug("Found " + finalBean.size() + " characterizations for sample: " + sampleId);
			
			return (finalBean.size() == 0) ? Response.status(Response.Status.NOT_FOUND)
					.entity(CommonUtil.wrapErrorMessageInList("There is no characterization with your sample.")).build()
							: Response.ok(finalBean).header("Access-Control-Allow-Credentials", "true")
							.header("Access-Control-Allow-Origin", "*").header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS")
							.header("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, Authorization").build();
			
		} catch (Exception e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity(CommonUtil.wrapErrorMessageInList(e.getMessage())).build();
		}
		
	}
	
	
	@GET
	@Path("/downloadImage")
	@Produces({"image/png", "application/json"})
	 public Response downloadImage(@Context HttpServletRequest httpRequest, @Context HttpServletResponse httpResponse,
	    		@DefaultValue("") @QueryParam("fileId") String fileId){
		try {
			CharacterizationBO characterizationBO = 
					(CharacterizationBO) SpringApplicationContext.getBean(httpRequest, "characterizationBO");
			
			java.io.File file = characterizationBO.download(fileId, httpRequest);
			
			return Response.ok((Object) file).build();
			
		} catch (Exception ioe) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity(CommonUtil.wrapErrorMessageInList(ioe.getMessage())).build();
		}
	}
	
	@GET
	@Path("/download")
	@Produces({"image/png", "application/json"})
	 public Response download(@Context HttpServletRequest httpRequest, @Context HttpServletResponse httpResponse,
	    		@DefaultValue("") @QueryParam("fileId") String fileId){
		try {
			CharacterizationBO characterizationBO = 
					(CharacterizationBO) SpringApplicationContext.getBean(httpRequest, "characterizationBO");
			
			String result = characterizationBO.download(fileId, httpRequest, httpResponse);
			return Response.ok(result).build();
		} 
		
		catch (Exception ioe) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity(CommonUtil.wrapErrorMessageInList(ioe.getMessage())).build();
		}
	}
	
	@GET
	@Path("/exportCharacterizationView")
	@Produces("application/json")
	 public Response exportCharacterizationView(@Context HttpServletRequest httpRequest, @Context HttpServletResponse httpResponse,
	    		@DefaultValue("") @QueryParam("sampleId") String sampleId, @QueryParam("type") String type){
	
		try {
			CharacterizationBO characterizationBO = 
					(CharacterizationBO) SpringApplicationContext.getBean(httpRequest, "characterizationBO");
			
			String result = characterizationBO.summaryExport(sampleId, type, httpRequest, httpResponse);
			return Response.ok(result).build();
		}
		catch (Exception ioe) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity(CommonUtil.wrapErrorMessageInList(ioe.getMessage())).build();
		}
	}
	
	@GET
	@Path("/edit")
	@Produces("application/json")
	 public Response edit(@Context HttpServletRequest httpRequest, 
	    		@DefaultValue("") @QueryParam("sampleId") String sampleId)
	{
		logger.debug("In edit Sample");
		
		if (!SpringSecurityUtil.isUserLoggedIn()) {
			logger.info("User not logged in");
			return Response.status(Response.Status.UNAUTHORIZED).entity(Constants.MSG_SESSION_INVALID).build();
		}
		SampleBO sampleBO = (SampleBO) SpringApplicationContext.getBean(httpRequest, "sampleBO");

		try {
			SampleEditGeneralBean sampleBean = sampleBO.summaryEdit(sampleId,httpRequest);
			return (sampleBean.getErrors().size() == 0) ?
					Response.ok(sampleBean).build() :
						Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(sampleBean.getErrors()).build();
		} 

		catch (Exception ioe) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity(CommonUtil.wrapErrorMessageInList(ioe.getMessage())).build();
		}
	}	
	
	@POST
	@Path("/savePOC")
	@Produces ("application/json")
	public Response savePOC(@Context HttpServletRequest httpRequest, SampleEditGeneralBean simpleEditBean) {
		logger.debug("In savePOC");
		
		SampleEditGeneralBean editBean = null;
		try {
			SampleBO sampleBO = (SampleBO) SpringApplicationContext.getBean(httpRequest, "sampleBO");
			
			if (!SpringSecurityUtil.isUserLoggedIn())
				return Response.status(Response.Status.UNAUTHORIZED)
						.entity(Constants.MSG_SESSION_INVALID).build();
			
			editBean = sampleBO.savePointOfContactList(simpleEditBean, httpRequest);
			List<String> errors = editBean.getErrors();
			logger.debug("SavePOC completed with " + errors.size() + " errors");
			
			//return Response.ok(editBean).build();
			return (errors == null || errors.size() == 0 || 
					(errors.size() > 0 && !editBean.getErrorType().equals("POC"))) ?
					Response.ok(editBean).build() :
						Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errors).build();

		} catch (Exception e) {
			logger.error(e.getMessage());
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity(CommonUtil.wrapErrorMessageInList("Error while saving Point of Contact: " + e.getMessage())).build();
		}
	}
	
	@POST
	@Path("/saveAccess")
	@Produces ("application/json")
	public Response saveAccess(@Context HttpServletRequest httpRequest, SampleEditGeneralBean simpleEdit) {
		logger.debug("In saveAccess");
		try {
			SampleBO sampleBO = 
					(SampleBO) SpringApplicationContext.getBean(httpRequest, "sampleBO");
			
			if (!SpringSecurityUtil.isUserLoggedIn())
				return Response.status(Response.Status.UNAUTHORIZED)
						.entity(Constants.MSG_SESSION_INVALID).build();
			
			SampleEditGeneralBean editBean = sampleBO.saveAccess(simpleEdit, httpRequest);
			List<String> errors = editBean.getErrors();
			return (errors == null || errors.size() == 0) ?
					Response.ok(editBean).build() :
						Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errors).build();

		} catch (Exception e) {
			logger.error(e.getMessage());
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity(CommonUtil.wrapErrorMessageInList("Error while saving Access: " + e.getMessage())).build();
		}
	}
	
	@GET
	@Path("/regenerateDataAvailability")
	@Produces("application/json")
	 public Response regenerateDataAvailability(@Context HttpServletRequest httpRequest, 
	    		@DefaultValue("") @QueryParam("sampleId") String sampleId){
		
		SampleBO sampleBO = 
				(SampleBO) SpringApplicationContext.getBean(httpRequest, "sampleBO");
		
		if (!SpringSecurityUtil.isUserLoggedIn())
			return Response.status(Response.Status.UNAUTHORIZED)
					.entity(Constants.MSG_SESSION_INVALID).build();

		try {
			SampleEditGeneralBean simpleBean = sampleBO.updateDataAvailability(sampleId.trim(), httpRequest);
			return (simpleBean.getErrors().size() == 0) ?
					Response.ok(simpleBean).build()
					:
					Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(simpleBean.getErrors()).build();
		} catch (Exception ioe) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity(CommonUtil.wrapErrorMessageInList(ioe.getMessage())).build();
		}
	}	
	
	@POST
	@Path("/updateSample")
	@Produces ("application/json")
	public Response updateSample(@Context HttpServletRequest httpRequest, SampleEditGeneralBean simpleEdit) {
		logger.info("In updateSample");
		try {
			SampleBO sampleBO = (SampleBO) SpringApplicationContext.getBean(httpRequest, "sampleBO");
			
			if (!SpringSecurityUtil.isUserLoggedIn())
				return Response.status(Response.Status.UNAUTHORIZED).entity(Constants.MSG_SESSION_INVALID).build();
			
			SampleEditGeneralBean editBean = sampleBO.update(simpleEdit, httpRequest);
			List<String> errors = editBean.getErrors();
			return (errors == null || errors.size() == 0) ?
					Response.ok(editBean).build() :
						Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errors).build();

		} catch (Exception e) {
			logger.error(e.getMessage());
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity(CommonUtil.wrapErrorMessageInList("Error while updating sample: " + e.getMessage())).build();
		}
	}
	
	@POST
	@Path("/submitSample")
	@Produces ("application/json")
	public Response submitSample(@Context HttpServletRequest httpRequest, SampleEditGeneralBean simpleEdit) {
		logger.debug("In submitSample");
		try {
			SampleBO sampleBO = 
					(SampleBO) SpringApplicationContext.getBean(httpRequest, "sampleBO");
			
			if (!SpringSecurityUtil.isUserLoggedIn())
				return Response.status(Response.Status.UNAUTHORIZED)
						.entity(Constants.MSG_SESSION_INVALID).build();
			
			SampleEditGeneralBean editBean = sampleBO.submit(simpleEdit, httpRequest);
			List<String> errors = editBean.getErrors();
			
			logger.debug("Sumbit sample completed with " + errors.size() + " errors");
			logger.debug("Submitted sample id: " + editBean.getSampleId());
			return (errors == null || errors.size() == 0) ?
					Response.ok(editBean).build() :
						Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errors).build();

		} catch (Exception e) {
			logger.error(e.getMessage());
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity(CommonUtil.wrapErrorMessageInList("Error while submitting sample: " + e.getMessage())).build();
		}
	}
	
	@POST
	@Path("/copySample")
	@Produces ("application/json")
	public Response copySample(@Context HttpServletRequest httpRequest, SampleEditGeneralBean simpleEdit) {
		logger.debug("In copySample");
		try {
			SampleBO sampleBO = 
					(SampleBO) SpringApplicationContext.getBean(httpRequest, "sampleBO");
			
			if (!SpringSecurityUtil.isUserLoggedIn())
				return Response.status(Response.Status.UNAUTHORIZED)
						.entity(Constants.MSG_SESSION_INVALID).build();
			
			if (simpleEdit.getNewSampleName() == null || simpleEdit.getNewSampleName().length() == 0)
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
						.entity("No new sample name is set for cloning").build();
			
			SampleEditGeneralBean editBean = sampleBO.clone(simpleEdit, httpRequest);
			List<String> errors = editBean.getErrors();
			return (errors == null || errors.size() == 0) ?
					Response.ok(editBean).build() :
						Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errors).build();

		} catch (Exception e) {
			logger.error(e.getMessage());
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity(CommonUtil.wrapErrorMessageInList("Error while copying sample: " + e.getMessage())).build();
		}
	}
	
	@GET
	@Path("/deleteSample")
	@Produces("application/json")
	 public Response deleteSample(@Context HttpServletRequest httpRequest, 
	    		@DefaultValue("") @QueryParam("sampleId") String sampleId){
		logger.debug("In deleteSample");
		try {
			SampleBO sampleBO = 
					(SampleBO) SpringApplicationContext.getBean(httpRequest, "sampleBO");
			
			if (!SpringSecurityUtil.isUserLoggedIn())
				return Response.status(Response.Status.UNAUTHORIZED)
						.entity(Constants.MSG_SESSION_INVALID).build();
			
			String msg = sampleBO.delete(sampleId, httpRequest);
			logger.debug("Delete sample complete: " + msg);
			return (msg == null || msg.startsWith("Error")) ?
					Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(msg).build()
					:
					Response.ok(msg).build();
						

		} catch (Exception e) {
			logger.error(e.getMessage());
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity(CommonUtil.wrapErrorMessageInList("Error while deleting sample: " + e.getMessage())).build();
		}
	}
	
	@GET
	@Path("/deleteSampleFromWorkspace")
	@Produces("application/json")
	 public Response deleteSampleFromWorkspace(@Context HttpServletRequest httpRequest, 
	    		@DefaultValue("") @QueryParam("sampleId") String sampleId){
		logger.debug("In deleteSampleFromWorkspace");
		try {
			SampleBO sampleBO = 
					(SampleBO) SpringApplicationContext.getBean(httpRequest, "sampleBO");
			
			if (!SpringSecurityUtil.isUserLoggedIn())
				return Response.status(Response.Status.UNAUTHORIZED)
						.entity(Constants.MSG_SESSION_INVALID).build();
			
			String msg = sampleBO.deleteSampleById(sampleId, httpRequest);
			logger.debug("Delete sample complete: " + msg);
			return (msg == null || msg.startsWith("Error")) ?
					Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(msg).build()
					:
					Response.ok(msg).build();
						

		} catch (Exception e) {
			logger.error(e.getMessage());
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity(CommonUtil.wrapErrorMessageInList("Error while deleting sample: " + e.getMessage())).build();
		}
	}
	
	
	@POST
	@Path("/deletePOC")
	@Produces("application/json")
	 public Response deletePOC(@Context HttpServletRequest httpRequest, SimplePointOfContactBean simplePOCBean){
		logger.debug("In deletePOC");
		try {
			SampleBO sampleBO = 
					(SampleBO) SpringApplicationContext.getBean(httpRequest, "sampleBO");
			
			if (!SpringSecurityUtil.isUserLoggedIn())
				return Response.status(Response.Status.UNAUTHORIZED)
						.entity(Constants.MSG_SESSION_INVALID).build();
			
			SampleEditGeneralBean editBean = sampleBO.deletePointOfContact(simplePOCBean, httpRequest);
			List<String> errors = editBean.getErrors();
			
			return (errors == null || errors.size() == 0) ?
					Response.ok(editBean).build() :
						Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errors).build();

		} catch (Exception e) {
			logger.error(e.getMessage());
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity(CommonUtil.wrapErrorMessageInList("Error while deleting POC from sample: " + e.getMessage())).build();
		}
	}
	
	@POST
	@Path("/deleteDataAvailability")
	@Produces("application/json")
	 public Response deleteDataAvailability(@Context HttpServletRequest httpRequest, SampleEditGeneralBean simpleSampleBean){
		logger.debug("In deleteDataAvailability");
		try {
			SampleBO sampleBO = 
					(SampleBO) SpringApplicationContext.getBean(httpRequest, "sampleBO");
			
			if (!SpringSecurityUtil.isUserLoggedIn())
				return Response.status(Response.Status.UNAUTHORIZED)
						.entity(Constants.MSG_SESSION_INVALID).build();
			
			SampleEditGeneralBean editBean = sampleBO.deleteDataAvailability(simpleSampleBean, httpRequest);
			List<String> errors = editBean.getErrors();
			
			return (errors == null || errors.size() == 0) ?
					Response.ok(editBean).build() :
						Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errors).build();

		} catch (Exception e) {
			logger.error(e.getMessage());
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity(CommonUtil.wrapErrorMessageInList("Error while deleting data availability from sample: " + e.getMessage())).build();
		}
	}
	
	@GET
	@Path("/submissionSetup")
	@Produces("application/json")
	 public Response submissionSetup(@Context HttpServletRequest httpRequest){
		logger.debug("In submissionSetup");
		
		if (!SpringSecurityUtil.isUserLoggedIn()) {
			logger.info("User not logged in");
			return Response.status(Response.Status.UNAUTHORIZED)
					.entity(Constants.MSG_SESSION_INVALID).build();
		}
		SampleBO sampleBO = (SampleBO) SpringApplicationContext.getBean(httpRequest, "sampleBO");

		try {
			SampleEditGeneralBean sampleBean = sampleBO.setupNew(httpRequest);
			return (sampleBean.getErrors().size() == 0) ?
					Response.ok(sampleBean).build()
					:
						Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(sampleBean.getErrors()).build();
		} 

		catch (Exception ioe) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity(CommonUtil.wrapErrorMessageInList("Error while setting up to submit sample." + ioe.getMessage())).build();
		
		}
	}	
	
	@GET
	@Path("/getSampleNames")
	@Produces("application/json")
	 public Response getSampleNames(@Context HttpServletRequest httpRequest){
		logger.debug("In getSampleNames");
		try {

			SampleBO sampleBO = (SampleBO) SpringApplicationContext.getBean(httpRequest, "sampleBO");
			
			if (!SpringSecurityUtil.isUserLoggedIn())
				return Response.status(Response.Status.UNAUTHORIZED).entity(Constants.MSG_SESSION_INVALID).build();
			
			List<String> names = sampleBO.getMatchedSampleNames(httpRequest, "");
			
			return 	Response.ok(names).build();
		} catch (Exception e) {
			logger.error(e.getMessage());
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity(CommonUtil.wrapErrorMessageInList("Error while deleting sample: " + e.getMessage())).build();
		}
	}
	
	@POST
	@Path("/deleteAccess")
	@Produces("application/json")
	 public Response deleteAccess(@Context HttpServletRequest httpRequest, SampleEditGeneralBean simpleSampleBean){
		logger.debug("In deleteAccess");
		try {

			SampleBO sampleBO = (SampleBO) SpringApplicationContext.getBean(httpRequest, "sampleBO");
			
			if (!SpringSecurityUtil.isUserLoggedIn())
				return Response.status(Response.Status.UNAUTHORIZED)
						.entity(Constants.MSG_SESSION_INVALID).build();
			
			SampleEditGeneralBean editBean = sampleBO.deleteAccess(simpleSampleBean, httpRequest);
			List<String> errors = editBean.getErrors();
			
			return (errors == null || errors.size() == 0) ?
					Response.ok(editBean).build() :
						Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errors).build();

		} catch (Exception e) {
			logger.error(e.getMessage());
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity(CommonUtil.wrapErrorMessageInList("Error while deleting Access from sample: " + e.getMessage())).build();
		}
	}
	
	@POST
	@Path("/submitForReview")
	@Produces("application/json")
	 public Response submitForReview(@Context HttpServletRequest httpRequest, DataReviewStatusBean reviewBean){
		logger.debug("In submitForReview");
		try {

			SampleBO sampleBO = (SampleBO) SpringApplicationContext.getBean(httpRequest, "sampleBO");
			
			if (!SpringSecurityUtil.isUserLoggedIn())
				return Response.status(Response.Status.UNAUTHORIZED).entity(Constants.MSG_SESSION_INVALID).build();
			
			String message = sampleBO.submitForReview(httpRequest, reviewBean);
			
			return Response.ok(message).build();

		} catch (Exception e) {
			logger.error(e.getMessage());
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity(CommonUtil.wrapErrorMessageInList("Error while submitting sample for review: " + e.getMessage())).build();
		}
	}
	
	@GET
	@Path("/getCurrentSampleName")
	@Produces ("application/json")
	 public Response getCurrentSampleName(@Context HttpServletRequest httpRequest, 
	    		@DefaultValue("") @QueryParam("sampleId") String sampleId){
		
		try { 
			SampleBO sampleBO = (SampleBO) SpringApplicationContext.getBean(httpRequest, "sampleBO");

			String sampelName = sampleBO.getCurrentSampleNameInSession(httpRequest, sampleId);

			JsonBuilderFactory factory = Json.createBuilderFactory(null);
			JsonObject value = factory.createObjectBuilder()
					.add("sampleName", sampelName).build();
			
			return 
					Response.ok(value)
/*					.header("Access-Control-Allow-Credentials", "true")
					.header("Access-Control-Allow-Origin", "*")
					.header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS")
					.header("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, Authorization")
*/					.build();
		} catch (Exception e) {
			//return Response.ok("Error while viewing the search results").build();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity(CommonUtil.wrapErrorMessageInList(e.getMessage())).build();
		}
	}
	
	@GET
	@Path("/setupAdvancedSearch")
	@Produces ("application/json")
    public Response setupAdvancedSearch(@Context HttpServletRequest httpRequest)
	{
		logger.debug("In setupAdvancedSearch");		
		
		try { 
			AdvancedSampleSearchBO searchSampleBO = (AdvancedSampleSearchBO) SpringApplicationContext.getBean(httpRequest, "advancedSampleSearchBO");
			
			Map<String, Object> dropdownTypeLists = searchSampleBO.setup(httpRequest);
			//AdvancedSampleSearchBean searchBean = searchSampleBO.setup(httpRequest);

			return Response.ok(dropdownTypeLists).build();
		} catch (Exception e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity(CommonUtil.wrapErrorMessageInList("Error while setting up drop down lists")).build();
		}
	}
	
	@GET
	@Path("/getDecoratedCharacterizationOptions")
	@Produces ("application/json")
    public Response getDecoratedCharacterizationOptions(@Context HttpServletRequest httpRequest, 
    		@DefaultValue("") @QueryParam("charType") String charType) {
		logger.debug("In getDecoratedCharacterizationOptions");		
		
		try { 
			CharacterizationManager characterizationMgr = 
					(CharacterizationManager) SpringApplicationContext.getBean(httpRequest, "characterizationManager");
			
			List<LabelValueBean> charOptions = characterizationMgr.getDecoratedCharacterizationOptions(httpRequest, charType);
					//searchSampleBO.setup(httpRequest);

			return Response.ok(charOptions).build();
		} catch (Exception e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity(CommonUtil.wrapErrorMessageInList("Error while creating characterization options")).build();
		}
	}
	
	@GET
	@Path("/getDecoratedDatumOptions")
	@Produces ("application/json")
    public Response getDecoratedDatumOptions(@Context HttpServletRequest httpRequest, 
    		@DefaultValue("") @QueryParam("charType") String charType, 
    		@DefaultValue("") @QueryParam("charName") String charName) {
		logger.debug("In getDecoratedDatumOptions");		
		
		try { 
			CharacterizationResultManager characterizationMgr = 
					(CharacterizationResultManager) SpringApplicationContext.getBean(httpRequest, "characterizationResultManager");
			
			List<LabelValueBean> datumOptions = characterizationMgr.getDecoratedDatumNameOptions(
					httpRequest, charType, charName, null);

			return Response.ok(datumOptions).build();
		} catch (Exception e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity(CommonUtil.wrapErrorMessageInList("Error while creating characterization datum options")).build();
		}
	}
	
	@GET
	@Path("/getDatumUnitOptions")
	@Produces ("application/json")
    public Response getDatumUnitOptions(@Context HttpServletRequest httpRequest, 
    		@DefaultValue("") @QueryParam("datumName") String datumName) {
		logger.debug("In getDatumUnitOptions");		
		
		try {
			CharacterizationResultManager characterizationResultManager = 
				(CharacterizationResultManager) SpringApplicationContext.getBean(httpRequest, "characterizationResultManager");
			
			List<String> names = characterizationResultManager
					.getColumnValueUnitOptions(httpRequest, datumName, "", false);
					
			return Response.ok(names).header("Access-Control-Allow-Credentials", "true")
						.header("Access-Control-Allow-Origin", "*").header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS")
						.header("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, Authorization").build();
		} catch (Exception e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity(CommonUtil.wrapErrorMessageInList(e.getMessage())).build();
		}
	}
	
	@POST
	@Path("/searchSampleAdvanced")
	@Produces ("application/json")
	public Response searchSampleAdvanced(@Context HttpServletRequest httpRequest, AdvancedSampleSearchBean searchBean )
	{
		
		logger.debug("In searchSampleAdvanced");
		
		try {
			AdvancedSampleSearchBO searchSampleBO = (AdvancedSampleSearchBO) SpringApplicationContext.getBean(httpRequest, "advancedSampleSearchBO");
			
			SimpleAdvancedSearchResultView resultView = searchSampleBO.search(httpRequest, searchBean);
			
			
			if (resultView.getErrors().size() > 0) {
				logger.debug("Search sampel has error: " + resultView.getErrors().get(0));
				return Response.status(Response.Status.NOT_FOUND).entity(resultView.getErrors()).build();
			} else {
				logger.debug("Sample search successful");
				return Response.ok(resultView).build();
			}

		} catch (Exception e) {
			logger.error(e.getMessage());
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity(CommonUtil.wrapErrorMessageInList("Error while searching for samples: " + e.getMessage())).build();
		}
	}
	
	@GET
	@Path("/isSampleEditable")
	@Produces ("application/json")
	 public Response isSampleEditable(@Context HttpServletRequest httpRequest, @DefaultValue("") @QueryParam("sampleId") String sampleId){
		
		try { 
			SampleBO sampleBO =	(SampleBO) SpringApplicationContext.getBean(httpRequest, "sampleBO");

			boolean editable = sampleBO.isSampleEditableByCurrentUser(httpRequest, sampleId);
			
			return Response.ok(editable)
					.header("Access-Control-Allow-Credentials", "true")
					.header("Access-Control-Allow-Origin", "*")
					.header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS")
					.header("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, Authorization").build();
			
		} catch (Exception e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity(CommonUtil.wrapErrorMessageInList("Error while evaluating if sample is editable by current user.")).build();
		}
	}
	
	@GET
	@Path("/summaryExport")
	@Produces ("application/vnd.ms-excel")
	 public Response summaryExport(@Context HttpServletRequest httpRequest, @Context HttpServletResponse httpResponse){
		try {
			AdvancedSampleSearchBO searchSampleBO = (AdvancedSampleSearchBO) SpringApplicationContext.getBean(httpRequest, "advancedSampleSearchBO");
			
			 String result = searchSampleBO.export(httpRequest, httpResponse);
				
			return Response.ok("").build();
			
		} catch (Exception e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(CommonUtil.wrapErrorMessageInList("Error while exporting the file" + e.getMessage())).build();

		}
	}

    /**
     * Export list of results as JSON.
     *
     * @param httpRequest
     * @param httpResponse
     * @param sampleIds
     */
    @GET
    @Path("/fullSampleExportJsonAll")
    @Produces ("application/json")
    public Response fullSampleExportJsonAll(@Context HttpServletRequest httpRequest, @Context HttpServletResponse httpResponse,
                                        @DefaultValue("") @QueryParam("sampleIds") String sampleIds)
    {
        try
        {
            String[] idlist = sampleIds.split( "\\s*,\\s*" );
            String jsonData = buildAllJson(httpRequest, httpResponse, idlist);
            return Response.ok(jsonData).header("Access-Control-Allow-Credentials", "true")
                    .header("Access-Control-Allow-Origin", "*")
                    .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS")
                    .header("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, Authorization").build();
        }
        catch( Exception e )
        {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(CommonUtil.wrapErrorMessageInList("Error sending JSON to client: " + e.getMessage())).build();
        }
    }

    /**
     * Export list of results as XML
     *
     * @param httpRequest
     * @param httpResponse
     * @param sampleIds  Comma separated list of Sample IDs
     */
    @GET
    @Path("/fullSampleExportXmlAll")
    @Produces ("application/xml")
    public Response fullSampleExportXmlAll(@Context HttpServletRequest httpRequest, @Context HttpServletResponse httpResponse,
                                        @DefaultValue("") @QueryParam("sampleIds") String sampleIds)
    {
        try
        {
            String[] idlist = sampleIds.split( "\\s*,\\s*" );
            String jsonData =  "{\n \"csNanoLabData\": " + buildAllJson(httpRequest, httpResponse, idlist) +"\n}\n";
            String xmlData = jsonToXml( jsonData );
            if( xmlData == null){
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity( "Error creating valid XML" ) .build();
            }

            return Response.ok(xmlData).header("Access-Control-Allow-Credentials", "true")
                    .header("Access-Control-Allow-Origin", "*")
                    .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS")
                    .header("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, Authorization").build();
        }
        catch( Exception e )
        {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(CommonUtil.wrapErrorMessageInList("Error sending XML to client: " + e.getMessage())).build();
        }
    }


    /**
     * Export list of results as JSON.
     *
     * @param httpRequest
     * @param httpResponse
     * @param sampleIds
     */
    @POST
    @Path("/fullSampleExportJsonAll")
    @Produces ("application/json")
    public Response fullSampleExportJsonAll(@Context HttpServletRequest httpRequest, String sampleIds, @Context HttpServletResponse httpResponse)
    {
    	sampleIds = sampleIds.replaceFirst("\\{\"sampleIds\":\"", "");
		sampleIds = sampleIds.replace("\"}", "");
		try
        {
            String[] idlist = sampleIds.split( "\\s*,\\s*" );
            String jsonData = buildAllJson(httpRequest, httpResponse, idlist);
            return Response.ok(jsonData).header("Access-Control-Allow-Credentials", "true")
                    .header("Access-Control-Allow-Origin", "*")
                    .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS")
                    .header("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, Authorization").build();
        }
        catch( Exception e )
        {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(CommonUtil.wrapErrorMessageInList("Error sending JSON to client: " + e.getMessage())).build();
        }
    }

    /**
     * Export list of results as XML
     *
     * @param httpRequest
     * @param httpResponse
     * @param sampleIds  Comma separated list of Sample IDs
     */
    @POST
    @Path("/fullSampleExportXmlAll")
    @Produces ("application/xml")
    public Response fullSampleExportXmlAll(@Context HttpServletRequest httpRequest, String sampleIds, @Context HttpServletResponse httpResponse)
    {
		sampleIds = sampleIds.replaceFirst("\\{\"sampleIds\":\"", "");
		sampleIds = sampleIds.replace("\"}", "");
		try
        {
            String[] idlist = sampleIds.split( "\\s*,\\s*" );
            String jsonData =  "{\n \"csNanoLabData\": " + buildAllJson(httpRequest, httpResponse, idlist) +"\n}\n";
            String xmlData = jsonToXml( jsonData );
            if( xmlData == null){
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity( "Error creating valid XML" ) .build();
            }

            return Response.ok(xmlData).header("Access-Control-Allow-Credentials", "true")
                    .header("Access-Control-Allow-Origin", "*")
                    .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS")
                    .header("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, Authorization").build();
        }
        catch( Exception e )
        {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(CommonUtil.wrapErrorMessageInList("Error sending XML to client: " + e.getMessage())).build();
        }
    }

    /**
     * Build JSON from a list of IDs.
     *
     * @param httpRequest
     * @param httpResponse
     * @param idlist These are Sample IDs, or pubmedId with the suffix _pubmed
     * @return
     */
    private String buildAllJson( HttpServletRequest httpRequest, HttpServletResponse httpResponse, String[] idlist ){
       StringBuilder jsonData = new StringBuilder( "[\n" );

        for( String id : idlist )
        {
            if( id.endsWith( "_pubmed" ))
            {
                jsonData.append( buildPubJson( httpRequest, id.replaceAll( "_pubmed$", ""), 2 ) + "\n," );
            }

            else if( id.endsWith( "_protocol" ))
            {
                // jsonData.append( buildProtocolJson( httpRequest, id.replaceAll( "_protocol$", ""), 2 ) + "\n," );
            }
            else
            {
                jsonData.append( buildSampleJson( httpRequest, httpResponse, id , 2) + "\n," );
            }

        }
        // Remove trailing ","
        if (jsonData.length() > 0) {
            jsonData.setLength(jsonData.length() - 1);
        }
       jsonData.append("\n]");

        try{
            new JsonParser().parse(jsonData.toString());
        }
        catch( JsonSyntaxException jse){
            logger.error("Not a valid Json String:" + jse.getMessage());
        }

        return jsonData.toString();
    }


    /**
     * Build one Pbublication string as JSON
     *
     * @param httpRequest
     * @param publicationId
     * @param indent
     * @return
     */
    private String buildPubJson( HttpServletRequest httpRequest, String publicationId, int indent )
    {
        StringBuilder jasonData = new StringBuilder( "{\n    \"publication\":\n    " );
        try
        {
            PublicationManager pubManager = (PublicationManager) SpringApplicationContext.getBean( httpRequest, "publicationManager" );
            Gson gson = new GsonBuilder().setPrettyPrinting().create();

            PublicationForm form = new PublicationForm();
            PublicationBean pubBean = new PublicationBean();
            form.setPublicationBean( pubBean );
            pubManager.retrievePubMedInfo( publicationId, form, httpRequest );

            jasonData.append( doIndent( gson.toJson( pubBean ), indent * 4 ) );
            jasonData.append( "\n}\n" );
            return jasonData.toString();
        }
        catch( Exception e )
        {
            e.printStackTrace();
            return e.getMessage();
        }
    }


    // This does not work, the Autowired protocolService is null
    private String buildProtocolJson( HttpServletRequest httpRequest, String protocolId, int indent )
    {
        StringBuilder jasonData = new StringBuilder( "{\n    \"protocol\":\n    " );
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        try
        {
            ProtocolBean protocolBean = protocolService.findProtocolById( protocolId );
            jasonData.append( doIndent( gson.toJson( protocolBean ), indent * 4 ) );
            jasonData.append( "\n}\n" );
            return jasonData.toString();
        }
        catch( Exception e )
        {
            e.printStackTrace();
            return e.getMessage();
        }
    }

    /**
     * Used to cleanup formatting when combining multiple JSON samples into one.
     *
     * @param str
     * @param n
     * @return
     */
    private String doIndent( String str, int n){
        // str = str.replaceAll( "^", sp.substring( 0,n )  );
        return str.replaceAll( "[\n\r]", "\n" + spc.substring( 0, n )  );
    }


    /**
     * Build one JSON  Sample record.
     *
     * @param httpRequest
     * @param httpResponse
     * @param sampleId
     * @return One JSON record.
     */
    private String buildSampleJson( HttpServletRequest httpRequest, HttpServletResponse httpResponse, String sampleId , int indent){
       // Opening brace of results
        StringBuilder jasonData = new StringBuilder( spc.substring( 0,indent ));
        jasonData.append( "{\n");
        jasonData.append( spc, 0, indent * 2 );
        jasonData.append( "\"sample\": {\n");
        jasonData.append( spc, 0, indent * 3 );

        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        // General Info
        SampleBO sampleBO = (SampleBO) SpringApplicationContext.getBean(httpRequest, "sampleBO");
        String sampleBeanData = "";
        try
        {
            sampleBeanData = sampleBO.summaryExport( sampleId, "all", httpRequest, httpResponse );
            sampleBeanData = doIndent(sampleBeanData, indent * 4);
        }
        catch( Exception e )
        {
            e.printStackTrace();
        }
        jasonData.append( "\"GeneralInfo\":" );
        jasonData.append(  sampleBeanData );
        jasonData.append(  "\n" );


        // Composition
        CompositionForm form;
        CompositionBO compositionBO;
        CompositionBean compBean;
        SimpleCompositionBean view = null;
        try
        {
            form = new CompositionForm();
            form.setSampleId(sampleId);
            compositionBO = (CompositionBO) SpringApplicationContext.getBean( httpRequest, "compositionBO" );
            compBean = compositionBO.summaryView( form, httpRequest );

            view = new SimpleCompositionBean();
            view.transferCompositionBeanForSummaryView( compBean );
        }
        catch( Exception e )
        {
            e.printStackTrace();
        }
        jasonData.append( ",\"Composition\": " );
        jasonData.append( doIndent( gson.toJson(view), indent * 4 ) );


        // Characterization
        CharacterizationSummaryViewBean charView = null;
        SimpleCharacterizationSummaryViewBean viewBean = null;
        List<SimpleCharacterizationsByTypeBean> finalBean = null;

        try {
            CharacterizationBO characterizationBO = ( CharacterizationBO) SpringApplicationContext.getBean(httpRequest, "characterizationBO" );
            charView = characterizationBO.summaryView( sampleId,httpRequest );
            viewBean = new SimpleCharacterizationSummaryViewBean();
            finalBean = viewBean.transferData( httpRequest, charView, sampleId );
        }
        catch( Exception e )
        {
            logger.error("Error exporting characterization", e);
        }

        jasonData.append( ",\"characterization\": "  );
        jasonData.append( doIndent( gson.toJson(finalBean), indent * 4 ) );
//TODO Add back when Synthesis added to main
//        //Synthesis
//		SynthesisBO synthesisBO;
//		SynthesisBean synthesisBean;
//		SimpleSynthesisBean simpleSynthesisBean=null;
//		SynthesisForm synthesisForm;
//		try{
//			synthesisForm = new SynthesisForm();
//			synthesisForm.setSampleId(sampleId);
//			synthesisBO = (SynthesisBO) SpringApplicationContext.getBean(httpRequest, "synthesisBO");
//			synthesisBean = synthesisBO.summaryView(synthesisForm, httpRequest);
//			simpleSynthesisBean = new SimpleSynthesisBean();
//			simpleSynthesisBean.transferSynthesisForSummaryView(synthesisBean);
//		} catch(Exception e){
//			logger.error("Error exporting synthesis", e);
//		}
//
//		jasonData.append(",\"synthesis\": ");
//		jasonData.append(doIndent(gson.toJson(simpleSynthesisBean), indent *4));
//		jasonData.append(doIndent(gson.toJson( new SimpleSynthesisExportBean( simpleSynthesisBean )), indent *4));


        // Publication
        PublicationBO publicationBO = null;
        PublicationSummaryViewBean pubBean = null;
        SimplePublicationSummaryViewBean publicationView = null;
        try {
            publicationBO = (PublicationBO) SpringApplicationContext.getBean( httpRequest, "publicationBO" );
            pubBean = publicationBO.summaryView( sampleId, httpRequest );
            publicationView = new SimplePublicationSummaryViewBean();
            publicationView.transferPublicationBeanForSummaryView( pubBean );
        }
        catch( Exception e )
        {
            e.printStackTrace();
        }

        jasonData.append( ",\"Publication\": " );
        jasonData.append( doIndent( gson.toJson(publicationView), indent * 4 ) );

        // Closing brace
        jasonData.append( "    }\n        }\n" );
       return jasonData.toString();
    }


    /**
     * Convert (formatted) JSON to XML
     *
     * @param jsonText
     * @return  Formatted XML
     */
    private String jsonToXml( String jsonText ) {
        try {
            jsonText = jsonText.replaceAll( "\\\\u00[0-1][1-9a-fA-F]", " " );
            JSONObject jso = new JSONObject( cleanJson(jsonText) );
            String xml = XML.toString(jso, "caNanoLabXml");
            StreamSource source = new StreamSource(new StringReader(xml));
            StringWriter writer = new StringWriter();
            StreamResult result = new StreamResult(writer);
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty( OutputKeys.ENCODING, "UTF-8");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.transform(source, result);
            return xmlFormat( writer.toString());
        }
        catch (Exception e)
        {
            System.err.println( "Error converting JSON to XML: " + cleanJson(jsonText) );
            // e.printStackTrace();
            return null;
        }
    }


    /**
     * Some JSON elements do not translate cleanly to XML
     *
     * @param json
     * @return modified JSON String
     *
     * @TODO Character reference "&#x2" is an invalid XML character.
     */
    private String cleanJson( String json){
	    StringBuilder cleanData = new StringBuilder(  );
        try
        {
            BufferedReader bufReader = new BufferedReader(new StringReader(json));
            String line;

            while( (line=bufReader.readLine()) != null )
            {
                if(line.matches("^\\s*\\\"[^:]+\\s.*:.*\\[")){
                    String pattern = "^(.*):.*";
                    String temp = line.replaceAll(pattern, "$1");
                    pattern = "^(\\s*)(.*)";
                    String data = temp.replaceAll( pattern, "$2");
                    temp = temp.replaceAll( pattern, "$1" + data.replaceAll( " ", "_" ));
                    line = temp  + ": [";
                }
                cleanData.append( line );
                cleanData.append( "\n" );
            }
        }
        catch( IOException e )
        {
            e.printStackTrace();
        }
        return cleanData.toString();
    }


    /**
     * Format (indent etc.) XML
     *
     * @param xml
     * @return formatted xml
     */
    private String xmlFormat(String xml) {
        try {
            // Turn xml string into a document
            Document document = DocumentBuilderFactory.newInstance()
                    .newDocumentBuilder()
                    .parse(new InputSource(new ByteArrayInputStream(xml.getBytes(StandardCharsets.UTF_8))));

            // Remove whitespaces outside tags
            document.normalize();
            XPath xPath = XPathFactory.newInstance().newXPath();
            NodeList nodeList = (NodeList) xPath.evaluate("//text()[normalize-space()='']",
                    document,
                    XPathConstants.NODESET);

            for (int i = 0; i < nodeList.getLength(); ++i) {
                Node node = nodeList.item(i);
                node.getParentNode().removeChild(node);
            }

            // Setup transformer options
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            transformerFactory.setAttribute("indent-number", 4);
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");

            // Return the formatted xml string
            StringWriter stringWriter = new StringWriter();
            transformer.transform(new DOMSource(document), new StreamResult(stringWriter));
            return stringWriter.toString();
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }
}

package gov.nih.nci.cananolab.ui.core;

import gov.nih.nci.cananolab.domain.common.File;
import gov.nih.nci.cananolab.dto.common.FileBean;
import gov.nih.nci.cananolab.dto.common.UserBean;
import gov.nih.nci.cananolab.dto.particle.SampleBean;
import gov.nih.nci.cananolab.exception.FileException;
import gov.nih.nci.cananolab.exception.SecurityException;
import gov.nih.nci.cananolab.service.common.FileService;
import gov.nih.nci.cananolab.service.common.impl.FileServiceLocalImpl;
import gov.nih.nci.cananolab.service.common.impl.FileServiceRemoteImpl;
import gov.nih.nci.cananolab.service.sample.SampleService;
import gov.nih.nci.cananolab.service.sample.impl.SampleServiceLocalImpl;
import gov.nih.nci.cananolab.service.sample.impl.SampleServiceRemoteImpl;
import gov.nih.nci.cananolab.util.Constants;
import gov.nih.nci.cananolab.util.PropertyUtils;

import java.io.FileInputStream;
import java.net.URL;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.validator.DynaValidatorForm;

/**
 * Base action for all annotation actions
 * 
 * @author pansu
 * 
 */
public abstract class BaseAnnotationAction extends AbstractDispatchAction {

	/**
	 * setupSample() will retrieve a SampleBean based on the sampleId which is
	 * in request/form. And then check user's access privilege, throws Exception
	 * if user doesn't have privilege. Otherwise, set visibility of Primary POC
	 * of sample based on user's privilege. Finally, set the SampleBean in
	 * request object.
	 * 
	 * @param theForm
	 * @param request
	 * @param location
	 * @return SampleBean
	 * @throws Exception
	 *             if user is not allowed to access the sample
	 */
	public SampleBean setupSample(DynaValidatorForm theForm,
			HttpServletRequest request, String location) throws Exception {
		String sampleId = request.getParameter("sampleId");
		if (sampleId != null) {
			theForm.set("sampleId", sampleId);
		} else {
			sampleId = (String) request.getAttribute("sampleId");
			if (sampleId == null) {
				sampleId = theForm.getString("sampleId");
			}
		}
		HttpSession session = request.getSession();
		UserBean user = (UserBean) session.getAttribute("user");
		SampleService service = null;
		if (Constants.LOCAL_SITE.equals(location)) {
			service = new SampleServiceLocalImpl();
		} else {
			String serviceUrl = InitSetup.getInstance().getGridServiceUrl(
					request, location);
			service = new SampleServiceRemoteImpl(serviceUrl);
		}
		// TODO remove this
		// service = new SampleServiceRemoteImpl(
		// "http://localhost:8080/wsrf/services/cagrid/CaNanoLabService");
		SampleBean sampleBean = service.findSampleById(sampleId, user);
		if (sampleBean != null) {
			sampleBean.setLocation(location);
		}
		request.setAttribute("theSample", sampleBean);
		return sampleBean;
	}

	protected void saveFilesToFileSystem(List<FileBean> files, UserBean user)
			throws Exception {
		// save file data to file system and set visibility
		FileService fileService = new FileServiceLocalImpl();
		for (FileBean fileBean : files) {
			fileService.writeFile(fileBean, user);
		}
	}

	public boolean loginRequired() {
		return false;
	}

	public boolean canUserExecute(UserBean user) throws SecurityException {
		return true;
	}

	// check for cases where delete can't happen
	protected boolean checkDelete(HttpServletRequest request,
			ActionMessages msgs, String id) throws Exception {
		return true;
	}

	/**
	 * Download action to handle file downloading and viewing
	 * 
	 * @param
	 * @return
	 */
	public ActionForward download(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String fileId = request.getParameter("fileId");
		UserBean user = (UserBean) request.getSession().getAttribute("user");
		String location = request.getParameter(Constants.LOCATION);
		FileService fileService = null;
		FileBean fileBean = null;
		String serviceUrl = null;
		if (location.equals(Constants.LOCAL_SITE)) {
			fileService = new FileServiceLocalImpl();
		} else {
			// CQL2HQL filters out subclasses, disabled the filter
			serviceUrl = InitSetup.getInstance().getGridServiceUrl(request,
					location);
			fileService = new FileServiceRemoteImpl(serviceUrl);
		}
		fileBean = fileService.findFileById(fileId, user);
		if (fileBean != null) {
			if (fileBean.getDomainFile().getUriExternal()) {
				response.sendRedirect(fileBean.getDomainFile().getUri());
				return null;
			}
		}
		if (!Constants.LOCAL_SITE.equals(location)) {
			// assume grid service is located on the same server and port as
			// webapp
			URL localURL = new URL(request.getRequestURL().toString());
			String actionPath = localURL.getPath();

			URL remoteUrl = new URL(serviceUrl);
			String remoteServerHostUrl = remoteUrl.getProtocol() + "://"
					+ remoteUrl.getHost() + ":" + remoteUrl.getPort();
			String remoteDownloadUrl = remoteServerHostUrl + actionPath
					+ "?dispatch=download" + "&fileId=" + fileId + "&location="
					+ Constants.LOCAL_SITE;
			// remote URL
			response.sendRedirect(remoteDownloadUrl);
			return null;
		}

		String fileRoot = PropertyUtils.getProperty(
				Constants.FILEUPLOAD_PROPERTY, "fileRepositoryDir");
		java.io.File dFile = new java.io.File(fileRoot + java.io.File.separator
				+ fileBean.getDomainFile().getUri());
		if (dFile.exists()) {
			response.setContentType("application/octet-stream");
			response.setHeader("Content-disposition", "attachment;filename=\""
					+ fileBean.getDomainFile().getName() + "\"");
			response.setHeader("cache-control", "Private");

			java.io.InputStream in = new FileInputStream(dFile);
			java.io.OutputStream out = response.getOutputStream();

			byte[] bytes = new byte[32768];

			int numRead = 0;
			while ((numRead = in.read(bytes)) > 0) {
				out.write(bytes, 0, numRead);
			}
			out.close();
		} else {
			ActionMessages msgs = new ActionMessages();
			ActionMessage msg = new ActionMessage("error.noFile");
			msgs.add(ActionMessages.GLOBAL_MESSAGE, msg);
			this.saveErrors(request, msgs);
			throw new FileException("File " + fileBean.getDomainFile().getUri()
					+ " doesn't exist on the server");
		}
		return null;
	}

	protected SampleBean[] prepareCopy(HttpServletRequest request,
			DynaValidatorForm theForm, SampleBean oldSampleBean)
			throws Exception {
		String[] otherSamples = (String[]) theForm.get("otherSamples");
		if (otherSamples.length == 0) {
			return null;
		}
		SampleBean[] sampleBeans = new SampleBean[otherSamples.length];
		SampleService sampleService = new SampleServiceLocalImpl();
		int i = 0;
		UserBean user = (UserBean) request.getSession().getAttribute("user");
		for (String other : otherSamples) {
			SampleBean sampleBean = sampleService.findSampleByName(other, user);
			sampleBean.setVisibilityGroups(oldSampleBean.getVisibilityGroups());
			i++;
		}
		return sampleBeans;
	}

	protected boolean validateFileBean(HttpServletRequest request,
			ActionMessages msgs, FileBean fileBean) {

		boolean noErrors = true;
		if (fileBean == null) {
			return noErrors;
		}
		File File = fileBean.getDomainFile();
		if (File.getTitle().length() == 0) {
			ActionMessage msg = new ActionMessage("errors.required",
					"file title");
			msgs.add(ActionMessages.GLOBAL_MESSAGE, msg);
			this.saveErrors(request, msgs);
			noErrors = false;
		}

		if (File.getType().length() == 0) {
			ActionMessage msg = new ActionMessage("errors.required",
					"file type");
			msgs.add(ActionMessages.GLOBAL_MESSAGE, msg);
			this.saveErrors(request, msgs);
			noErrors = false;
		}

		if (File.getUriExternal()) {
			if (fileBean.getExternalUrl() == null
					|| fileBean.getExternalUrl().trim().length() == 0) {
				ActionMessage msg = new ActionMessage("errors.required",
						"external url");
				msgs.add(ActionMessages.GLOBAL_MESSAGE, msg);
				this.saveErrors(request, msgs);
				noErrors = false;
			}
		} else {
			// all empty
			if ((fileBean.getUploadedFile() == null || fileBean
					.getUploadedFile().toString().trim().length() == 0)
					&& (fileBean.getExternalUrl() == null || fileBean
							.getExternalUrl().trim().length() == 0)
					&& (fileBean.getDomainFile() == null || fileBean
							.getDomainFile().getName() == null)) {
				ActionMessage msg = new ActionMessage("errors.required",
						"uploaded file");
				msgs.add(ActionMessages.GLOBAL_MESSAGE, msg);
				this.saveErrors(request, msgs);
				noErrors = false;
				// the case that user switch from url to upload file, but no
				// file is selected
			} else if ((fileBean.getUploadedFile() == null || fileBean
					.getUploadedFile().getFileName().length() == 0)
					&& fileBean.getExternalUrl() != null
					&& fileBean.getExternalUrl().trim().length() > 0) {
				ActionMessage msg = new ActionMessage("errors.required",
						"uploaded file");
				msgs.add(ActionMessages.GLOBAL_MESSAGE, msg);
				this.saveErrors(request, msgs);
				noErrors = false;
			}
		}
		return noErrors;
	}

	/**
	 * Retrieve a value from request by name in the order of Parameter - Request
	 * Attribute
	 * 
	 * @param request
	 * @param name
	 * @return
	 */
	protected Object getValueFromRequest(HttpServletRequest request, String name) {
		Object value = request.getParameter(name);
		if (value == null) {
			value = request.getAttribute(name);
		}
		return value;
	}
}

/**
 * The caNanoLab Software License, Version 1.5
 *
 * Copyright 2006 SAIC. This software was developed in conjunction with the National
 * Cancer Institute, and so to the extent government employees are co-authors, any
 * rights in such works shall be subject to Title 17 of the United States Code,
 * section 105.
 * 
 */
package gov.nih.nci.cananolab.ui.admin;

/**
 *
 * @author houyh
 */
import gov.nih.nci.cananolab.dto.common.UserBean;
import gov.nih.nci.cananolab.exception.SecurityException;
import gov.nih.nci.cananolab.ui.core.AbstractDispatchAction;
import gov.nih.nci.cananolab.ui.security.InitSecuritySetup;
import gov.nih.nci.cananolab.util.Constants;
import gov.nih.nci.cananolab.util.ExportUtils;
import gov.nih.nci.cananolab.util.PropertyUtils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.DynaActionForm;
import org.apache.struts.upload.FormFile;

public class AdministrationAction extends AbstractDispatchAction {
	
	private static Logger logger = 
		Logger.getLogger(AdministrationAction.class);

	/**
	 * Action to show site preference page.
	 *
	 * @param
	 * @return
	 */
	public ActionForward sitePreference(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		DynaActionForm theForm = (DynaActionForm) form;
		String siteName = PropertyUtils.getProperty(
				Constants.FILEUPLOAD_PROPERTY, Constants.SITE_NAME);
		String siteLogo = PropertyUtils.getProperty(
				Constants.FILEUPLOAD_PROPERTY, Constants.SITE_LOGO);
		theForm.set(Constants.SITE_NAME, siteName);
		theForm.set(Constants.SITE_LOGO, siteLogo);
		
		if (logger.isInfoEnabled()) {
			logger.info("siteName = " + siteName);
		}
		
		return mapping.findForward("sitePreference");
	}

	/**
	 * Action to clear site preferences: remove site and site logo.
	 *
	 * @param
	 * @return
	 */
	public ActionForward clearAll(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ActionMessages messages = new ActionMessages();
		saveMessages(request, messages);
		
		// Remove site name, set failure message if failed.
		this.setSiteName(null, messages);
		
		// Remove site logo, set failure message if failed.
		this.setSiteLogo(null, messages);
		
		File logoFile = new File(getLogoFileName());
		if (logoFile.exists()) {
			logoFile.delete();
		}
		
		// Set success message in request if everything is fine now.
		if (messages.size() == 0) {
			ActionMessage msg = new ActionMessage("admin.sitePreference.success");
			messages.add(ActionMessages.GLOBAL_MESSAGE, msg);
		}
		
		return mapping.findForward("sitePreference");
	}

	/**
	 * Action to update site preference settings.
	 *
	 * @param
	 * @return
	 */
	public ActionForward update(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		DynaActionForm theForm = (DynaActionForm) form;
		String siteName = (String) theForm.getString(Constants.SITE_NAME);
		ActionMessages messages = new ActionMessages();
		saveMessages(request, messages);
		OutputStream out = null;
		
		// Save site name, if fail, set failure message;
		this.setSiteName(siteName, messages);
		
		// Save site logo, if fail, set failure message;
		FormFile file = (FormFile) theForm.get(Constants.SITE_LOGO);
		File siteLogo = new File(getLogoFileName());
		String logoFilename = null;
		byte[] data = null;
		if (file != null) {
			data = file.getFileData();
			logoFilename = file.getFileName();
		}
		
		// If user doesn't upload a file, remove current logo file.
		if (data == null || data.length == 0) {
			this.setSiteLogo(null, messages);
			if (siteLogo.exists()) {
				siteLogo.delete();
			}
		} else {
			// If the new logo file is too large, set error msg to warn user.
			if (data.length > Constants.MAX_LOGO_SIZE) {
				ActionMessage msg = 
					new ActionMessage("admin.sitePreference.error.logoOversize", Constants.MAX_LOGO_SIZE);
				messages.add(ActionMessages.GLOBAL_MESSAGE, msg);
			} else {
				// Save the uploaded logo file name in property.
				if (this.setSiteLogo(logoFilename, messages)) {
			        try {
						out = new BufferedOutputStream(new FileOutputStream(siteLogo));
			            out.write(data);
			        } catch (Exception e) {
						ActionMessage msg = 
							new ActionMessage("admin.sitePreference.error.siteLogo");
						messages.add(ActionMessages.GLOBAL_MESSAGE, msg);
			        } finally {
			            //Close the BufferedOutputStream
		                if (out != null) {
				            try {
			                    out.flush();
			                    out.close();
				            } catch (IOException e) {
				            }
		                }
			        }		
				}
			}
		}
		
		// Set success message in request if everything is fine now.
		if (messages.size() == 0) {
			ActionMessage msg = new ActionMessage("admin.sitePreference.success");
			messages.add(ActionMessages.GLOBAL_MESSAGE, msg);
		}
		
		return mapping.findForward("success");
	}

	/**
	 * Download action to handle file downloading and viewing
	 *
	 * @param
	 * @return
	 */
	public ActionForward getSiteLogo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		File siteLogo = new File(getLogoFileName());
		if (siteLogo.exists() && siteLogo.length() > 0) {
			response.setContentType(ExportUtils.IMAGE_CONTENT_TYPE);
			response.setHeader(ExportUtils.CONTENT_DISPOSITION, ExportUtils.ATTACHMENT
					+ Constants.SITE_LOGO_FILENAME + "\"");
			response.setHeader(ExportUtils.CACHE_CONTROL, ExportUtils.PRIVATE);

			InputStream in = new BufferedInputStream(new FileInputStream(siteLogo));
			OutputStream out = response.getOutputStream();

			byte[] bytes = new byte[Constants.MAX_LOGO_SIZE];
			int numRead = 0;
			try {
				while ((numRead = in.read(bytes)) > 0) {
					out.write(bytes, 0, numRead);
				}
			} finally {
				try {
					out.close();
				} catch (Exception e) {
				}
			}
		}
		return null;
	}

	/**
	 * Get the file name of site logo.
	 *
	 * @return file name of site logo.
	 */
	public static String getLogoFileName() {
		StringBuilder sb = new StringBuilder();
		String fileRoot = PropertyUtils.getProperty(
				Constants.FILEUPLOAD_PROPERTY, Constants.FILE_REPOSITORY_DIR);
		sb.append(fileRoot).append(File.separator).append(Constants.SITE_LOGO_FILENAME);
		
		return sb.toString();
	}
	
	/**
	 * Set site name in property, set failure message if failed.
	 * 
	 * @param siteName
	 * @param messages
	 */
	private boolean setSiteName(String siteName, ActionMessages messages) {
		boolean success = PropertyUtils.setProperty(
				Constants.FILEUPLOAD_PROPERTY, Constants.SITE_NAME, siteName);
		
		if (!success) {
			ActionMessage msg = new ActionMessage("admin.sitePreference.error.siteName");
			messages.add(ActionMessages.GLOBAL_MESSAGE, msg);
		}
		
		return success;
	}
	
	/**
	 * Set site logo in property, set failure message if failed.
	 * 
	 * @param siteName
	 * @param messages
	 */
	private boolean setSiteLogo(String siteLogo, ActionMessages messages) {
		boolean success = PropertyUtils.setProperty(
				Constants.FILEUPLOAD_PROPERTY, Constants.SITE_NAME, siteLogo);
		
		if (!success) {
			ActionMessage msg = new ActionMessage("admin.sitePreference.error.siteLogo");
			messages.add(ActionMessages.GLOBAL_MESSAGE, msg);
		}
		
		return success;
	}
	
	public boolean loginRequired() {
		return true;
	}

	public boolean canUserExecute(UserBean user)
			throws SecurityException {
		return InitSecuritySetup.getInstance().userHasCreatePrivilege(user,
				Constants.CSM_PG_PARTICLE);
	}

}

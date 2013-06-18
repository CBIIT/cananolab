/*L
 *  Copyright SAIC
 *  Copyright SAIC-Frederick
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/cananolab/LICENSE.txt for details.
 */

package gov.nih.nci.calab.ui.core;

import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.tiles.TilesRequestProcessor;

/**
 * 
 * This class extends the default Tiles RequesetProcessor to check and make sure
 * session is not expired before every request. This is useful for directing
 * requests to the log in page when action forms involve indexed properties and
 * session is expired.
 * 
 * @author pansu
 */
public class CustomRequestProcessor extends TilesRequestProcessor {
	protected ActionForm processActionForm(
			javax.servlet.http.HttpServletRequest request,
			javax.servlet.http.HttpServletResponse response,
			ActionMapping mapping) {
		HttpSession session = request.getSession();
		if (session.isNew() && (request.getServletPath().startsWith("/create"))) {
			return null;
		} else {
			return super.processActionForm(request, response, mapping);
		}
	}
}

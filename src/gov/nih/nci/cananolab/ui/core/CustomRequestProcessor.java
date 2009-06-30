package gov.nih.nci.cananolab.ui.core;

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
//		HttpSession session = request.getSession();
//		String dispatch = request.getParameter("dispatch");
//		// private dispatch
//		boolean privateDispatch = false;
//		for (String theDispatch : Constants.PRIVATE_DISPATCHES) {
//			if (dispatch != null && dispatch.startsWith(theDispatch)) {
//				privateDispatch = true;
//				break;
//			}
//		}
//		if (session.isNew()
//				&& (dispatch == null || privateDispatch)) {
//			return null;
//		} else {
//			return super.processActionForm(request, response, mapping);
//		}
		return super.processActionForm(request, response, mapping);
	}
}

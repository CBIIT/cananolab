package gov.nih.nci.calab.dto.inventory;

import org.displaytag.decorator.TableDecorator;

/**
 * This decorator is used to for decorate different properties of a sample container to be
 * shown properly in the view page using display tag lib.
 * 
 * @author pansu
 * 
 */
public class ContainerDecorator extends TableDecorator {
	public String getContainerId() throws Exception {
		ContainerBean container = (ContainerBean) getCurrentRowObject();
		String checked = "";
		if (getListIndex() == 0) {
			checked = "checked";
		}
		String containerIdstr = "<input type='radio' name='containerId' value='"
				+ container.getContainerId() + "' " + checked + ">";

		return containerIdstr;
	}
}

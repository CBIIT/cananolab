package gov.nih.nci.calab.dto.characterization.composition;

import gov.nih.nci.calab.domain.nano.characterization.physical.composition.ParticleComposition;
import gov.nih.nci.calab.service.util.CaNanoLabConstants;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents properties of a base composition where there is one
 * core, one or more shells, one or more coatings to be shown in the view page.
 * 
 * @author pansu
 * 
 */
public class BaseCoreShellCoatingBean extends CompositionBean {
	private ComposingElementBean core = new ComposingElementBean();;

	private List<ComposingElementBean> shells = new ArrayList<ComposingElementBean>();

	private List<ComposingElementBean> coatings = new ArrayList<ComposingElementBean>();

	private String numberOfShells;

	private String numberOfCoatings;

	public BaseCoreShellCoatingBean() {

		this.core.setElementType(CaNanoLabConstants.CORE);
		getComposingElements().add(this.core);
	}

	public BaseCoreShellCoatingBean(ParticleComposition doComp) {
		super(doComp);
		this.core = new ComposingElementBean();
		for (ComposingElementBean element : getComposingElements()) {
			if (element.getElementType().equals(CaNanoLabConstants.CORE)) {
				this.core = element;
			} else if (element.getElementType().equals(
					CaNanoLabConstants.COATING)) {
				this.coatings.add(element);
			} else if (element.getElementType()
					.equals(CaNanoLabConstants.SHELL)) {
				this.shells.add(element);
			}
		}
		this.setNumberOfShells(this.shells.size() + "");
		this.setNumberOfCoatings(this.coatings.size() + "");
	}

	public List<ComposingElementBean> getCoatings() {
		return this.coatings;
	}

	public void setCoatings(List<ComposingElementBean> coatings) {
		this.coatings = coatings;
	}

	public ComposingElementBean getCore() {
		return this.core;
	}

	public void setCore(ComposingElementBean core) {
		this.core = core;
	}

	public List<ComposingElementBean> getShells() {
		return this.shells;
	}

	public void setShells(List<ComposingElementBean> shells) {
		this.shells = shells;
	}

	public String getNumberOfCoatings() {
		return this.numberOfCoatings;
	}

	public void setNumberOfCoatings(String numberOfCoatings) {
		this.numberOfCoatings = numberOfCoatings;
	}

	public String getNumberOfShells() {
		return this.numberOfShells;
	}

	public void setNumberOfShells(String numberOfShells) {
		this.numberOfShells = numberOfShells;
	}
}

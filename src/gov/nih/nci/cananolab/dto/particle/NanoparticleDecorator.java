package gov.nih.nci.cananolab.dto.particle;

import gov.nih.nci.cananolab.exception.CaNanoLabException;
import gov.nih.nci.cananolab.ui.core.InitSetup;
import gov.nih.nci.cananolab.util.SortableName;
import gov.nih.nci.cananolab.util.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.SortedSet;
import java.util.TreeSet;

import org.displaytag.decorator.TableDecorator;

/**
 * This decorator is used to for decorate different properties of a nanoparticle
 * to be shown properly in the view page using display tag lib.
 * 
 * @author pansu
 * 
 */
public class NanoparticleDecorator extends TableDecorator {
	public SortableName getEditParticleURL() {
		ParticleBean particle = (ParticleBean) getCurrentRowObject();
		String particleId = particle.getDomainParticleSample().getId()
				.toString();
		String particleName = particle.getDomainParticleSample().getName();
		String editParticleURL = "submitNanoparticleSample.do?dispatch=setupUpdate&particleId="
				+ particleId;
		String link = "<a href=" + editParticleURL + ">" + particleName
				+ "</a>";

		SortableName sortableLink = new SortableName(particleName, link);
		return sortableLink;
	}

	public SortableName getViewParticleURL() {
		ParticleBean particle = (ParticleBean) getCurrentRowObject();
		String particleId = particle.getDomainParticleSample().getId()
				.toString();
		String particleName = particle.getDomainParticleSample().getName();
		String viewParticleURL = "submitNanoparticleSample.do?dispatch=setupView&particleId="
				+ particleId + "&location=" + particle.getLocation();
		;
		String link = "<a href=" + viewParticleURL + ">" + particleName
				+ "</a>";
		SortableName sortableLink = new SortableName(particleName, link);
		return sortableLink;
	}

	public SortableName getRemoteViewURL() throws UnsupportedEncodingException {
		ParticleBean particle = (ParticleBean) getCurrentRowObject();

		String particleName = URLEncoder.encode(particle
				.getDomainParticleSample().getName(), "UTF-8");
		String remoteViewURL = "remoteNanoparticleGeneralInfo.do?dispatch=view"
				+ "&particleName=" + particleName + "&gridNodeHost=";
		String link = "<a href=" + remoteViewURL + ">" + particleName + "</a>";
		SortableName sortableLink = new SortableName(particleName, link);
		return sortableLink;
	}

	public String getKeywordStr() {
		ParticleBean particle = (ParticleBean) getCurrentRowObject();
		String keywordsStr = particle.getKeywordsStr();
		String[] strs = keywordsStr.split("\r\n");
		return StringUtils.join(strs, "<br>");
	}

	public String getCompositionStr() throws CaNanoLabException {
		ParticleBean particle = (ParticleBean) getCurrentRowObject();
		SortedSet<String> compEntityNames = new TreeSet<String>();
		for (String name : particle.getFunctionalizingEntityClassNames()) {
			String displayName = InitSetup.getInstance().getDisplayName(name,
					this.getPageContext().getServletContext());
			if (displayName.length() == 0) {
				compEntityNames.add(name);
			} else {
				compEntityNames.add(displayName);
			}
		}
		for (String name : particle.getNanoparticleEntityClassNames()) {
			String displayName = InitSetup.getInstance().getDisplayName(name,
					this.getPageContext().getServletContext());
			if (displayName.length() == 0) {
				compEntityNames.add(name);
			} else {
				compEntityNames.add(displayName);
			}
		}
		return StringUtils.join(compEntityNames, "<br>");
	}

	public String getFunctionStr() throws CaNanoLabException {
		ParticleBean particle = (ParticleBean) getCurrentRowObject();
		SortedSet<String> functionNames = new TreeSet<String>();
		for (String name : particle.getFunctionClassNames()) {
			String displayName = InitSetup.getInstance().getDisplayName(name,
					this.getPageContext().getServletContext());
			functionNames.add(displayName);
		}
		return StringUtils.join(functionNames, "<br>");
	}

	public String getCharacterizationStr() throws CaNanoLabException {
		ParticleBean particle = (ParticleBean) getCurrentRowObject();
		SortedSet<String> charNames = new TreeSet<String>();
		for (String name : particle.getCharacterizationClassNames()) {
			String displayName = InitSetup.getInstance().getDisplayName(name,
					this.getPageContext().getServletContext());
			charNames.add(displayName);
		}
		return StringUtils.join(charNames, "<br>");
	}
}

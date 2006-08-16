/**
 * 
 */
package gov.nih.nci.calab.domain.nano.function;

import gov.nih.nci.calab.domain.nano.particle.BaseNanoParticle;

/**
 * @author zengje
 *
 */
public class Function {
	private Long id;
	private String functionType;
	private String activationMethod;
	
	private BaseNanoParticle nanoParticle;
	
	/**
	 * 
	 */
	public Function() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getActivationMethod() {
		return activationMethod;
	}

	public void setActivationMethod(String activationMethod) {
		this.activationMethod = activationMethod;
	}

	public String getFunctionType() {
		return functionType;
	}

	public void setFunctionType(String functionType) {
		this.functionType = functionType;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public BaseNanoParticle getNanoParticle() {
		return nanoParticle;
	}

	public void setNanoParticle(BaseNanoParticle nanoParticle) {
		this.nanoParticle = nanoParticle;
	}

	
}

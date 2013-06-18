/*L
 *  Copyright SAIC
 *  Copyright SAIC-Frederick
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/cananolab/LICENSE.txt for details.
 */

/**
 * 
 */
package gov.nih.nci.cananolab.exception;

/**
 * @author lethai
 *
 */
public class StudyException extends BaseException {
	
	private static final long serialVersionUID = 1234567890L;

	public StudyException() {
		super("Exception working with particles");
	}

	public StudyException(String message) {
		super(message);
	}

	public StudyException(String message, Throwable cause) {
		super(message, cause);
	}

	public StudyException(Throwable cause) {
		super(cause);
	}

}

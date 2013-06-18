/*L
 *  Copyright SAIC
 *  Copyright SAIC-Frederick
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/cananolab/LICENSE.txt for details.
 */

package gov.nih.nci.cananolab.exception;

/**
 * @author pansu
 * 
 */
public class DataAvailabilityException extends BaseException {

	private static final long serialVersionUID = 1234567890L;

	public DataAvailabilityException() {
		super("Exception working with particles data availability");
	}

	public DataAvailabilityException(String message) {
		super(message);
	}

	public DataAvailabilityException(String message, Throwable cause) {
		super(message, cause);
	}

	public DataAvailabilityException(Throwable cause) {
		super(cause);
	}
}

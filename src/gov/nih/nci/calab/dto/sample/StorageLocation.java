/*L
 *  Copyright SAIC
 *  Copyright SAIC-Frederick
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/cananolab/LICENSE.txt for details.
 */

package gov.nih.nci.calab.dto.sample;

import gov.nih.nci.calab.service.util.StringUtils;

/**
 * This class captures all properties of a storage location.
 * 
 * @author pansu
 * 
 */
/* CVS $Id: StorageLocation.java,v 1.2 2007-11-08 20:41:35 pansu Exp $ */

public class StorageLocation {
	private String lab = "";

	private String room = "";

	private String freezer = "";

	private String shelf = "";

	private String rack = "";

	private String box = "";

	public StorageLocation() {
	}

	public StorageLocation(StorageLocation loc) {
		this.lab = loc.getLab();
		this.room = loc.getRoom();
		this.freezer = loc.getFreezer();
		this.shelf = loc.getShelf();
		this.rack = loc.getRack();
		this.box = loc.getBox();
	}

	public StorageLocation(String lab, String room, String freezer,
			String shelf, String rack, String box) {

		this.lab = lab;
		this.room = room;
		this.freezer = freezer;
		this.shelf = shelf;
		this.rack = rack;
		this.box = box;
	}

	public StorageLocation(String room, String freezer, String shelf, String box) {

		this.room = room;
		this.freezer = freezer;
		this.shelf = shelf;
		this.box = box;
	}

	public String getBox() {
		return this.box;
	}

	public void setBox(String box) {
		this.box = box;
	}

	public String getFreezer() {
		return this.freezer;
	}

	public void setFreezer(String freezer) {
		this.freezer = freezer;
	}

	public String getLab() {
		return this.lab;
	}

	public void setLab(String lab) {
		this.lab = lab;
	}

	public String getRack() {
		return this.rack;
	}

	public void setRack(String rack) {
		this.rack = rack;
	}

	public String getRoom() {
		return this.room;
	}

	public void setRoom(String room) {
		this.room = room;
	}

	public String getShelf() {
		return this.shelf;
	}

	public void setShelf(String shelf) {
		this.shelf = shelf;
	}

	public String toString() {
		String locationStr = "";
		String lab = (getLab() == null) ? "" : "Lab " + getLab();
		String room = (getRoom() == null) ? "" : "Room " + getRoom();
		String freezer = (getFreezer() == null) ? "" : "Freezer "
				+ getFreezer();
		String shelf = (getShelf() == null) ? "" : "Shelf " + getShelf();
		String rack = (getRack() == null) ? "" : "Rack " + getRack();
		String box = (getBox() == null) ? "" : "Box " + getBox();
		String[] strs = new String[] { lab, room, freezer, shelf, rack, box };
		locationStr = StringUtils.join(strs, "-");
		return locationStr;
	}
}

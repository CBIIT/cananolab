package gov.nih.nci.cananolab.domain.common;
// Generated Oct 17, 2017 2:18:23 PM by Hibernate Tools 4.0.1.Final

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

/**
 * Keyword generated by hbm2java
 */
public class Keyword implements java.io.Serializable {

	private Long id;
	private String name;
	private Set fileCollection = new HashSet(0);

	public Keyword() {
	}

	public Keyword(String name, Set fileCollection) {
		this.name = name;
		this.fileCollection = fileCollection;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}


	public Set getFileCollection() {
		return this.fileCollection;
	}

//	@ManyToMany(cascade = { CascadeType.ALL })
//	@JoinTable(name = "keyword_file", joinColumns = { @JoinColumn(name = "keyword_pk_id") }, inverseJoinColumns = { @JoinColumn(name = "file_pk_id") })
	public void setFileCollection(Set fileCollection) {
		this.fileCollection = fileCollection;
	}

}

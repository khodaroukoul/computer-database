package fr.excilys.formation.cdb.dto;

import java.io.Serializable;

public class CompanyDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private int id;
	private String name;

	public CompanyDTO() {
	}

	public CompanyDTO(int id) {
		this.id = id;
	}

	public CompanyDTO(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "CompanyDTO [name=" + name + "]";
	}

}

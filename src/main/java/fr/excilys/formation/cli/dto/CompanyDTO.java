package fr.excilys.formation.cli.dto;

import java.io.Serializable;

public class CompanyDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private int id;
	private String name;

	public CompanyDTO() {
		super();
	}

	public CompanyDTO(int id) {
		this.id = id;
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

package fr.excilys.formation.cli.dto;

import java.io.Serializable;

public class ComputerDTO implements Serializable{

	private static final long serialVersionUID = 1L;

	private int id;
	private String name;
	private String introduced;
	private String discontinued;
	private CompanyDTO company;

	public ComputerDTO(String name, String introduced, String discontinued, CompanyDTO company) {
		this.name = name;
		this.introduced = introduced;
		this.discontinued = discontinued;
		this.company = company;
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
	
	public String getIntroduced() {
		return introduced;
	}
	
	public void setIntroduced(String introduced) {
		this.introduced = introduced;
	}
	
	public String getDiscontinued() {
		return discontinued;
	}
	
	public void setDiscontinued(String discontinued) {
		this.discontinued = discontinued;
	}
	
	public CompanyDTO getCompany() {
		return company;
	}
	
	public void setCompany(CompanyDTO company) {
		this.company = company;
	}

	@Override
	public String toString() {
		return "ComputerDTO [name=" + name + ", introduced=" + introduced + ", discontinued=" + discontinued
				+ ", company=" + company + "]";
	}
}

package fr.excilys.formation.cli.beans;

public class Company {
	private int id;
	private String name;
	
	public Company() {
		super();
	}

	public Company(int id) {
		this.id = id;
	}

	public Company(int id, String name) {
		super();
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
		return "Company [id=" + id + ", name=" + name + "]";
	}
	

}
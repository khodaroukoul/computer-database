package fr.excilys.formation.cli.beans;

public class Company {
	private int id;
	private String name;

	private Company(CompanyBuilder builder) {
		this.id = builder.id;
		this.name = builder.name;
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

	public static class CompanyBuilder {
		private int id;
		private String name;

		public CompanyBuilder() {
		}

		public CompanyBuilder setId(int id) {
			this.id = id;
			return this;
		}

		public CompanyBuilder setName(String name) {
			this.name = name;
			return this;
		}

		public Company build() {
			Company company =  new Company(this);
			return company;
		}
	}
}

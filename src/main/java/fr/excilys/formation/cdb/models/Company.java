package fr.excilys.formation.cdb.models;

public class Company {
	private int id;
	private String name;

	private Company(Builder builder) {
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
		return "Company [name=" + name + "]";
	}

	public static class Builder {
		private int id;
		private String name;

		public Builder() {
		}

		public Builder setId(int id) {
			this.id = id;
			return this;
		}

		public Builder setName(String name) {
			this.name = name;
			return this;
		}

		public Company build() {
			Company company =  new Company(this);
			return company;
		}
	}
}

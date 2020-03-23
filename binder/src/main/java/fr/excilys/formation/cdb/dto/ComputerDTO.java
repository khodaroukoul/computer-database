package fr.excilys.formation.cdb.dto;

public class ComputerDTO {

    private int id;
    private final String name;
    private final String introduced;
    private final String discontinued;
    private final CompanyDTO company;

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

    public String getIntroduced() {
        return introduced;
    }

    public String getDiscontinued() {
        return discontinued;
    }

    public CompanyDTO getCompany() {
        return company;
    }

    @Override
    public String toString() {
        return "ComputerDTO [name=" + name + ", introduced=" + introduced + ", discontinued=" + discontinued
                + ", company=" + company + "]";
    }
}
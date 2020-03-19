package fr.excilys.formation.cdb.model;

import java.time.LocalDate;
import javax.persistence.*;

@Entity
@Table(name = "computer")
public class Computer {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "introduced")
    private LocalDate introduced;

    @Column(name = "discontinued")
    private LocalDate discontinued;

    @ManyToOne(targetEntity = Company.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "company_id", referencedColumnName = "id")
    private Company company;

    public Computer() {
    }

    private Computer(Builder builder) {
        this.name = builder.name;
        this.introduced = builder.introduced;
        this.discontinued = builder.discontinued;
        this.company = builder.company;
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

    public LocalDate getIntroduced() {
        return introduced;
    }

    public void setIntroduced(LocalDate introduced) {
        this.introduced = introduced;
    }

    public LocalDate getDiscontinued() {
        return discontinued;
    }

    public void setDiscontinued(LocalDate discontinued) {
        this.discontinued = discontinued;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    @Override
    public String toString() {
        return "Computer [id=" + id + ", name=" + name
                + ", introduced=" + introduced + ", discontinued=" + discontinued
                + ", company=" + company.getName() + "]";
    }

    public static class Builder {
        private final String name;
        private LocalDate introduced;
        private LocalDate discontinued;
        private Company company;

        public Builder(String name) {
            this.name = name;
        }

        public Builder setIntroduced(LocalDate introduced) {
            this.introduced = introduced;
            return this;
        }

        public Builder setDiscontinued(LocalDate discontinued) {
            this.discontinued = discontinued;
            return this;
        }

        public Builder setCompany(Company company) {
            this.company = company;
            return this;
        }

        public Computer build() {
            Computer computer = new Computer(this);
            return computer;
        }
    }
}
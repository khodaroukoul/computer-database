package fr.excilys.formation.cdb.daoTest;

import fr.excilys.formation.cdb.configuration.HibernateConfig;
import fr.excilys.formation.cdb.configuration.SpringConfig;
import fr.excilys.formation.cdb.service.CompanyService;
import fr.excilys.formation.cdb.service.ComputerService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {SpringConfig.class, HibernateConfig.class})
public class DAOTest {

    @Autowired
    ComputerService computerService;
    @Autowired
    CompanyService companyService;

    @Test
    public void testFindComputer() {
        assertTrue(computerService.findById(5).isPresent());
        assertTrue(computerService.findById(0).isEmpty());
    }

    @Test
    public void testDeleteComputer() {
        assertEquals(computerService.deleteFromConsole(2), 1);
        assertEquals(computerService.deleteFromConsole(0), 0);
    }

    @Test
    public void testCountComputer() {
        assertEquals(50, computerService.countAll());
    }

    @Test
    public void testFindCompany() {
        assertTrue(companyService.findById(5).isPresent());
        assertTrue(companyService.findById(0).isEmpty());
    }

    @Test
    public void testDeleteCompany() {
        assertTrue(companyService.deleteCompany("2"));
        assertFalse(companyService.deleteCompany("0"));
    }
}

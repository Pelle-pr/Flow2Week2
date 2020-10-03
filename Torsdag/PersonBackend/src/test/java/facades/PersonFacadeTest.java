package facades;

import dto.PersonAllDTO;
import dto.PersonDTO;
import entities.Address;
import utils.EMF_Creator;
import entities.Person;
import exceptions.MissingInputException;
import exceptions.PersonNotFoundException;
import java.lang.reflect.Array;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

//Uncomment the line below, to temporarily disable this test
//@Disabled
public class PersonFacadeTest {

    private static EntityManagerFactory emf;
    private static PersonFacade facade;
    private static Person p1 = new Person("Pelle", "Rasmussen", "12345678");
    private static Person p2 = new Person("John", "Snow", "87654321");
    
    
    public PersonFacadeTest() {
    }

    @BeforeAll
    public static void setUpClass() {
       emf = EMF_Creator.createEntityManagerFactoryForTest();
       facade = PersonFacade.getPersonFacade(emf);
    }

    @AfterAll
    public static void tearDownClass() {
//        Clean up database after test is done or use a persistence unit with drop-and-create to start up clean on every test
    }

    // Setup the DataBase in a known state BEFORE EACH TEST
    //TODO -- Make sure to change the script below to use YOUR OWN entity class
    @BeforeEach
    public void setUp() {
        EntityManager em = emf.createEntityManager();
        Address a1 = new Address("Smedeløkken 66", 3770, "Allinge");
        Address a2 = new Address("Storegade 5", 3700, "Rønne");
        p1.setAdress(a1);
        p2.setAdress(a2);
        
        try {
            em.getTransaction().begin();
            em.createNamedQuery("Person.deleteAllRows").executeUpdate();
            em.persist(p1);
            em.persist(p2);

            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @AfterEach
    public void tearDown() {
//        Remove any data after each test was run
    }

    // TODO: Delete or change this method 
    @Test
    public void testAFacadeMethod() {
        assertEquals(2, facade.getPersonCount(), "Expects two rows in the database");
    }
    
    @Test
    public void testGetAllPerson(){
 
        
        
       
     }
    
    @Test
    public void addPersonTest() throws MissingInputException{
        
        facade.addPerson("Bent", "Hansen", "666", "Djævlevej 666", 666, "Helvede");
        
        assertEquals(3, facade.getPersonCount());
    }
    @Test
    public void DeletePersonTest () throws PersonNotFoundException{
        
        facade.deletePerson(p1.getId());
       assertEquals(1, facade.getPersonCount());
              
        
    }
    @Test
    public void getPersonById () throws PersonNotFoundException{
        
        PersonDTO p = facade.getPerson(p1.getId());
        
        assertTrue(p.getfName().equals("Pelle"));
        
        
    }
    
    @Test
    public void editPersonTest() throws PersonNotFoundException, MissingInputException{
        
        PersonDTO p = new PersonDTO(p1);
        p.setfName("Johnny");
        facade.editPerson(p);
        
        PersonDTO p2 = facade.getPerson(p1.getId());
        assertTrue(p2.getfName().equals("Johnny"));
        
        
    }
    

}

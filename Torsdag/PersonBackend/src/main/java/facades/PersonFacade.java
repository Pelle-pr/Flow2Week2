package facades;

import dto.PersonAllDTO;
import dto.PersonDTO;
import entities.Address;
import entities.Person;
import exceptions.MissingInputException;
import exceptions.PersonNotFoundException;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 *
 * Rename Class to a relevant name Add add relevant facade methods
 */
public class PersonFacade implements IPersonFacade {

    private static PersonFacade instance;
    private static EntityManagerFactory emf;

    //Private Constructor to ensure Singleton
    private PersonFacade() {
    }

    /**
     *
     * @param _emf
     * @return an instance of this facade class.
     */
    public static PersonFacade getPersonFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new PersonFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    //TODO Remove/Change this before use
    public long getPersonCount() {
        EntityManager em = emf.createEntityManager();
        try {
            long personCount = (long) em.createQuery("SELECT COUNT(p) FROM Person p").getSingleResult();
            return personCount;
        } finally {
            em.close();
        }

    }

    @Override
    public PersonDTO addPerson(String fName, String lName, String Phone, String street, int zip, String city ) throws MissingInputException {

        if (fName.isEmpty() || lName.isEmpty() || Phone.isEmpty()) {
            throw new MissingInputException("Something is missing in the input");
        }
        EntityManager em = emf.createEntityManager();
        Person p = new Person(fName, lName, Phone);
        Address address = new Address(street,zip,city);
        
        p.setAdress(address);
        
        try {
            em.getTransaction().begin();
            em.persist(p);
            em.getTransaction().commit();
            PersonDTO pDTO = new PersonDTO(p);
            return pDTO;

        } finally {
            em.close();
        }

    }

    @Override
    public PersonDTO deletePerson(long id) throws PersonNotFoundException {

        EntityManager em = emf.createEntityManager();
        Person p = em.find(Person.class, id);
        if (p == null) {
            throw new PersonNotFoundException("Nothing to delete here");
        }
        try {
            em.getTransaction().begin();
            PersonDTO dto = new PersonDTO(p);
            em.remove(p);
            em.getTransaction().commit();

            return dto;
        } finally {
            em.close();
        }

    }

    @Override
    public PersonDTO getPerson(long id) throws PersonNotFoundException {

        EntityManager em = emf.createEntityManager();

        TypedQuery query = em.createQuery("SELECT p FROM Person p WHERE p.id = :id", Person.class);
        query.setParameter("id", id);

        try {
            PersonDTO personDTO = new PersonDTO((Person) query.getSingleResult());
            return personDTO;
        } catch (Exception e) {
            throw new PersonNotFoundException("No person with provided id found");
        }

    }

    @Override
    public PersonAllDTO getAllPersons() {
        EntityManager em = emf.createEntityManager();

        TypedQuery<Person> query = em.createQuery("SELECT p FROM Person p", Person.class);

        List<Person> result = query.getResultList();

        PersonAllDTO dto = new PersonAllDTO(result);

        return dto;

    }

    @Override
    public PersonDTO editPerson(PersonDTO p) throws PersonNotFoundException, MissingInputException {
        if(p.getfName().isEmpty() || p.getlName().isEmpty()){
            throw new MissingInputException("Something is missing in the input");
        }
        EntityManager em = emf.createEntityManager();
        
        try {
            em.getTransaction().begin();
            Person person = em.find(Person.class, p.getId());
            if (person == null) {
                throw new PersonNotFoundException("No person with that ID is editable");
            }
            person.setFname(p.getfName());
            person.setLname(p.getlName());
            person.setPhone(p.getPhone());
            person.setLastEdited(new Date());
            Address adress = new Address(p.getStreet(),p.getZip(),p.getCity());
            person.setAdress(adress);
            em.getTransaction().commit();

            PersonDTO dto = new PersonDTO(person);
            return dto;

        } finally {
            em.close();
        }
    }

}

package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dto.PersonDTO;
import exceptions.MissingInputException;
import exceptions.PersonNotFoundException;
import utils.EMF_Creator;
import facades.PersonFacade;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;


//Todo Remove or change relevant parts before ACTUAL use
@Path("person")
public class PersonResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
    
    //An alternative way to get the EntityManagerFactory, whithout having to type the details all over the code
    //EMF = EMF_Creator.createEntityManagerFactory(DbSelector.DEV, Strategy.CREATE);
    
    private static final PersonFacade FACADE =  PersonFacade.getPersonFacade(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
            
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String demo() {
        return "{\"msg\":\"Hello World\"}";
    }
    @Path("count")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String getRenameMeCount() {
        long count = FACADE.getPersonCount();
        //System.out.println("--------------->"+count);
        return "{\"count\":"+count+"}";  //Done manually so no need for a DTO
    }
    
    @Path("all")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String getAllPersons(){
        
        return GSON.toJson(FACADE.getAllPersons());
        
    }
    
    @Path("{id}")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String getByID (@PathParam("id") int id) throws PersonNotFoundException{
        
        return GSON.toJson(FACADE.getPerson(id));
        
    }
    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public String addPerson(String person) throws MissingInputException{
        
        PersonDTO p = GSON.fromJson(person, PersonDTO.class);
        
        PersonDTO pAdded = FACADE.addPerson(p.getfName(), p.getlName(), p.getPhone(), p.getStreet(),p.getZip(),p.getCity() );
        
        return GSON.toJson(pAdded);         
        
    }
    @PUT
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public String editPerson(String person) throws PersonNotFoundException, MissingInputException{
        
        PersonDTO p = GSON.fromJson(person, PersonDTO.class);
        
        PersonDTO pEdit = FACADE.editPerson(p);
        
        return GSON.toJson(pEdit);
    }
    
    @DELETE
    @Produces({MediaType.APPLICATION_JSON})
    @Path("{id}")
    public String deletePerson(@PathParam("id") long id)throws PersonNotFoundException{
        
        PersonDTO pDelete = FACADE.deletePerson(id);
        
        return GSON.toJson(pDelete);
        
    }
    
}

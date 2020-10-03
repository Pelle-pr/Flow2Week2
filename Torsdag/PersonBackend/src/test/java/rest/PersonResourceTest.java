package rest;

import dto.PersonDTO;
import entities.Address;
import entities.Person;
import utils.EMF_Creator;
import io.restassured.RestAssured;
import static io.restassured.RestAssured.given;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import java.net.URI;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.core.UriBuilder;
import org.glassfish.grizzly.http.server.HttpServer;
import static org.glassfish.grizzly.http.util.Header.ContentType;
import org.glassfish.grizzly.http.util.HttpStatus;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
//Uncomment the line below, to temporarily disable this test
//@Disabled
public class PersonResourceTest {

    private static final int SERVER_PORT = 7777;
    private static final String SERVER_URL = "http://localhost/api";
    private static Person p1 = new Person("Pelle", "Rasmussen", "12345678");
    private static Person p2 = new Person("John", "Snow", "87654321");

    
    static final URI BASE_URI = UriBuilder.fromUri(SERVER_URL).port(SERVER_PORT).build();
    private static HttpServer httpServer;
    private static EntityManagerFactory emf;

    static HttpServer startServer() {
        ResourceConfig rc = ResourceConfig.forApplication(new ApplicationConfig());
        return GrizzlyHttpServerFactory.createHttpServer(BASE_URI, rc);
    }

    @BeforeAll
    public static void setUpClass() {
        //This method must be called before you request the EntityManagerFactory
        EMF_Creator.startREST_TestWithDB();
        emf = EMF_Creator.createEntityManagerFactoryForTest();
        
        httpServer = startServer();
        //Setup RestAssured
        RestAssured.baseURI = SERVER_URL;
        RestAssured.port = SERVER_PORT;
        RestAssured.defaultParser = Parser.JSON;
    }
    
    @AfterAll
    public static void closeTestServer(){
        //System.in.read();
         //Don't forget this, if you called its counterpart in @BeforeAll
         EMF_Creator.endREST_TestWithDB();
         httpServer.shutdownNow();
    }
    
    // Setup the DataBase (used by the test-server and this test) in a known state BEFORE EACH TEST
    //TODO -- Make sure to change the EntityClass used below to use YOUR OWN (renamed) Entity class
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
    
    @Test
    public void testServerIsUp() {
        System.out.println("Testing is server UP");
        given().when().get("/person").then().statusCode(200);
    }
   
    //This test assumes the database contains two rows
    @Test
    public void testDummyMsg() throws Exception {
        given()
        .contentType("application/json")
        .get("/person/").then()
        .assertThat()
        .statusCode(HttpStatus.OK_200.getStatusCode())
        .body("msg", equalTo("Hello World"));   
    }
    
    @Test
    public void testCount() throws Exception {
        given()
        .contentType("application/json")
        .get("/person/count").then()
        .assertThat()
        .statusCode(HttpStatus.OK_200.getStatusCode())
        .body("count", equalTo(2));   
    }
    
    @Test
    public void getAllPersons() throws Exception {
        List<PersonDTO> personsDTO;
        personsDTO = given()
                .contentType("application/json")
                .when()
                .get("/person/all")
                .then()
                .extract().body().jsonPath().getList("all", PersonDTO.class);
        
        
        PersonDTO p1DTO = new PersonDTO(p1);
        PersonDTO p2DTO = new PersonDTO(p2);
        
        assertThat(personsDTO,containsInAnyOrder(p1DTO,p2DTO));
     
    }
    
    @Test
    public void getPersonByIdTest() throws Exception {
        
       Long id = p1.getId();
           given()
                   .contentType("application/json")
                   .get("/person/{id}",id).then()
                   .assertThat().statusCode(HttpStatus.OK_200.getStatusCode())
                   .body("fName", equalTo(p1.getFname()));
       }
    @Test
    public void addPersonTest() throws Exception {
        
        given()
                .contentType("application/json")
                .body(new PersonDTO("Kill", "Bill", "666","Lolvej", 1234, "Lolby"))
                .when()
                .post("person")
                .then()
                .body("fName", equalTo("Kill"))
                .body("lName", equalTo("Bill"))
                .body("id", notNullValue());
        
    }
    @Test
    public void exceptionGetPerson(){
        given()
                .contentType("application/json")
                .get("/person/666").then()
                .assertThat().statusCode(404)
                .body("message", equalTo("No person with provided id found"));
        
        
    }
    
    @Test
         public void exceptionAddPerson(){
        
        given()
                .contentType("application/json")
                .body(new PersonDTO("", "John", "242145","Lolvej", 555,"Lolby"))
                .when()
                .post("person")
                .then()
                .body("message", equalTo("Something is missing in the input"))
                .and()
                .assertThat().statusCode(404);
    }
    
    @Test 
    public void exceptionEditPerson(){
        p1.setFname("");
        
        
        given()
                .contentType("application/json")
                .body(new PersonDTO(p1))
                .when()
                .put("person")
                .then()
                .body("message",equalTo("Something is missing in the input"))
                .and()
                .assertThat().statusCode(404);
               
    }
    
   
//    @Test
//    public void deletePersonTest()throws Exception {
//        
//         given()
//        .contentType("application/json")
//        .delete("person/delete/20").then()
//        .assertThat()
//        .statusCode(HttpStatus.OK_200.getStatusCode())
//        .body("status", equalTo("Is now removed"));   
//            
//    }
    }
    
    
    


      


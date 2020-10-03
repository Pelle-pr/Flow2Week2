/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto;

import entities.Person;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Tha-Y
 */
public class PersonAllDTO {
    
    
    
    List<PersonDTO> all = new ArrayList();

    public PersonAllDTO(List<Person> personEntities) {
        
           for (Person personEntity : personEntities) {
            all.add(new PersonDTO(personEntity));
            
        }
        
    }
    
   
}

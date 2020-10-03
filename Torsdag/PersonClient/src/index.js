import "./style.css"
import "bootstrap"
import "bootstrap/dist/css/bootstrap.css"
import personFacade from "./personFacade"





getAllPersons();


document.getElementById("reload").addEventListener("click", getAllPersons)
document.getElementById("savebtn").addEventListener("click", addPerson)
document.getElementById("tbody").addEventListener("click", deleteOrEditPerson)
function getAllPersons() {


  personFacade.getAllPersons()
    .then(persons => {
      const person = persons.all
      const personRows = person.map(person => `
        <tr>
        <td>${person.id}</td>
        <td>${person.fName}</td>
        <td>${person.lName}</td>
        <td>${person.phone}</td>
        <td>${person.street}</td>
        <td>${person.zip}</td>
        <td>${person.city}</td>
        <td>
        <div id="btndelete">
        <a href="#"  class="btndelete" id=${person.id}>delete
        </div>
       
        <a href="#" class="btnedit" id=${person.id} data-toggle="modal" data-target="#myModaledit" >edit </td>
        
        </tr>
        
        `).join("")
      document.getElementById("tbody").innerHTML = personRows
    })
    .catch(e => e.status
      .then(e => document.getElementById("error").innerHTML = e.message))



}

function addPerson() {

  let person = {
    fName: document.getElementById("fName").value,
    lName: document.getElementById("lName").value,
    phone: document.getElementById("phone").value,
    street: document.getElementById("street").value,
    zip: document.getElementById("zip").value,
    city: document.getElementById("city").value

  }
  document.getElementById("error").innerHTML = ""
  personFacade.addPerson(person)

    .catch(e => e.fullError
      .then(e => document.getElementById("error").innerHTML = e.message))
    .then(getAllPersons)



}

function deleteOrEditPerson(event){
  event.preventDefault()

  let buttonPressed = event.target.innerText
  
  let id = event.target.id
  
  switch(buttonPressed){
  
  case("delete"):
  document.getElementById("error").innerHTML= ""
  personFacade.deletePerson(id)
  .catch(e => e.fullError
    .then(e => document.getElementById("error").innerHTML = e.message))
  .then(getAllPersons)
  break
  case("edit"):
  
  getPerson(id)
 document.getElementById("editSubmit").addEventListener("click", function(){
  editPerson(id)
  
 })
  break
  

  default:

  
  }
}
function editPerson (id){

  let person = {
    id: id,
    fName: document.getElementById("efName").value,
    lName: document.getElementById("elName").value,
    phone: document.getElementById("ephone").value,
    street: document.getElementById("estreet").value,
    zip: document.getElementById("ezip").value,
    city: document.getElementById("ecity").value

  }
  document.getElementById("error").innerHTML = ""
  personFacade.editPerson(person)
  .catch(e => e.fullError
  .then(e=> document.getElementById("error").innerHTML = e.message)
  
  )
  .then(getAllPersons)

}

function getPerson(id){
 personFacade.getPerson(id)
 .then(person => {
  document.getElementById("efName").value = person.fName
  document.getElementById("elName").value = person.lName
  document.getElementById("ephone").value = person.phone
  document.getElementById("estreet").value = person.street
  document.getElementById("ezip").value = person.zip
  document.getElementById("ecity").value = person.city
 })
 .catch(e => e.fullError
 .then(e=> document.getElementById("error").innerHTML = e.message)
 )

}






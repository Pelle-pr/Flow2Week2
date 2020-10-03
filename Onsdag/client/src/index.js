import "./style.css"
import "bootstrap/dist/css/bootstrap.css"
import "./jokeFacade"
import jokeFacade from "./jokeFacade"
import userFacade from "./userFacade"

/* 
  Add your JavaScript for all exercises Below or in separate js-files, which you must the import above
*/

/* JS For Exercise-1 below */
function makeJokeList() {
  const jokes = jokeFacade.getJokes();
  let jokeList = jokes.map(joke => `<li>${joke}</li>`).join("")
  document.getElementById("jokes").innerHTML = jokeList
}
makeJokeList()

function getJokeById(event) {
  event.preventDefault()
  let jokeID = document.getElementById("textinput").value
  console.log(jokeID)
  let joke = jokeFacade.getJokeById(jokeID)
  document.getElementById("jokep").innerHTML = joke


}
document.getElementById("findjoke").addEventListener("click", getJokeById)
document.getElementById("addjoke").addEventListener("click", addJoke)

function addJoke(event) {
  event.preventDefault()
  let joke = document.getElementById("textinput").value
  jokeFacade.addJoke(joke)
  makeJokeList()

}

/* JS For Exercise-2 below */
document.getElementById("quoteBtn").addEventListener("click", fetchQuote)
setInterval(fetchQuote, "3600000")

function fetchQuote() {

  fetch("https://studypoints.info/jokes/api/jokes/period/hour")
    .then(function (response) {
      return response.json();
    })
    .then(function (data) {
      document.getElementById("quote").innerHTML = data.joke
    }
    );
}




/* JS For Exercise-3 below */

getAllUsers();
document.getElementById("findbyidbtn").addEventListener("click", getUserByID)
document.getElementById("refresh").addEventListener("click", getAllUsers)
document.getElementById("addUser").addEventListener("click", addUser)
document.getElementById("deleteUser").addEventListener("click", deleteUser)
document.getElementById("editUser").addEventListener("click", editUser)


function getAllUsers() {
  userFacade.getUsers()
    .then(users => {
      const userRows = users.map(user => `
<tr>
<td>${user.id}</td>
<td>${user.age}</td>
<td>${user.name}</td>
<td>${user.gender}</td>
<td>${user.email}</td>
</tr>
`).join("")
      document.getElementById("allUserRows").innerHTML = userRows

    })
}


function getUserByID(event) {
  event.preventDefault();
  let id = document.getElementById("findbyid").value
  document.getElementById("errorId").innerHTML = ""
  userFacade.getUser(id)
    
    .then(user => {
      const foundUser = (`
    <tr>
    <td>${user.id}</td>
    <td>${user.age}</td>
    <td>${user.name}</td>
    <td>${user.gender}</td>
    <td>${user.email}</td>
    </tr>
    `)


      document.getElementById("allUserRows").innerHTML = foundUser
      


    })
    .catch(e => e.fullError
      .then(e => document.getElementById("errorId").innerHTML = e.msg))


}

function addUser() {
  const formElements = document.getElementById("formAdduser").elements

  let user = {
    age: formElements.namedItem("age").value,
    name: formElements.namedItem("name").value,
    gender: formElements.namedItem("gender").value,
    email: formElements.namedItem("email").value
  }
  
  document.getElementById("errorAdd").innerHTML = ""
  userFacade.addUser(user)
    .then(getAllUsers())
    .catch(e => e.fullError
      .then(e => document.getElementById("errorAdd").innerHTML = e.msg))

}
function editUser() {

  const formElements = document.getElementById("formEditUser").elements
  const id = formElements.namedItem("id").value

  let user = {
    age: formElements.namedItem("age").value,
    name: formElements.namedItem("name").value,
    gender: formElements.namedItem("gender").value,
    email: formElements.namedItem("email").value
  }
  document.getElementById("errorEdit").innerHTML = ""
  userFacade.editUser(user, id)
    .catch(e => e.fullError
      .then(e => document.getElementById("errorEdit").innerHTML = e.msg))



}



function deleteUser() {
  let id = document.getElementById("deleteById").value
  document.getElementById("errorDelete").innerHTML = ""
  userFacade.deleteUser(id)
  
  .catch(e => e.fullError
    .then(e => document.getElementById("errorDelete").innerHTML = e.msg))

}













/* 
Do NOT focus on the code below, UNLESS you want to use this code for something different than
the Period2-week2-day3 Exercises
*/

function hideAllShowOne(idToShow) {
  document.getElementById("about_html").style = "display:none"
  document.getElementById("ex1_html").style = "display:none"
  document.getElementById("ex2_html").style = "display:none"
  document.getElementById("ex3_html").style = "display:none"
  document.getElementById(idToShow).style = "display:block"
}

function menuItemClicked(evt) {
  const id = evt.target.id;
  switch (id) {
    case "ex1": hideAllShowOne("ex1_html"); break
    case "ex2": hideAllShowOne("ex2_html"); break
    case "ex3": hideAllShowOne("ex3_html"); break
    default: hideAllShowOne("about_html"); break
  }
  evt.preventDefault();
}
document.getElementById("menu").onclick = menuItemClicked;
hideAllShowOne("about_html");




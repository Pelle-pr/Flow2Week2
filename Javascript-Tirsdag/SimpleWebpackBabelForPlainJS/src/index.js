import "./style.css"
import "bootstrap/dist/css/bootstrap.css"
import "./jokeFacade"
import members from "./memberFacade"

// document.getElementById("content").innerHTML = "<h2>HOW COOL IS THIS</h2>"




const tableRows = members.map(member => `<tr><td>${member.name}</td><td>${member.age}</td><td>${member.gender}</td></tr>
`).join("\n")
document.getElementById("tbody").innerHTML = tableRows
import "./style.css"
import "bootstrap/dist/css/bootstrap.css"
import countryFacade from "./countryFacade"

document.getElementById("map").addEventListener("click", fetchData)
  

let lastId = "de"




function fetchData (event){
 
  let id = event.target.id
  
  if(lastId !== id){
    document.getElementById(lastId).style.fill = "#c0c0c0"

  }
  document.getElementById(id).style.fill = "#FF0000"
 

  countryFacade.getCountryInfo(id)
  .then(countryArray => {
    console.log(countryArray)
    const country = countryArray[0]
    document.getElementById("countryInfo").innerHTML = `
    Country Name: ${country.name}<br>
    Capital Name: ${country.capital}<br>
    Population: ${country.population}<br>
    Borders: ${country.borders}<br>          
    `
  })
  lastId = id
  

}
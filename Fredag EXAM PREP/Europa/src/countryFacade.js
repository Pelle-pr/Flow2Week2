const URL = "https://restcountries.eu/rest/v1/alpha?codes="
const countryFacade = {
    getCountryInfo

}

function getCountryInfo(id){
    console.log(id)
    if(id == "ru-main"){
        return fetch(URL+"RU")
        .then(handleHttpErrors)
    }else
    return fetch(URL+id)
    .then(handleHttpErrors)


}




function makeOptions(method, body) {
    var opts =  {
      method: method,
      headers: {
        "Content-type": "application/json",
        "Accept": "application/json"
      }
    }
    if(body){
      opts.body = JSON.stringify(body);
    }
    return opts;
   }

   function handleHttpErrors(res){
    if(!res.ok){
      return Promise.reject({status: res.status, fullError: res.json() })
    }
    return res.json();
   }
   
   export default countryFacade
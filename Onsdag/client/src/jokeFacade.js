/*
  This file is just added to show you how you can use ES6 exports
  Observe, in index.js, how you can import
*/

//Setup some dummy test data
const jokes = [
  "A day without sunshine is like, night.",
  "At what age is it appropriate to tell my dog that he's adopted?",
  "I intend to live forever, or die trying",
]

function addJoke(joke) {
  if(joke.length < 4){
    alert("Joke is too short")
  }else
  jokes.push(joke);
}

function getJokeById(i) {

  if(i > jokes.length){
    return "No Jokes by that ID"
  }
  return jokes[i];
}

function getJokes() {
  return jokes;
}

/* Make sure you understand what we create here, it involves VITAL JavaScript knowledge */
const jokeFacade = {
  addJoke,
  getJokeById,
  getJokes
}


window.jokes = jokes; //Only for debugging

export default jokeFacade;
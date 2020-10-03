var names = ["Hassan", "Jan", "Peter", "Boline", "Frederik"];


// 1-a
var filterNames = names.filter(name => name.includes("a"))
console.log(filterNames);


//1-b
var mapNames = names.map(name => reverseString(name))

function reverseString(s){
    return s.split("").reverse().join("");

}
console.log(mapNames)

// 2-a

let myOwnFilter = myFilter(names,"a",filterBy)

function myFilter(array,contains,callback){
    let newArray = []

    array.forEach(element => {
        let result = callback(element,contains)
        if (result === true){
            newArray.push(element)      
        }
    
              
    } 
    
    );    

    return newArray
}

function filterBy(string,letter){
    return string.includes(letter)
}

console.log(myOwnFilter)


//2-b

let myMappedArray = myMap(names,reverseString)

console.log(myMappedArray)

function myMap(array, callback){
    let mappedArray = []

    array.forEach(element => {
        let reverseString = callback(element)
        mappedArray.push(reverseString)
        
    });

    return mappedArray

}

// 3-a
var numbers = [1,3,5,10,11]

var result = [4,8,15,21,11]



//3-b

let navigationsMap = names.map(name => `<a href="">${name}</a>`).join("")

console.log(navigationsMap)

//3-c

var persons = [{name:"Hassan",phone:"1234567"}, 
              {name: "Peter",phone: "675843"}, 
              {name: "Jan", phone: "98547"},
              {name: "Boline", phone: "79345"}];

let makeColumns = persons.map(person => `<tr><td>${person.name}</td><td>${person.phone}</td></tr>`).join("")

console.log(makeColumns)

// 4-a

var all = ["Hassan", "Peter", "Carla", "Boline"]

let joinedAll = all.join("#")

console.log(joinedAll)

// 4-b

var numbers = [2,3,67,33]
var result = numbers.reduce(totalSum)
console.log(result)

function totalSum (total,num){
    return total + num
}


// 4-c

var members = [
    {name : "Peter", age: 18},
    {name : "Jan", age: 35},
    {name : "Janne", age: 25},
    {name : "Martin", age: 22}]
   
var reduceMembers = members.reduce(function(accumulator, members, index, arr){
            

        
})

console.log(reduceMembers)


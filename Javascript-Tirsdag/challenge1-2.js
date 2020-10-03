

// Challenge 1
let car = {make: "volvo", year: 2011}
console.log(car) //1

let carCopy = { ...car };
console.log(carCopy) //2

carCopy.year = 2018;

console.log(car) //3
console.log(carCopy) //4


// Challenge 2

var numbers = [1, 3, 5, 10, 11];

function makeListItem(number) {
  return `<li>${number}</li>`
}

function changeSign(number) {
    return number * -1
  }

function myMap(callback,array){

    let arrayCopy = [];

    array.forEach(element =>  {
        const newItem = callback(element)
        arrayCopy.push(newItem);
        
    });
    return arrayCopy;
}

let listItems1 = myMap(makeListItem,numbers).join("")
console.log(listItems1)

let listItems2 = numbers.map(makeListItem).join("")
console.log(listItems2)

let listItems3 = myMap(changeSign,numbers).join("")
console.log(listItems3)




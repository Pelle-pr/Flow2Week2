const obj = { a: 1, b: true, c: "hello" }
console.log(Object.keys(obj));
console.log(Object.values(obj));
console.log(Object.entries(obj));
printKeyValuePairs(obj)




function printKeyValuePairs(obj) {
  for (const [key, value] of Object.entries(obj)) {
    console.log(`${key}: ${value}`);
  }
}
function setValueDynamically(obj, key, val) {
  obj[key] = val;
}
setValueDynamically(obj, "c", "HELLO");

let peter = {name: "Peter", age: "14", gender: "male"}

function addMayDriveProperty(member){

    var age = member.age
    let checkMaybeDrive = {...member}
    if(age < 18 ){
        checkMaybeDrive.mayDrive = false
        return checkMaybeDrive
    }
    else checkMaybeDrive.mayDrive = true
    return checkMaybeDrive

}
const addJustedMember = addMayDriveProperty(peter)
console.log(addJustedMember)
console.log(peter)


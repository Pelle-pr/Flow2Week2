var members = [
    { name: "Peter", age: 14, gender : "male" },
    { name: "Jan", age: 35, gender : "male" },
    { name: "Janne", age: 25,  gender : "female" },
    { name: "Martin", age: 22, gender : "male" }]


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

    var addJusted = members.map(addMayDriveProperty)
    console.log(addJusted)
    console.log(members)
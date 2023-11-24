package com.example.medreport_care.doctor

data class rvPateintModel(
    var userId:Any,
    var name :Any,
    var bmi :Any,
    var bp :Any,
    var doctorname :Any,
    var sugar :Any,
    var heartrate :Any)
{
    // Add an empty constructor
    constructor() :this("","","","","","","")
}


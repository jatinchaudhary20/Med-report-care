package com.example.medreport_care.doctor.model

data class rvPateintModel(
    var name :Any,
    var bmi :Any,
    var bp :Any,
    var doctorname :Any,
    var sugar :Any,
    var userId:Any
)
{
    // Add an empty constructor
    constructor() :this("","","","","","")
}


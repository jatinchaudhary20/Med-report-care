package com.example.medreport_care.HealthWorker.Model

data class rvlistModel(
    var nameFirst: String = "",
    var age: String = "",
    var location: String = "",
    var userId :String=""
) {
    // Add an empty constructor
    constructor() : this("", "", "")
}

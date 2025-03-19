package com.example.test.dataModels

data class UserResponse(
    val results: List<User>
)

data class User(
    val name: Name,
    val dob: Dob,
    val location: Location,
    val picture: Picture,
    val login: Login
)

data class Name(
    val first: String,
    val last: String
)

data class Dob(
    val age: Int
)

data class Location(
    val country: String
)

data class Picture(
    val thumbnail: String
)

data class Login(
    val uuid: String
)
package com.example.crudfirebase

data class Kelas(
    val id : String?,
    val nama : String,
    val alamat : String,
    val email : String,
    val jurusan : String,
    val kelas : String
){
    constructor():this("","","","","", ""){

    }
}
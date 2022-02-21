package com.example.crudfirebase

data class Donat(
    val id : String?,
    val nama : String,
    val alamat : String,
    val jenis : String,
    val toping : String
){
    constructor():this("","","","",""){

    }
}
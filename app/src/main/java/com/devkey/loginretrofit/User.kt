package com.devkey.loginretrofit

data class User(
    val id: Long,
    val email: String,
    val first_name: String,
    val last_name: String,
    val avatar: String){

    fun getFullName(): String = "$first_name $last_name"

}

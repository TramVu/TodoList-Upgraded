package com.example.mytodolist

class TodoModel {

    companion object Factory {
        fun createList() : TodoModel = TodoModel()
    }

    var UID : String? = null
    var itemDataText : String? = null
    var done : Boolean? = false

}
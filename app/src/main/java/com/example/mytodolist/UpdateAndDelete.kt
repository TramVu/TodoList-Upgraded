package com.example.mytodolist

interface UpdateAndDelete {

    fun modifyItem (itemUID: String, isDone: Boolean)
    fun onDeleteItem (itemUID: String)

}
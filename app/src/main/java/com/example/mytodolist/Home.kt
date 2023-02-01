package com.example.mytodolist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.ImageView
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.mytodolist.R.id.listView_item
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.app

class Home : AppCompatActivity(), UpdateAndDelete {

    private lateinit var auth: FirebaseAuth
    lateinit var taskDataRef : DatabaseReference
    private lateinit var fab : FloatingActionButton
    private var todoList : MutableList<TodoModel>?= null
    private lateinit var adapter : TodoAdapter
    var listViewItem : ListView?= null
    private lateinit var signOut: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        auth = Firebase.auth
        taskDataRef = FirebaseDatabase
            .getInstance("https://mytodolist-ea1e8-default-rtdb.europe-west1.firebasedatabase.app/")
            .reference
        fab = findViewById(R.id.fab)
        listViewItem = findViewById(listView_item)
        signOut = findViewById(R.id.fab_signOut)


        fab.setOnClickListener {view ->
            val alertDialog = AlertDialog.Builder(this)
            val taskEdit = EditText(this)
            alertDialog.setMessage("Add your Todo")
            alertDialog.setTitle("What is your mission ?")
            alertDialog.setView(taskEdit)

            alertDialog.setPositiveButton("Add") {dialog, i ->
                val todoItemData = TodoModel.createList()
                todoItemData.itemDataText = taskEdit.text.toString()
                todoItemData.done = false

                //store data in firebase
                val newItemData = taskDataRef.child("todoList").push()
                todoItemData.UID = newItemData.key
                newItemData.setValue(todoItemData)

                dialog.dismiss()
                Toast.makeText(this, "Item saved", Toast.LENGTH_SHORT).show()
            }
            alertDialog.show()
        }

        todoList = mutableListOf<TodoModel>()
        adapter = TodoAdapter(this, todoList!!)
        listViewItem!!.adapter = adapter
        taskDataRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                todoList!!.clear ()
                addItemToList (snapshot)
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(applicationContext, "No item added", Toast.LENGTH_SHORT).show()
            }

        })

        //SIGN OUT BUTTON
        auth = Firebase.auth
        signOut.setOnClickListener{
            Log.e(signOut().toString(), "log out succeed")
            signOut()
            val homePage = Intent(this, SignIn::class.java)
            startActivity(homePage)
            finish()
        }

    }

    private fun signOut() {
        // [START auth_sign_out]
        Firebase.auth.signOut()
        // [END auth_sign_out]
    }


    //get realtime database changed
    private fun addItemToList(snapshot: DataSnapshot) {
        // an iterator contains lots of items
        val items = snapshot.children.iterator()

        //if iterator has next item is true -> return itemsIterator
        if (items.hasNext()) {
            val todoIndexedValue = items.next()
            val itemsIterator = todoIndexedValue.children.iterator()

            while (itemsIterator.hasNext()) {
                //check if itemsIterator has next object is true => return next object as currentItem
                val currentItem = itemsIterator.next()

                //create new list with new currentItems and store in todoItemData
                val todoItemData = TodoModel.createList()
                todoItemData.UID = currentItem.key       //new item key
                val map = currentItem.getValue() as HashMap<*, *>
                todoItemData.done = map.get("done") as Boolean?
                todoItemData.itemDataText = map.get("itemTextData") as String?

                todoList!!.add(todoItemData)

            }
        }

        adapter.notifyDataSetChanged()
    }

    override fun modifyItem(itemUID: String, isDone: Boolean) {
        val itemRef = taskDataRef.child("todoList").child(itemUID)
        itemRef.child("done").setValue(isDone)
    }

    override fun onDeleteItem(itemUID: String) {
        val itemRef  = taskDataRef.child("todoList").child(itemUID)
        itemRef.removeValue()
        adapter.notifyDataSetChanged()
    }

}



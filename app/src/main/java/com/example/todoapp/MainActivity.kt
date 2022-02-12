package com.example.todoapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.FileUtils
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.io.File
import java.io.IOException
import java.nio.charset.Charset

class MainActivity : AppCompatActivity() {

    var listOfTasks = mutableListOf<String>()
    var listOfDates = mutableListOf<String>()
    lateinit var adapter: TaskItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val onLongClickListener = object: TaskItemAdapter.OnLongClickListener {
            override fun onItemLongClicked(position: Int) {
                // remove item from list
                listOfTasks.removeAt(position)
                listOfDates.removeAt(position)
                // notify adapter of change
                adapter.notifyDataSetChanged()

                saveItems()
            }
        }

         loadItems()

        // Lookup recyclerview
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        // Create adapter passing in data
        adapter = TaskItemAdapter(listOfTasks, listOfDates, onLongClickListener)
        // Attach adapter to recycler view
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Set up button and input field for user to enter a task
        val inputTextField = findViewById<EditText>(R.id.addTaskField)
        val inputDateField = findViewById<EditText>(R.id.addDate)

        // onclick listener for button
        findViewById<Button>(R.id.addTaskButton).setOnClickListener {
            // grab text from user
            val userInputtedTask = inputTextField.text.toString()
            val userInputtedDate = inputDateField.text.toString()

            // add string to list of tasks
            listOfTasks.add(userInputtedTask)
            listOfDates.add(userInputtedDate)

            // Notify adapter that data is updated
            adapter.notifyItemInserted(listOfTasks.size-1)

            // Reset text field
            inputTextField.setText("")
            inputDateField.setText("")

            saveItems()
        }
    }

    // Save the data that the user has inputted

    // Create a method to get file
    fun getDataFile(): File {
        // Every line represents a specific task
        return File(filesDir, "data.txt")
    }

    // Create a method to get file
    fun getDateDataFile(): File {
        // Every line represents a specific task
        return File(filesDir, "dateData.txt")
    }

    // Load items
    fun loadItems() {
        try {
            listOfTasks = org.apache.commons.io.FileUtils.readLines(getDataFile(), Charset.defaultCharset())
            listOfDates = org.apache.commons.io.FileUtils.readLines(getDateDataFile(), Charset.defaultCharset())
        } catch (ioException: IOException) {
            ioException.printStackTrace()
        }
    }

    // Save items by writing into our file
    fun saveItems() {
        try {
            org.apache.commons.io.FileUtils.writeLines(getDataFile(), listOfTasks)
            org.apache.commons.io.FileUtils.writeLines(getDateDataFile(), listOfDates)
        } catch (ioException: IOException) {
            ioException.printStackTrace()
        }
    }
}
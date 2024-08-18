package com.example.zooappfinal

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.zooappfinal.data.FirebaseManager.getUniqueAnimalTypes
import com.example.zooappfinal.data.FirebaseManager.getUniqueHabitatTypes

class MainActivity : AppCompatActivity() {

    private lateinit var tvTitle: TextView
    private lateinit var tvPrompt: TextView
    private lateinit var btnAnimals: Button
    private lateinit var btnHabitats: Button
    private lateinit var listViewData: ListView
    private lateinit var btnAddNew: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize views
        tvTitle = findViewById(R.id.tvTitle)
        tvPrompt = findViewById(R.id.tvPrompt)
        btnAnimals = findViewById(R.id.btnAnimals)
        btnHabitats = findViewById(R.id.btnHabitats)
        listViewData = findViewById(R.id.listViewData)
        btnAddNew = findViewById(R.id.btnAddNew)

        // Set up initial UI
        updateUI("init")

        // Animals button click logic
        btnAnimals.setOnClickListener {
            updateUI("animals")

            // Get unique animal types in alphabetical order

            getUniqueAnimalTypes{ sortedTypes ->
                // Display animal types in ListView
                val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, sortedTypes)
                listViewData.adapter = adapter
                // Set item click listener
                listViewData.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
                    val selectedAnimalType = sortedTypes[position]
                    navigateToSubMenuActivity(selectedAnimalType, "Animal")
                }
            }

            //Set add new button click listener
            btnAddNew.setOnClickListener {
                navigateToDetailsActivity("Animal")
            }
        }

        // Set Habitats button click listener
        btnHabitats.setOnClickListener {
            updateUI("habitats")

            // Get unique habitat names in alphabetical order
            getUniqueHabitatTypes { sortedTypes ->
                // Display habitat names in ListView
                val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, sortedTypes)
                listViewData.adapter = adapter
                // Set item click listener
                listViewData.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
                    val selectedHabitatName = sortedTypes[position]
                    navigateToSubMenuActivity(selectedHabitatName, "Habitat")
                }
            }
            //Set add new button click listener
            btnAddNew.setOnClickListener {
                navigateToDetailsActivity("Habitat")
            }

        }

    }
    private fun updateUI(state: String) {
        when (state) {
            "init" -> {
                tvTitle.text = "Zoo Monitoring System"
                tvPrompt.text = "Please select a menu"
                btnAnimals.visibility = View.VISIBLE
                btnHabitats.visibility = View.VISIBLE
            }

            "animals" -> {
                tvPrompt.text = "Please select an animal type"
                btnAddNew.text = "Add a New Animal"
                btnAddNew.visibility = View.VISIBLE
            }

            "habitats" -> {
                tvPrompt.text = "Please select a habitat type"
                btnAddNew.text = "Add a New Habitat"
                btnAddNew.visibility = View.VISIBLE
            }

            else -> {
                Toast.makeText(this, "An error occured", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun navigateToSubMenuActivity(itemSelection: String, itemSubject: String) {
        val intent = Intent(this, SubMenuActivity::class.java)
        intent.putExtra(SubMenuActivity.EXTRA_SELECTION, itemSelection)
        intent.putExtra(SubMenuActivity.EXTRA_SUBJECT, itemSubject)
        startActivity(intent)
    }
    private fun navigateToDetailsActivity(subject: String) {
        when (subject) {
            "Animal" -> {
//                val intent = Intent(this, AnimalDetailsActivity::class.java)
//                startActivity(intent)
                Toast.makeText(this, "Add new Animal", Toast.LENGTH_SHORT).show()
            }
            "Habitat" -> {
//                val intent = Intent(this, HabitatDetailsActivity::class.java)
//                startActivity(intent)
                Toast.makeText(this, "Add new Habitat", Toast.LENGTH_SHORT).show()
            }
            else -> {
                Toast.makeText(this, "An error occured", Toast.LENGTH_SHORT).show()
            }
        }
    }
}


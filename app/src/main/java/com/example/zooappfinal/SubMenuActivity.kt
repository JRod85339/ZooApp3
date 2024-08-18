package com.example.zooappfinal

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.zooappfinal.data.Animal
import com.example.zooappfinal.data.FirebaseManager.getAnimalDataByType
import com.example.zooappfinal.data.FirebaseManager.getAnimalNamesByType
import com.example.zooappfinal.data.FirebaseManager.getHabitatDataByType
import com.example.zooappfinal.data.FirebaseManager.getHabitatNamesByType


class SubMenuActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_SELECTION = "extra_selection"
        const val EXTRA_SUBJECT = "extra_subject"
    }

    private lateinit var tvDetailsTitle: TextView
    private lateinit var listViewDetails: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sub_menu)

        tvDetailsTitle = findViewById(R.id.tvDetailsTitle)
        listViewDetails = findViewById(R.id.listViewDetails)

        //Retrieve the subject and selection from the intent
        val subject = intent.getStringExtra(EXTRA_SUBJECT)
        val selection = intent.getStringExtra(EXTRA_SELECTION)

        when (subject) {
            "Animal" -> {
                // Set the page title
                tvDetailsTitle.text = "Please select an available $selection"
                // Get animals of the selected type
                if (selection != null) {
                    getAnimalDataByType(selection) { animalsList ->
                        // Set the list view
                        val animalDetails = animalsList.map { animal -> animal.Name }
                        val adapter = object : ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, animalDetails) {
                            override fun getView(position: Int, convertView: android.view.View?, parent: android.view.ViewGroup): android.view.View {
                                val view = super.getView(position, convertView, parent)
                                val animal = animalsList[position]
                                val textView = view.findViewById<TextView>(android.R.id.text1)
                                val flag = animal.Health != "None" || animal.Food == "None"
                                if (flag) {
                                    textView.setTextColor(Color.RED)
                                    textView.setTypeface(null, Typeface.BOLD)
                                }
                                else {
                                    textView.setTextColor(Color.BLACK)
                                    textView.setTypeface(null, Typeface.NORMAL)
                                }
                                return view
                            }
                        }
                        listViewDetails.adapter = adapter

                        // Set item click listener
                        listViewDetails.setOnItemClickListener { _, _, position, _ ->
                            val selection = animalsList[position]
                            Toast.makeText(this, "Animal selected: ${selection.Name}", Toast.LENGTH_SHORT).show()
//                            val intent = Intent(this, AnimalDetailsActivity::class.java)
//                            intent.putExtra(AnimalDetailsActivity.EXTRA_ID, selection.ID)
//                            intent.putExtra(AnimalDetailsActivity.EXTRA_NAME, selection.Name)
//                            intent.putExtra(AnimalDetailsActivity.EXTRA_TYPE, selection.Type)
//                            intent.putExtra(AnimalDetailsActivity.EXTRA_HEALTH, selection.Health)
//                            intent.putExtra(AnimalDetailsActivity.EXTRA_FOOD, selection.Food)
//                            startActivity(intent)
                        }

                    }
                }
                else {
                    Toast.makeText(this, "No selection", Toast.LENGTH_SHORT).show()
                }
            }
            "Habitat" -> {
                // Set the page title
                tvDetailsTitle.text = "Please select an available $selection"
                // Get habitats of the selected type
                if (selection != null) {
                    getHabitatDataByType(selection) { habitatsList ->
                        // Set the list view
                        val habitatDetails = habitatsList.map { habitat -> habitat.Name }
                        val adapter = object : ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, habitatDetails) {
                            override fun getView(position: Int, convertView: android.view.View?, parent: android.view.ViewGroup): android.view.View {
                                val view = super.getView(position, convertView, parent)
                                val habitat = habitatsList[position]
                                val textView = view.findViewById<TextView>(android.R.id.text1)
                                val flag = habitat.cFlag == true || habitat.tFlag == true || habitat.fFlag == true
                                if (flag) {
                                    textView.setTextColor(Color.RED)
                                    textView.setTypeface(null, Typeface.BOLD)
                                }
                                else {
                                    textView.setTextColor(Color.BLACK)
                                    textView.setTypeface(null, Typeface.NORMAL)
                                }
                                return view
                            }
                        }
                        listViewDetails.adapter = adapter

                        // Set item click listener
                        listViewDetails.setOnItemClickListener { _, _, position, _ ->
                            val selection = habitatsList[position]
                            Toast.makeText(this, "Habitat selected: ${selection.Name}", Toast.LENGTH_SHORT).show()
//                            val intent = Intent(this, HabitatsDetailsActivity::class.java)
//                            intent.putExtra(HabitatsDetailsActivity.EXTRA_ID, selection.ID)
//                            intent.putExtra(HabitatsDetailsActivity.EXTRA_NAME, selection.Name)
//                            intent.putExtra(HabitatsDetailsActivity.EXTRA_TYPE, selection.Type)
//                            intent.putExtra(HabitatsDetailsActivity.EXTRA_C_FLAG, selection.cFlag)
//                            intent.putExtra(HabitatsDetailsActivity.EXTRA_T_FLAG, selection.tFlag)
//                            intent.putExtra(HabitatsDetailsActivity.EXTRA_F_FLAG, selection.fFlag)
//                            startActivity(intent)
                        }

                    }
                }
                else {
                    Toast.makeText(this, "No selection", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}

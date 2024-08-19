package com.example.zooappfinal

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.zooappfinal.data.Animal
import com.example.zooappfinal.data.FirebaseManager.addAnimal
import com.example.zooappfinal.data.FirebaseManager.getAnimalById
import com.example.zooappfinal.data.FirebaseManager.deleteAnimal
import com.example.zooappfinal.data.FirebaseManager.updateAnimalById


class AnimalDetailsActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_ID = "extra_animal_id"
    }

    private lateinit var tvTitle: TextView
    private lateinit var tvId: TextView
    private lateinit var etType: EditText
    private lateinit var etName: EditText
    private lateinit var etAge: EditText
    private lateinit var lineH: LinearLayout
    private lateinit var etHealth: EditText
    private lateinit var lineF: LinearLayout
    private lateinit var etFood: EditText
    private lateinit var btnEditSubmit: Button
    private lateinit var btnDelete: Button
    private lateinit var animal: Animal

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_animal_details)

        // Initialize views
        tvTitle = findViewById(R.id.tvTitle)
        tvId = findViewById(R.id.tvId)
        etType = findViewById(R.id.etType)
        etName = findViewById(R.id.etName)
        etAge = findViewById(R.id.etAge)
        lineH = findViewById(R.id.healthConcernLine)
        etHealth = findViewById(R.id.etHealth)
        lineF = findViewById(R.id.feedingScheduleLine)
        etFood = findViewById(R.id.etFood)
        btnEditSubmit = findViewById(R.id.btnEditSubmit)
        btnDelete = findViewById(R.id.btnDelete)

        // Set title
        tvTitle.text = getString(R.string.new_animal)

        // Initialize animal as null
        animal = Animal()

        // Check if animal ID is passed via intent extras
        val animalIDString = intent.getStringExtra(EXTRA_ID)
        if (animalIDString != null) {
            // Fetch animal data using the animal ID
            getAnimalById(animalIDString) { animalData ->
                // Check if animal data was fetched successfully
                if (animalData != null) {
                    // Update UI with animal data
                    animal = animalData
                    animal.ID = animalIDString
                    val titleText = animal.Name + " the " + animal.Type
                    val idText = "ID: " + animal.ID
                    tvTitle.text = titleText
                    tvId.text = idText
                    etType.setText(animal.Type)
                    etName.setText(animal.Name)
                    etAge.setText(animal.Age.toString())
                    etHealth.setText(animal.Health)
                    etFood.setText(animal.Food)
                    // highlight lines if warning condition is met
                    if (animal.Health != "None") {
                        lineH.setBackgroundColor(Color.rgb(236, 151, 151))
                    }
                    if (animal.Food == "None") {
                        lineF.setBackgroundColor(Color.rgb(236, 151, 151))
                    }
                    // Enable the delete button if an animal is present
                    btnDelete.isEnabled = true
                } else {
                    // Handle the case where animal data failed to fetch
                    Toast.makeText(this, "Animal not found in remote database", Toast.LENGTH_SHORT)
                        .show()
                    finish()
                }
            }
        }
        // Handle the case where the animal ID is not passed via intent extras
        else {
            Toast.makeText(this, "Animal ID not received", Toast.LENGTH_SHORT).show()
            finish()
        }

        // Set click listener for edit/submit button
        btnEditSubmit.setOnClickListener {
            // Check if any of the edit texts are blank
            if (!checkBlankEditTexts(etType, etName, etAge, etHealth, etFood)) {
                // If all fields are filled, update or add the animal
                animal.Age = etAge.text.toString().toInt()
                animal.Type = etType.text.toString()
                animal.Name = etName.text.toString()
                animal.Health = etHealth.text.toString()
                animal.Food = etFood.text.toString()

                // Check if animal ID is null (new animal)
                if (animal.ID == null) {

                    addAnimal(animal)
                    returnToMain()
                }
                // If animal ID is not null (existing animal), update it
                else {
                    updateAnimalById(animal.ID.toString(), animal) { success ->
                        if (success) {
                            // Update was successful
                            Toast.makeText(this, "Animal updated successfully", Toast.LENGTH_SHORT)
                                .show()
                        } else {
                            // Update failed
                            Toast.makeText(this, "Failed to update animal", Toast.LENGTH_SHORT)
                                .show()
                        }
                        returnToMain()
                    }
                }
            }
            // If any field is blank, show a toast message
            else {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            }

            // Set click listener for delete button
            var clickCount = 0
            btnDelete.setOnClickListener {
                clickCount++
                when (clickCount) {
                    1 -> {
                        btnDelete.text = getString(R.string.confirm_delete)
                        btnDelete.setBackgroundColor(Color.RED)
                    }

                    2 -> {
                        deleteAnimal(animal.ID.toString())
                        returnToMain()
                    }

                    else -> {
                        Toast.makeText(this, "An error occurred", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    // Helper function to check if any of the edit texts are blank
    private fun checkBlankEditTexts(vararg editTexts: EditText): Boolean {
        for (editText in editTexts) {
            if (editText.text.isBlank()) {
                return true
            }
        }
        return false
    }

    private fun returnToMain() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}




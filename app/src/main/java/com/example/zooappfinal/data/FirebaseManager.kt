package com.example.zooappfinal.data

import android.util.Log
import android.widget.Toast
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

object FirebaseManager {

    private val database = FirebaseDatabase.getInstance()
    private val animalsRef = database.getReference("Animals")
    private val habitatsRef = database.getReference("Habitats")

    fun addAnimal(animal: Animal) {
        // Generate a unique ID for the new animal
        val id = animalsRef.push().key
        // Ensure the ID is not null
        if (id != null) {
            // Add the animal with the generated ID
            animalsRef.child(id).setValue(animal)
                .addOnSuccessListener {
                    // Successfully added
                    Log.d("FirebaseManager", "Animal added with ID: $id")
                }
                .addOnFailureListener { e ->
                    // Failed to add
                    Log.e("FirebaseManager", "Failed to add animal: ${e.message}")
                }
        }
        // Handle the case where the ID is null
        else {
            Log.e("FirebaseManager", "Failed to generate a new animal ID")
        }
    }

    fun addHabitat(id: String, habitat: Habitat) {
        habitatsRef.child(id).setValue(habitat)
            .addOnSuccessListener {
                // Successfully added
            }
            .addOnFailureListener {
                // Failed to add
            }
    }

    fun getUniqueAnimalTypes(onResult: (List<String>) -> Unit) {
        animalsRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val animalTypes = mutableSetOf<String>()
                for (data in snapshot.children) {
                    val animal = data.getValue(Animal::class.java)
                    animal?.Type?.let { animalTypes.add(it) }
                }
                // Convert to list, sort alphabetically, and return through the callback
                val sortedTypes = animalTypes.toList().sorted()
                onResult(sortedTypes)
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle error
                onResult(emptyList()) // Return an empty list in case of error
            }
        })
    }

    fun getAnimalNamesByType(animalType: String, onResult: (List<String>) -> Unit) {
        animalsRef.orderByChild("Type").equalTo(animalType).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val animalNames = mutableListOf<String>()
                for (data in snapshot.children) {
                    val animal = data.getValue(Animal::class.java)
                    animal?.Name?.let { animalNames.add(it) }
                }
                // Return the list of names through the callback
                onResult(animalNames)
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle error
                onResult(emptyList())
            }
        })
    }
    fun getAnimalById(id: String, onResult: (Animal?) -> Unit) {
        // Create a reference to the specific animal node in the database
        val animalRef = animalsRef.child(id)
        // Read the data from the database
        animalRef.addListenerForSingleValueEvent(object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            // Check if data exists
            if (snapshot.exists()) {
                // Retrieve data from the snapshot
                val animal = snapshot.getValue(Animal::class.java)
                // Return the animal through the callback
                onResult(animal)
            } else {
                // No data found, return null through the callback
                onResult(null)
            }
        }
            override fun onCancelled(error: DatabaseError) {
                // Handle possible errors
                Log.e("FirebaseManager", "Error reading animal data: ${error.message}")
            }
        })
    }

    fun getAnimalDataByType(animalType: String, onResult: (List<Animal>) -> Unit) {
        animalsRef.orderByChild("Type").equalTo(animalType).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val animalsList = mutableListOf<Animal>()
                for (data in snapshot.children) {
                    val animal = data.getValue(Animal::class.java)
                    val id = data.key
                    if (animal != null && id != null) {
                        animal.ID = id
                        animalsList.add(animal)
                    }
                }
                // Return the list of animals through the callback
                onResult(animalsList)
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle error
                onResult(emptyList())
            }
        })
    }



    fun getHabitats() {
        habitatsRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val habitats = mutableListOf<Habitat>()
                for (data in snapshot.children) {
                    val habitat = data.getValue(Habitat::class.java)
                    habitat?.let { habitats.add(it) }
                }
                // Do something with the list of habitats
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle error
            }
        })
    }

    fun getUniqueHabitatTypes(onResult: (List<String>) -> Unit) {
        habitatsRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val habitatTypes = mutableSetOf<String>()
                for (data in snapshot.children) {
                    val habitat = data.getValue(Habitat::class.java)
                    habitat?.Type?.let { habitatTypes.add(it) }
                }
                // Convert to list, sort alphabetically, and return through the callback
                val sortedTypes = habitatTypes.toList().sorted()
                onResult(sortedTypes)
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle error
                onResult(emptyList()) // Return an empty list in case of error
            }
        })
    }

    fun getHabitatNamesByType(habitatType: String, onResult: (List<String>) -> Unit) {
        val database = FirebaseDatabase.getInstance()
        val habitatsRef = database.getReference("Habitats")

        animalsRef.orderByChild("Type").equalTo(habitatType).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val habitatNames = mutableListOf<String>()
                for (data in snapshot.children) {
                    val habitat = data.getValue(Habitat::class.java)
                    habitat?.Name?.let { habitatNames.add(it) }
                }
                // Return the list of names through the callback
                onResult(habitatNames)
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle error
                onResult(emptyList())
            }
        })
    }

    fun getHabitatDataByType(animalType: String, onResult: (List<Habitat>) -> Unit) {
        habitatsRef.orderByChild("Type").equalTo(animalType).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val habitatsList = mutableListOf<Habitat>()
                for (data in snapshot.children) {
                    val habitat = data.getValue(Habitat::class.java)
                    val id = data.key
                    if (habitat != null && id != null) {
                        habitat.ID = id
                        habitatsList.add(habitat)
                    }
                }
                // Return the list of habitats through the callback
                onResult(habitatsList)
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle error
                onResult(emptyList())
            }
        })
    }

    fun updateAnimalById(id: String, updatedAnimal: Animal, onComplete: (Boolean) -> Unit) {
        // Create a reference to the specific animal node
        val animalRef = animalsRef.child(id)

        // Replace the entire animal data using setValue
        animalRef.setValue(updatedAnimal)
            .addOnSuccessListener {
                // Update was successful
                onComplete(true)
                Log.d("FirebaseManager", "Animal with ID: $id successfully updated")
            }
            .addOnFailureListener { e ->
                // Handle any errors
                onComplete(false)
                Log.e("FirebaseManager", "Failed to update animal: ${e.message}")
            }
    }


    fun updateHabitat(id: String, updatedHabitat: Habitat) {
        habitatsRef.child(id).setValue(updatedHabitat)
            .addOnSuccessListener {
                // Successfully updated
            }
            .addOnFailureListener {
                // Failed to update
            }
    }

    fun deleteAnimal(id: String) {
        animalsRef.child(id).removeValue()
            .addOnSuccessListener {
                // Successfully deleted
                Log.d("FirebaseManager", "Animal with ID: $id successfully deleted")
            }
            .addOnFailureListener { e ->
                // Failed to delete
                Log.e("FirebaseManager", "Failed to delete animal: ${e.message}")
            }
    }

    fun deleteHabitat(id: String) {
        habitatsRef.child(id).removeValue()
            .addOnSuccessListener {
                // Successfully deleted
            }
            .addOnFailureListener {
                // Failed to delete
            }
    }



}
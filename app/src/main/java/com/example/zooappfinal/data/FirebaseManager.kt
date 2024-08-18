package com.example.zooappfinal.data

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

object FirebaseManager {

    val database = FirebaseDatabase.getInstance()
    val animalsRef = database.getReference("Animals")
    val habitatsRef = database.getReference("Habitats")

    fun addAnimal(id: String, animal: Animal) {
        animalsRef.child(id).setValue(animal)
            .addOnSuccessListener {
                // Successfully added
            }
            .addOnFailureListener {
                // Failed to add
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

    fun updateAnimal(id: String, updatedAnimal: Animal) {
        animalsRef.child(id).setValue(updatedAnimal)
            .addOnSuccessListener {
                // Successfully updated
            }
            .addOnFailureListener {
                // Failed to update
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
            }
            .addOnFailureListener {
                // Failed to delete
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
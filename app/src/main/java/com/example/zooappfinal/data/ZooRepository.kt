//package com.example.zooappfinal.data
//
//import com.google.firebase.database.*
//import com.google.firebase.database.ktx.getValue
//import java.util.UUID
//
//object ZooRepository {
//    private val database: DatabaseReference = FirebaseDatabase.getInstance().reference
//
//    // Function to get all animals
//    fun getAnimals(callback: (List<Animal>) -> Unit) {
//        database.child("Animals").addListenerForSingleValueEvent(object : ValueEventListener {
//            override fun onDataChange(dataSnapshot: DataSnapshot) {
//                val animals = mutableListOf<Animal>()
//                for (animalSnapshot in dataSnapshot.children) {
//                    val animal = animalSnapshot.getValue<Animal>()
//                    animal?.let { animals.add(it) }
//                }
//                callback(animals)
//            }
//
//            override fun onCancelled(databaseError: DatabaseError) {
//                // Handle possible errors.
//            }
//        })
//    }
//
//    // Function to get a single animal by ID
//    fun getAnimal(id: String, callback: (Animal?) -> Unit) {
//        database.child("Animals").child(id).addListenerForSingleValueEvent(object : ValueEventListener {
//            override fun onDataChange(dataSnapshot: DataSnapshot) {
//                val animal = dataSnapshot.getValue<Animal>()
//                callback(animal)
//            }
//
//            override fun onCancelled(databaseError: DatabaseError) {
//                // Handle possible errors.
//            }
//        })
//    }
//
//    // Function to add a new animal
//    fun addAnimal(animal: Animal) {
//        val id = database.child("Animals").push().key ?: UUID.randomUUID().toString()
//        database.child("Animals").child(id).setValue(animal)
//    }
//
//    // Function to update an existing animal
//    fun updateAnimal(id: String, animal: Animal) {
//        database.child("Animals").child(id).setValue(animal)
//    }
//
//    // Function to delete an animal
//    fun deleteAnimal(id: String) {
//        database.child("Animals").child(id).removeValue()
//    }
//
//    // Function to get all habitats
//    fun getHabitats(callback: (List<Habitat>) -> Unit) {
//        database.child("Habitats").addListenerForSingleValueEvent(object : ValueEventListener {
//            override fun onDataChange(dataSnapshot: DataSnapshot) {
//                val habitats = mutableListOf<Habitat>()
//                for (habitatSnapshot in dataSnapshot.children) {
//                    val habitat = habitatSnapshot.getValue<Habitat>()
//                    habitat?.let { habitats.add(it) }
//                }
//                callback(habitats)
//            }
//
//            override fun onCancelled(databaseError: DatabaseError) {
//                // Handle possible errors.
//            }
//        })
//    }
//
//    // Function to get a single habitat by ID
//    fun getHabitat(id: String, callback: (Habitat?) -> Unit) {
//        database.child("Habitats").child(id).addListenerForSingleValueEvent(object : ValueEventListener {
//            override fun onDataChange(dataSnapshot: DataSnapshot) {
//                val habitat = dataSnapshot.getValue<Habitat>()
//                callback(habitat)
//            }
//
//            override fun onCancelled(databaseError: DatabaseError) {
//                // Handle possible errors.
//            }
//        })
//    }
//
//    // Function to add a new habitat
//    fun addHabitat(habitat: Habitat) {
//        val id = database.child("Habitats").push().key ?: UUID.randomUUID().toString()
//        database.child("Habitats").child(id).setValue(habitat)
//    }
//
//    // Function to update an existing habitat
//    fun updateHabitat(id: String, habitat: Habitat) {
//        database.child("Habitats").child(id).setValue(habitat)
//    }
//
//    // Function to delete a habitat
//    fun deleteHabitat(id: String) {
//        database.child("Habitats").child(id).removeValue()
//    }
//}

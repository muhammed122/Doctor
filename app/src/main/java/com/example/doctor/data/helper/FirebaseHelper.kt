package com.example.doctor.data.helper

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

object FirebaseHelper {

    val firebaseReference: DatabaseReference
        get() {
            return FirebaseDatabase.getInstance().reference
        }

    val firebaseAuth:FirebaseAuth
    get() {
        return FirebaseAuth.getInstance()
    }

}
package com.example.doctor.data.repositry

import androidx.lifecycle.MutableLiveData
import com.example.doctor.data.helper.FirebaseHelper
import com.example.doctor.data.model.HospitalModel
import com.example.doctor.data.model.HospitalModelClass
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

object HospitalRepository {


  //  val errorMessage = MutableLiveData<String>()

    lateinit var errorMessage : String


  suspend fun getHospitalsData() :List<HospitalModelClass>{

        val hospitals:ArrayList<HospitalModelClass> = ArrayList<HospitalModelClass>()
        FirebaseHelper.firebaseReference.addListenerForSingleValueEvent(object :
            ValueEventListener {

            override fun onCancelled(error: DatabaseError) {
                errorMessage =error.message
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {

                    for (data in dataSnapshot.children) {
                    val obj=  data.getValue<HospitalModelClass>(HospitalModelClass::class.java)
                        if (obj != null) {
                            println("dola obj"+obj.location)
                            hospitals.add(obj)
                        }
                    }
                }


        })

      println("dola obj2"+hospitals.size)

        return hospitals
    }

}
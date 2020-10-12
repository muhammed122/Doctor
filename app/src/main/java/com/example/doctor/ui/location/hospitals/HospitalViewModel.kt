package com.example.doctor.ui.location.hospitals

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.doctor.data.helper.FirebaseHelper
import com.example.doctor.data.model.HospitalModel
import com.example.doctor.data.model.HospitalModelClass
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeoutOrNull
import kotlin.system.measureTimeMillis

class HospitalViewModel : ViewModel() {


    val hospitals: MutableLiveData<List<HospitalModel>> = MutableLiveData()
    val message: MutableLiveData<String> = MutableLiveData()


    val hospitalList: ArrayList<HospitalModel> = ArrayList()


    private lateinit var job: Job


//    FirebaseHelper.firebaseReference.addListenerForSingleValueEvent(object :
//        ValueEventListener {
//
//        override fun onCancelled(error: DatabaseError) {
//            HospitalRepository.errorMessage =error.message
//        }
//
//        override fun onDataChange(dataSnapshot: DataSnapshot) {
//
//            for (data in dataSnapshot.children) {
//                val obj=  data.getValue<HospitalModelClass>(HospitalModelClass::class.java)
//                if (obj != null) {
//                    println("dola obj"+obj.location)
//                    hospitals.add(obj)
//                }
//            }
//        }
//
//
//    })
//
//    println("dola obj2"+hospitals.size)
//
//    return hospitals
//}


    fun getHospitals() {
        job = Job()
        viewModelScope.launch(IO + job) {

            val time = measureTimeMillis {
                try {
                    val time = withTimeoutOrNull(10000) {


                   //     FirebaseDatabase.getInstance().reference.addValueEventListener(object :
                        FirebaseHelper.firebaseReference.addValueEventListener(object :
                            ValueEventListener {
                            override fun onCancelled(error: DatabaseError) {
                                job.cancel(error.message)
                            }

                            override fun onDataChange(dataSnapshot: DataSnapshot) {

                                hospitalList.clear()
                                for (data in dataSnapshot.children) {

                                    val obj =
                                        data.getValue<HospitalModelClass>(HospitalModelClass::class.java)
                                    println("dola object last ${data.getValue<HospitalModelClass>(HospitalModelClass::class.java)}")
                                    if (obj != null) {


                                        hospitalList.add(
                                            HospitalModel(
                                                data.key.toString(),
                                                data.key.toString(), obj.emptyofunits,
                                                obj.Nofunit.minus(obj.emptyofunits),
                                                obj.location, obj.langitude, obj.latitude
                                            )
                                        )


                                    }
                                }
                                hospitals.postValue(hospitalList)
                            }
                        })
                    }
                    if (time == null) {
                        job.cancel("request time out")
                    }

                } catch (e: Exception) {
                    job.cancel(e.localizedMessage)
                }
            }
       //     println("dola $time")
        }

        job.invokeOnCompletion {
            if (it != null)
                message.postValue(it.localizedMessage)
        }
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()

    }

}
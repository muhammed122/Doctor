package com.example.doctor.ui.bedstatus

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.doctor.data.model.BedResponse
import com.example.doctor.data.source.ApiManager
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeoutOrNull
import kotlin.system.measureTimeMillis

class StatusBedViewModel : ViewModel() {


    private lateinit var job: Job

    val beds=MutableLiveData<BedResponse>()
    val message=MutableLiveData<String>()

    fun getBeds() {
        job = Job()

        viewModelScope.launch(IO + job) {

            val totalTime= measureTimeMillis {

                try {
                    val time = withTimeoutOrNull(10000) {
                        beds.postValue(ApiManager.getBedService().getBeds(1))
                }

                        if (time ==null)
                        job.cancel("request time out")


                } catch (e: Exception) {
                    job.cancel(e.localizedMessage)
                }
            }
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




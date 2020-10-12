package com.example.doctor.ui.chatroom

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.preference.PreferenceManager
import com.example.doctor.data.model.MessageResponse
import com.example.doctor.data.source.ApiManager
import com.google.gson.JsonObject
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeoutOrNull
import kotlin.system.measureNanoTime

class ChatRoomViewModel(application: Application) :AndroidViewModel(application){

    val responseMessage:MutableLiveData<MessageResponse> = MutableLiveData()

    val errorMessage:MutableLiveData<String> = MutableLiveData()

    lateinit var job:Job

    fun sendReceiveMessage(input:String){
        job= Job()
        viewModelScope.launch(IO+job){
            val totalTime = measureNanoTime {
                try {
                    val time = withTimeoutOrNull(60000) {

                        val preferencesPerv =
                        PreferenceManager.getDefaultSharedPreferences(getApplication())
                            .getStringSet("PREF_COOKIES", HashSet<String>()) as HashSet<String>

                        println("dola cookies $preferencesPerv")



                        val json = JsonObject()
                        json.addProperty("input",input)
                        println("dola here"+json)
                        val m =  ApiManager.getChatService(getApplication()).
                        sendReceiveMessage(json)

                        if (m.message==null)
                        {
//                            val memes =
//                                PreferenceManager.getDefaultSharedPreferences(getApplication()).edit()
//                            memes.putStringSet("PREF_COOKIES" ,preferencesPerv).apply()
//                            memes.commit()
                        }


                        println("dola test"+m)
                        responseMessage.postValue(m)
                    }

                        if (time==null) {
                            job.cancel("request time out")
                            println("dola test timeout")
                        }

                }catch (e:Exception){
                    job.cancel(e.localizedMessage)
                    println("dola here"+e.localizedMessage)
                }
            }

            job.invokeOnCompletion{
                if (it != null)
                    errorMessage.postValue(it.localizedMessage)
            }


        }
    }


    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }


}
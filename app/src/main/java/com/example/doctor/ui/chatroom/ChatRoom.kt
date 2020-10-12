package com.example.doctor.ui.chatroom

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.doctor.R
import com.example.doctor.data.helper.FirebaseHelper
import com.example.doctor.data.model.MessageModel
import com.example.doctor.data.model.MessageResponse
import com.example.doctor.data.source.ApiManager
import com.example.doctor.ui.adapter.ChatAdapter
import com.google.gson.JsonObject
import kotlinx.android.synthetic.main.fragment_chat_room.*
import kotlinx.android.synthetic.main.hospital_item.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.ArrayList

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

/**
 * A simple [Fragment] subclass.
 * Use the [ChatRoom.newInstance] factory method to
 * create an instance of this fragment.
 */
class ChatRoom : Fragment() {

    var chatAdapter: ChatAdapter?=null
    lateinit var layoutManager: RecyclerView.LayoutManager

     var messageModels: ArrayList<MessageModel>?=null


    lateinit var chatRoomViewModel: ChatRoomViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_chat_room, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        val preferences =
            PreferenceManager.getDefaultSharedPreferences(context)
                .getStringSet("PREF_COOKIES", null)


        chatRoomViewModel =
            ViewModelProviders.of(requireActivity()).get(ChatRoomViewModel::class.java)

        if (preferences==null)
        chatRoomViewModel.sendReceiveMessage("1")

        chatRoomViewModel.responseMessage.observe(viewLifecycleOwner, Observer {

            if (it.message==null)
                messageModels!!.add(MessageModel("", 0,"invalid symptoms...!"))
              else {
                if(messageModels==null)
                    messageModels=ArrayList()
                for (message in it.message)
                    messageModels!!.add(MessageModel("", 0, message))
            }

            if (chatAdapter==null)
        setupRecycler()
        else
            chatAdapter!!.notifyDataSetChanged()

        })


        //setupRecycler()


        btn_send.setOnClickListener {
            val myMassage = chat_message.text.toString()
            if (myMassage != "") {
                messageModels!!.add(MessageModel("", 1, myMassage))
                chatAdapter!!.notifyDataSetChanged()

                if (myMassage.trim() == "2") {

                    val builder = AlertDialog.Builder(requireContext())
                    builder.setTitle("Booking")
                    builder.setMessage("Do you want go to booking..?")
                    builder.setPositiveButton("Yes") { dialog, which ->
                        findNavController().navigate(R.id.hospital)
                    }
                        builder.setNegativeButton("Cancel") { dialog, which ->
                            Toast.makeText(
                                context,
                                "Cancel", Toast.LENGTH_SHORT
                            ).show()
                        }

                        builder.show()
                }
            } else
                Toast.makeText(
                    requireContext(), "please enter the message",
                    Toast.LENGTH_LONG
                ).show()


            chatRoomViewModel.sendReceiveMessage(myMassage)
            chat_message.setText("")


        }

    }

    fun setupRecycler() {
        //addFakeData()
        layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
        chatAdapter = ChatAdapter(messageModels!!)
        chat_list.adapter = chatAdapter
        chat_list.layoutManager = layoutManager
    }





    }



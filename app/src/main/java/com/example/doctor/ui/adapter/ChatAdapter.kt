package com.example.doctor.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.doctor.R
import com.example.doctor.data.model.MessageModel
import kotlinx.android.synthetic.main.chatbot_item.view.*

class ChatAdapter(var messages: ArrayList<MessageModel> = ArrayList()) :
    RecyclerView.Adapter<ChatAdapter.ChatHolder>() {

    val MY_TYPE:Int = 1
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatHolder {

        val itemView: View = if (viewType == MY_TYPE) {
            LayoutInflater.from(parent.context).
            inflate(R.layout.my_chat_item, parent, false)
        } else {
            LayoutInflater.from(parent.context).
            inflate(R.layout.chatbot_item, parent, false)
        }

        return ChatHolder(itemView)
    }

    override fun getItemCount(): Int {
       return messages.size
    }

    override fun getItemViewType(position: Int): Int {
        var type:Int=0
        if(messages.get(position).type==1){
            type =  MY_TYPE
       }
        return type

    }
    override fun onBindViewHolder(holder: ChatHolder, position: Int) {

          val  obj=messages.get(position)
                    holder.itemView.message.text=obj.content
    }

    class ChatHolder(itemView: View) : RecyclerView.ViewHolder(itemView)


}
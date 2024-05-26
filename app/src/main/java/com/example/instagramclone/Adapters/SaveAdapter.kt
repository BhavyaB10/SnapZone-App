package com.example.instagramclone.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.instagramclone.Model.Posts
import com.example.instagramclone.R

class SaveAdapter(private val  saveList:ArrayList<Posts>) :RecyclerView.Adapter<SaveAdapter.MyViewHolder>() {

    public class MyViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val caption:TextView = itemView.findViewById(R.id.caption)
        val postImage:ImageView = itemView.findViewById(R.id.postImage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView= LayoutInflater.from(parent.context).inflate(R.layout.save_item,parent,false)

        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
     return saveList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val post : Posts = saveList[position]
        holder.caption.text=post.caption

    }
}
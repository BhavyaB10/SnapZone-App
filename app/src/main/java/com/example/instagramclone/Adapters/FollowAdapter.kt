package com.example.instagramclone.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.instagramclone.Model.User
import com.example.instagramclone.R
import com.example.instagramclone.databinding.FolloeRvBinding
import com.example.instagramclone.databinding.SerachRvBinding

class FollowAdapter(var context: Context, var followList: ArrayList<User>) :RecyclerView.Adapter<FollowAdapter.MyViewHolder>(){

    inner class MyViewHolder(var binding: FolloeRvBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
       var binding = FolloeRvBinding.inflate(LayoutInflater.from(context),parent,false)
       return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return followList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        Glide.with(context).load(followList.get(position).image).placeholder(R.drawable.user).into(holder.binding.profileImage)
        holder.binding.name.text=followList.get(position).name
    }
}
package com.example.instagramclone.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.instagramclone.Model.User
import com.example.instagramclone.R
import com.example.instagramclone.Utils.FOLLOW
import com.example.instagramclone.databinding.SerachRvBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore

class SearchAdapter(
    var context: Context,
    var userList: ArrayList<User>
) : RecyclerView.Adapter<SearchAdapter.ViewHolder>() {

    inner class ViewHolder(var binding: SerachRvBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var binding = SerachRvBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var isFollow = false
        //Use Glide
        Glide.with(context).load(userList.get(position).image).placeholder(R.drawable.user)
            .into(holder.binding.profileImage)
        holder.binding.name.text = userList.get(position).name


        Firebase.firestore.collection(Firebase.auth.currentUser!!.uid + FOLLOW)
            .whereEqualTo("email", userList.get(position).email).get().addOnSuccessListener {

                if (it.documents.size==0) {
                    isFollow = false

                } else {
                    holder.binding.follow.text = "Unfollow"
                    isFollow = true
                }

            }
        holder.binding.follow.setOnClickListener {
            if (isFollow) {
                Firebase.firestore.collection(Firebase.auth.currentUser!!.uid + FOLLOW)
                    .whereEqualTo("email", userList.get(position).email).get().addOnSuccessListener {

                        Firebase.firestore.collection(Firebase.auth.currentUser!!.uid+ FOLLOW).document(it.documents.get(0).id).delete()
                        holder.binding.follow.text="Follow"
                        isFollow=false
                    }
            } else {
                Firebase.firestore.collection(Firebase.auth.currentUser!!.uid + FOLLOW).document()
                    .set(userList.get(position))

                holder.binding.follow.text = "Unfollow"
                isFollow=true

            }


        }
    }
}
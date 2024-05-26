package com.example.instagramclone.Adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.instagramclone.Model.Posts
import com.example.instagramclone.Model.User
import com.example.instagramclone.R
import com.example.instagramclone.Utils.FOLLOW
import com.example.instagramclone.Utils.LIKE
import com.example.instagramclone.Utils.SAVE
import com.example.instagramclone.Utils.USER_NODE
import com.example.instagramclone.databinding.PostRvBinding
import com.github.marlonlom.utilities.timeago.TimeAgo
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject


class PostAdapter(var context: Context, var postList:ArrayList<Posts>) :RecyclerView.Adapter<PostAdapter.MyHolder>(){

    inner class MyHolder(var binding:PostRvBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        var binding=PostRvBinding.inflate(LayoutInflater.from(context),parent,false)
        return MyHolder(binding)
    }

    override fun getItemCount(): Int {
     return postList.size
    }
    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        var isLike = false
        var isSave = false
        try{
            Firebase.firestore.collection(USER_NODE).document(postList.get(position).uid).get().addOnSuccessListener {
                var user =it.toObject<User>()
                Glide.with(context).load(user!!.image).placeholder(R.drawable.user).into(holder.binding.profileImage)
                holder.binding.name.text=user.name
            }
        }catch (e:Exception){

        }
        Glide.with(context).load(postList.get(position).postUrl).placeholder(R.drawable.loading).into(holder.binding.postImage)
        try {
            val time = TimeAgo.using(postList.get(position).time.toLong())
            holder.binding.time.text=time
        } catch (e: Exception) {
            holder.binding.time.text=""
        }

        Firebase.firestore.collection(Firebase.auth.currentUser!!.uid + LIKE)
            .whereEqualTo("time", postList.get(position).time).get().addOnSuccessListener {

                if (it.documents.size==0) {
                    holder.binding.like.setImageResource(R.drawable.like)
                    isLike = false

                } else {
                    holder.binding.like.setImageResource(R.drawable.heart)
                    isLike = true
                }

            }

        Firebase.firestore.collection(Firebase.auth.currentUser!!.uid + SAVE)
            .whereEqualTo("postUrl", postList.get(position).postUrl).get().addOnSuccessListener {

                if (it.documents.size==0) {
                    isSave = false
                    holder.binding.save.setImageResource(R.drawable.save)

                } else {
                    holder.binding.save.setImageResource(R.drawable.bookmark)
                    isSave = true
                }

            }

        holder.binding.share.setOnClickListener {
            var intent= Intent(Intent.ACTION_SEND)
            intent.type="text/plain"
            intent.putExtra(Intent.EXTRA_TEXT,postList.get(position).postUrl)
            context.startActivity(intent)
        }
        holder.binding.caption.text = postList.get(position).caption

        holder.binding.like.setOnClickListener {
            if (isLike) {
                Firebase.firestore.collection(Firebase.auth.currentUser!!.uid + LIKE)
                    .whereEqualTo("time", postList.get(position).time).get().addOnSuccessListener {

                        Firebase.firestore.collection(Firebase.auth.currentUser!!.uid+ FOLLOW).document(it.documents.get(0).id).delete()
                        holder.binding.like.setImageResource(R.drawable.like)
                        isLike=false
                    }
            } else {
                Firebase.firestore.collection(Firebase.auth.currentUser!!.uid + LIKE).document()
                    .set(postList.get(position))

                holder.binding.like.setImageResource(R.drawable.heart)
                isLike=true
            }


        }

        holder.binding.save.setOnClickListener {
            if (isSave) {
                Firebase.firestore.collection(Firebase.auth.currentUser!!.uid + SAVE)
                    .whereEqualTo("postUrl", postList.get(position).postUrl).get().addOnSuccessListener {

                        Firebase.firestore.collection(Firebase.auth.currentUser!!.uid+ FOLLOW).document(it.documents.get(0).id).delete()
                        holder.binding.save.setImageResource(R.drawable.save)
                        isSave=false
                    }
            } else {
                Firebase.firestore.collection(Firebase.auth.currentUser!!.uid + SAVE).document()
                    .set(postList.get(position))

                holder.binding.save.setImageResource(R.drawable.bookmark)
                isSave=true
            }


        }
    }
}
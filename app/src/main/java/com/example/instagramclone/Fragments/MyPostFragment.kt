package com.example.instagramclone.Fragments

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.instagramclone.Adapters.MyPostRVAdapter
import com.example.instagramclone.Model.Posts
import com.example.instagramclone.R
import com.example.instagramclone.databinding.FragmentMyPostBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject

class MyPostFragment : Fragment() {
    private lateinit var binding: FragmentMyPostBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMyPostBinding.inflate(inflater, container, false)
        var postList = ArrayList<Posts>()
        var adapter = MyPostRVAdapter(requireContext(), postList)
        binding.rv.layoutManager=StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL)
        binding.rv.adapter=adapter

        Firebase.firestore.collection(Firebase.auth.currentUser!!.uid).get().addOnSuccessListener {
            var tempList = arrayListOf<Posts>()
            for (i in it.documents) {
                var post: Posts = i.toObject<Posts>()!!
                tempList.add(post)
            }

            postList.addAll(tempList)
            adapter.notifyDataSetChanged()
        }


        return binding.root
    }

    companion object {

    }
}
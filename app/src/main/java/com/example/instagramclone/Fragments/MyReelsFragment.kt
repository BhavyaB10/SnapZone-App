package com.example.instagramclone.Fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.instagramclone.Adapters.MyReelAdapter
import com.example.instagramclone.Model.Reels
import com.example.instagramclone.Utils.REEL
import com.example.instagramclone.databinding.FragmentMyReelsBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject

class MyReelsFragment : Fragment() {
    private lateinit var binding: FragmentMyReelsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?{
        // Inflate the layout for this fragment
        binding= FragmentMyReelsBinding.inflate(inflater, container, false)

        val reelList = ArrayList<Reels>()
        val adapter = MyReelAdapter(requireContext(), reelList)
        binding.rv.layoutManager= StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)

        binding.rv.adapter=adapter

        Firebase.firestore.collection(Firebase.auth.currentUser!!.uid+REEL).get().addOnSuccessListener {
            val tempList = arrayListOf<Reels>()
            for (i in it.documents) {
                val reel: Reels = i.toObject<Reels>()!!
                tempList.add(reel)
            }

            reelList.addAll(tempList)
            adapter.notifyDataSetChanged()
        }

        return binding.root
    }
    companion object {}

}
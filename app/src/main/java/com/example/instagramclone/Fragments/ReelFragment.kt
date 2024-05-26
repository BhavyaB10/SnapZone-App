package com.example.instagramclone.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.instagramclone.Adapters.ReelAdapter
import com.example.instagramclone.Model.Reels
import com.example.instagramclone.R
import com.example.instagramclone.Utils.REEL
import com.example.instagramclone.databinding.FragmentReelBinding
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject


class ReelFragment : Fragment() {
    private lateinit var binding : FragmentReelBinding
    lateinit var adapter:ReelAdapter
    var reelList=ArrayList<Reels>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentReelBinding.inflate(inflater, container, false)
        adapter= ReelAdapter(requireContext(),reelList)
        binding.viewPager.adapter=adapter

        Firebase.firestore.collection(REEL).get().addOnSuccessListener {

            val tempList=ArrayList<Reels>()
            reelList.clear()

            for(i in it.documents){
                var reel = i.toObject<Reels>()!!
                tempList.add(reel)
            }
            reelList.addAll(tempList)
            reelList.reverse()
            adapter.notifyDataSetChanged()
        }


        return binding.root
    }

    companion object {
    }
}
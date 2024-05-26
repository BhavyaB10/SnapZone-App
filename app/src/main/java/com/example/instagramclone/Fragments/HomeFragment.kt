package com.example.instagramclone.Fragments

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.instagramclone.Adapters.FollowAdapter
import com.example.instagramclone.Adapters.PostAdapter
import com.example.instagramclone.Model.Posts
import com.example.instagramclone.Model.User
import com.example.instagramclone.R
import com.example.instagramclone.Utils.FOLLOW
import com.example.instagramclone.Utils.POST
import com.example.instagramclone.databinding.FragmentHomeBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.firestore.toObjects

class HomeFragment : Fragment() {
    private lateinit var binding :FragmentHomeBinding
    private var postList = ArrayList<Posts>()
    private var followList=ArrayList<User>()
    private lateinit var adapter:PostAdapter
    private lateinit var followAdapter:FollowAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        adapter = PostAdapter(requireContext(),postList)
        binding.postRv.layoutManager=LinearLayoutManager(requireContext())
        binding.postRv.adapter=adapter

        followAdapter= FollowAdapter(requireContext(),followList)
        binding.followedPeoples.layoutManager=LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
        binding.followedPeoples.adapter=followAdapter

        setHasOptionsMenu(true)


        (requireContext() as AppCompatActivity).setSupportActionBar(binding.materialToolbar2)

        Firebase.firestore.collection(Firebase.auth.currentUser!!.uid+ FOLLOW).get().addOnSuccessListener {
            var templist=ArrayList<User>()
            followList.clear()
            for(i in it.documents){
                var user:User=i.toObject<User>()!! //Typecasting
                templist.add(user)
            }

            followList.addAll(templist)
            followAdapter.notifyDataSetChanged()
        }
        Firebase.firestore.collection(POST).get().addOnSuccessListener {

            var tempList=ArrayList<Posts>()
            postList.clear()
            for(i in it.documents){
                var post:Posts=i.toObject<Posts>()!!
                tempList.add(post)
            }
            postList.addAll(tempList)
            adapter.notifyDataSetChanged()
        }


        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.option_menu,menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    companion object {}
}
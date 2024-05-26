package com.example.instagramclone.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.instagramclone.Adapters.SearchAdapter
import com.example.instagramclone.Model.User
import com.example.instagramclone.R
import com.example.instagramclone.Utils.USER_NODE
import com.example.instagramclone.databinding.FragmentSearchBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject


class SearchFragment : Fragment() {
    private lateinit var binding: FragmentSearchBinding
    lateinit var adapter: SearchAdapter
    var userList = ArrayList<User>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        adapter = SearchAdapter(requireContext(), userList)
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        binding.rvFollow.layoutManager = LinearLayoutManager(requireContext())
        binding.rvFollow.adapter = adapter


        Firebase.firestore.collection(USER_NODE).get().addOnSuccessListener {

            var tempList = ArrayList<User>()
            userList.clear()

            for (i in it.documents) {

                if(i.id.toString().equals(Firebase.auth.currentUser!!.uid.toString())){

                }

                else{
                    //Typecasting
                    var user: User = i.toObject<User>()!!

                    tempList.add(user)
                }
                }
            userList.addAll(tempList)
            adapter.notifyDataSetChanged()
        }
        binding.searchButton.setOnClickListener {
            var text=binding.searchView.text.toString()
             Firebase.firestore.collection(USER_NODE).whereEqualTo("name",text).get().addOnSuccessListener {

                 var tempList = ArrayList<User>()
                 userList.clear()

                 if(it.isEmpty){

                 }
                 else{
                     for (i in it.documents) {

                         if(i.id.toString().equals(Firebase.auth.currentUser!!.uid.toString())){

                         }

                         else{
                             //Typecasting
                             var user: User = i.toObject<User>()!!

                             tempList.add(user)
                         }
                     }
                     userList.addAll(tempList)
                     adapter.notifyDataSetChanged()
                 }





             }

        }
        return binding.root
    }

    companion object {

    }
}

package com.example.instagramclone.Fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.instagramclone.Adapters.ViewPagerAdapter
import com.example.instagramclone.Model.User
import com.example.instagramclone.R
import com.example.instagramclone.SaveActivity
import com.example.instagramclone.SignUpActivity
import com.example.instagramclone.Utils.USER_NODE
import com.example.instagramclone.databinding.ActivityLoginBinding.inflate
import com.example.instagramclone.databinding.FragmentPostBinding
import com.example.instagramclone.databinding.FragmentProfileBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import com.squareup.picasso.Picasso

class ProfileFragment : Fragment() {
    private lateinit var binding:FragmentProfileBinding
    private lateinit var viewPagerAdapter: ViewPagerAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       binding=FragmentProfileBinding.inflate(inflater,container,false)

        binding.editProfile.setOnClickListener {
            val intent= Intent(activity,SignUpActivity::class.java)
            intent.putExtra("Mode",1)
           activity?.startActivity(intent)
            activity?.finish()
        }

        viewPagerAdapter=ViewPagerAdapter(requireActivity().supportFragmentManager)
        viewPagerAdapter.addFragment(MyPostFragment(),"My Post")
        viewPagerAdapter.addFragment(MyReelsFragment(),"My Reels")
        binding.viewPager.adapter=viewPagerAdapter
        binding.tabLayout.setupWithViewPager(binding.viewPager)

        binding.threeDots.setOnClickListener {
            startActivity(Intent(requireContext(),SaveActivity::class.java))
        }

        return binding.root
    }

    companion object {

    }

    override fun onStart() {
        super.onStart()

        Firebase.firestore.collection(USER_NODE).document(Firebase.auth.currentUser!!.uid).get()
            .addOnSuccessListener {
                val user:User = it.toObject<User>()!!
                binding.name.text=user.name
                binding.bio.text=user.email
                //Check Image are present or not
                if(!user.image.isNullOrEmpty()){
                    Picasso.get().load(user.image).into(binding.profileImage)
                }

            }
    }
}
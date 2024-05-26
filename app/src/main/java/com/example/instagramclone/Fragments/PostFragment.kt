package com.example.instagramclone.Fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.instagramclone.Post.PostActivity
import com.example.instagramclone.Post.ReelsActivity
import com.example.instagramclone.R
import com.example.instagramclone.databinding.FragmentPostBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class PostFragment : BottomSheetDialogFragment() {
    private lateinit var binding : FragmentPostBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentPostBinding.inflate(inflater, container, false)

        binding.post.setOnClickListener{
            activity?.startActivity(Intent(requireContext(),PostActivity::class.java))
            activity?.finish()
        }

        binding.reels.setOnClickListener{
            activity?.startActivity(Intent(requireContext(), ReelsActivity::class.java))
        }

        return binding.root
    }

    companion object {

    }
}
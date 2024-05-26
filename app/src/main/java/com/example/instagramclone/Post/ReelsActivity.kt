package com.example.instagramclone.Post

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.instagramclone.HomeActivity
import com.example.instagramclone.Model.Reels
import com.example.instagramclone.Model.User
import com.example.instagramclone.Utils.*
import com.example.instagramclone.databinding.ActivityReelsBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject

class ReelsActivity : AppCompatActivity() {

    val binding by lazy{
        ActivityReelsBinding.inflate(layoutInflater)

    }

    private lateinit var videoUrl:String

    private lateinit var progressDialog:ProgressDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        progressDialog= ProgressDialog(this)

        setSupportActionBar(binding.materialToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        binding.materialToolbar.setNavigationOnClickListener {
            startActivity(Intent(this@ReelsActivity,HomeActivity::class.java))
            finish()
        }


        val launchar =
            registerForActivityResult(ActivityResultContracts.GetContent()) { uri -> //Uniform Resource Identifier
                uri?.let {
                    uploadVideo(uri, REEL_FOLDER,progressDialog) { url ->
                        if (url != null) {

                            videoUrl = url
                        }
                    }
                }
            }


        binding.selectReel.setOnClickListener {
            launchar.launch("video/*")
        }

        binding.buttonCancel.setOnClickListener {
            startActivity(Intent(this@ReelsActivity, HomeActivity::class.java))
            finish()
        }

        binding.buttonPost.setOnClickListener {
            Firebase.firestore.collection(USER_NODE).document(Firebase.auth.currentUser!!.uid).get().addOnSuccessListener {
                var user : User = it.toObject<User>()!!

                val reel: Reels = Reels(videoUrl!!, binding.caption.editText?.text.toString(),user.image!!)

                Firebase.firestore.collection(REEL).document().set(reel).addOnSuccessListener {

                    Firebase.firestore.collection(Firebase.auth.currentUser!!.uid+ REEL).document().set(reel)
                        .addOnSuccessListener {
                            startActivity(Intent(this@ReelsActivity,HomeActivity::class.java))
                            finish()
                        }
                }


            }
        }

    }
}
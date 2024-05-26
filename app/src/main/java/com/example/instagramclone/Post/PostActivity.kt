package com.example.instagramclone.Post

import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.instagramclone.HomeActivity
import com.example.instagramclone.Model.Posts
import com.example.instagramclone.Model.User
import com.example.instagramclone.Utils.POST
import com.example.instagramclone.Utils.POST_FOLDER
import com.example.instagramclone.Utils.USER_NODE
import com.example.instagramclone.Utils.uploadImage
import com.example.instagramclone.databinding.ActivityPostBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject

class PostActivity : AppCompatActivity() {
    val binding by lazy {
        ActivityPostBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        var imageUrl: String? = null

        val launchar =
            registerForActivityResult(ActivityResultContracts.GetContent()) { uri -> //Uniform Resource Identifier
                uri?.let {
                    uploadImage(uri, POST_FOLDER) { url ->
                        if (url != null) {
                            binding.selectImage.setImageURI(uri)
                            imageUrl = url
                        }
                    }
                }
            }

        setSupportActionBar(binding.materialToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        binding.selectImage.setOnClickListener {
            launchar.launch("image/*")
        }

        binding.buttonCancel.setOnClickListener {
            startActivity(Intent(this@PostActivity, HomeActivity::class.java))
            finish()
        }

        binding.materialToolbar.setNavigationOnClickListener {
            startActivity(Intent(this@PostActivity, HomeActivity::class.java))
            finish()
        }

        binding.buttonPost.setOnClickListener {
            Firebase.firestore.collection(USER_NODE).document().get().addOnSuccessListener {

//                var user = it.toObject<User>()!!

                val post: Posts = Posts(
                    postUrl = imageUrl!!,
                    caption = binding.caption.editText?.text.toString(),
                    uid = Firebase.auth.currentUser!!.uid,
                    time = System.currentTimeMillis().toString()
                )

                Firebase.firestore.collection(POST).document().set(post).addOnSuccessListener {

                    Firebase.firestore.collection(Firebase.auth.currentUser!!.uid).document()
                        .set(post)
                        .addOnSuccessListener {
                            startActivity(Intent(this@PostActivity, HomeActivity::class.java))
                            finish()
                        }
                }

            }


        }
    }
}
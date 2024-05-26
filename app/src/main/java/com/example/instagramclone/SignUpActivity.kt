package com.example.instagramclone

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.example.instagramclone.Model.User
import com.example.instagramclone.Utils.USER_NODE
import com.example.instagramclone.Utils.USER_PROFILE_FOLDER
import com.example.instagramclone.Utils.uploadImage
import com.example.instagramclone.databinding.ActivitySignUpBinding
import com.google.firebase.Firebase
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import com.squareup.picasso.Picasso

class SignUpActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivitySignUpBinding.inflate(layoutInflater)
    }
    private lateinit var user: User
    private val launchar =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri -> //Uniform Resource Identifier
            uri?.let {
                uploadImage(uri, USER_PROFILE_FOLDER) {
                    if (it != null) {
                        user.image = it
                        binding.profileImage.setImageURI(uri)
                    }
                }
            }
        }

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val text =
            "<font color =#FF000000>Already have an account ? </font><font color =#1E88E5>Login</font>"
        binding.login.text = Html.fromHtml(text)

        user = User()

        if (intent.hasExtra("MODE")) {
            if (intent.getIntExtra("MODE", -1) == 1) {
                binding.signUpBtn.text = "Update Profile"
                //Here we get user data
                Firebase.firestore.collection(USER_NODE).document(Firebase.auth.currentUser!!.uid)
                    .get()
                    .addOnSuccessListener {
                        user = it.toObject<User>()!!

                        //Here we get image which we want to upgrade
                        if (!user.image.isNullOrEmpty()) {
                            Picasso.get().load(user.image).into(binding.profileImage)
                        }
                        binding.textName?.editText?.setText(user.name)
                        binding.textEmail?.editText?.setText(user.email)
                        binding.textPassword?.editText?.setText(user.password)

                    }
            }
        }

        binding.signUpBtn?.setOnClickListener {
            if (intent.hasExtra("MODE")) {
                if (intent.getIntExtra("MODE", -1) == 1) {

                    Firebase.firestore.collection(USER_NODE)
                        .document(Firebase.auth.currentUser!!.uid).set(user)
                }

            } else {


                if (binding.textName?.editText?.text.toString() == "" ||
                    binding.textEmail?.editText?.text.toString() == "" ||
                    binding.textPassword?.editText?.text.toString() == ""
                ) {
                    Toast.makeText(applicationContext, "Please Enter Details", Toast.LENGTH_SHORT)
                        .show()

                } else {
                    FirebaseAuth.getInstance().createUserWithEmailAndPassword(
                        binding.textEmail?.editText?.text.toString(),
                        binding.textPassword.editText?.text.toString(),
                    ).addOnCompleteListener { result ->
                        if (result.isSuccessful) {
                            user.name = binding.textName?.editText?.text.toString()
                            user.email = binding.textEmail?.editText?.text.toString()
                            user.password = binding.textPassword?.editText?.text.toString()
                            Firebase.firestore.collection(USER_NODE)
                                .document(Firebase.auth.currentUser!!.uid).set(user)
                                .addOnSuccessListener {
                                    startActivity(
                                        Intent(
                                            this@SignUpActivity,
                                            HomeActivity::class.java
                                        )
                                    )
                                    finish()
                                }
                        } else {
                            Toast.makeText(
                                applicationContext,
                                result.exception?.localizedMessage,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }

                }
            }
            FirebaseApp.initializeApp(this@SignUpActivity)

        }

        binding.addImage.setOnClickListener {
            launchar.launch("image/*")
        }
        binding.login?.setOnClickListener {
            startActivity(Intent(this@SignUpActivity, LoginActivity::class.java))
            finish()
        }
    }
}
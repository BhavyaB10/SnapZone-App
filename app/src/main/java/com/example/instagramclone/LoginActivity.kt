package com.example.instagramclone

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.instagramclone.Model.User
import com.example.instagramclone.databinding.ActivityLoginBinding
import com.example.instagramclone.databinding.ActivitySignUpBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.auth.userProfileChangeRequest

class LoginActivity : AppCompatActivity() {
    lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityLoginBinding.inflate(layoutInflater)
        val view=binding.root
        setContentView(view)

        binding.loginBtn.setOnClickListener{

            if(binding.email.editText?.text.toString() == ""
                ||binding.password.editText?.text.toString()==""){
                Toast.makeText(applicationContext,"Please enter details to login",Toast.LENGTH_SHORT).show()
            }
            else{
                var user = User(binding.email.editText?.text.toString(),binding.password.editText?.text.toString())

                Firebase.auth.signInWithEmailAndPassword(user.email!!,user.password!!)
                    .addOnCompleteListener {
                        if(it.isSuccessful){
                            startActivity(Intent(this@LoginActivity,HomeActivity::class.java))
                            finish()
                        }
                        else{
                            Toast.makeText(applicationContext,it.exception?.localizedMessage,Toast.LENGTH_SHORT).show()
                        }
                    }

            }


        }

        binding.createNewAccountBtn.setOnClickListener {
            startActivity(Intent(this@LoginActivity,SignUpActivity::class.java))
            startActivity(intent)
            finish()
        }

    }
}
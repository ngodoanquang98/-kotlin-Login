package com.example.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import com.example.login.databinding.ActivityLoginBinding
import com.example.login.databinding.ActivityProfileBinding
import com.google.firebase.auth.FirebaseAuth

class ProfileActivity : AppCompatActivity() {
    //viewBinding
    private lateinit var binding: ActivityProfileBinding

    private  lateinit var actionBar: ActionBar

    private  lateinit var  firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        actionBar = supportActionBar!!
        actionBar.title = "hehe hihi"

        firebaseAuth = FirebaseAuth.getInstance()
        checkUser()

        binding.logout.setOnClickListener{
            firebaseAuth.signOut()
            checkUser()
        }

    }

    private fun checkUser() {
        val firebaseUser =firebaseAuth.currentUser
        if(firebaseUser != null){
            val email =firebaseUser.email
            binding.emailhome.text =email
        }else{
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }
}
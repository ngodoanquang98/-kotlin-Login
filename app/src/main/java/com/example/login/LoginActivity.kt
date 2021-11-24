package com.example.login

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PatternMatcher
import android.text.TextUtils
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import com.example.login.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    //viewBinding
    private lateinit var binding:ActivityLoginBinding
    //ActionBar
    private  lateinit var actionBar:ActionBar
    //ProgressDialog
    private  lateinit var progressDialog:ProgressDialog
    //FirebaseAuth
    private  lateinit var  firebaseAuth:FirebaseAuth
    private var email = ""
    private var password = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //configure actionBar
        actionBar = supportActionBar!!
        actionBar.title = "Login"

        //configure ProgressDialog
        progressDialog = ProgressDialog(this)
        progressDialog.setTitle(" chờ tý ! ")
        progressDialog.setMessage(" đang vào ...")
        progressDialog.setCanceledOnTouchOutside(false)

        //init filebase
        firebaseAuth = FirebaseAuth.getInstance()
        checkUser()

        //handle click login
        binding.login.setOnClickListener{
            validateData()
        }

        //register
        binding.noacc.setOnClickListener{
            startActivity(Intent(this, SignUpActivity::class.java))
        }

    }

    private fun validateData() {
        //get data
        email = binding.email1.text.toString()
        password = binding.password1.text.toString()

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            binding.email1.error = "loi email"
        }else if(TextUtils.isEmpty(password)){
            binding.password1.error = "loi password"
        }else{
            firebaseLogin()
        }
    }

    private  fun firebaseLogin(){
        progressDialog.show()
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                progressDialog.dismiss()
                val firebaseUser = firebaseAuth.currentUser
                val email = firebaseUser?.email
                Toast.makeText(this,"dang nhap thanh cong $email",Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, ProfileActivity::class.java))
                finish()
            }
            .addOnFailureListener{ e->
                progressDialog.dismiss()
                Toast.makeText(this,"dang nhap loi ${e.message}",Toast.LENGTH_SHORT).show()
            }
    }

    private fun checkUser(){
        val firebaseUser =firebaseAuth.currentUser
        if(firebaseUser != null){
            startActivity(Intent(this, ProfileActivity::class.java))
            finish()
        }
    }
}
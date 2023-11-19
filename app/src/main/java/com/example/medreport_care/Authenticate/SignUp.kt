package com.example.medreport_care.Authenticate

import android.content.Intent
import android.os.Binder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import com.example.medreport_care.MainActivity
import com.example.medreport_care.R
import com.example.medreport_care.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

class SignUp : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var docref : DatabaseReference
    private lateinit var hwref :DatabaseReference
    private lateinit var binding: ActivitySignUpBinding
    private lateinit var user :String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()
        auth= Firebase.auth

        binding.tvAlreadySignin.setOnClickListener {
            var intent= Intent(this, login::class.java)
            startActivity(intent)

        }


        binding.Register.setOnClickListener{
            val email = binding.etEmailSignup.text.toString()
            val password = binding.etPassSignup.text.toString()

            if (binding.rdbtndoc.isChecked){
                user="doctor"
            }
            else{
                user = "Health Worker"
            }

            if (checkAllfield()){
                auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener{

                    if(it.isSuccessful){
                        userReg(user);
                        auth.signOut()
                        Toast.makeText(this,"Account has been created ",Toast.LENGTH_SHORT).show()

                        binding.etEmailSignup.text?.clear()
                        binding.etPassSignup.text?.clear()
                        binding.etUsernameSignup.text?.clear()
                        binding.phoneSignup.text?.clear()

                        Handler(Looper.getMainLooper()).postDelayed({
                            val intent = Intent(this, login::class.java)
                            startActivity(intent)
                            finish()
                        },100)
                    }
                    else{
                        Log.e("error",it.exception.toString())
                        Toast.makeText(this,"button not working",Toast.LENGTH_SHORT).show()
                    }
                }
            }

        }

    }


 //field check
    private fun checkAllfield() : Boolean{
        val username= binding.etUsernameSignup.text.toString()
        val email = binding.etEmailSignup.text.toString()
        val password = binding.etPassSignup.text.toString()
        val phone = binding.phoneSignup.text.toString()

        if(username==""){
            binding.etUsernameSignup.error="This is required field"
            return false
        }

        if(email==""){
            binding.etEmailSignup.error="This is required field"
            return false
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            binding.etEmailSignup.error="Email format wrong"
            return false
        }


        //password criteria
        if(password==""){
            binding.etPassSignup.error="This is required field"
            return false
        }

        if(password.length<=6){
            binding.etPassSignup.error="Password Should be atleast of 6 character"
            return false
        }
        if (phone==""){
            binding.phoneSignup.error="This is required field"
            return false
        }
        if (binding.rdgrpsignup.checkedRadioButtonId == -1){
            Toast.makeText(this,"Please Select User Type",Toast.LENGTH_SHORT).show()
            return false
        }

        return true
    }

    private fun userReg(user:String){
        val userId= FirebaseAuth.getInstance().currentUser?.uid.toString()
        docref = FirebaseDatabase.getInstance().getReference("Doctor Data").child(userId)
        hwref = FirebaseDatabase.getInstance().getReference("Health Woker Data").child(userId)

        if(user=="doctor"){
            docref.setValue(binding.etUsernameSignup.text.toString()).addOnCompleteListener {
                Toast.makeText(this, "Data Inserted Success", Toast.LENGTH_SHORT).show()
            }.addOnFailureListener {
                Toast.makeText(this, "Fail To Insert Data", Toast.LENGTH_SHORT).show()
            }
        }

        if(user!="doctor"){
            hwref.setValue(binding.etUsernameSignup.text.toString()).addOnCompleteListener {
                Toast.makeText(this, "Data Inserted Success", Toast.LENGTH_SHORT).show()
            }.addOnFailureListener {
                Toast.makeText(this, "Fail To Insert Data", Toast.LENGTH_SHORT).show()
            }
        }




    }
}
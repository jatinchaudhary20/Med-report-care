package com.example.medreport_care.Authenticate

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import com.example.medreport_care.doctor.reportList
import com.example.medreport_care.HealthWorker.patientList
import com.example.medreport_care.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class login : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var docref: DatabaseReference
    private lateinit var hwref: DatabaseReference

    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = Firebase.auth

        binding.signupPageText.setOnClickListener {
            val intent = Intent(this, SignUp::class.java)
            startActivity(intent)
        }

        binding.loginbutton.setOnClickListener {
            val email = binding.loginEmail.text.toString()
            val password = binding.loginPassword.text.toString()

            if (checkAllfield()) {

                //auth verification
                auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                    if (task.isSuccessful) {

                        //user check
                        val user = if (binding.radiodoc.isChecked){ "doctor"}
                        else {"Health Worker"}

                        // Launch a coroutine to execute userCheck function
                             GlobalScope.launch(Dispatchers.Main) {
                            val check = userCheck(user)
                            // Now you can use the result here
                            if (check) {
                                // The user is a doctor or health worker
                                Toast.makeText(this@login, "Login successful", Toast.LENGTH_SHORT).show()

                                if(user =="doctor"){
                                    val intent = Intent(this@login,reportList::class.java)
                                    startActivity(intent)
                                    finish()
                                }

                                else if(user=="Health Worker"){
                                    val intent = Intent(this@login,patientList::class.java)
                                    startActivity(intent)
                                    finish()
                                }

                                else{
                                    Toast.makeText(this@login,"error login user not found",Toast.LENGTH_SHORT).show()
                                }







                            } else {
                                // The user is not a doctor or health worker
                                Toast.makeText(this@login, "Invalid user type", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                    else {
                        Toast.makeText(this, "Login failed", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun checkAllfield(): Boolean {
        if (binding.loginEmail.text.toString().isEmpty()) {
            binding.loginEmail.error = "Field required"
            return false
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(binding.loginEmail.text.toString()).matches()) {
            binding.loginEmail.error = "Invalid format"
            return false
        }

        if (binding.loginPassword.text.toString().isEmpty()) {
            binding.loginPassword.error = "Field required"
            return false
        }

        if (binding.radiogroup.checkedRadioButtonId == -1) {
            Toast.makeText(this, "Please Select User Type", Toast.LENGTH_SHORT).show()
            return false
        }

        return true
    }

    private suspend fun userCheck(user: String): Boolean {
        val userId = FirebaseAuth.getInstance().currentUser?.uid.toString()
        docref = FirebaseDatabase.getInstance().getReference("Doctor Data").child(userId)
        hwref = FirebaseDatabase.getInstance().getReference("Health Worker Data").child(userId)

        return if (user == "doctor") {
            // Use withContext to perform the database operation in the IO dispatcher
            withContext(Dispatchers.IO) {
                docref.get().await().exists()
            }
        } else {
            // Use withContext to perform the database operation in the IO dispatcher
            withContext(Dispatchers.IO) {
                hwref.get().await().exists()
            }
        }
    }

}

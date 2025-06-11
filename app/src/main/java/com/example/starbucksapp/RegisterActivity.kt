package com.example.starbucksapp

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.starbucksapp.databinding.ActivityRegisterBinding
import java.util.*

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private var isOtpSent = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.btnSendOtp.setOnClickListener {
            val phoneNumber = binding.etPhoneNumber.text.toString()
            if (phoneNumber.isNotEmpty()) {
                isOtpSent = true
                binding.btnSendOtp.text = "RESEND OTP"
                binding.tilOtp.visibility = android.view.View.VISIBLE
                binding.llPersonalInfo.visibility = android.view.View.VISIBLE
                Toast.makeText(this, "OTP sent to $phoneNumber", Toast.LENGTH_SHORT).show()
            } else {
                binding.etPhoneNumber.error = "Phone number is required"
            }
        }

        binding.etDateOfBirth.setOnClickListener {
            showDatePicker()
        }

        binding.btnSignUp.setOnClickListener {
            if (validateRegistration()) {
                Toast.makeText(this, "Registration successful! (UI Demo)", Toast.LENGTH_SHORT).show()
                finish()
            }
        }

        binding.tvSignIn.setOnClickListener {
            finish()
        }
    }

    private fun showDatePicker() {
        val calendar = Calendar.getInstance()
        val datePickerDialog = DatePickerDialog(
            this,
            { _, year, month, dayOfMonth ->
                val selectedDate = "$dayOfMonth/${month + 1}/$year"
                binding.etDateOfBirth.setText(selectedDate)
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        datePickerDialog.show()
    }

    private fun validateRegistration(): Boolean {
        val phoneNumber = binding.etPhoneNumber.text.toString()
        val otp = binding.etOtp.text.toString()
        val name = binding.etName.text.toString()
        val email = binding.etEmail.text.toString()
        val password = binding.etPassword.text.toString()
        val confirmPassword = binding.etConfirmPassword.text.toString()
        val dateOfBirth = binding.etDateOfBirth.text.toString()

        if (phoneNumber.isEmpty()) {
            binding.etPhoneNumber.error = "Phone number is required"
            return false
        }

        if (!isOtpSent) {
            Toast.makeText(this, "Please send OTP first", Toast.LENGTH_SHORT).show()
            return false
        }

        if (otp.isEmpty()) {
            binding.etOtp.error = "OTP is required"
            return false
        }

        if (name.isEmpty()) {
            binding.etName.error = "Name is required"
            return false
        }

        if (email.isEmpty()) {
            binding.etEmail.error = "Email is required"
            return false
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.etEmail.error = "Invalid email format"
            return false
        }

        if (password.isEmpty()) {
            binding.etPassword.error = "Password is required"
            return false
        }

        if (password.length < 6) {
            binding.etPassword.error = "Password must be at least 6 characters"
            return false
        }

        if (confirmPassword.isEmpty()) {
            binding.etConfirmPassword.error = "Please confirm password"
            return false
        }

        if (password != confirmPassword) {
            binding.etConfirmPassword.error = "Passwords do not match"
            return false
        }

        if (dateOfBirth.isEmpty()) {
            binding.etDateOfBirth.error = "Date of birth is required"
            return false
        }

        return true
    }
}
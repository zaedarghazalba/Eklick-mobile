package com.example.ekclick

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.ekclick.api.RetrofitClient
import com.example.ekclick.model.LoginRequest
import com.example.ekclick.model.LoginResponse
import com.example.ekclick.model.RegisterRequest
import com.example.ekclick.model.RegisterResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LoginRegistActivity : AppCompatActivity() {

    private val apiService = RetrofitClient.apiService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_regist)

        // View references
        val submitLogincard = findViewById<View>(R.id.SubmitLogin)
        val submitDaftarcard = findViewById<View>(R.id.SubmitDaftar)
        val registrasi = findViewById<TextView>(R.id.Registrasi)
        val daftarButton = findViewById<View>(R.id.daftarButton)
        val login = findViewById<TextView>(R.id.Login)
        val loginButton = findViewById<View>(R.id.loginButton)
        val loginView = findViewById<View>(R.id.loginView)
        val registerView = findViewById<View>(R.id.registerView)

        // Setup listeners for changing views
        registrasi.setOnClickListener { showRegisterForm(loginView, registerView) }
        daftarButton.setOnClickListener { showRegisterForm(loginView, registerView) }
        login.setOnClickListener { showLoginForm(loginView, registerView) }
        loginButton.setOnClickListener { showLoginForm(loginView, registerView) }

        // Submit button for login
        submitLogincard.setOnClickListener {
            val email = findViewById<EditText>(R.id.edNameLogin).text.toString()
            val password = findViewById<EditText>(R.id.edPASSLogin).text.toString()
            handleLogin(email, password)
        }

        // Submit button for registration
        submitDaftarcard.setOnClickListener {
            val username = findViewById<EditText>(R.id.edNAMERegister).text.toString()
            val email = findViewById<EditText>(R.id.edEMAILRegister).text.toString()
            val password = findViewById<EditText>(R.id.edPASSRegister).text.toString()
            val password_confirmation = findViewById<EditText>(R.id.edPASSconfirmRegister).text.toString()

            handleRegistration(username, email, password, password_confirmation)
        }
    }

    private fun showRegisterForm(loginView: View, registerView: View) {
        loginView.visibility = View.GONE
        registerView.visibility = View.VISIBLE
    }

    private fun showLoginForm(loginView: View, registerView: View) {
        registerView.visibility = View.GONE
        loginView.visibility = View.VISIBLE
    }

    private fun handleLogin(username: String, password: String) {
        Log.d("LoginRegistActivity", "Login attempt with email: $username")

        // Validate inputs before making the API call
        if (username.isBlank() || password.isBlank()) {
            Log.d("LoginRegistActivity", "Login failed: Email or Password is empty")
            showToast("Email and Password cannot be empty")
            return
        }

        val loginRequest = LoginRequest(username, password)

        // Call the login API via Retrofit
        apiService.login(loginRequest).enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful) {
                    val loginResponse = response.body()
                    loginResponse?.let {
                        Log.d("LoginRegistActivity", "Login successful for user: $username")

                        // Save the access token in SharedPreferences
                        val tokenString = loginResponse.data.access_token  // Access token from the 'data' field
                        if (tokenString != null) {
                            val sharedPref = getSharedPreferences("user_prefs", MODE_PRIVATE)
                            val editor = sharedPref.edit()
                            editor.putString("access_token", tokenString) // Save the token
                            editor.putString("username", username) // Save the username
                            editor.apply()  // Use apply instead of commit for asynchronous save
                            Log.d("LoginRegistActivity", "Token saved result: true")

                            // Proceed to main activity upon success
                            startActivity(Intent(this@LoginRegistActivity, MainActivity::class.java))
                            showToast("Login Successful!")
                        } else {
                            Log.d("LoginRegistActivity", "Token was null, cannot save")
                            showToast("Login Failed: Token not found")
                        }
                    }
                } else {
                    // Log error response if the response is not successful
                    val errorResponseJson = response.errorBody()?.string()
                    Log.d("LoginRegistActivity", "Login failed: ${response.message()}")
                    Log.d("LoginRegistActivity", "Error Response JSON: $errorResponseJson")
                    showToast("Login Failed: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Log.e("LoginRegistActivity", "Login error: ${t.message}", t)
                showToast("Login failed: ${t.message}")
            }
        })
    }


    private fun handleRegistration(username: String, email: String, password: String, password_confirmation: String) {
        Log.d("LoginRegistActivity", "Registration attempt with username: $username, email: $email")

        // Validate input fields
        if (username.isBlank() || email.isBlank() || password.isBlank()) {
            Log.d("LoginRegistActivity", "Registration failed: Fields are empty")
            showToast("All fields are required")
            return
        }

        // Validate email format
        if (!isValidEmail(email)) {
            Log.d("LoginRegistActivity", "Registration failed: Invalid email format")
            showToast("Invalid email format")
            return
        }

        // Validate password match
        if (password != password_confirmation) {
            Log.d("LoginRegistActivity", "Registration failed: Passwords do not match")
            showToast("Passwords do not match")
            return
        }

        // Create the register request
        val registerRequest = RegisterRequest(username, email, password, password_confirmation)

        apiService.register(registerRequest).enqueue(object : Callback<RegisterResponse> {
            override fun onResponse(call: Call<RegisterResponse>, response: Response<RegisterResponse>) {
                if (response.isSuccessful) {
                    val registerResponse = response.body()
                    if (registerResponse != null) {
                        Log.d("LoginRegistActivity", "Registration successful for user: ${registerResponse.user?.name}")
                        // Handle success (navigate to another activity or show message)
                        showToast("Registration Successful!")
                        startActivity(Intent(this@LoginRegistActivity, MainActivity::class.java))
                    } else {
                        // Handle the case when the response is empty or null
                        Log.d("LoginRegistActivity", "Response body is null")
                        showToast("Registration failed: No response body.")
                    }
                } else {
                    // Log the error response as raw string for debugging
                    val errorResponse = response.errorBody()?.string()
                    Log.d("LoginRegistActivity", "Error Response: $errorResponse")
                    showToast("Registration failed: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                Log.e("LoginRegistActivity", "Registration error: ${t.message}", t)
                showToast("Registration failed: ${t.message}")
            }
        })
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}

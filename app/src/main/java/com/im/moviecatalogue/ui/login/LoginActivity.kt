package com.im.moviecatalogue.ui.login

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.im.moviecatalogue.R
import com.im.moviecatalogue.ui.main.MainActivity
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    companion object{
        private const val PRIVATE_MODE = 0
        private const val PREF_NAME = "MovieCatalogue"
        private const val IS_LOGIN = "IS_LOGIN"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        btn_login.setOnClickListener {
            login()
        }

    }

    private fun login(){
        val email = et_email.text.toString()
        val password = et_password.text.toString()

        if(email.isEmpty()) showMessage("Please input email")
        else if(password.isEmpty()) showMessage("Please input password")
        else if(!email.contains("@")) showMessage("Please input valid email")
        else if(password.length < 9) showMessage("Password at least 8 characters")
        else {
            var sharedPref: SharedPreferences = getSharedPreferences(
                PREF_NAME,
                PRIVATE_MODE
            )
            sharedPref.edit().putBoolean(IS_LOGIN, true).apply()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun showMessage(message: String){
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }
}
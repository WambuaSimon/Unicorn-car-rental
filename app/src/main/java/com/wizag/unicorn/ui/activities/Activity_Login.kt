package com.wizag.unicorn.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.wizag.unicorn.R
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.singleTop

class Activity_Login : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        login.setOnClickListener {
            startActivity(intentFor<MainActivity>().singleTop())
        }
    }
}

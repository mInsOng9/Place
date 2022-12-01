package com.song.wheretogo.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.song.wheretogo.R
import com.song.wheretogo.databinding.ActivitySignupBinding

class SignupActivity : AppCompatActivity() {

    val binding:ActivitySignupBinding by lazy{ActivitySignupBinding.inflate(layoutInflater)}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_signup)
        setContentView(binding.root)

        //set toolbar as action bar
        setSupportActionBar(binding.toolbar)
        // upbutton w/ no title
        supportActionBar?.setDisplayHomeAsUpEnabled(true) //upbutton
        supportActionBar?.setDisplayShowTitleEnabled(false)//dont show title
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_ios_24) //change upbutton image

        binding.btnSignup.setOnClickListener { clickSignUp() }
    }


    //callback method of upbutton
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    private fun clickSignUp(){
        //insert user's signup info into Firebase Firestore DB [앱과 firebase 플랫폼 연동]
    }//clickSignUP()
}
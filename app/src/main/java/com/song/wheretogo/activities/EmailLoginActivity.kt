package com.song.wheretogo.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.firestore.FirebaseFirestore
import com.song.wheretogo.G
import com.song.wheretogo.R
import com.song.wheretogo.databinding.ActivityEmailLoginBinding
import com.song.wheretogo.model.UserAccount

class EmailLoginActivity : AppCompatActivity() {

    val binding:ActivityEmailLoginBinding by lazy { ActivityEmailLoginBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_email_login)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_ios_24)

        binding.btnSignin.setOnClickListener { clickSignin() }
    }

    //upbutton click listener
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    private fun clickSignin(){
        var email=binding.etEmail.text.toString()
        var password=binding.etPassword.text.toString()

        //Firebase Firestore DB 에서 이메일 로그인 여부 확인

        var db:FirebaseFirestore=FirebaseFirestore.getInstance()
        db.collection("emailUsers")
            .whereEqualTo("email",email)
            .whereEqualTo("password",password)
            .get().addOnSuccessListener {
                if(it.documents.size>0){ //where 조건에 맞는 데이터가 있다는 것
                    //sign-in success [ 회원정보를 다른 Activity에서도 사용할 가능성 있으므로 전역변수처럼 클래스 이름만으로 사용가능한 변수에 저장하기]
                    var id=it.documents[0].id //document의 랜덤한 식별자
                    G.userAccount= UserAccount(id,email)

                    //sign-in success! -> move to MainActivity page
                    val intent= Intent(this,MainActivity::class.java)
                    //다른 액티비티로 넘어가면서 task에 있는 모든 액티비티들을 제거하고 새로운 task로 시작
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)

                }else{
                    //sign-in failed
                    AlertDialog.Builder(this).setMessage("Check your email and password again").show()
                    binding.etEmail.requestFocus()
                    binding.etEmail.selectAll()
                }
            }.addOnFailureListener {
                Toast.makeText(this, "error occured : ${it.message}", Toast.LENGTH_SHORT).show()
            }
    }//clickSignin()
}
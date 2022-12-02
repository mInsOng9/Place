package com.song.wheretogo.activities

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.firestore.FirebaseFirestore
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

        var email:String =binding.etEmail.text.toString()
        var password:String = binding.etPassword.text.toString()
        var passwordConfirm:String = binding.etPasswordConfirm.text.toString()

        //원래는 정규표현식(RegExp)을 이용하여 유효성 검사함. 시간상 pass

        //패스워드가 올바른지 확인
        if(password!=passwordConfirm){
            AlertDialog.Builder(this).setMessage("Password did not match.").show()
            binding.etPasswordConfirm.selectAll() //입력되어 있ㅆ는 글씨를 모두 선택상태로 하여 손쉽게 새로 입력이 가능.
            return
        }

        //Firebase Firestore DB에 저장하기 위해 Firestore DB 관리자객체 소환
        var db:FirebaseFirestore=FirebaseFirestore.getInstance()

        // 이미 가입한 적이 있는 email 인지 검사
        // 필드값 중에 'email' 의 값이 EditText에 입력한 email과 같은 것이 있는 찾아달라고 요청
        db.collection("emailUsers")
            .whereEqualTo("email", email)
            .get().addOnSuccessListener {
                //같은 값을 가진 Document가 있다면.. 기존에 같은 email이 있다는 것임
                if(it.documents.size>0){
                    AlertDialog.Builder(this).setMessage("Email already exists.").show()
                    binding.etEmail.requestFocus() //selectAll()하려면 포커스가 있어야 함.
                    binding.etEmail.selectAll()
                }else{
                    //신규 email

                    //저장할 데이터들을 하나로 묶기위해 HashMap
                    var user:MutableMap<String,String> = mutableMapOf()
                    user.put("email",email)
                    user.put("password",password)

                    //DB안에 collection 명은 "emailUsers"로 지정 [ RDBMS의 테이블 이름 같은역할]
                    //별도의 Document 명을 주지 않으면 Random 값으로 설정됨. 이 랜덤값을 회원번호의 역할로 사용함.
                    db.collection("emailUsers").add(user).addOnSuccessListener {
                        AlertDialog.Builder(this)
                            .setMessage("Congratulations! \n Sign-up completed")
                            .setPositiveButton("OK",object:DialogInterface.OnClickListener{
                                override fun onClick(p0: DialogInterface?, p1: Int) {
                                    finish()
                                }

                            }).show()
                    }.addOnFailureListener {
                        Toast.makeText(this, "error occured with your sign-up. "+it.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }

    }//clickSignUP()
}
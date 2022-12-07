package com.song.wheretogo.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Toast
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.util.Utility
import com.kakao.sdk.user.UserApiClient
import com.song.wheretogo.G
import com.song.wheretogo.databinding.ActivityLoginBinding
import com.song.wheretogo.model.UserAccount

class LoginActivity : AppCompatActivity() {
    val binding:ActivityLoginBinding by lazy { ActivityLoginBinding.inflate(LayoutInflater.from(this)) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_login)
        setContentView(binding.root)

        // 둘러보기 글씨 클릭으로 로그인 없이 Main 화면으로 이동
        binding.tvGo.setOnClickListener {
            startActivity(Intent(this,MainActivity::class.java))
            finish()
        }
        //회원가입 버튼 클릭
        binding.tvSignup.setOnClickListener {
            startActivity(Intent(this,SignupActivity::class.java))
        }

        //이메일 로그인 버튼 클릭
        binding.layoutEmail.setOnClickListener {
            startActivity(Intent(this,EmailLoginActivity::class.java))
        }

        //간편로그인 버튼들
        binding.btnLoginKakao.setOnClickListener { clickedLoginKakao() }
        binding.btnLoginGoogle.setOnClickListener { clickedLoginGoogle() }
        binding.btnLoginNaver.setOnClickListener { clickedLoginNaver() }


        //Kakao SDK용 KeyHash 값
        var keyHash= Utility.getKeyHash(this)
        Log.i("keyHash",keyHash)
    }

    fun clickedLoginKakao(){
        //Kakao Login API를 이용하여 사용자 정보 취득

        //Login 시도한 결과를 받았을때 발동하는 콜백함수를 별도로 만들기
        val callback:(OAuthToken?,Throwable?)->Unit ={ token,error ->
            if(error!=null) Toast.makeText(this, "kakao login failed", Toast.LENGTH_SHORT).show()
            else {
                Toast.makeText(this, "kakao login success", Toast.LENGTH_SHORT).show()

                UserApiClient.instance.me { user, error ->
                    if(user!=null){
                        var id:String=user.id.toString()
                        var email=user.kakaoAccount?.email ?:"" //혹시 null이면 이메일의 기본값을 ""

                        Toast.makeText(this, "email: $email", Toast.LENGTH_SHORT).show()
                        G.userAccount= UserAccount(id,email)

                        startActivity(Intent(this,MainActivity::class.java))
                        finish()

                    }
                }
            }
        }

        //Login with KakaoTalk is recommended but if kakaotalk is not installed Login with KakaoAccount
        if(UserApiClient.instance.isKakaoTalkLoginAvailable(this)){
            UserApiClient.instance.loginWithKakaoTalk(this, callback = callback)
        }else{
            UserApiClient.instance.loginWithKakaoAccount(this,callback=callback)
        }
    }
    fun clickedLoginGoogle(){
        //구글 로그인 화면(액티비티)를 실행하여 결과를 받아와서 사용자정보 취득
    }
    fun clickedLoginNaver(){
        //사용자정보를 취득하는 토큰값을 발급받아 REST API 방식으로 사용자정보를 취득
    }
}
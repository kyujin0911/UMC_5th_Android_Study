package umc.mission.floclone.ui.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import umc.mission.floclone.R
import umc.mission.floclone.data.local.AUTH
import umc.mission.floclone.data.local.JWT
import umc.mission.floclone.data.remote.auth.AuthService
import umc.mission.floclone.ui.login.LoginActivity
import umc.mission.floclone.ui.main.MainActivity

class SplashActivity : AppCompatActivity(), LoginAutoView {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val authService = AuthService()
        authService.setLoginAutoView(this)

        val spf = getSharedPreferences(AUTH, MODE_PRIVATE)
        val token = spf.getString(JWT, "")!!
        Log.d("token", token.toString())
        authService.loginAuto(token)
    }

    override fun onLoginSuccess() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    override fun onLoginFailure() {
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }
}
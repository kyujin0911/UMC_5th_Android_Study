package umc.mission.floclone.data.remote.auth

import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import umc.mission.floclone.ui.splash.LoginAutoView
import umc.mission.floclone.ui.login.LoginView
import umc.mission.floclone.ui.singup.SignUpView
import umc.mission.floclone.data.entities.User
import umc.mission.floclone.utils.getRetrofit

class AuthService {
    private lateinit var signUpView: SignUpView
    private lateinit var loginView: LoginView
    private lateinit var loginAutoView: LoginAutoView

    fun setSignUpView(signUpView: SignUpView){
        this.signUpView = signUpView
    }

    fun setLoginView(loginView: LoginView){
        this.loginView = loginView
    }

    fun setLoginAutoView(loginAutoView: LoginAutoView){
        this.loginAutoView = loginAutoView
    }

    fun signUp(user: User){
        val authService = getRetrofit().create(AuthRetrofitInterface::class.java)
        authService.singUp(user).enqueue(object: Callback<AuthResponse> {
            override fun onResponse(call: Call<AuthResponse>, response: Response<AuthResponse>) {
                Log.d("SIGNUP/SUCCESS", response.toString())
                val resp: AuthResponse = response.body()!!
                when(resp.code){
                    1000 -> signUpView.onSignUpSuccess()
                    else -> {
                        signUpView.onSignUpFailure(resp.message) }
                    }
                }

            override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
                Log.d("SIGNUP/FAILURE", t.message.toString())
            }
        })
        Log.d("SIGNUP", "HELLO")
    }

    fun login(user: User){
        val authService = getRetrofit().create(AuthRetrofitInterface::class.java)
        authService.login(user).enqueue(object: Callback<AuthResponse> {
            override fun onResponse(call: Call<AuthResponse>, response: Response<AuthResponse>) {
                Log.d("LOGIN/SUCCESS", response.toString())
                val resp: AuthResponse = response.body()!!
                when(resp.code){
                    1000 -> loginView.onLoginSuccess(resp.code, resp.result!!)
                    else -> loginView.onLoginFailure(resp.code)
                }
            }

            override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
                Log.d("LOGIN/FAILURE", t.message.toString())
            }
        })
        Log.d("LOGIN", "HELLO")
    }

    fun loginAuto(token: String){
        val authService = getRetrofit().create(AuthRetrofitInterface::class.java)
        authService.loginAuto(token).enqueue(object: Callback<AuthResponse> {
            override fun onResponse(call: Call<AuthResponse>, response: Response<AuthResponse>) {
                Log.d("LOGIN-AUTO/SUCCESS", response.toString())
                val resp: AuthResponse = response.body()!!
                Log.d("LOGIN_AUTO", response.body().toString())
                when(resp.code){
                    1000 -> loginAutoView.onLoginSuccess()
                    else -> loginAutoView.onLoginFailure()
                }
            }

            override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
                Log.d("LOGIN-AUTO/FAILURE", t.message.toString())
            }
        })
        Log.d("LOGIN-AUTO", "HELLO")
    }
}
package umc.mission.floclone.ui.login

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Toast
import umc.mission.floclone.R
import umc.mission.floclone.ui.singup.SignUpActivity
import umc.mission.floclone.data.local.AUTH
import umc.mission.floclone.data.local.JWT
import umc.mission.floclone.data.remote.auth.Result
import umc.mission.floclone.data.entities.User
import umc.mission.floclone.databinding.ActivityLoginBinding
import umc.mission.floclone.databinding.ToastLikeBinding
import umc.mission.floclone.data.remote.auth.AuthService
import umc.mission.floclone.ui.main.MainActivity

class LoginActivity : AppCompatActivity(), LoginView {
    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.activityLoginSignUpTv.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }
        Log.d("test", "${binding.activityLoginIdEt.text.toString()}")

        enableLoginBtn()

        binding.activityLoginLoginBtn.setOnClickListener {
            login()
        }
    }

    private fun login(){
        if(binding.activityLoginIdEt.text.toString().isEmpty() || binding.activityLoginEmailInputEt.text.toString().isEmpty()){
            Toast.makeText(this, "이메일을 입력해주세요", Toast.LENGTH_SHORT).show()
            return
        }

        if(binding.activityLoginPwEt.text.toString().isEmpty()){
            Toast.makeText(this, "비밀번호를 입력해주세요", Toast.LENGTH_SHORT).show()
            return
        }

        val email: String = binding.activityLoginIdEt.text.toString() + "@" +
                binding.activityLoginEmailInputEt.text.toString()
        val password: String = binding.activityLoginPwEt.text.toString()

        /*val userDB = SongDatabase.getInstance(this)!!
        val user = userDB.userDao().getUser(email, password)
        user?.let {
            Log.d("LOGIN_ACT/GET_USER", user.toString())
            saveJWT(user.id)
            startActivity(Intent(this, MainActivity::class.java))
        }
        val users = userDB.userDao().getUsers()
        if(!users.contains(user))
            Toast.makeText(this, "회원정보가 존재하지 않습니다.", Toast.LENGTH_SHORT).show()*/
        val authService = AuthService()
        authService.setLoginView(this)

        authService.login(User(email, password, ""))
    }

    private fun saveJWT(jwt: String){
        val spf = getSharedPreferences(AUTH, MODE_PRIVATE)
        val editor = spf.edit()
        editor.putString(JWT, jwt)
        editor.apply()
    }

    private fun enableLoginBtn(){

        val textWatcher = object: TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                if(binding.activityLoginIdEt.text.toString().isNotEmpty() && binding.activityLoginEmailInputEt.text.toString().isNotEmpty()
                    && binding.activityLoginPwEt.text.toString().isNotEmpty()){
                    binding.activityLoginLoginBtn.apply {
                        isEnabled = true
                        setTextColor(Color.WHITE)
                        setBackgroundColor(resources.getColor(R.color.category_selected))
                    }
                }
                else{
                    binding.activityLoginLoginBtn.apply {
                        isEnabled = false
                        setTextColor(resources.getColor(R.color.category_unselected))
                        setBackgroundColor(resources.getColor(R.color.category_unselected_btn))
                    }
                }
            }
        }
        binding.activityLoginPwEt.addTextChangedListener(textWatcher)
        binding.activityLoginIdEt.addTextChangedListener(textWatcher)
        binding.activityLoginEmailInputEt.addTextChangedListener(textWatcher)
    }

    override fun onLoginSuccess(code: Int, result: Result) {
        when(code){
            1000 -> {
                saveJWT(result.jwt)
                startActivity(Intent(this, MainActivity::class.java))
            }
        }
    }

    override fun onLoginFailure(code: Int) {
        val toastLikeBinding = ToastLikeBinding.inflate(layoutInflater)
        toastLikeBinding.run {
            toastLikeBinding.toastTv.text = when (code) {
                2015 -> "이메일을 입력해주세요."
                2019 -> "존재하지 않는 계정입니다."
                else -> "존재하지 않는 아이디이거나 비밀번호가 틀렸습니다."
            }
            val toast = Toast(this@LoginActivity)
            toast.view = toastLikeBinding.root
            toast.duration = Toast.LENGTH_SHORT
            toast.show()
        }
    }
}
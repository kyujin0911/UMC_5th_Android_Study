package umc.mission.floclone

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import umc.mission.floclone.data.AUTH
import umc.mission.floclone.data.JWT
import umc.mission.floclone.data.SongDatabase
import umc.mission.floclone.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
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

        val userDB = SongDatabase.getInstance(this)!!
        val user = userDB.userDao().getUser(email, password)
        user?.let {
            Log.d("LOGIN_ACT/GET_USER", user.toString())
            saveJWT(user.id)
            startActivity(Intent(this, MainActivity::class.java))
        }
        val users = userDB.userDao().getUsers()
        if(!users.contains(user))
            Toast.makeText(this, "회원정보가 존재하지 않습니다.", Toast.LENGTH_SHORT).show()
    }

    private fun saveJWT(jwt: Int){
        val spf = getSharedPreferences(AUTH, MODE_PRIVATE)
        val editor = spf.edit()
        editor.putInt(JWT, jwt)
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
}
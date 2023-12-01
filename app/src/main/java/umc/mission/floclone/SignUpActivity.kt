package umc.mission.floclone

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import umc.mission.floclone.data.SongDatabase
import umc.mission.floclone.data.User
import umc.mission.floclone.databinding.ActivitySignUpBinding

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.activitySingSignUpBtn.setOnClickListener {
            signUp()
        }
    }

    private fun getUser(): User {
        val email: String = binding.activitySignIdEt.text.toString() + "@" +
                binding.activitySignDirectInputEt.text.toString()
        val password: String = binding.activitySignPasswordEt.text.toString()

        return User(email, password)
    }

    private fun signUp(){
        if(binding.activitySignIdEt.text.toString().isEmpty() || binding.activitySignDirectInputEt.text.toString().isEmpty()){
            Toast.makeText(this, "이메일 형식이 잘못되었습니다.", Toast.LENGTH_SHORT).show()
            return
        }

        if(binding.activitySignPasswordEt.text.toString() != binding.activitySignPasswordCheckEt.text.toString()){
            Toast.makeText(this, "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show()
            return
        }

        val userDB = SongDatabase.getInstance(this)!!
        userDB.userDao().insert(getUser())

        val user = userDB.userDao().getUsers()
        Log.d("SignUp", user.toString())

        finish()
    }
}
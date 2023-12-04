package umc.mission.floclone.ui.singup

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import umc.mission.floclone.data.entities.User
import umc.mission.floclone.databinding.ActivitySignUpBinding
import umc.mission.floclone.data.remote.auth.AuthService

class SignUpActivity : AppCompatActivity(), SignUpView {
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
        var name: String = binding.activitySignNameEt.text.toString()

        return User(email, password, name)
    }

    /*private fun signUp(){
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
    }*/

    private fun signUp() {
        if (binding.activitySignIdEt.text.toString()
                .isEmpty() || binding.activitySignDirectInputEt.text.toString().isEmpty()
        ) {
            Toast.makeText(this, "이메일 형식이 잘못되었습니다.", Toast.LENGTH_SHORT).show()
            return
        }

        if (binding.activitySignNameEt.text.toString().isEmpty()) {
            Toast.makeText(this, "이름 형식이 잘못되었습니다.", Toast.LENGTH_SHORT).show()
            return
        }

        if (binding.activitySignPasswordEt.text.toString() != binding.activitySignPasswordCheckEt.text.toString()) {
            Toast.makeText(this, "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show()
            return
        }

        val authService = AuthService()
        authService.setSignUpView(this)

        authService.signUp(getUser())
    }

    override fun onSignUpSuccess() {
        finish()
    }

    override fun onSignUpFailure(respMessage: String) {
        binding.signUpEmailErrorTv.apply {
            visibility = View.VISIBLE
            text = respMessage
        }
    }
}

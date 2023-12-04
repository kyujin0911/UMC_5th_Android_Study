package umc.mission.floclone.ui.singup

interface SignUpView {
    fun onSignUpSuccess()
    fun onSignUpFailure(respMessage: String)
}
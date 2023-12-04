package umc.mission.floclone.ui.login

import umc.mission.floclone.data.remote.auth.Result

interface LoginView {
    fun onLoginSuccess(code: Int, result: Result)
    fun onLoginFailure(code: Int)
}
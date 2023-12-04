package umc.mission.floclone.data.remote.auth

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import umc.mission.floclone.data.entities.User

interface AuthRetrofitInterface {
    @POST("/users")
    fun singUp(@Body user: User): Call<AuthResponse>

    @POST("/users/login")
    fun login(@Body user: User): Call<AuthResponse>

    @GET("/users/auto-login")
    fun loginAuto(@Header("X-ACCESS-TOKEN") token: String?): Call<AuthResponse>
}
package fr.maloof.hapticscenariosv2.network


import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface ApiService {
    @POST("users")
    fun createUser(@Body user: DataModel.User): Call<DataModel.User>



    @POST("telephones")
    fun createTelephone(@Body telephone: DataModel.Telephone): Call<DataModel.Telephone>

    @POST("emotional_experiences")
    fun postEmotionalExperience(
        @Body experience: DataModel.EmotionalExperience
    ): Call<DataModel.EmotionalExperience>


}




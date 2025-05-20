package fr.maloof.hapticscenariosv2.network


import retrofit2.Call
import retrofit2.mock.Calls

class FakeApiService : ApiService {
    override fun createUser(user: DataModel.User): Call<DataModel.User> {
        return Calls.response(user) // retourne une "réponse immédiate" simulée
    }

    override fun createTelephone(telephone: DataModel.Telephone): Call<DataModel.Telephone> {
        return Calls.response(telephone)
    }

    override fun postEmotionalExperience(experience: DataModel.EmotionalExperience): Call<DataModel.EmotionalExperience> {
        return Calls.response(experience)
    }
}

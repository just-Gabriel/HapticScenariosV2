package fr.maloof.hapticscenariosv2.network


object ServiceLocator {

    // Change cette variable pour basculer entre vrai et faux backend
    private val useFakeApi = false

    val apiService: ApiService by lazy {
        if (useFakeApi) {
            FakeApiService()
        } else {
            RetrofitInstance.api
        }
    }
}

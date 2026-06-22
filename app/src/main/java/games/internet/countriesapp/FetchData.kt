package games.internet.countriesapp

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface CountryApi{
    @GET("all?fields=name,currencies,capital")
    suspend fun getCountries() : List<Country>
}


object RetrofitInstance{
    private const val BASE_URL = "https://restcountries.com/v3.1/"

    val api: CountryApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CountryApi::class.java)
    }
}
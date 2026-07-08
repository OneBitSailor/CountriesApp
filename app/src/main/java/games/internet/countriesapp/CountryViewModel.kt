package games.internet.countriesapp

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
/*The project does not have seperate repository*/
class CountryViewModel : ViewModel() {

    var countries by mutableStateOf<List<Country>>(emptyList())
        private set

    var filteredCountries by mutableStateOf<List<String>>(emptyList())
        private set

    var stats by mutableStateOf(DataStats(isLoading = false, errorMessage = null))
        private set

    init {
        fetchCountries()
    }

    fun onQueryChange(query: String) {
        val allNames = countries.map { it.name.common }

        println("Countries size: ${countries.size}")
        println("Filtered size: ${filteredCountries.size}")
        filteredCountries = if (query.isBlank()) {
            allNames
        } else {
            allNames.filter {
                it.contains(query, ignoreCase = true)
            }
        }
    }

    private fun fetchCountries(){
        viewModelScope.launch {
            stats = stats.copy(isLoading = true)
            try {
                val fetchedData = RetrofitInstance.api.getCountries()
                countries = fetchedData
                filteredCountries = fetchedData.map { it.name.common }
                stats = stats.copy(isLoading = false)
            } catch (e: Exception){
                Log.e("COUNTRY_DEBUG", "Error fetching data", e)
                /*See Logcat*/
                stats = stats.copy(
                    isLoading = false,
                    errorMessage = e.message
                )
            }
        }
    }
}

data class DataStats(
    var isLoading: Boolean,
    var errorMessage: String?
)

package no.uio.ifi.in2000.testgit.ui.Activity

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.example.AlertFeatures
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import no.uio.ifi.in2000.testgit.MainApplication
import no.uio.ifi.in2000.testgit.data.MainRepository
import no.uio.ifi.in2000.testgit.data.algorithms.padleAlgoritme
import no.uio.ifi.in2000.testgit.data.room.City
import no.uio.ifi.in2000.testgit.data.room.DatabaseRepository
import no.uio.ifi.in2000.testgit.data.algorithms.seileAlgoritme
import no.uio.ifi.in2000.testgit.data.algorithms.surfeAlgoritme
import no.uio.ifi.in2000.testgit.data.algorithms.swimmingAlgorithm
import no.uio.ifi.in2000.testgit.model.nowcast.Details
import no.uio.ifi.in2000.testgit.ui.map.OceanForeCastUIState

data class selectedActivityUIState(
    var selectedactivity: String?
)
data class NowCastUIState(
    val nowCastData: Details?
)

data class MetAlertsUIState(
    val metAlertsData: AlertFeatures?
)

data class ReccomendationUIState(
    val level: Int?
)

data class AcitivityUIState(
    val favorite : Boolean
)

// this viewmodel handles api calls depending on city chosen
// this viewmodel will be created by user interaction with locations in no.uio.ifi.in2000.testgit.ui.home.HomeScreen and mapscreen
class ActivityScreenViewModel(
    savedStateHandle: SavedStateHandle,
    private val dbRepository : DatabaseRepository
): ViewModel() {
    private val repository: MainRepository = MainRepository()

    val _selectedActivityUIState = MutableStateFlow(selectedActivityUIState("bading"))
    val selectedActivityUIState: StateFlow<selectedActivityUIState> = _selectedActivityUIState.asStateFlow()

    private val _nowCastUIState = MutableStateFlow(NowCastUIState(null))
    val nowCastUIState: StateFlow<NowCastUIState> = _nowCastUIState.asStateFlow()

    private val _metAlertsUIState = MutableStateFlow(MetAlertsUIState(null))
    val metAlertsUIState: StateFlow<MetAlertsUIState> = _metAlertsUIState.asStateFlow()

    private val _oceanForecastUIState = MutableStateFlow(OceanForeCastUIState(null))
    val oceanForeCastUIState: StateFlow<OceanForeCastUIState> = _oceanForecastUIState.asStateFlow()

    private val _reccomendationUIState = MutableStateFlow(ReccomendationUIState(0))
    val reccomendationUIState: StateFlow<ReccomendationUIState> = _reccomendationUIState.asStateFlow()


    private val _activityUIState = MutableStateFlow(AcitivityUIState(false))

    val acitivityUIState : StateFlow<AcitivityUIState> = _activityUIState.asStateFlow()

    val cityName = checkNotNull(savedStateHandle["placename"])

    val lat: String = checkNotNull(savedStateHandle["lat"])
    val lon: String = checkNotNull(savedStateHandle["lon"])


    init {
        viewModelScope.launch {
            loadAll(lat, lon)
            loadRecommendationBar("bading")
        }
    }

    //Updates the UI state of the selectedactivity

    fun changeActivity(activity: String){
        selectedActivityUIState.value.selectedactivity = (activity)
    }

    //Updates the UI state of the reccomendation bar
    fun changeReccomendationBar(activity: String){
        viewModelScope.launch {
            loadRecommendationBar(activity)
        }
    }

    //Loads the recommendationbar with the score  selectedactivity
    suspend fun loadRecommendationBar(activity: String) {

        val level = if (oceanForeCastUIState.value.oceanDetails != null && nowCastUIState.value.nowCastData != null){
            when(activity){
                "kayaking" -> padleAlgoritme(oceanForeCastUIState.value, nowCastUIState.value)
                "bading" -> swimmingAlgorithm(oceanForeCastUIState.value, nowCastUIState.value)
                "sailing" -> seileAlgoritme(oceanForeCastUIState.value, nowCastUIState.value)
                "surfing" -> surfeAlgoritme(oceanForeCastUIState.value, nowCastUIState.value)
                else -> 0
            }
        } else{
            0
        }
        Log.i("ActivityScreenViewModel, loadRecommendationBar", "Score for $activity is $level ")
        val newRecommendationUIState = _reccomendationUIState.value.copy(level = level)
        _reccomendationUIState.value = newRecommendationUIState
    }

    //Loads nowcast API
    private suspend fun loadNowCast(lat: String, lon: String){
        val nowCastData = repository.fetchNowCast(lat,lon)
        val newNowCastUIState = _nowCastUIState.value.copy(nowCastData = nowCastData)
        _nowCastUIState.value = newNowCastUIState
    }

    //Loads oceanforecast API

    private suspend fun loadOceanForecast(lat: String, lon: String) {
        val oceanForecastData = repository.fetchOceanForecast(lat, lon)
        val newOceanForecastUIState = _oceanForecastUIState.value.copy(oceanDetails = oceanForecastData)
        _oceanForecastUIState.value = newOceanForecastUIState
    }

    //Loads metalerts API
    private suspend fun loadMetAlerts(lat: String, lon: String) {
        val metAlertsData = repository.fetchMetAlerts(lat, lon)
        val newMetAlertsUIState = _metAlertsUIState.value.copy(metAlertsData = metAlertsData)
        _metAlertsUIState.value = newMetAlertsUIState
    }


    //Loads metAlerts, oceanforecast and nowcast API
    private suspend fun loadAll(lat: String, lon: String){
        val nowcastDeffered = viewModelScope.async { loadNowCast(lat, lon) }
        val metAlertsDeffered = viewModelScope.async { loadMetAlerts(lat, lon) }
        val oceanForecastDeffered = viewModelScope.async { loadOceanForecast(lat, lon) }

        nowcastDeffered.await()
        metAlertsDeffered.await()
        oceanForecastDeffered.await()

    }

    //Adds location to favourites
    fun onEvent(event: ActivityEvent){
         when (event) {

            is ActivityEvent.AddFavorite -> {
                viewModelScope.launch(Dispatchers.IO) {
                    if (dbRepository.isInDatabase(event.name)) {
                        dbRepository.setFavoriteByName(event.name)
                        Log.w("ActivityScreenViewModel", "Added old city as favorite")
                    } else {
                        val newCity = City(
                            name = if ( event.name.first().isWhitespace()) {
                                event.name.replaceFirstChar {""}
                            } else {
                                event.name
                            },
                            lat = event.lat.toDoubleOrNull() ?: 0.0,
                            lon = event.lon.toDoubleOrNull() ?: 0.0,
                            customized = 1,
                            favorite = 1
                        )
                        Log.w("ActivityScreenViewModel", "Added new city as favorite")
                        dbRepository.saveCity(city = newCity)
                    }
                }
            }
            is ActivityEvent.RemoveFavorite -> {
                viewModelScope.launch (Dispatchers.IO){
                    if (dbRepository.isCustom(event.name)){
                        dbRepository.deleteCityByName(event.name)
                    } else {
                        dbRepository.removeFavoriteByName(event.name)
                    }
                }
            }

            is ActivityEvent.CheckFavorite -> {
                viewModelScope.launch (Dispatchers.IO) {
                    if (dbRepository.isFavorite(name = event.name)) {
                        _activityUIState.update {
                            it.copy(favorite = true)
                        }
                    }
                }
            }
        }
    }
    @Suppress("UNCHECKED_CAST")
    companion object{
        val Factory : ViewModelProvider.Factory = object  : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(
                modelClass: Class<T>,
                extras : CreationExtras
            ): T {
                val application = checkNotNull(extras[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY])
                return ActivityScreenViewModel(
                    dbRepository = (application as MainApplication).databaseRepository,
                    savedStateHandle = extras.createSavedStateHandle()
                ) as T
            }
        }
    }
}


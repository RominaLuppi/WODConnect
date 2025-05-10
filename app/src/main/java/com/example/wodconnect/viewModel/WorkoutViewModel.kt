package com.example.wodconnect.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.wodconnect.modelo.CrossfitApiService
import com.example.wodconnect.modelo.Workout
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WorkoutViewModel @Inject constructor(
    private val apiService: CrossfitApiService
) : ViewModel() {

    private val _workouts = MutableLiveData<List<Workout>>()
    val workout: LiveData<List<Workout>> = _workouts

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    private val _selectedWorkout = MutableLiveData<Workout?>()
    val selectedWorkout: LiveData<Workout?> = _selectedWorkout

    fun obtenerWorkouts(){
        viewModelScope.launch{
            try {
                val response = apiService.getWorkouts()
                if(response.isSuccessful){
                    _workouts.value = response.body()?.data ?: emptyList()
                }else{
                    _error.value = "Error: ${response.code()}"                }
            }catch (e: Exception){
                _error.value = "Exception: %{e.message}"
            }

        }
    }

    fun obtenerWorkoutsById(workoutId: String){
        viewModelScope.launch {
            try {
                val response = apiService.getWorkoutById(workoutId)
                if(response.isSuccessful){
                    _selectedWorkout.value = response.body()
                } else{
                    _error.value = "Error: ${response.code()}"
                }
            }catch (e: Exception){
                _error.value = "Exception: ${e.message}"
            }
        }
    }
}
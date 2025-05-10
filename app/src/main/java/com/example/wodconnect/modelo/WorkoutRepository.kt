package com.example.wodconnect.modelo

import javax.inject.Inject

class WorkoutRepository @Inject constructor(
    private val api: CrossfitApiService
) {
    suspend fun getWorkoutRandom(): Result<Workout>{
        return try {
            val response = api.getWorkouts()
            if (response.isSuccessful) {
                val workout = response.body()?.data
                if (!workout.isNullOrEmpty()) {
                    val workout = workout.random()
                    Result.success(workout)
                } else {
                    Result.failure(Exception("No hay entrenamientos disponibles"))
                }
            } else{
                Result.failure((Exception("Error ${response.code()}: ${response.message()}")))
            }

        }catch (e: Exception){
            Result.failure(e)
        }
    }

    suspend fun getWorkoutById(id: String): Result<Workout>{
        return try{
            val response = api.getWorkoutById(id)
            if(response.isSuccessful){
                response.body()?.let {
                    Result.success(it)
                } ?: Result.failure(Exception("Entrenamiento no encontrado"))
            }else{
                Result.failure(Exception("Error ${response.code()}: ${response.message()}"))
            }
        }catch (e: Exception){
            Result.failure(e)
        }
    }

}
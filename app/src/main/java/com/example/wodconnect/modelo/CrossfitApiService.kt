package com.example.wodconnect.modelo

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CrossfitApiService {

    //obtener los entrenemientos con opcion de filtrar por mode
    @GET("workouts")
    suspend fun getWorkouts(@Query("mode") mode:String?= null): Response<WorkoutResponse>

    //obtener un entrenamiento por su Id
    @GET("workouts/{workoutId}")
    suspend fun getWorkoutById(@Path("workoutId") workoutId: String): Response<Workout>



}
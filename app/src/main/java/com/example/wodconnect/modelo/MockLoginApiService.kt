package com.example.wodconnect.modelo

import retrofit2.Response
import retrofit2.http.GET

interface MockLoginApiService {
    //Obtener datos de la API con Mocky
    @GET("v3/e5b0fa64-0fe3-44a9-89d7-013888167f49")
    suspend fun getMockUser(): Response<MockUserResponse>
}
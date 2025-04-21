package com.example.wodconnect.data
/*
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.postgrest
import java.util.UUID

//Repositorio para manejar las consultas en Supabase
class SupabaseRepository(private val supabase: SupabaseClient){

    suspend fun getUsers(): List<Users>{
        return supabase.postgrest["users"].select().decodeList()
    }

    suspend fun getClasses(): List<Class>{
        return supabase.postgrest["class"].select().decodeList()
    }

   /* suspend fun getReserves(userId: String): List<Reserve>{
        val userUuid = UUID.fromString(userId)
        return supabase.postgrest["reserves"]
            .select()
            .filter("user_id", "eq",userId)
    }**/

}

 */
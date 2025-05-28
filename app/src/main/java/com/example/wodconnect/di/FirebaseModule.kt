package com.example.wodconnect.di

import com.example.wodconnect.data.repository.FirebaseAuthRepository
import com.example.wodconnect.modelo.domain.repository.AuthRepository
import com.example.wodconnect.modelo.domain.repository.ClasesRepository
import com.example.wodconnect.data.repository.ClasesRepositoryImpl
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FirebaseModule {

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

    @Provides
    @Singleton
    fun provideAuthRepository(auth: FirebaseAuth): AuthRepository =
        FirebaseAuthRepository(auth)

    @Provides
    @Singleton
    fun provideFirestore(): FirebaseFirestore = FirebaseFirestore.getInstance()

    @Provides
    @Singleton
    fun provideClasesFirestoreRepository(firestore: FirebaseFirestore): ClasesRepository =
        ClasesRepositoryImpl(firestore)
}
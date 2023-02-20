package com.example.readerapp.di

import com.example.readerapp.data.AuthRepositoryImpl
import com.example.readerapp.data.BooksRepositoryImpl
import com.example.readerapp.repository.AuthRepository
import com.example.readerapp.repository.BooksRepository
import com.example.readerapp.repository.BooksApi
import com.example.readerapp.utils.Constants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Singleton
    @Provides
    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

    @Singleton
    @Provides
    fun provideFirebaseFirestore(): FirebaseFirestore = FirebaseFirestore.getInstance()

    @Singleton
    @Provides
    fun provideAuthRepository(
        firebaseAuth: FirebaseAuth,
        firebaseFirestore: FirebaseFirestore
    ): AuthRepository = AuthRepositoryImpl(firebaseAuth, firebaseFirestore)

    @Singleton
    @Provides
    fun provideBookRepository(
        booksApi: BooksApi,
        firebaseAuth: FirebaseAuth,
        firebaseFirestore: FirebaseFirestore
    ): BooksRepository = BooksRepositoryImpl(booksApi, firebaseFirestore, firebaseAuth)

    @Singleton
    @Provides
    fun provideBooksApi(): BooksApi {
        return Retrofit.Builder().baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(BooksApi::class.java)
    }
}
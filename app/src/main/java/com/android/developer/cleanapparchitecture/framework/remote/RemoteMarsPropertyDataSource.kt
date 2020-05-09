package com.android.developer.cleanapparchitecture.framework.remote

import android.util.Log
import com.android.developer.cleanapparchitecture.data.CacheState
import com.android.developer.cleanapparchitecture.data.remote.MarsPropertyRemoteDataSource
import com.android.developer.cleanapparchitecture.domain.MarsProperty
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class RemoteMarsPropertyDataSource : MarsPropertyRemoteDataSource {

    @ExperimentalCoroutinesApi
    override suspend fun getRemoteMarsProperty(): Flow<CacheState<List<MarsProperty>>> = flow {
        emit(CacheState.Loading<List<MarsProperty>>())
        //val retrofit = MarsApi.retrofitService
        //val property = retrofit.getProperties()

        val ref = FirebaseFirestore.getInstance().collection("mars_properties")
            .get()



        //Log.d("MainActivity", "IN Flow ${property.size}")
       // emit(CacheState.Success(property.asDomainModel()))
    }.catch {
        Log.d("MainActivity", " In Flow Catch${it.message}")
        //emit(CacheState.Failed<List<MarsProperty>>("Message: ${it.message.toString()}" +
               // " Localized Message: ${it.localizedMessage}"))
    }.flowOn(Dispatchers.IO)

}
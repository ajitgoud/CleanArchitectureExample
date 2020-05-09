package com.android.developer.cleanapparchitecture.data.remote

import com.android.developer.cleanapparchitecture.data.CacheState
import com.android.developer.cleanapparchitecture.domain.MarsProperty
import kotlinx.coroutines.flow.Flow

interface MarsPropertyRemoteDataSource {
    suspend fun getRemoteMarsProperty(): Flow<CacheState<List<MarsProperty>>>
}
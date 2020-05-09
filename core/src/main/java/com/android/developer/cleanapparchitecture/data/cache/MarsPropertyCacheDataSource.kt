package com.android.developer.cleanapparchitecture.data.cache

import com.android.developer.cleanapparchitecture.data.CacheState
import com.android.developer.cleanapparchitecture.domain.MarsProperty
import kotlinx.coroutines.flow.Flow

interface MarsPropertyCacheDataSource {
    fun getCacheMarsProperty(): Flow<CacheState<List<MarsProperty>>>
    suspend fun addCacheMarsProperty(properties: List<MarsProperty>)
    suspend fun addCacheProperty(property: MarsProperty)
    suspend fun updateCacheProperty(property: MarsProperty)
    suspend fun deleteCacheProperty(property: MarsProperty)
    suspend fun getCacheProperty(id: String): MarsProperty
}
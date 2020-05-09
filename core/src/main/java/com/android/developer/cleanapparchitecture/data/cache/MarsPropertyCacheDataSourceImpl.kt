package com.android.developer.cleanapparchitecture.data.cache

import com.android.developer.cleanapparchitecture.domain.MarsProperty

class MarsPropertyCacheDataSourceImpl(private val source: MarsPropertyCacheDataSource) {
    fun getMarsProperty() = source.getCacheMarsProperty()
    suspend fun addMarsProperty(properties: List<MarsProperty>) = source.addCacheMarsProperty(properties)
    suspend fun addProperty(property: MarsProperty) = source.addCacheProperty(property)
    suspend fun updateProperty(property: MarsProperty) = source.updateCacheProperty(property)
    suspend fun deleteProperty(property: MarsProperty) = source.deleteCacheProperty(property)
    suspend fun getProperty(id: String) = source.getCacheProperty(id)
}
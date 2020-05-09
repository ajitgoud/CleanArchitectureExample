package com.android.developer.cleanapparchitecture.interactors

import com.android.developer.cleanapparchitecture.data.cache.MarsPropertyCacheDataSourceImpl

class Get(private val repository: MarsPropertyCacheDataSourceImpl) {
    suspend operator fun invoke(id: String) = repository.getProperty(id)
}
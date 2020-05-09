package com.android.developer.cleanapparchitecture.interactors

import com.android.developer.cleanapparchitecture.data.cache.MarsPropertyCacheDataSourceImpl

class GetCacheMarsProperty(private val repository: MarsPropertyCacheDataSourceImpl) {
    operator fun invoke() = repository.getMarsProperty()
}
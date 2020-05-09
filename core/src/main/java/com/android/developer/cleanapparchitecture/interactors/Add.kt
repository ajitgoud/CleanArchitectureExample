package com.android.developer.cleanapparchitecture.interactors

import com.android.developer.cleanapparchitecture.data.cache.MarsPropertyCacheDataSourceImpl
import com.android.developer.cleanapparchitecture.domain.MarsProperty

class Add(private val repository: MarsPropertyCacheDataSourceImpl) {
    suspend operator fun invoke(property: MarsProperty) = repository.addProperty(property)
}
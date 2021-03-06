package com.android.developer.cleanapparchitecture.interactors

import com.android.developer.cleanapparchitecture.data.cache.MarsPropertyCacheDataSourceImpl
import com.android.developer.cleanapparchitecture.domain.MarsProperty

class Update(private val repository: MarsPropertyCacheDataSourceImpl) {
    suspend operator fun invoke(property: MarsProperty) = repository.updateProperty(property)
}
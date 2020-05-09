package com.android.developer.cleanapparchitecture.interactors

import com.android.developer.cleanapparchitecture.data.cache.MarsPropertyCacheDataSourceImpl
import com.android.developer.cleanapparchitecture.domain.MarsProperty

class AddMarsProperty(private val repository: MarsPropertyCacheDataSourceImpl) {
    suspend operator fun invoke(properties: List<MarsProperty>) = repository.addMarsProperty(properties)
}
package com.android.developer.cleanapparchitecture.interactors

import com.android.developer.cleanapparchitecture.data.remote.MarsPropertyRemoteDataSourceImpl

class GetRemoteMarsProperty(private val repository: MarsPropertyRemoteDataSourceImpl) {
    suspend operator fun invoke() = repository.getMarsProperty()
}
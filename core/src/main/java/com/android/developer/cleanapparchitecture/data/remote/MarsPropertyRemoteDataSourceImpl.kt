package com.android.developer.cleanapparchitecture.data.remote

class MarsPropertyRemoteDataSourceImpl(private val source: MarsPropertyRemoteDataSource) {
    suspend fun getMarsProperty() = source.getRemoteMarsProperty()
}
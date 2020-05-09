package com.android.developer.cleanapparchitecture.framework.db

import android.content.Context
import android.util.Log
import com.android.developer.cleanapparchitecture.data.CacheState
import com.android.developer.cleanapparchitecture.data.cache.MarsPropertyCacheDataSource
import com.android.developer.cleanapparchitecture.domain.MarsProperty
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*

@ExperimentalCoroutinesApi
class RoomMarsPropertyDataSource(private val context: Context) : MarsPropertyCacheDataSource {

    private val dao = MarsDatabase.getDatabase(context).marsPropertyDao()


    override fun getCacheMarsProperty(): Flow<CacheState<List<MarsProperty>>> = flow<CacheState<List<MarsProperty>>> {
        dao.getDogDistinctUntilChanged().collect {
            emit(CacheState.Success(it.asDomainModel()))
        }
    }.catch {
        Log.d("MainActivity", " In Flow cache Catch${it.message}")
        emit(CacheState.failed("Message: ${it.message.toString()}"))
    }.flowOn(Dispatchers.IO)

    override suspend fun addCacheMarsProperty(properties: List<MarsProperty>) {
        Log.d("MainActivity", " Adding data to room")
        dao.insertAllMarsProperties(properties = properties.asEntityModel())
        Log.d("MainActivity", " Added data to room")
    }

    override suspend fun addCacheProperty(property: MarsProperty) {
        dao.insertMars(property.asEntityModel())
        Log.d("MainActivity", " Adding data to room")
    }

    override suspend fun updateCacheProperty(property: MarsProperty) {
        dao.updateMars(property.asEntityModel())
        Log.d("MainActivity", " Updating data to room")
    }

    override suspend fun deleteCacheProperty(property: MarsProperty) {
        dao.deleteMars(property.asEntityModel())
        Log.d("MainActivity", " Deleting data to room")
    }

    override suspend fun getCacheProperty(id: String): MarsProperty = dao.getMars(id).asDomainModel()
}


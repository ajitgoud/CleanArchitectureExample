package com.android.developer.cleanapparchitecture.presentation.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.android.developer.cleanapparchitecture.data.CacheState
import com.android.developer.cleanapparchitecture.data.cache.MarsPropertyCacheDataSourceImpl
import com.android.developer.cleanapparchitecture.data.remote.MarsPropertyRemoteDataSourceImpl
import com.android.developer.cleanapparchitecture.domain.MarsProperty
import com.android.developer.cleanapparchitecture.framework.db.MarsDatabase
import com.android.developer.cleanapparchitecture.framework.db.MarsPropertyDao
import com.android.developer.cleanapparchitecture.framework.db.RoomMarsPropertyDataSource
import com.android.developer.cleanapparchitecture.framework.remote.FirebaseQueryListener
import com.android.developer.cleanapparchitecture.framework.remote.MarsPropertyRemote
import com.android.developer.cleanapparchitecture.framework.remote.RemoteMarsPropertyDataSource
import com.android.developer.cleanapparchitecture.framework.remote.asDomainModel
import com.android.developer.cleanapparchitecture.interactors.*
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart

class MainActivityViewModel(application: Application) : ViewModel() {


    /**
     * This is the job for all coroutines started by this ViewModel.
     *
     * Cancelling this job will cancel all coroutines started by this ViewModel.
     */
    private val viewModelJob = SupervisorJob()

    /**
     * This is the main scope for all coroutines launched by MainViewModel.
     *
     * Since we pass viewModelJob, you can cancel all coroutines launched by uiScope by calling
     * viewModelJob.cancel()
     */
    private val viewModelScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val remoteSource: MarsPropertyRemoteDataSourceImpl by lazy {
        MarsPropertyRemoteDataSourceImpl(RemoteMarsPropertyDataSource())
    }

    private val cacheSource: MarsPropertyCacheDataSourceImpl by lazy {
        MarsPropertyCacheDataSourceImpl(RoomMarsPropertyDataSource(application))
    }

    private val dao: MarsPropertyDao by lazy {
        MarsDatabase.getDatabase(application).marsPropertyDao()
    }

    val marsPropertyLiveData: LiveData<QuerySnapshot>
        get() = FirebaseQueryListener()

    private var _marsProperty = MutableLiveData<List<MarsProperty>>()

    @ExperimentalCoroutinesApi
    val marsProperty: LiveData<CacheState<List<MarsProperty>>> = GetCacheMarsProperty(cacheSource).invoke()
        .onStart { emit(CacheState.Loading<List<MarsProperty>>()) }
        .asLiveData(viewModelScope.coroutineContext)


    /*liveData(viewModelScope.coroutineContext) {
        emitSource(
            GetCacheMarsProperty(cacheSource).invoke().onStart {
                emit(CacheState.Loading<List<MarsProperty>>())
            }.asLiveData(viewModelScope.coroutineContext)
        )
    }*/
    //get() = _marsProperty

    fun getCacheProperty() {
        viewModelScope.launch {
            //_marsProperty = GetList(cacheSource).invoke().asLiveData(this.coroutineContext) as MutableLiveData<List<MarsProperty>>
            /*GetCacheMarsProperty(cacheSource).invoke()
                .collect {
                    when (it) {
                        is CacheState.Loading -> {
                            Log.d("Main", "Loading From Cache State")
                        }
                        is CacheState.Success -> {
                            Log.d("Main", "Success Loading From Cache State")
                            _marsProperty.value = it.data
                            when (it.data.size) {
                                0 -> {
                                    Log.d("Main", "0-> From room vm data size: ${it.data.size}")
                                    //getRemoteMarsProperty()
                                }
                                else -> {
                                    Log.d("Main", "Else-> From room vm data size: ${it.data.size}")
                                    _marsProperty.value = it.data
                                }
                            }
                        }
                        is CacheState.Failed -> {
                            Log.d("Main", "Failed Loading From Cache State")
                            Log.d("Main", "Msg: ${it.message}")
                        }
                    }
                }*/
        }
    }

    fun addDataToRoom(marsProperty: MarsPropertyRemote) {
        viewModelScope.launch {
            Add(cacheSource).invoke(marsProperty.asDomainModel())
        }
    }

    fun updateDataToRoom(marsProperty: MarsPropertyRemote) {
        viewModelScope.launch {
            Update(cacheSource).invoke(marsProperty.asDomainModel())
        }
    }

    fun deleteDataToRoom(marsProperty: MarsPropertyRemote) {
        viewModelScope.launch {
            Delete(cacheSource).invoke(marsProperty.asDomainModel())
        }
    }


    private suspend fun getRemoteMarsProperty() {
        GetRemoteMarsProperty(remoteSource).invoke().collect {
            when (it) {
                is CacheState.Loading -> {
                    Log.d("Main", "Loading From Remote collecting")
                }
                is CacheState.Success -> {
                    Log.d("Main", "Success Loading From Cache State")
                    _marsProperty.value = it.data
                    Log.d("Main", "Remote collection assigned")
                    AddMarsProperty(cacheSource).invoke(it.data)
                    Log.d("Main", "Cache added")
                }
                is CacheState.Failed -> {
                    Log.d("Main", "Failed Loading From Network State")
                    Log.d("Main", "Msg: ${it.message}")
                }

            }


        }
    }


    /**
     * Factory for constructing DevByteViewModel with parameter
     */
    class Factory(private val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MainActivityViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return MainActivityViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }

}


package com.android.developer.cleanapparchitecture.presentation.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.android.developer.cleanapparchitecture.R
import com.android.developer.cleanapparchitecture.data.CacheState
import com.android.developer.cleanapparchitecture.databinding.ActivityMainBinding
import com.android.developer.cleanapparchitecture.framework.remote.MarsPropertyRemote
import com.android.developer.cleanapparchitecture.presentation.adapter.MarsRecyclerAdapter
import com.android.developer.cleanapparchitecture.presentation.viewmodels.MainActivityViewModel
import com.google.firebase.firestore.DocumentChange
import kotlinx.coroutines.ExperimentalCoroutinesApi

class MainActivity : AppCompatActivity() {


    private val mViewModel: MainActivityViewModel by lazy {
        ViewModelProvider(this, MainActivityViewModel.Factory(application))
            .get(MainActivityViewModel::class.java)
    }

    @ExperimentalCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)

        binding.lifecycleOwner = this
        binding.viewModel = mViewModel

        val adapter = MarsRecyclerAdapter()
        binding.marsRecyclerView.adapter = adapter

        mViewModel.marsProperty.observe(this, Observer { state ->
            state?.let {
                when (it) {
                    is CacheState.Loading -> Log.d("MainActivity", "Loading From cache")
                    is CacheState.Success -> adapter.submitList(it.data)
                    is CacheState.Failed -> Log.d("MainActivity", "Failed: ${it.message}")
                }
            }
        })

        mViewModel.marsPropertyLiveData.observe(this, Observer {
            it?.let { snapshot ->
                snapshot.documentChanges.forEach { change ->
                    val document = change.document
                    val mars = document.toObject(MarsPropertyRemote::class.java)
                    when (change.type) {
                        DocumentChange.Type.ADDED -> {
                            mViewModel.addDataToRoom(mars)
                        }
                        DocumentChange.Type.MODIFIED -> {
                            mViewModel.updateDataToRoom(mars)
                        }
                        DocumentChange.Type.REMOVED -> {
                            mViewModel.deleteDataToRoom(mars)
                        }
                    }
                }
            }
        })

    }
}

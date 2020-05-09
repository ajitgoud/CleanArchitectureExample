package com.android.developer.cleanapparchitecture.framework.remote

import android.util.Log
import androidx.lifecycle.LiveData
import com.google.firebase.firestore.*

class FirebaseQueryListener : LiveData<QuerySnapshot>() {

    private val listener = MySnapshotListener()

    private val query = FirebaseFirestore.getInstance().collection("mars_properties")

    private lateinit var reg: ListenerRegistration

    override fun onActive() {
        super.onActive()

        reg = query.addSnapshotListener(listener)

    }

    override fun onInactive() {
        super.onInactive()
        reg.remove()
    }


    inner class MySnapshotListener : EventListener<QuerySnapshot> {
        override fun onEvent(snapshot: QuerySnapshot?, error: FirebaseFirestoreException?) {

            error?.let {
                Log.d("MainActivity", "Failed: ${it.message}")
                return
            }

            snapshot?.let {
                postValue(it)
            }

        }
    }

}
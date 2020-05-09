package com.android.developer.cleanapparchitecture.framework.db

import androidx.room.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged

@Dao
interface MarsPropertyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllMarsProperties(properties: List<MarsPropertyEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMars(property: MarsPropertyEntity)

    @Update
    suspend fun updateMars(property: MarsPropertyEntity)

    @Delete
    suspend fun deleteMars(property: MarsPropertyEntity)

    @Query("Select * From mars_property where id = :id")
    suspend fun getMars(id: String): MarsPropertyEntity

    @Query("Select * From mars_property")
    fun getMarsProperties(): Flow<List<MarsPropertyEntity>>

    @ExperimentalCoroutinesApi
    fun getDogDistinctUntilChanged() = getMarsProperties().distinctUntilChanged()

}
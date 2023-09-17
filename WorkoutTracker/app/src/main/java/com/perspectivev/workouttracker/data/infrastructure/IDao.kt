package com.perspectivev.workouttracker.data.infrastructure

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface IDao<TEntity> {
    fun get(): List<TEntity>
    fun get(id: Int): Flow<TEntity?>
    fun getAll(): Flow<List<TEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(vararg items: TEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(item: TEntity)

    @Update
    suspend fun update(item: TEntity)

    @Update
    suspend fun update(vararg items: TEntity)

    @Delete
    suspend fun delete(item: TEntity)

    @Delete
    suspend fun delete(vararg items: TEntity)
}
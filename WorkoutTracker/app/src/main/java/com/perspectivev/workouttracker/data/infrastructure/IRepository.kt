package com.perspectivev.workouttracker.data.infrastructure

import kotlinx.coroutines.flow.Flow

interface IRepository<TEntity> {
    /**
     * Retrieve all the items from the the given data source.
     */
    fun get(): List<TEntity>

    /**
     * Retrieve all the items from the the given data source.
     */
    fun getAll(): Flow<List<TEntity>>

    /**
     * Retrieve an item from the given data source that matches with the [id].
     */
    fun get(id: Int): Flow<TEntity?>

    /**
     * Insert item in the data source
     */
    suspend fun insert(item: TEntity)
    suspend fun insert(vararg items: TEntity)

    /**
     * Delete item from the data source
     */
    suspend fun delete(item: TEntity)
    suspend fun delete(vararg items: TEntity)

    /**
     * Update item in the data source
     */
    suspend fun update(item: TEntity)
    suspend fun update(vararg items: TEntity)
}
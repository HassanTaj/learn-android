package com.perspectivev.todo.db.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

open class Repository<TEntity, TDao>(
    private var dao: TDao,
    public var allItems: Flow<List<TEntity>>,
    public var searchResults: MutableStateFlow<List<TEntity>>
) : IRepository<TEntity>
        where TDao : IDao<TEntity> {
    override fun getAll(): Flow<List<TEntity>> = this.dao.getAll()
    override fun get(): List<TEntity> = this.dao.get()
    override fun get(id: Int): Flow<TEntity?> = this.dao.get(id)
    override suspend fun update(item: TEntity) = this.dao.update(item)
    override suspend fun update(vararg items: TEntity) = this.dao.update(items = items)
    override suspend fun delete(item: TEntity) = this.dao.delete(item)
    override suspend fun delete(vararg items: TEntity) = this.dao.delete(items = items)
    override suspend fun insert(item: TEntity) = this.dao.insert(item)
    override suspend fun insert(vararg items: TEntity) = this.dao.insert(items = items)

}
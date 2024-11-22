package it.czerwinski.examples.compose.model

interface ItemsRepository {
    suspend fun getAll(): List<Item>
    suspend fun getById(id: Long): Item
}

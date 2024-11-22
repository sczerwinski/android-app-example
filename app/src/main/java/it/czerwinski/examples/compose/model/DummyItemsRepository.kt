package it.czerwinski.examples.compose.model

import dagger.Reusable
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

@Reusable
class DummyItemsRepository @Inject constructor() : ItemsRepository {

    override suspend fun getAll(): List<Item> =
        withContext(Dispatchers.IO) {
            delay(timeMillis = DUMMY_DELAY)
            createItems()
        }

    private fun createItems(): List<Item> =
        sequence {
            for (index in 0..<ITEMS_COUNT) {
                yield(createItem(id = index.toLong()))
            }
        }.toList()

    private fun createItem(id: Long): Item =
        Item(
            id = id,
            name = "Item ${id + 1}",
            description = "Description of item ${id + 1}"
        )

    override suspend fun getById(id: Long): Item =
        withContext(Dispatchers.IO) {
            delay(timeMillis = DUMMY_DELAY)
            createItem(id)
        }

    companion object {
        private const val DUMMY_DELAY = 2000L
        private const val ITEMS_COUNT = 30
    }
}

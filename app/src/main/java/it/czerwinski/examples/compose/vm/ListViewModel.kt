package it.czerwinski.examples.compose.vm

import dagger.hilt.android.lifecycle.HiltViewModel
import it.czerwinski.examples.compose.model.Item
import it.czerwinski.examples.compose.model.ItemsRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

@HiltViewModel
class ListViewModel @Inject constructor(
    private val itemsRepository: ItemsRepository
) : ProgressViewModel() {

    private val _items = MutableStateFlow(value = emptyList<Item>())
    val items: Flow<List<Item>> get() = _items

    init {
        loadItems()
    }

    fun loadItems() {
        withProgress {
            val items = itemsRepository.getAll()
            _items.emit(items)
        }
    }
}

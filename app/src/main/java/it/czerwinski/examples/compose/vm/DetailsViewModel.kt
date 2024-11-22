package it.czerwinski.examples.compose.vm

import dagger.hilt.android.lifecycle.HiltViewModel
import it.czerwinski.examples.compose.model.Item
import it.czerwinski.examples.compose.model.ItemsRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val itemsRepository: ItemsRepository
) : ProgressViewModel() {

    private val _item = MutableStateFlow<Item?>(value = null)
    val item: Flow<Item?> get() = _item

    fun loadItem(id: Long) {
        withProgress {
            val item = itemsRepository.getById(id)
            _item.emit(item)
        }
    }
}

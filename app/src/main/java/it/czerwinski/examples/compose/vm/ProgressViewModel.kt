package it.czerwinski.examples.compose.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

abstract class ProgressViewModel : ViewModel() {

    private val _isProgress = MutableStateFlow(value = false)
    val isProgress: Flow<Boolean> get() = _isProgress

    fun withProgress(block: suspend CoroutineScope.() -> Unit) {
        viewModelScope.launch {
            _isProgress.emit(value = true)
            block()
            _isProgress.emit(value = false)
        }
    }
}

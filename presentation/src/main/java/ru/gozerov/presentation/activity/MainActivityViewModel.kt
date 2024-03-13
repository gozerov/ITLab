package ru.gozerov.presentation.activity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.gozerov.domain.usecases.login.CheckFirstRun
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val checkFirstRun: CheckFirstRun
): ViewModel() {

    private val _viewState = MutableStateFlow<Boolean?>(null)
    val viewState: StateFlow<Boolean?>
        get() = _viewState.asStateFlow()

    init {
        viewModelScope.launch {
            _viewState.emit(checkFirstRun.execute())
        }
    }

}
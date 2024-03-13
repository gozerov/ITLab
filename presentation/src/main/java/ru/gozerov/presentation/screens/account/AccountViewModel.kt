package ru.gozerov.presentation.screens.account

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.gozerov.domain.models.login.GetCurrentUserResult
import ru.gozerov.domain.usecases.login.GetCurrentUser
import ru.gozerov.presentation.screens.account.models.AccountIntent
import ru.gozerov.presentation.screens.account.models.AccountViewState
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor(
    private val getCurrentUser: GetCurrentUser
) : ViewModel() {

    private val _viewState = MutableStateFlow<AccountViewState>(AccountViewState.None)
    val viewState: StateFlow<AccountViewState>
        get() = _viewState.asStateFlow()

    init {
        viewModelScope.launch {
            getCurrentUser.result.collect { result ->
                when (result) {
                    is GetCurrentUserResult.Incognito -> _viewState.emit(AccountViewState.Incognito())
                    is GetCurrentUserResult.Success -> _viewState.emit(
                        AccountViewState.LoggedUser(
                            result.user
                        )
                    )

                    is GetCurrentUserResult.Error -> _viewState.emit(AccountViewState.Error())
                }
            }
        }
    }

    fun handleIntent(intent: AccountIntent) {
        viewModelScope.launch {
            when (intent) {
                is AccountIntent.LoadUser -> getCurrentUser.execute(Unit)
            }
        }
    }

}
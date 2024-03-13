package ru.gozerov.presentation.screens.choose_account

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.gozerov.domain.models.login.DeleteUserResult
import ru.gozerov.domain.models.login.GetUsersResult
import ru.gozerov.domain.models.login.GuestModeResult
import ru.gozerov.domain.models.login.LoginWithoutPasswordResult
import ru.gozerov.domain.usecases.login.DeleteUser
import ru.gozerov.domain.usecases.login.GetUsers
import ru.gozerov.domain.usecases.login.GuestMode
import ru.gozerov.domain.usecases.login.LoginWithoutPassword
import ru.gozerov.presentation.screens.choose_account.models.ChooseAccountIntent
import ru.gozerov.presentation.screens.choose_account.models.ChooseAccountViewState
import javax.inject.Inject

@HiltViewModel
class ChooseAccountViewModel @Inject constructor(
    private val getUsers: GetUsers,
    private val loginWithoutPassword: LoginWithoutPassword,
    private val guestMode: GuestMode,
    private val deleteUser: DeleteUser
) : ViewModel() {

    private val _viewState = MutableStateFlow<ChooseAccountViewState>(ChooseAccountViewState.None())
    val viewState: StateFlow<ChooseAccountViewState>
        get() = _viewState.asStateFlow()

    init {
        viewModelScope.launch {
            observeGetUsersResult()
            observeLoginWithoutPasswordResult()
            observeDeleteUserResult()
            observeGuestModeResult()
        }
    }

    private fun CoroutineScope.observeGetUsersResult() {
        launch {
            getUsers.result.collect { result ->
                when (result) {
                    is GetUsersResult.EmptyList -> {
                        _viewState.emit(ChooseAccountViewState.EmptyList())
                    }

                    is GetUsersResult.Success -> {
                        _viewState.emit(ChooseAccountViewState.UserList(result.users))
                    }

                    is GetUsersResult.Error -> {
                        _viewState.emit(ChooseAccountViewState.Error())
                    }
                }
            }
        }
    }

    private fun CoroutineScope.observeLoginWithoutPasswordResult() {
        launch {
            loginWithoutPassword.result.collect { result ->
                when (result) {
                    is LoginWithoutPasswordResult.Success -> {
                        _viewState.emit(ChooseAccountViewState.SuccessLogin())
                    }

                    is LoginWithoutPasswordResult.Error -> {
                        _viewState.emit(ChooseAccountViewState.Error())
                    }
                }
            }
        }
    }

    private fun CoroutineScope.observeDeleteUserResult() {
        launch {
            deleteUser.result.collect { result ->
                when (result) {
                    is DeleteUserResult.Success -> {}

                    is DeleteUserResult.Error -> {
                        _viewState.emit(ChooseAccountViewState.Error())
                    }
                }
            }
        }
    }

    private fun CoroutineScope.observeGuestModeResult() {
        launch {
            guestMode.result.collect { result ->
                when (result) {
                    is GuestModeResult.Success -> {
                        _viewState.emit(ChooseAccountViewState.SuccessLogin())
                    }

                    is GuestModeResult.Error -> {}
                }
            }
        }
    }

    fun handleIntent(intent: ChooseAccountIntent) {
        viewModelScope.launch {
            when (intent) {
                is ChooseAccountIntent.GetUsers -> {
                    getUsers.execute(Unit)
                }

                is ChooseAccountIntent.Login -> {
                    loginWithoutPassword.execute(intent.user.username)
                }

                is ChooseAccountIntent.LoginAsGuest -> {
                    guestMode.execute(Unit)
                }

                is ChooseAccountIntent.DeleteAccount -> {
                    deleteUser.execute(intent.user.username)
                }

                is ChooseAccountIntent.ExitScreen -> {
                    _viewState.emit(ChooseAccountViewState.None())
                }
            }
        }
    }

}
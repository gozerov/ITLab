package ru.gozerov.presentation.screens.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.gozerov.domain.models.login.LoginData
import ru.gozerov.domain.models.login.LoginResult
import ru.gozerov.domain.models.login.SignUpResult
import ru.gozerov.domain.usecases.login.PerformLogin
import ru.gozerov.domain.usecases.login.PerformRegister
import ru.gozerov.presentation.screens.login.models.LoginIntent
import ru.gozerov.presentation.screens.login.models.LoginViewState
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val performLogin: PerformLogin,
    private val performRegister: PerformRegister
) : ViewModel() {

    private val _viewState = MutableStateFlow<LoginViewState>(LoginViewState.None)
    val viewState: StateFlow<LoginViewState>
        get() = _viewState.asStateFlow()

    init {
        viewModelScope.launch {
            observeLoginResult()
            observeRegisterResult()
        }
    }

    private fun CoroutineScope.observeLoginResult() {
        launch {
            performLogin.result.collect { result ->
                when (result) {
                    is LoginResult.Loading -> {
                        _viewState.emit(LoginViewState.Loading())
                    }

                    is LoginResult.SuccessLogin -> {
                        _viewState.emit(LoginViewState.Success())
                    }

                    is LoginResult.BadCredentials -> {
                        _viewState.emit(LoginViewState.BadCredentialsError())
                    }

                    is LoginResult.UnknownException -> {
                        _viewState.emit(LoginViewState.UnknownError())
                    }
                }
            }
        }
    }

    private fun CoroutineScope.observeRegisterResult() {
        launch {
            performRegister.result.collect { result ->
                when (result) {
                    is SignUpResult.Loading -> {
                        _viewState.emit(LoginViewState.Loading())
                    }

                    is SignUpResult.SuccessLogin -> {
                        _viewState.emit(LoginViewState.Success())
                    }

                    is SignUpResult.AccountExist -> {
                        _viewState.emit(LoginViewState.AccountExistsError())
                    }

                    is SignUpResult.UnknownException -> {
                        _viewState.emit(LoginViewState.UnknownError())
                    }
                }
            }
        }
    }

    fun handleIntent(intent: LoginIntent) {
        viewModelScope.launch {
            when (intent) {
                is LoginIntent.PerformLogin -> {
                    performLogin.execute(LoginData(intent.username, intent.password))
                }

                is LoginIntent.PerformRegister -> {
                    performRegister.execute(LoginData(intent.username, intent.password))
                }
                is LoginIntent.Exit -> {
                    _viewState.emit(LoginViewState.None)
                }
            }
        }
    }

}
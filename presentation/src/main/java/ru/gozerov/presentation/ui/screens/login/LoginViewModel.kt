package ru.gozerov.presentation.ui.screens.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
import ru.gozerov.presentation.ui.screens.login.models.LoginIntent
import ru.gozerov.presentation.ui.screens.login.models.LoginViewState
import javax.inject.Inject

class LoginViewModel @Inject constructor(
    private val performLogin: PerformLogin,
    private val performRegister: PerformRegister
) : ViewModel() {

    private val _viewState = MutableStateFlow<LoginViewState>(LoginViewState.Empty)
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
                    is LoginResult.SuccessLogin -> {
                        _viewState.emit(LoginViewState.Success())
                    }

                    is LoginResult.BadCredentials -> {

                    }

                    is LoginResult.UnknownException -> {

                    }
                }
            }
        }
    }

    private fun CoroutineScope.observeRegisterResult() {
        launch {
            performRegister.result.collect { result ->
                when (result) {
                    is SignUpResult.SuccessLogin -> {
                        _viewState.emit(LoginViewState.Success())
                    }

                    is SignUpResult.AccountExist -> {

                    }

                    is SignUpResult.UnknownException -> {

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
            }
        }
    }

}
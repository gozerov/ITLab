package ru.gozerov.itlab.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import ru.gozerov.presentation.ui.screens.login.LoginViewModel

@Module
interface ViewModelBindModule {

    @Binds
    @[IntoMap ViewModelKey(LoginViewModel::class)]
    fun provideLoginViewModel(loginViewModel: LoginViewModel) : ViewModel

}
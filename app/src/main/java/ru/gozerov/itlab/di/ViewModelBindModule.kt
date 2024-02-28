package ru.gozerov.itlab.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.multibindings.IntoMap
import ru.gozerov.presentation.screens.login.LoginViewModel
/*

@InstallIn(ViewModelComponent::class)
@Module
interface ViewModelBindModule {

    @Binds
    @[IntoMap ViewModelKey(LoginViewModel::class)]
    fun provideLoginViewModel(loginViewModel: LoginViewModel) : ViewModel

}*/

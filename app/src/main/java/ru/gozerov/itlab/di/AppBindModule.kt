package ru.gozerov.itlab.di

import dagger.Binds
import dagger.Module
import ru.gozerov.data.login.LoginRepositoryImpl
import ru.gozerov.data.login.remote.LoginRemote
import ru.gozerov.data.login.remote.LoginRemoteImpl
import ru.gozerov.domain.repositories.LoginRepository
import javax.inject.Singleton

@Module
interface AppBindModule {

    @Binds
    @Singleton
    fun bindLoginRemoteImplToLoginRemote(loginRemoteImpl: LoginRemoteImpl): LoginRemote

    @Binds
    @Singleton
    fun bindLoginRepoImplToLoginRepo(loginRepositoryImpl: LoginRepositoryImpl): LoginRepository

}
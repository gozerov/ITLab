package ru.gozerov.itlab.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.gozerov.data.login.LoginRepositoryImpl
import ru.gozerov.data.login.cache.UserStorage
import ru.gozerov.data.login.cache.UserStorageImpl
import ru.gozerov.data.login.remote.LoginRemote
import ru.gozerov.data.login.remote.LoginRemoteImpl
import ru.gozerov.data.tags.TagRepositoryImpl
import ru.gozerov.data.tags.remote.TagRemote
import ru.gozerov.data.tags.remote.TagRemoteImpl
import ru.gozerov.domain.repositories.LoginRepository
import ru.gozerov.domain.repositories.TagRepository
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
interface AppBindModule {

    @Binds
    @Singleton
    fun bindLoginRemoteImplToLoginRemote(loginRemoteImpl: LoginRemoteImpl): LoginRemote

    @Binds
    @Singleton
    fun bindLoginRepoImplToLoginRepo(loginRepositoryImpl: LoginRepositoryImpl): LoginRepository

    @Binds
    @Singleton
    fun bindTagRemoteImplToTagRemote(tagRemoteImpl: TagRemoteImpl): TagRemote

    @Binds
    @Singleton
    fun bindTagRepoImplToTagRepo(tagRepositoryImpl: TagRepositoryImpl): TagRepository

    @Binds
    @Singleton
    fun bindUserStorageImplToUserStorage(userStorageImpl: UserStorageImpl): UserStorage

}
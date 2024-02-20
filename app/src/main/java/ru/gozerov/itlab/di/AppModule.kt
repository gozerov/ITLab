package ru.gozerov.itlab.di

import dagger.Module

@Module(
    includes = [
        AppBindModule::class,
        RetrofitModule::class
    ]
)
class AppModule
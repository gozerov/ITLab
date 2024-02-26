package ru.gozerov.domain.usecases

import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow

abstract class UseCase<T, R> {

    protected abstract suspend fun loadData(arg: T): Flow<R>

    protected val _result = MutableSharedFlow<R>(1, 0, BufferOverflow.DROP_OLDEST)
    val result: SharedFlow<R> = _result.asSharedFlow()

    suspend fun execute(
        arg: T
    ) {
        try {
            val res = loadData(arg)
            res.collect {
                _result.emit(it)
            }
        } catch (e: Exception) {
            onError(e)
        }
    }

    protected open suspend fun onError(e: Exception) {}

}
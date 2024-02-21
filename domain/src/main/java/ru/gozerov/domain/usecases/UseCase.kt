package ru.gozerov.domain.usecases

import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import retrofit2.HttpException

abstract class UseCase<T, R> {

    protected abstract suspend fun loadData(arg: T): R

    protected val _result = MutableSharedFlow<R>(1, 0, BufferOverflow.DROP_OLDEST)
    val result: SharedFlow<R> = _result.asSharedFlow()

    suspend fun execute(
        arg: T
    ) {
        try {
            val result = loadData(arg)
            _result.emit(result)
        } catch (e: HttpException) {
            onHttpException(e)
        } catch (e: Exception) {
            onException(e)
        }
    }

    protected open suspend fun onException(e: Exception) {}

    protected open suspend fun onHttpException(e: HttpException) {}

}
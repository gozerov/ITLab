package ru.gozerov.domain.usecases

import org.json.JSONException
import org.json.JSONObject
import retrofit2.HttpException
import ru.gozerov.domain.models.login.LoginData
import ru.gozerov.domain.models.login.SignUpResult
import ru.gozerov.domain.repositories.LoginRepository
import javax.inject.Inject

class PerformRegister @Inject constructor(
    private val loginRepository: LoginRepository
) : UseCase<LoginData, SignUpResult>() {

    override suspend fun loadData(arg: LoginData): SignUpResult {
        return loginRepository.register(arg.username, arg.password)
    }

    override suspend fun onHttpError(e: HttpException) {
        e.response()?.errorBody()?.string()?.let {
            try {
                JSONObject(it).getString("detail")
                _result.emit(SignUpResult.AccountExist)
            } catch (e: JSONException) {
                _result.emit(SignUpResult.UnknownException)
            }
        } ?: _result.emit(SignUpResult.UnknownException)
    }

    override suspend fun onError(e: Exception) {
        _result.emit(SignUpResult.UnknownException)
    }

}
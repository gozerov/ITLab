package ru.gozerov.domain.usecases

import org.json.JSONException
import org.json.JSONObject
import retrofit2.HttpException
import ru.gozerov.domain.models.login.LoginData
import ru.gozerov.domain.models.login.LoginResult
import ru.gozerov.domain.repositories.LoginRepository
import javax.inject.Inject

class PerformLogin @Inject constructor(
    private val loginRepository: LoginRepository
) : UseCase<LoginData, LoginResult>() {

    override suspend fun loadData(arg: LoginData): LoginResult {
        return loginRepository.login(arg.username, arg.password)
    }

    override suspend fun onHttpError(e: HttpException) {
        e.response()?.errorBody()?.string()?.let {
            try {
                JSONObject(it).getString("detail")
                _result.emit(LoginResult.BadCredentials)
            } catch (e: JSONException) {
                _result.emit(LoginResult.UnknownException)
            }
        } ?: _result.emit(LoginResult.UnknownException)
    }

    override suspend fun onError(e: Exception) {
        _result.emit(LoginResult.UnknownException)
    }

}
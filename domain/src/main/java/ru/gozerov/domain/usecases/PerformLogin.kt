package ru.gozerov.domain.usecases

import org.json.JSONException
import org.json.JSONObject
import retrofit2.HttpException
import ru.gozerov.domain.models.LoginRequest
import ru.gozerov.domain.models.LoginResponse
import ru.gozerov.domain.repositories.LoginRepository
import javax.inject.Inject

class PerformLogin @Inject constructor(
    private val loginRepository: LoginRepository
) : UseCase<LoginRequest, LoginResponse>() {

    override suspend fun loadData(arg: LoginRequest): LoginResponse {
        return loginRepository.login(arg.username, arg.password)
    }

    override suspend fun onHttpException(e: HttpException) {
        e.response()?.errorBody()?.string()?.let {
            try {
                JSONObject(it).getString("detail")
                _result.emit(LoginResponse.BadCredentials)
            } catch (e: JSONException) {
                _result.emit(LoginResponse.UnknownException)
            }
        } ?: _result.emit(LoginResponse.UnknownException)
    }

    override suspend fun onException(e: Exception) {
        _result.emit(LoginResponse.UnknownException)
    }

}
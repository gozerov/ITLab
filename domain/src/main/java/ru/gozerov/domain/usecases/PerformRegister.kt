package ru.gozerov.domain.usecases

import org.json.JSONException
import org.json.JSONObject
import retrofit2.HttpException
import ru.gozerov.domain.models.LoginRequest
import ru.gozerov.domain.models.LoginResponse
import ru.gozerov.domain.models.SignUpResponse
import ru.gozerov.domain.repositories.LoginRepository
import javax.inject.Inject

class PerformRegister @Inject constructor(
    private val loginRepository: LoginRepository
) : UseCase<LoginRequest, SignUpResponse>() {

    override suspend fun loadData(arg: LoginRequest): SignUpResponse {
        return loginRepository.register(arg.username, arg.password)
    }

    override suspend fun onHttpException(e: HttpException) {
        e.response()?.errorBody()?.string()?.let {
            try {
                JSONObject(it).getString("detail")
                _result.emit(SignUpResponse.AccountExist)
            } catch (e: JSONException) {
                _result.emit(SignUpResponse.UnknownException)
            }
        } ?: _result.emit(SignUpResponse.UnknownException)
    }

    override suspend fun onException(e: Exception) {
        _result.emit(SignUpResponse.UnknownException)
    }

}
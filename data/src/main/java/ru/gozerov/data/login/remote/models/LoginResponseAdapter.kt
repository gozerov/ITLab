package ru.gozerov.data.login.remote.models

import com.squareup.moshi.FromJson
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonDataException
import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonWriter
import com.squareup.moshi.ToJson

class LoginResponseAdapter : JsonAdapter<LoginResponseBody>() {

    @FromJson
    override fun fromJson(jsonReader: JsonReader): LoginResponseBody {
        jsonReader.beginObject()
        return when (jsonReader.nextName()) {
            "access_token" -> {
                val token = jsonReader.nextString()
                jsonReader.nextName()
                jsonReader.nextString()
                jsonReader.endObject()
                LoginResponseBody.SuccessLogin(token)
            }
            "detail" -> {
                val message = jsonReader.nextString()
                jsonReader.endObject()
                LoginResponseBody.BadCredentials(message)
            }
            else -> {
                jsonReader.close()
                throw JsonDataException("Unknown type")
            }
        }
    }

    @ToJson
    override fun toJson(jsonWriter: JsonWriter, p1: LoginResponseBody?) {}


}
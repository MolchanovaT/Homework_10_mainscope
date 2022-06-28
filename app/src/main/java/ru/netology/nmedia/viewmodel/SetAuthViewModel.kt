package ru.netology.nmedia.viewmodel

import android.widget.Toast
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import ru.netology.nmedia.api.Api
import ru.netology.nmedia.auth.AppAuth
import ru.netology.nmedia.error.ApiError
import java.io.IOException
import android.app.Application
import androidx.lifecycle.AndroidViewModel

@ExperimentalCoroutinesApi
class SetAuthViewModel(application: Application) : AndroidViewModel(application) {
    val app = application

    fun updateUser(login: String, pass: String) {
        val text = "Неверный логин/пароль"
        val textNetwork = "Проверьте подключение"
        val textUnknown = "Неизвестная ошибка"
        val duration = Toast.LENGTH_SHORT
        viewModelScope.launch {
            try {
                val response = Api.service.updateUser(login, pass)
                if (!response.isSuccessful) {
                    val toast = Toast.makeText(app, text, duration)
                    toast.show()
                    //throw ApiError(response.code(), response.message())
                } else {
                    val body =
                        response.body() ?: throw ApiError(response.code(), response.message())
                    AppAuth.getInstance().setAuth(body.id, body.token)
                }
            } catch (e: IOException) {
                val toast = Toast.makeText(app, textNetwork, duration)
                toast.show()
                //throw NetworkError
            } catch (e: Exception) {
                val toast = Toast.makeText(app, textUnknown, duration)
                toast.show()
                //throw UnknownError
            }
        }
    }
}
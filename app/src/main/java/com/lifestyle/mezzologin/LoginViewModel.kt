package com.lifestyle.mezzologin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

sealed class LoginResult {
    data class Error(val message: String) : LoginResult()
    data object Success : LoginResult()
}

class LoginViewModel : ViewModel() {

    private val _loginState = MutableSharedFlow<LoginResult>(extraBufferCapacity = 1)
    val loginState = _loginState.asSharedFlow()

    fun login(userName: String, password: String) {
        viewModelScope.launch {
            when {
                userName.isBlank() -> {
                    _loginState.emit(LoginResult.Error("Username Can't be Empty"))
                }

                password.isBlank() -> {
                    _loginState.emit(LoginResult.Error("Password Can't be Empty"))
                }

                password.length < 8 -> {
                    _loginState.emit(LoginResult.Error("Password must be at least 8 characters"))
                }

                else -> {
                    _loginState.emit(LoginResult.Success)
                }
            }
        }
    }
}

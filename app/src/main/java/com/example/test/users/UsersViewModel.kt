package com.example.test.users

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.test.dataModels.User
import kotlinx.coroutines.launch

class UsersViewModel : ViewModel() {
    private val repository = UserRepositoryInstance()

    private val _users = MutableLiveData<List<User>>()
    val users: LiveData<List<User>> get() = _users

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading
    private val maxPages = 4

    fun fetchUsers(page: Int) {
        if (_isLoading.value == true || page >= maxPages) return

        _isLoading.postValue(true)
        viewModelScope.launch {
            try {
                val response = repository.getUsers(page)
                val currentList = _users.value?.toMutableList() ?: mutableListOf()

                val uniqueUsers = response.results.filterNot { newUser ->
                    currentList.any { it.login.uuid == newUser.login.uuid }
                }

                currentList.addAll(uniqueUsers)
                _users.postValue(uniqueUsers)
            } catch (e: Exception) {
                Log.e("UsersViewModel", "API Call Failed", e)
            } finally {
                _isLoading.postValue(false)
            }
        }
    }
}
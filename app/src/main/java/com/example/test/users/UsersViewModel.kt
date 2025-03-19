package com.example.test.users

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

    fun fetchUsers(page: Int) {
        viewModelScope.launch {
            try {
                val response = repository.getUsers(page)
                _users.postValue(response.results)
            } catch (e: Exception) {
                android.util.Log.e("UsersViewModel", "API Call Failed", e)
            }
        }
    }
}
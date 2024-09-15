package com.example.categoryai.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.categoryai.api.CategoryRc
import com.example.categoryai.api.NetworkRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RecommendViewModel(private val repository: NetworkRepository) : ViewModel() {

    // Change the type to List<CategoryRc> to match the expected data structure
    private val _recommendedCategories = MutableStateFlow<List<CategoryRc>?>(null)
    val recommendedCategories: StateFlow<List<CategoryRc>?> = _recommendedCategories

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    fun fetchRecommendedCategories() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                // Fetch a list of CategoryRc objects from the repository
                val categories = repository.getRecommendedCategories()
                _recommendedCategories.value = categories
                _errorMessage.value = null
            } catch (e: Exception) {
                _errorMessage.value = "Failed to load categories"
                e.printStackTrace()
            } finally {
                _isLoading.value = false
            }
        }
    }
}


// ViewModel Factory to create instances of RecommendViewModel
class RecommendViewModelFactory(private val repository: NetworkRepository) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RecommendViewModel::class.java)) {
            return RecommendViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}

package com.example.categoryai.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.categoryai.api.Category
import com.example.categoryai.api.NetworkRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CategoryViewModel(private val networkRepository: NetworkRepository) : ViewModel() {

    private val _categories = MutableStateFlow<List<Category>>(emptyList())
    val categories: StateFlow<List<Category>> = _categories.asStateFlow()

    fun fetchRelatedCategories(title: String, description: String) {
        viewModelScope.launch {
            val result = networkRepository.getRelatedCategories(title, description)

            // Directly set the list of Category objects
            _categories.value = result ?: emptyList()
        }
    }
}

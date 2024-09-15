package com.example.categoryai

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.categoryai.api.NetworkRepository
import com.example.categoryai.ui.CategoryScreen
import com.example.categoryai.ui.RecommendScreen
import com.example.categoryai.viewmodel.CategoryViewModel
import com.example.categoryai.ui.theme.CategoryAiTheme

class MainActivity : ComponentActivity() {
    private val networkRepository = NetworkRepository()
    private val categoryViewModel = CategoryViewModel(networkRepository)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CategoryAiTheme {
                CategoryScreen(categoryViewModel)
            }
        }
    }
}



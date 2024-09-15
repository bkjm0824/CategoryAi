package com.example.categoryai.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberImagePainter
import com.example.categoryai.api.NetworkRepository
import com.example.categoryai.api.CategoryRc
import com.example.categoryai.viewmodel.RecommendViewModel
import com.example.categoryai.viewmodel.RecommendViewModelFactory

@Composable
fun RecommendScreen() {
    // Initialize the repository and ViewModel
    val repository = NetworkRepository()
    val viewModel: RecommendViewModel = viewModel(factory = RecommendViewModelFactory(repository))

    // Observe ViewModel's state
    val recommendedCategories by viewModel.recommendedCategories.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchRecommendedCategories()
    }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        when {
            isLoading -> {
                CircularProgressIndicator()
            }
            errorMessage != null -> {
                Text(text = errorMessage ?: "An error occurred", color = MaterialTheme.colorScheme.error)
            }
            recommendedCategories != null -> {
                // LazyColumn to display the list of CategoryRc
                LazyColumn(modifier = Modifier.padding(16.dp)) {
                    // Use 'items' to iterate through the list of CategoryRc
                    items(recommendedCategories!!) { categoryRc ->
                        CategoryItem(categoryRc)
                    }
                }
            }
            else -> {
                Text(text = "No categories available")
            }
        }
    }
}

@Composable
fun CategoryItem(categoryRc: CategoryRc) {
    Column(modifier = Modifier.fillMaxWidth().padding(8.dp)) {
        // Display the title from CategoryRc
        Text(text = categoryRc.title, style = MaterialTheme.typography.titleMedium)

        Spacer(modifier = Modifier.height(8.dp))

        // Display the list of categories
        Text(text = "Categories: ${categoryRc.categories}")

        Spacer(modifier = Modifier.height(8.dp))

        // Display the popup images if available
        categoryRc.popupImages.forEach { imageUrl ->
            Image(
                painter = rememberImagePainter(imageUrl),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

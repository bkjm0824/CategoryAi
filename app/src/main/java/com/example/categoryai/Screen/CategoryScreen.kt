package com.example.categoryai.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.categoryai.api.NetworkRepository
import com.example.categoryai.viewmodel.CategoryViewModel

@Composable
fun CategoryScreen(viewModel: CategoryViewModel) {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    val categories by viewModel.categories.collectAsState()

    Column(modifier = Modifier.padding(16.dp)) {
        TextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Title") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("Description") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            viewModel.fetchRelatedCategories(title, description)
        }) {
            Text("Find Related Categories")
        }
        Spacer(modifier = Modifier.height(16.dp))
        LazyColumn {
            items(categories) { category ->
                // Assuming Category has a 'category' field to display
                Text(text = "Category: ${category.category}")
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

// Preview function
@Preview(showBackground = true)
@Composable
fun PreviewCategoryScreen() {
    val viewModel = CategoryViewModel(NetworkRepository()) // Mock or provide a proper instance
    CategoryScreen(viewModel = viewModel)
}

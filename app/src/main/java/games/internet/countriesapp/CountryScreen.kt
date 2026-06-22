package games.internet.countriesapp

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DockedSearchBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.collectAsState

@Composable
fun CountryScreen(modifier: Modifier){

    val viewModel: CountryViewModel = viewModel()

    println("Loaded: ${viewModel.countries.size}")
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ){
        AppSearchBar(modifier = Modifier.fillMaxWidth(), viewModel = viewModel)

        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
            Text("Search country \nby name.",
                style = MaterialTheme.typography.titleLarge,
                fontSize = 38.sp)
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppSearchBar(modifier: Modifier, viewModel: CountryViewModel){

    var query by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }

    Box(modifier = modifier.padding(16.dp)){
        DockedSearchBar(
            modifier = Modifier.align(Alignment.TopCenter),
            query = query,
            onQueryChange = {
                query = it
                viewModel.onQueryChange(it)},
            onSearch = {expanded = false},
            active = expanded,
            onActiveChange = {expanded = it},
            placeholder = {Text("Mexico..")},
            shape = RoundedCornerShape(if (expanded) 12.dp else 28.dp),
            leadingIcon = {Icon(Icons.Default.Search, contentDescription = "Search Country.")}
        ) {
            if (viewModel.stats.isLoading){
                Box(modifier = Modifier.fillMaxWidth().padding(16.dp), contentAlignment = Alignment.Center){
                    CircularProgressIndicator()
                }
            } else{
                LazyColumn(modifier = Modifier.fillMaxWidth().heightIn(max = 400.dp)) {
                    items(viewModel.filteredCountries){
                        country ->
                        ListItem(
                            headlineContent = {Text(country)}
                        )
                    }
                }
            }

        }
    }
}
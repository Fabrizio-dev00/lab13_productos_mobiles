package com.miempresa.jimenez_semana13.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.miempresa.jimenez_semana13.viewmodel.ProductoViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListScreen(
    viewModel: ProductoViewModel,
    onAdd: () -> Unit,
    onEdit: (String) -> Unit,
    onDetails: (String) -> Unit = {},
    onLogout: () -> Unit
) {
    val productos by viewModel.productos.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.cargarProductos()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Mis Productos") },
                actions = {
                    TextButton(onClick = onLogout) {
                        Text("Salir")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onAdd) {
                Text("+")
            }
        }
    ) { padding ->
        LazyColumn(modifier = Modifier.padding(padding).fillMaxSize()) {
            items(productos) { producto ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .clickable { onDetails(producto.id) }
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(producto.nombre, style = MaterialTheme.typography.titleLarge)
                            Text("Categoría: ${producto.categoria}")
                            Text("Precio: S/. ${producto.precio}")
                        }
                        Column(horizontalAlignment = Alignment.End) {
                            TextButton(onClick = { onEdit(producto.id) }) {
                                Text("Editar")
                            }
                            TextButton(onClick = { viewModel.eliminarProducto(producto.id) }) {
                                Text("Borrar")
                            }
                        }
                    }
                }
            }
        }
    }
}

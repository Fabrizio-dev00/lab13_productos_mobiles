package com.miempresa.jimenez_semana13.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.miempresa.jimenez_semana13.data.local.Producto

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListScreen(
    productos: List<Producto>,
    onAddClick: () -> Unit,
    onEditClick: (Producto) -> Unit,
    onDeleteClick: (Producto) -> Unit,
    onSearchChange: (String) -> Unit
) {
    var busqueda by remember { mutableStateOf("") }
    var showDialog by remember { mutableStateOf(false) }
    var productoAEliminar by remember { mutableStateOf<Producto?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Gestión de Productos") }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onAddClick) {
                Icon(Icons.Default.Add, "Agregar")
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            OutlinedTextField(
                value = busqueda,
                onValueChange = {
                    busqueda = it
                    onSearchChange(it)
                },
                label = { Text("Buscar por nombre o categoría") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            if (productos.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("No hay productos registrados")
                }
            } else {
                LazyColumn {
                    items(productos) { producto ->
                        ProductoItem(
                            producto = producto,
                            onEdit = { onEditClick(producto) },
                            onDelete = {
                                productoAEliminar = producto
                                showDialog = true
                            }
                        )
                    }
                }
            }
        }
    }

    if (showDialog && productoAEliminar != null) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Confirmar eliminación") },
            text = { Text("¿Desea eliminar el producto '${productoAEliminar?.nombre}'?") },
            confirmButton = {
                TextButton(
                    onClick = {
                        productoAEliminar?.let { onDeleteClick(it) }
                        showDialog = false
                    }
                ) {
                    Text("Eliminar")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text("Cancelar")
                }
            }
        )
    }
}

@Composable
fun ProductoItem(
    producto: Producto,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = producto.nombre, style = MaterialTheme.typography.titleMedium)
                Text(text = "Categoría: ${producto.categoria}", style = MaterialTheme.typography.bodySmall)
                Text(text = "Precio: S/ ${producto.precio}", style = MaterialTheme.typography.bodyMedium)
                Text(text = "Stock: ${producto.stock}", style = MaterialTheme.typography.bodySmall)
                Text(text = "Código: ${producto.codigoBarras}", style = MaterialTheme.typography.bodySmall)
            }

            Row {
                IconButton(onClick = onEdit) {
                    Icon(Icons.Default.Edit, "Editar")
                }
                IconButton(onClick = onDelete) {
                    Icon(Icons.Default.Delete, "Eliminar")
                }
            }
        }
    }
}
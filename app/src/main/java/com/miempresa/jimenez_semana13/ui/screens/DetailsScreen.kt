package com.miempresa.jimenez_semana13.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.miempresa.jimenez_semana13.viewmodel.ProductoViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsScreen(
    viewModel: ProductoViewModel,
    productId: String,
    onBack: () -> Unit
) {
    val producto by viewModel.productoActual.collectAsState()

    LaunchedEffect(productId) {
        viewModel.cargarProducto(productId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detalle del Producto") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Text("<")
                    }
                }
            )
        }
    ) { padding ->
        producto?.let { p ->
            Column(
                modifier = Modifier
                    .padding(padding)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text("Nombre: ${p.nombre}", style = MaterialTheme.typography.titleLarge)
                Text("Categoría: ${p.categoria}")
                Text("Precio: S/. ${p.precio}")
                Text("Stock: ${p.stock}")
                Text("Código de barras: ${p.codigoBarras}")
                Text("Descripción: ${p.descripcion}")
            }
        }
    }
}

package com.miempresa.jimenez_semana13.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.miempresa.jimenez_semana13.data.model.Producto
import com.miempresa.jimenez_semana13.viewmodel.ProductoViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormScreen(
    viewModel: ProductoViewModel,
    productId: String? = null,
    onFinished: () -> Unit
) {
    val productoActual by viewModel.productoActual.collectAsState()

    var nombre by remember { mutableStateOf("") }
    var categoria by remember { mutableStateOf("") }
    var precio by remember { mutableStateOf("") }
    var stock by remember { mutableStateOf("") }
    var codigo by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }

    // Cargar datos si es edición
    LaunchedEffect(productId) {
        if (productId != null) {
            viewModel.cargarProducto(productId)
        }
    }

    LaunchedEffect(productoActual) {
        productoActual?.let {
            nombre = it.nombre
            categoria = it.categoria
            precio = it.precio.toString()
            stock = it.stock.toString()
            codigo = it.codigoBarras
            descripcion = it.descripcion
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (productId == null) "Nuevo Producto" else "Editar Producto") }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier.padding(padding).padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedTextField(value = nombre, onValueChange = { nombre = it }, label = { Text("Nombre") })
            OutlinedTextField(value = categoria, onValueChange = { categoria = it }, label = { Text("Categoría") })
            OutlinedTextField(value = precio, onValueChange = { precio = it }, label = { Text("Precio") })
            OutlinedTextField(value = stock, onValueChange = { stock = it }, label = { Text("Stock") })
            OutlinedTextField(value = codigo, onValueChange = { codigo = it }, label = { Text("Código de Barras") })
            OutlinedTextField(value = descripcion, onValueChange = { descripcion = it }, label = { Text("Descripción") })

            Button(
                onClick = {
                    val producto = Producto(
                        id = productId ?: "",
                        nombre = nombre,
                        categoria = categoria,
                        precio = precio.toDoubleOrNull() ?: 0.0,
                        stock = stock.toIntOrNull() ?: 0,
                        codigoBarras = codigo,
                        descripcion = descripcion
                    )

                    if (productId == null) {
                        viewModel.agregarProducto(producto)
                    } else {
                        viewModel.actualizarProducto(producto)
                    }

                    onFinished()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Guardar")
            }
        }
    }
}

package com.miempresa.jimenez_semana13.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.miempresa.jimenez_semana13.data.local.Producto

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormScreen(
    producto: Producto? = null,
    onSave: (Producto) -> Unit,
    onBack: () -> Unit
) {
    var nombre by remember { mutableStateOf(producto?.nombre ?: "") }
    var categoria by remember { mutableStateOf(producto?.categoria ?: "") }
    var precio by remember { mutableStateOf(producto?.precio?.toString() ?: "") }
    var stock by remember { mutableStateOf(producto?.stock?.toString() ?: "") }
    var codigoBarras by remember { mutableStateOf(producto?.codigoBarras ?: "") }
    var descripcion by remember { mutableStateOf(producto?.descripcion ?: "") }

    val isEdit = producto != null

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (isEdit) "Editar Producto" else "Nuevo Producto") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, "Volver")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedTextField(
                value = nombre,
                onValueChange = { nombre = it },
                label = { Text("Nombre *") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = categoria,
                onValueChange = { categoria = it },
                label = { Text("Categoría") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = precio,
                onValueChange = { precio = it },
                label = { Text("Precio *") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = stock,
                onValueChange = { stock = it },
                label = { Text("Stock *") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = codigoBarras,
                onValueChange = { if (it.length <= 13) codigoBarras = it },
                label = { Text("Código de Barras (13 caracteres) *") },
                enabled = !isEdit,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth(),
                supportingText = { Text("${codigoBarras.length}/13") }
            )

            OutlinedTextField(
                value = descripcion,
                onValueChange = { descripcion = it },
                label = { Text("Descripción (opcional)") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    val nuevoProducto = Producto(
                        idProducto = producto?.idProducto ?: 0,
                        nombre = nombre,
                        categoria = categoria,
                        precio = precio.toDoubleOrNull() ?: 0.0,
                        stock = stock.toIntOrNull() ?: 0,
                        codigoBarras = codigoBarras,
                        descripcion = descripcion
                    )
                    onSave(nuevoProducto)
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(if (isEdit) "Actualizar" else "Guardar")
            }
        }
    }
}
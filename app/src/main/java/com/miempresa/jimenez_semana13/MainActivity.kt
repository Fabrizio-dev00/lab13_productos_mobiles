package com.miempresa.jimenez_semana13

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.miempresa.jimenez_semana13.data.local.AppDatabase
import com.miempresa.jimenez_semana13.data.local.Producto
import com.miempresa.jimenez_semana13.data.repository.ProductoRepository
import com.miempresa.jimenez_semana13.ui.ProductoViewModel
import com.miempresa.jimenez_semana13.ui.screens.FormScreen
import com.miempresa.jimenez_semana13.ui.screens.ListScreen
import com.miempresa.jimenez_semana13.ui.theme.Jimenez_semana13Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val database = AppDatabase.getDatabase(this)
        val repository = ProductoRepository(database.productoDao())
        val viewModel = ProductoViewModel(repository)

        setContent {
            Jimenez_semana13Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ProductosApp(viewModel)
                }
            }
        }
    }
}

@Composable
fun ProductosApp(viewModel: ProductoViewModel) {
    val productos by viewModel.productos.collectAsState()
    var pantalla by remember { mutableStateOf("lista") }
    var productoEditar by remember { mutableStateOf<Producto?>(null) }
    val context = LocalContext.current

    when (pantalla) {
        "lista" -> {
            ListScreen(
                productos = productos,
                onAddClick = {
                    productoEditar = null
                    pantalla = "formulario"
                },
                onEditClick = { producto ->
                    productoEditar = producto
                    pantalla = "formulario"
                },
                onDeleteClick = { producto ->
                    viewModel.eliminarProducto(producto) {
                        Toast.makeText(context, "Producto eliminado", Toast.LENGTH_SHORT).show()
                    }
                },
                onSearchChange = { busqueda ->
                    viewModel.buscarProductos(busqueda)
                }
            )
        }

        "formulario" -> {
            FormScreen(
                producto = productoEditar,
                onSave = { producto ->
                    if (productoEditar == null) {
                        viewModel.insertarProducto(
                            producto,
                            onSuccess = {
                                Toast.makeText(context, "Producto guardado", Toast.LENGTH_SHORT).show()
                                pantalla = "lista"
                            },
                            onError = { mensaje ->
                                Toast.makeText(context, mensaje, Toast.LENGTH_LONG).show()
                            }
                        )
                    } else {
                        viewModel.actualizarProducto(
                            producto,
                            onSuccess = {
                                Toast.makeText(context, "Producto actualizado", Toast.LENGTH_SHORT).show()
                                pantalla = "lista"
                            },
                            onError = { mensaje ->
                                Toast.makeText(context, mensaje, Toast.LENGTH_LONG).show()
                            }
                        )
                    }
                },
                onBack = { pantalla = "lista" }
            )
        }
    }
}
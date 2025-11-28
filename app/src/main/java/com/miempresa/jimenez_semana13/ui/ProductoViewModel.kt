package com.miempresa.jimenez_semana13.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.miempresa.jimenez_semana13.data.local.Producto
import com.miempresa.jimenez_semana13.data.repository.ProductoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProductoViewModel(private val repository: ProductoRepository) : ViewModel() {

    private val _productos = MutableStateFlow<List<Producto>>(emptyList())
    val productos: StateFlow<List<Producto>> = _productos

    init {
        cargarProductos()
    }

    fun cargarProductos() {
        viewModelScope.launch {
            repository.getAllProductos().collect { lista ->
                _productos.value = lista
            }
        }
    }

    fun buscarProductos(busqueda: String) {
        viewModelScope.launch {
            if (busqueda.isEmpty()) {
                cargarProductos()
            } else {
                repository.buscarProductos(busqueda).collect { lista ->
                    _productos.value = lista
                }
            }
        }
    }

    fun insertarProducto(producto: Producto, onSuccess: () -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            try {
                if (producto.nombre.isEmpty()) {
                    onError("El nombre es obligatorio")
                    return@launch
                }
                if (producto.precio <= 0) {
                    onError("El precio debe ser mayor a 0")
                    return@launch
                }
                if (producto.stock < 0) {
                    onError("El stock no puede ser negativo")
                    return@launch
                }
                if (producto.codigoBarras.length != 13) {
                    onError("El código de barras debe tener 13 caracteres")
                    return@launch
                }
                if (!repository.verificarCodigoUnico(producto.codigoBarras)) {
                    onError("El código de barras ya existe")
                    return@launch
                }

                repository.insertProducto(producto)
                onSuccess()
            } catch (e: Exception) {
                onError("Error al guardar: ${e.message}")
            }
        }
    }

    fun actualizarProducto(producto: Producto, onSuccess: () -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            try {
                if (producto.nombre.isEmpty()) {
                    onError("El nombre es obligatorio")
                    return@launch
                }
                if (producto.precio <= 0) {
                    onError("El precio debe ser mayor a 0")
                    return@launch
                }
                if (producto.stock < 0) {
                    onError("El stock no puede ser negativo")
                    return@launch
                }

                repository.updateProducto(producto)
                onSuccess()
            } catch (e: Exception) {
                onError("Error al actualizar: ${e.message}")
            }
        }
    }

    fun eliminarProducto(producto: Producto, onSuccess: () -> Unit) {
        viewModelScope.launch {
            repository.deleteProducto(producto)
            onSuccess()
        }
    }
}
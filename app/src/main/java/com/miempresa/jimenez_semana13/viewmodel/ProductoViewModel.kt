package com.miempresa.jimenez_semana13.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.miempresa.jimenez_semana13.data.model.Producto
import com.miempresa.jimenez_semana13.data.repository.ProductoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProductoViewModel : ViewModel() {

    private val repo = ProductoRepository()

    private val _productos = MutableStateFlow<List<Producto>>(emptyList())
    val productos: StateFlow<List<Producto>> = _productos

    private val _productoActual = MutableStateFlow<Producto?>(null)
    val productoActual: StateFlow<Producto?> = _productoActual

    fun cargarProductos() {
        viewModelScope.launch {
            _productos.value = repo.obtenerProductos()
        }
    }

    fun cargarProducto(id: String) {
        viewModelScope.launch {
            _productoActual.value = repo.obtenerProducto(id)
        }
    }

    fun agregarProducto(producto: Producto) {
        viewModelScope.launch {
            repo.agregarProducto(producto)
            cargarProductos()
        }
    }

    fun actualizarProducto(producto: Producto) {
        viewModelScope.launch {
            repo.actualizarProducto(producto)
            cargarProductos()
        }
    }

    fun eliminarProducto(id: String) {
        viewModelScope.launch {
            repo.eliminarProducto(id)
            cargarProductos()
        }
    }

    // 🔥 IMPORTANTE: limpiar el producto seleccionado
    fun limpiarProductoActual() {
        _productoActual.value = null
    }
}

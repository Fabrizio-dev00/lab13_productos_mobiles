package com.miempresa.jimenez_semana13.ui.components

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType

@Composable
fun CampoTexto(
    valor: String,
    onValorCambio: (String) -> Unit,
    etiqueta: String,
    modifier: Modifier = Modifier,
    habilitado: Boolean = true,
    tipoTeclado: KeyboardType = KeyboardType.Text,
    lineasMinimas: Int = 1,
    textoAyuda: String? = null
) {
    OutlinedTextField(
        value = valor,
        onValueChange = onValorCambio,
        label = { Text(etiqueta) },
        modifier = modifier,
        enabled = habilitado,
        keyboardOptions = KeyboardOptions(keyboardType = tipoTeclado),
        minLines = lineasMinimas,
        supportingText = if (textoAyuda != null) {
            { Text(textoAyuda) }
        } else null
    )
}
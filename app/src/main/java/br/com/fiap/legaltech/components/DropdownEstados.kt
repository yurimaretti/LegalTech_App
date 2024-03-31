package br.com.fiap.legaltech.components

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import br.com.fiap.legaltech.enum.EstadosBrasileiros

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownEstados(

) {
    var isExpanded by remember {
        mutableStateOf(false)
    }
    var estado by remember {
        mutableStateOf("")
    }
    var lista = EstadosBrasileiros.values()

    ExposedDropdownMenuBox(
        expanded = isExpanded,
        onExpandedChange = {
            isExpanded = it
        }
    ) {
        OutlinedTextField(
            value = estado,
            onValueChange = {},
            trailingIcon = {
                ExposedDropdownMenuDefaults
                    .TrailingIcon(expanded = isExpanded)
            },
            modifier = Modifier.menuAnchor(),
            readOnly = true,
            colors = ExposedDropdownMenuDefaults.textFieldColors(),
            shape = RoundedCornerShape(16.dp)
        )
        ExposedDropdownMenu(
            expanded = isExpanded,
            onDismissRequest = { isExpanded = !isExpanded }
        ) {
            lista.forEach{
                DropdownMenuItem(
                    text = {
                        Text(text = it.toString())
                    },
                    onClick = {
                        estado = it.toString()
                        isExpanded = !isExpanded
                    }
                )
            }
        }
    }
}
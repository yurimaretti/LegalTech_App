package br.com.fiap.legaltech.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp

@Composable
fun InputBox(
    label: String,
    placeholder: String,
    value: String,
    kayboardType: KeyboardType,
    modifier: Modifier,
    updateValue: (String) -> Unit,
    visualTransformation: VisualTransformation
) {
    Text(text = label)
    OutlinedTextField(
        value = value,
        onValueChange = updateValue,
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 16.dp),
        placeholder = {
            Text(text = placeholder)
        },
        keyboardOptions = KeyboardOptions(keyboardType = kayboardType),
        shape = RoundedCornerShape(16.dp),
        visualTransformation = visualTransformation
    )
}
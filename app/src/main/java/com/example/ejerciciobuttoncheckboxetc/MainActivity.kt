package com.example.ejerciciobuttoncheckboxetc
//José Latorre Domínguez
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxColors
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.ejerciciobuttoncheckboxetc.ui.theme.EjerciciobuttoncheckboxetcTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EjerciciobuttoncheckboxetcTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f)
                                .background(Color.Gray)
                        ) {
                            ButtonWithTextUpdate()
                        }
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f)
                                .background(Color.Cyan)
                        ) {
                            Column {
                                CheckboxLabelExample()
                            }
                        }
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f)
                                .background(Color.Green)
                        ){
                            IconDisplay()
                        }
                        var showRadioButtons by remember { mutableStateOf(false) } // Control de visibilidad

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f)
                                .background(Color.Blue)
                        ) {
                            SwitchDisplay { switchState ->
                                showRadioButtons = switchState
                            }
                        }

                        if (showRadioButtons) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(1f)
                                    .background(Color.Red)
                            ) {
                                var selected by rememberSaveable {
                                    mutableStateOf("Radio1")
                                }
                                MyRadioButton3(selected) { selected = it }
                            }
                        }
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f)
                                .background(Color.Blue)
                        ) {
                            ImageSwitcher()
                        }
                    }
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun ButtonWithTextUpdate() {
    var buttonText by remember { mutableStateOf("Presionar") }
    var showProgress by remember { mutableStateOf(false) }
    var textFieldText by remember { mutableStateOf("") }

    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier.fillMaxSize(),
    ) {
        BasicTextField(
            value = textFieldText,
            onValueChange = { newValue -> textFieldText = newValue },
            modifier = Modifier.padding(1.dp)
        )

        Button(
            onClick = {
                buttonText = "Botón presionado"
                showProgress = true
                coroutineScope.launch {
                    delay(5000L) // Simula una operación que toma 5 segundos
                    showProgress = false
                    buttonText = "Presionar"
                }
            },
            modifier = Modifier.padding(1.dp)
        ) {
            Text(text = buttonText)
        }

        if (showProgress) {
            CircularProgressIndicator(modifier = Modifier.padding(1.dp))
        }
    }
}

@Composable
fun MessageTextField(initialText: String) {
    var textFieldText by remember { mutableStateOf(initialText) }

    BasicTextField(
        value = textFieldText,
        onValueChange = { newValue -> textFieldText = newValue },
        modifier = Modifier.padding(16.dp)
    )
}

@Composable
fun LabelledCheckbox(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    colors: CheckboxColors = CheckboxDefaults.colors()
) {
    Row(
        modifier = modifier.height(48.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Checkbox(
            checked = checked,
            onCheckedChange = onCheckedChange,
            enabled = enabled,
            colors = colors
        )
        Spacer(Modifier.width(32.dp))
        Text(label)
    }
}@Composable
    fun IconDisplay() {
        Icon(
            painter = painterResource(id = R.drawable.icon),
            contentDescription = "Icono de elección",
            modifier = Modifier.padding(16.dp)
        )
    }

@Composable
fun CheckboxLabelExample() {
    var isChecked by remember { mutableStateOf(false) }
    val (selectedText, setSelectedText) = remember { mutableStateOf("Mensaje inicial") }

    LabelledCheckbox(
        checked = isChecked,
        onCheckedChange = { isChecked = it },
        label = "Checkbox con etiqueta"
    )

    if (isChecked) {
        MessageTextField(selectedText)
    }
}


@Composable
fun SwitchDisplay(onSwitchChange: (Boolean) -> Unit) {
    var switchState by remember { mutableStateOf(false) }

    Column {
        Switch(
            checked = switchState,
            onCheckedChange = { checked ->
                switchState = checked
                onSwitchChange(checked) // Llama a la función lambda
            },
            modifier = Modifier.padding(16.dp)
        )
    }
}


@Composable
fun MyRadioButton3(selectedText: String, onTextSelected: (String) -> Unit) {
    Column(Modifier.fillMaxSize()) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            RadioButton(
                selected = selectedText == "Radio 1",
                onClick = {
                    onTextSelected("Radio 1")
                }
            )
            Text(text = "Radio 1")
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            RadioButton(
                selected = selectedText == "Radio 2",
                onClick = {
                    onTextSelected("Radio 2")
                }
            )
            Text(text = "Radio 2")
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            RadioButton(
                selected = selectedText == "Radio 3",
                onClick = {
                    onTextSelected("Radio 3")
                }
            )
            Text(text = "Radio 3")
        }
    }
}
@Composable
fun ImageSwitcher() {
    var imageIndex by remember { mutableStateOf(0) }

    val images = listOf(
        R.drawable.image1,
        R.drawable.image2,
        R.drawable.image3
    )

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
    ) {
        Icon(
            painter = painterResource(id = images[imageIndex]),
            contentDescription = "Imagen",
            modifier = Modifier
                .scale(5f)
                .padding(16.dp)
        )

        Button(
            onClick = {
                imageIndex = (imageIndex + 1) % images.size
            },
            modifier = Modifier.padding(16.dp)
        ) {
            Text("Cambiar Imagen")
        }
    }
}




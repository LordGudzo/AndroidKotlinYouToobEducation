package com.firstproject.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.firstproject.myapplication.ui.theme.MyApplicationTheme
import kotlin.math.roundToInt

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ConverterChanged()
                }
            }
        }
    }

    @Composable
    fun ConverterChanged() {
        val inputValue = remember { mutableStateOf("") }
        var isDropDownOpen by remember { mutableStateOf(false) }
        var textInSelectBnt by remember { mutableStateOf("Select") }
        var rate by remember { mutableDoubleStateOf(1.0) }
        var result by remember { mutableDoubleStateOf(0.0) }


        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "Currency changed",
                style = MaterialTheme.typography.titleLarge
            )

            Spacer(Modifier.height(30.dp))

            //<editor-fold desc="field for input">
            OutlinedTextField(
                value = inputValue.value,
                label = { Text("Enter amount your GRN") },
                onValueChange = { inputValue.value = it })
            //</editor-fold>

            Spacer(Modifier.height(30.dp))

            Row {
                //<editor-fold desc="select btn and drop down menu">
                Box {
                    //<editor-fold desc="Select btn">
                    Button(onClick = { isDropDownOpen = true }) {
                        Text(textInSelectBnt)
                        Icon(
                            Icons.Default.ArrowDropDown,
                            contentDescription = null
                        )
                    }
                    //</editor-fold>

                    //<editor-fold desc="drop down menu">
                    DropdownMenu(
                        expanded = isDropDownOpen,
                        onDismissRequest = {
                            isDropDownOpen = false
                        },  //if click in another place (not drop down place)
                    ) {
                        DropdownMenuItem(
                            text = { Text("to USD: 0.023") },
                            onClick = {
                                isDropDownOpen = false
                                textInSelectBnt = "USD: 0.023"
                                result = 0.0
                                rate = 0.023
                            })
                        DropdownMenuItem(
                            text = { Text("to EUR: 0.020") },
                            onClick = {
                                isDropDownOpen = false
                                textInSelectBnt = "EUR: 0.020"
                                result = 0.0
                                rate = 0.020
                            })
                    }
                    //</editor-fold>
                }
                //</editor-fold>

                Spacer(Modifier.width(20.dp))

                //<editor-fold desc="calculate btn">
                Box {
                    Button(onClick = {
                        val currentValue = inputValue.value.toDoubleOrNull()

                        if (currentValue != null) {
                            val currentCulculate = currentValue * rate
                            result = (currentCulculate * 100).roundToInt() / 100.0
                        } else inputValue.value = "Enter your value in number"
                    }) {
                        Text("Calculate")
                    }
                }
                //</editor-fold>
            }

            Spacer(Modifier.height(30.dp))

            Text(
                "Result: $result",
                color = Color.Blue,
                fontSize = 52.sp,
                fontStyle = FontStyle.Italic
            )
        }
    }


    @Preview(showBackground = true, showSystemUi = true)
    @Composable
    fun ConverterChangedPreview() {
        ConverterChanged()
    }
}
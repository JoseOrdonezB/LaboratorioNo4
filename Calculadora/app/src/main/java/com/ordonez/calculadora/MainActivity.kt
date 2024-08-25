package com.ordonez.calculadora

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ordonez.calculadora.ui.theme.CalcComposeTheme
import calculadora.Calculator

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CalcComposeTheme {
                CalculatorApp()
            }
        }
    }
}

@Composable
fun CalculatorApp(modifier: Modifier = Modifier) {
    var expression by remember { mutableStateOf("0") }
    var result by remember { mutableStateOf("") }
    val calculator = Calculator()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.End
    ) {
        DisplayCalc(expression, result)

        Spacer(modifier = Modifier.height(16.dp))

        val buttons = listOf(
            listOf("7", "8", "9", "/"),
            listOf("4", "5", "6", "*"),
            listOf("1", "2", "3", "-"),
            listOf(".", "0", "<-", "+"),
            listOf("(", ")", "C", "=")
        )

        for (row in buttons) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                for (buttonText in row) {
                    Box(
                        modifier = Modifier.weight(1f)
                    ) {
                        CalculatorButton(
                            onClickedValue = {
                                when (buttonText) {
                                    "<-" -> if (expression.length > 1) expression = expression.dropLast(1) else expression = "0"
                                    "C" -> {
                                        expression = "0"
                                        result = ""
                                    }
                                    "=" -> result = calculator.evaluate(expression).toString()
                                    else -> {
                                        if (expression == "0" && buttonText != ".") expression = buttonText
                                        else if (buttonText == "." && expression.lastOrNull()?.isDigit() == true && !expression.contains(".")) {
                                            expression += buttonText
                                        } else if (buttonText != ".") {
                                            expression += buttonText
                                        }
                                    }
                                }
                            },
                            text = buttonText
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun CalculatorButton(
    onClickedValue: () -> Unit,
    text: String = "",
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClickedValue,
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(1f),
        shape = RoundedCornerShape(8.dp)
    ) {
        Text(text = text, fontSize = 20.sp)
    }
}

@Composable
fun DisplayCalc(expression: String, result: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.End
    ) {
        Text(
            text = expression,
            style = MaterialTheme.typography.titleLarge.copy(fontSize = 36.sp),
            textAlign = TextAlign.End,
            color = Color.White,
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.DarkGray)
                .padding(16.dp)
        )
        Text(
            text = result,
            style = MaterialTheme.typography.bodyMedium.copy(fontSize = 24.sp),
            textAlign = TextAlign.End,
            color = Color.LightGray,
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.DarkGray)
                .padding(8.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CalculatorAppPreview() {
    CalcComposeTheme {
        CalculatorApp()
    }
}

package calculadora

import java.util.*

class Evaluator {
    // Conjunto de operadores soportados por el evaluador
    private val operators = setOf('+', '-', '*', '/', '^', '√', 'e')

    // Función principal que evalúa una expresión en notación postfija
    fun evaluatePostfix(tokens: Queue<String>): Double {
        val stack = Stack<Double>() // Pila para almacenar operandos

        // Itera sobre cada token en la expresión postfija
        for (token in tokens) {
            when {
                // Si el token es un número (Double), se apila
                token.isDouble() -> stack.push(token.toDouble())

                // Si el token es un operador, se desapilan operandos y se aplica el operador
                token.isOperator() -> {
                    val b = stack.pop() // Extrae el operando superior
                    // Si el operador no es la raíz cuadrada, se necesita un segundo operando
                    val a = if (stack.isNotEmpty() && token[0] != '√') stack.pop() else 0.0
                    // Aplica el operador a los operandos y apila el resultado
                    stack.push(applyOperator(token[0], a, b))
                }
            }
        }
        // El resultado final de la evaluación está en la cima de la pila
        return stack.pop()
    }

    // Aplica el operador especificado a los operandos a y b, devolviendo el resultado
    private fun applyOperator(op: Char, a: Double, b: Double): Double {
        return when (op) {
            '+' -> a + b
            '-' -> a - b
            '*' -> a * b
            '/' -> a / b
            '^' -> Math.pow(a, b)
            '√' -> Math.sqrt(b) // Solo usa el operando b para la raíz cuadrada
            'e' -> Math.exp(b) // Calcula e^b
            else -> throw IllegalArgumentException("Unknown operator: $op") // Error si el operador no es conocido
        }
    }

    // Extensión de String para verificar si el token puede ser convertido a Double
    private fun String.isDouble(): Boolean = this.toDoubleOrNull() != null

    // Extensión de String para verificar si el token es un operador válido
    private fun String.isOperator(): Boolean = this.length == 1 && this[0] in operators
}

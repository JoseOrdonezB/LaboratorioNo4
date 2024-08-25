package calculadora

import java.util.*

class ShuntingYard {
    // Conjunto de operadores soportados por el algoritmo
    private val operators = setOf('+', '-', '*', '/', '^', '√', 'e')

    // Mapa que define la precedencia de cada operador
    private val precedence = mapOf(
        '+' to 1, '-' to 1, // Suma y resta tienen la menor precedencia
        '*' to 2, '/' to 2, // Multiplicación y división tienen precedencia intermedia
        '^' to 3,           // Potenciación tiene mayor precedencia que multiplicación y división
        '√' to 4, 'e' to 4  // Raíz cuadrada y la función exponencial tienen la mayor precedencia
    )

    // Función que convierte una lista de tokens en notación infija a una cola de tokens en notación postfija
    fun toPostfix(tokens: List<String>): Queue<String> {
        val outputQueue: Queue<String> = LinkedList() // Cola de salida donde se almacenará la expresión en postfijo
        val operatorStack = ArrayDeque<String>() // Pila de operadores

        for (token in tokens) {
            when {
                // Si el token es un número, se añade directamente a la cola de salida
                token.isDouble() -> outputQueue.add(token)

                // Si el token es un operador
                token.isOperator() -> {
                    // Desapilar operadores de mayor o igual precedencia y añadirlos a la cola de salida
                    while (operatorStack.isNotEmpty() && operatorStack.peek() != "(" &&
                        operatorStack.peek().isOperator() &&
                        precedence[token[0]]!! <= precedence[operatorStack.peek()[0]]!!) {
                        outputQueue.add(operatorStack.pop())
                    }
                    // Apilar el operador actual
                    operatorStack.push(token)
                }

                // Si el token es un paréntesis izquierdo, se apila
                token == "(" -> operatorStack.push("(")

                // Si el token es un paréntesis derecho
                token == ")" -> {
                    // Desapilar operadores hasta encontrar el paréntesis izquierdo correspondiente
                    while (operatorStack.peek() != "(") {
                        outputQueue.add(operatorStack.pop())
                    }
                    // Se elimina el paréntesis izquierdo de la pila
                    operatorStack.pop()
                }
            }
        }

        // Después de procesar todos los tokens, se vacía la pila de operadores en la cola de salida
        while (operatorStack.isNotEmpty()) {
            outputQueue.add(operatorStack.pop())
        }

        // Se devuelve la cola de tokens en notación postfija
        return outputQueue
    }

    // Extensión de String para verificar si el token puede ser convertido a Double
    private fun String.isDouble(): Boolean = this.toDoubleOrNull() != null

    // Extensión de String para verificar si el token es un operador válido
    private fun String.isOperator(): Boolean = this.length == 1 && this[0] in operators
}

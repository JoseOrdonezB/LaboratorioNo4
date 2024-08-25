package calculadora

class Calculator {
    // Instancias de las clases que manejan el proceso de tokenización, conversión a notación postfija y evaluación
    private val tokenizer = Tokenizer()
    private val shuntingYard = ShuntingYard()
    private val evaluator = Evaluator()

    // Función principal para evaluar una expresión matemática
    fun evaluate(expression: String): Double {
        // Tokeniza la expresión (convierte la cadena de texto en una lista de tokens)
        val tokens = tokenizer.tokenize(expression)
        // Convierte los tokens a notación postfija utilizando el algoritmo Shunting Yard
        val postfix = shuntingYard.toPostfix(tokens)
        // Evalúa la expresión en notación postfija y devuelve el resultado como un Double
        return evaluator.evaluatePostfix(postfix)
    }
}

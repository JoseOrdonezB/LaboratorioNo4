package calculadora

class Tokenizer {
    // Expresión regular que identifica números, operadores, paréntesis y espacios en blanco
    private val regex = Regex("""(\d+(\.\d+)?)|([+\-*/()])|(\s+)""")

    // Función que tokeniza una expresión matemática en una lista de tokens
    fun tokenize(expression: String): List<String> {
        // Aplica la expresión regular sobre la expresión dada, obteniendo todas las coincidencias
        return regex.findAll(expression)
            .map { it.value } // Convierte cada coincidencia en una cadena de texto
            .filter { it.isNotBlank() } // Filtra y elimina cualquier token que sea solo espacio en blanco
            .toList() // Devuelve los tokens como una lista
    }
}


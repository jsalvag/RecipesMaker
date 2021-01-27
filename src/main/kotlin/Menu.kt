import model.Recipe
import service.RecipeService

class Menu(var title: String) {
    private val recipeService = RecipeService()
    fun star() {
        println(title)
        do {
            print(
                """
                    
                ================================================================================
                ============================== Recipes Maker App ===============================
                ================================================================================
                
                    1 - Crear receta
                    ${if (this.recipeService.list().isNotEmpty()) "2 - Listar recetar\n" else ""}
                    0. Salir
                Seleccione una opción:  
                """.trimIndent()
            )
            try {
                when (val opt = readLine().orEmpty().toInt()) {
                    1 -> {
                        val new = this.recipeService.new()
                        this.recipeService.create(Recipe(this.recipeService.list().size.inc(), new))
                    }
                    2 -> {
                        println("opción seleccionada: $opt")
                        println(
                            this.recipeService.list()
                                .map { r ->
                                    "Receta ${r.index}:\n\t${
                                        r.items.joinToString(separator = "\n\t") { category ->
                                            "${category.name}:\n\t\t${
                                                category.items.joinToString(
                                                    " | "
                                                ) { element ->
                                                    "${element.name.padEnd(10)}: ${
                                                        element.amount.toString().padStart(10)
                                                    } ${element.unity.padEnd(4)}"
                                                }
                                            }"
                                        }
                                    }"
                                })
                    }
                    0 -> {
                        println("Adiós...")
                        break
                    }
                    else -> {
                        println("Opción inválida")
                    }
                }
            } catch (e: Exception) {
                println("Opción inválida")
            }
        } while (true)
    }
}

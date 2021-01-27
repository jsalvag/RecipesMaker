package service

import model.Category
import model.Element

class CategoryService : CRUD<Category>() {
    private var categories = mutableListOf<Category>()

    init {
        categories = mutableListOf(
            Category(
                "Agua", mutableListOf(
                    Element("Agua", "ml")
                )
            ),
            Category(
                "Leche", mutableListOf(
                    Element("Leche", "ml")
                )
            ),
            Category(
                "Carne", mutableListOf(
                    Element("Res", "kg"),
                    Element("Cordero", "kg"),
                    Element("Cerdo", "kg"),
                    Element("Ave", "kg"),
                )
            ),
            Category(
                "Verduras", mutableListOf(
                    Element("Zanahoria", "g"),
                    Element("Remolacha", "g"),
                    Element("Papa", "g")
                )
            ),
            Category(
                "Frutas", mutableListOf(
                    Element("Cambur", "g"),
                    Element("Aguacate", "g"),
                    Element("Fresa", "g"),
                    Element("Nispero", "g"),
                    Element("Mango", "g"),
                    Element("Piña", "g"),
                    Element("Pomalaca", "g"),
                )
            ),
            Category(
                "Cereal", mutableListOf(
                    Element("Maíz", "g"),
                    Element("Avena", "g"),
                    Element("Arroz", "g"),
                    Element("Trigo", "g")
                )
            ),
            Category(
                "Huevos", mutableListOf(
                    Element("Gallina", "u"),
                    Element("Codorniz", "u"),
                    Element("Pato", "u")
                )
            ),
            Category(
                "Aceite", mutableListOf(
                    Element("Oliva", "mL"),
                    Element("Maíz", "mL"),
                    Element("Linaza", "mL"),
                    Element("Coco", "mL"),
                    Element("Girasol", "mL")
                )
            )
        )
    }

    override fun create(item: Category): Boolean {
        this.categories.add(item)
        return true
    }

    override fun read(index: Int): Category? {
        for ((i, c) in categories.withIndex()) {
            if (i == index) {
                return c
            }
        }
        return null
    }

    override fun list(): List<Category> {
        return this.categories
    }

    override fun update(index: Int, item: Category): Boolean {
        val read = this.read(index)
        read?.items = item.items
        read?.name = item.name
        return true
    }

    override fun delete(index: Int): Boolean {
        TODO("Not yet implemented")
    }

    fun selectOne(): Category {
        val selectedCategory = Category("", mutableListOf())
        if (this.categories.isNotEmpty()) {
            println(
                """
                ================================================================================
                =========================== Seleccione una Categoría ===========================
                ================================================================================

            """.trimIndent()
            )
            selectCategory@ do {
                printCategoriesList()
                if (selectedCategory.items.isNotEmpty()) {
                    println("   ${this.categories.size.inc()} - Guardar")
                }
                print(
                    """
                    0 - Cancelar
                Seleccione una opción: 
                """.trimIndent()
                )
                try {
                    when (val opt = readLine()?.toInt()) {
                        in 1 until this.categories.size -> {
                            val (name, items) = this.categories[opt!!.dec()]
                            if (getElement(selectedCategory, name, items)) break@selectCategory
                        }
                        this.categories.size -> {

                        }
                        else -> {
                        }
                    }
                } catch (e: Exception) {
                    println("La opción seleccionada no es válida, intente nuevamente...")
                }
            } while (true)
        }
        return selectedCategory
    }

    private fun getElement(
        selectedCategory: Category, name: String, items: List<Element>
    ): Boolean {
        selectedCategory.name = name
        var selectedElement = Element("", "")
        if (items.size > 1) {
            do {
                println("Seleccione un elemento de $name")
                for ((i, el) in items.withIndex()) {
                    print("\t${i.inc()} - ${el.name.padEnd(10)} ${if (i.inc() % 4 == 0) "\n" else ""}")
                }
                print(
                    """
                        
                        0 - Cancelar
                    Seleccione una opción: 
                    """.trimIndent()
                )
                when (val elOpt = readLine()?.toInt()) {
                    0 -> return true
                    in 1..items.size -> {
                        selectedElement = items[elOpt!!.dec()]
                        getAmount(selectedElement)
                    }
                    else -> {
                        println("La opción seleccionada no es válida, intente nuevamente...\nDebe estar entre 1 y ${items.size}")
                    }
                }
                if (selectedElement.amount > 0) {
                    print(
                        """
                            Ha seleccionado ${selectedElement.name} [${selectedElement.amount} ${selectedElement.unity}]
                            Desea continuar? (S/n): 
                        """.trimIndent()
                    )
                    selectedCategory.items.add(selectedElement)
                    if (readLine()?.toLowerCase().equals("n")) {
                        return true
                    }
                }
            } while (true)
        } else {
            selectedElement = items[0]
            getAmount(selectedElement)
            selectedCategory.items.add(selectedElement)
        }
        return false
    }

    private fun getAmount(selectedElement: Element) {
        do {
            try {
                print("amount of ${selectedElement.name} [${selectedElement.unity}]: ")
                readLine()?.toDouble()!!.also {
                    if (it > 0.0)
                        selectedElement.amount = it
                }
                break
            } catch (e: Exception) {
                println("Cantidad incorrecta. Intente nuevamente")
            }
        } while (true)
    }

    private fun printCategoriesList() {
        for ((i, item) in this.categories.withIndex()) {
            print("\t${i.inc()} - ${item.name.padEnd(10)}${if (i.inc() % 4 == 0) "\n" else ""}")
        }
    }
}

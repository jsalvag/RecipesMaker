package service

import model.Category
import model.Recipe

class RecipeService : CRUD<Recipe>() {
    private val recipes = mutableListOf<Recipe>()
    private val categoryService: CategoryService = CategoryService()
    override fun create(item: Recipe): Boolean {
        this.recipes.add(item)
        return true
    }

    override fun read(index: Int): Recipe? {
        return this.recipes.find { r -> r.index == index }
    }

    override fun update(index: Int, item: Recipe): Boolean {
        val read = this.read(index)
        read?.items = item.items
        return true
    }

    override fun delete(index: Int): Boolean {
        TODO("Not yet implemented")
    }

    fun new(): MutableList<Category>{
        val categories = mutableListOf<Category>()
        do {
            val category = this.categoryService.selectOne()
            print(
                """
                Seleccionó la categoría ${category.name} con ${category.items.joinToString { "${it.name}: ${it.amount} ${it.unity}" }}
                Es correcto? (S/n): 
                """.trimIndent()
            )
            if (readLine()?.toLowerCase().equals("n")) {
                continue
            }
            categories.add(category)
            print("Desea agregar otros elementos a la receta? (S/n): ")
            if (readLine()?.toLowerCase().equals("n")){
                break
            }
        } while (true)
        return categories
    }

    override fun list(): List<Recipe> {
        return this.recipes
    }
}

import model.Category

class Config {
    fun getCategories(): List<Category> {
        val agua = Category("Agua", mutableListOf())
        val leche = Category("Leche", mutableListOf())
        val carne = Category("Carne", mutableListOf())
        val verduras = Category("Verduras", mutableListOf())
        val frutas = Category("Frutas", mutableListOf())
        val cereal = Category("Cereal", mutableListOf())
        val huevos = Category("Huevos", mutableListOf())
        val aceite = Category("Aceite", mutableListOf())
        return mutableListOf(agua, leche, carne, verduras, frutas, cereal, huevos, aceite)
    }
}

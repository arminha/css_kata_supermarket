package dojo.supermarket.model

import java.util.ArrayList

class ShoppingCart {

    private val items = mutableListOf<ProductQuantity>()

    fun getItems(): List<ProductQuantity> {
        return ArrayList(items)
    }

    fun addItem(product: Product) {
        this.addItemQuantity(product, 1.0)
    }

    fun addItemQuantity(product: Product, quantity: Double) {
        items.add(ProductQuantity(product, quantity))
    }

    fun productQuantities(): Map<Product, Double> {
        // TODO simplify!
        return items.groupBy { it.product }
            .map { entry -> entry.key to entry.value.sumOf { it.quantity } }
            .toMap()
    }
}

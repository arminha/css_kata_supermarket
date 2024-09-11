package dojo.supermarket.model

interface Offer {
    val product: Product
    fun discount(quantity: Double, unitPrice: Double): Discount?
}
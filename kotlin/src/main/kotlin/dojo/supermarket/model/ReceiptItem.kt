package dojo.supermarket.model

data class ReceiptItem(
    val product: Product,
    val quantity: Double,
    val price: Double
) {

    fun getTotalPrice() = quantity * price

}

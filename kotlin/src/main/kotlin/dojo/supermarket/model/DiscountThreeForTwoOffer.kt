package dojo.supermarket.model

class DiscountThreeForTwoOffer(override val product: Product) : Offer {

    override fun discount(quantity: Double, unitPrice: Double): Discount? {
        val x = 3
        return if (quantity.toInt() > 2) {
            val numberOfXs1 = quantity.toInt() / x
            val numberOfXs = numberOfXs1
            val discountAmount =
                quantity * unitPrice - (numberOfXs.toDouble() * 2.0 * unitPrice + quantity.toInt() % 3 * unitPrice)
            Discount(product, "3 for 2", discountAmount)
        } else
            null
    }

}

package dojo.supermarket.model

class DiscountFiveForAmountOffer(override val product: Product, private val argument: Double) : Offer {

    override fun discount(quantity: Double, unitPrice: Double): Discount? {
        val x = 5
        return if (quantity.toInt() >= 5) {
            val numberOfXs1 = quantity.toInt() / x
            val numberOfXs = numberOfXs1
            val discountTotal =
                unitPrice * quantity - (argument * numberOfXs + quantity.toInt() % 5 * unitPrice)
            Discount(product, "$x for $argument", discountTotal)
        } else null
    }

}

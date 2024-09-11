package dojo.supermarket.model

class DiscountTwoForAmountOffer(override val product: Product, val argument: Double) : Offer {

    override fun discount(quantity: Double, unitPrice: Double): Discount? {
        val x = 2
        return if (quantity.toInt() >= 2) {
            val total = argument * (quantity.toInt() / x) + quantity.toInt() % 2 * unitPrice
            val discountN = unitPrice * quantity - total
            Discount(product, "2 for " + argument, discountN)
        } else null
    }

}

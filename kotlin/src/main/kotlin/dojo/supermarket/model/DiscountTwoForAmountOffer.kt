package dojo.supermarket.model

class DiscountTwoForAmountOffer(override val product: Product, val argument: Double) : Offer {

    private val bundleAmount = 2

    override fun discount(quantity: Double, unitPrice: Double): Discount? {
        return if (quantity.toInt() >= bundleAmount) {
            val total = argument * (quantity.toInt() / bundleAmount) + quantity.toInt() % bundleAmount * unitPrice
            val discountN = unitPrice * quantity - total
            Discount(product, "$bundleAmount for " + argument, discountN)
        } else null
    }

}

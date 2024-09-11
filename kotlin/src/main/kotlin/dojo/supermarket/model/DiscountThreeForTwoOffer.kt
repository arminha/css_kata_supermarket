package dojo.supermarket.model

class DiscountThreeForTwoOffer(override val product: Product) : Offer {

    private val bundleAmount = 3

    override fun discount(quantity: Double, unitPrice: Double): Discount? {
        return if (quantity.toInt() >= bundleAmount) {
            val numberOfBundles = quantity.toInt() / bundleAmount
            val discountAmount =
                quantity * unitPrice - (numberOfBundles.toDouble() * 2.0 * unitPrice + quantity.toInt() % bundleAmount * unitPrice)
            Discount(product, "$bundleAmount for 2", discountAmount)
        } else
            null
    }

}

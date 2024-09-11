package dojo.supermarket.model

class DiscountFiveForAmountOffer(override val product: Product, private val argument: Double) : Offer {

    private val bundleAmount = 5

    override fun discount(quantity: Double, unitPrice: Double): Discount? {
        val quantityAsInt = quantity.toInt()
        return if (quantityAsInt >= bundleAmount) {
            val numberOfBundles = quantityAsInt / bundleAmount
            val discountTotal =
                unitPrice * quantity - (argument * numberOfBundles + quantityAsInt % bundleAmount * unitPrice)
            Discount(product, "$bundleAmount for $argument", discountTotal)
        } else null
    }

}

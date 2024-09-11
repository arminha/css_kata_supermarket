package dojo.supermarket.model

class DiscountPercentOffer(override val product: Product, private val argument: Double) : Offer {

    override fun discount(quantity: Double, unitPrice: Double): Discount {
        return Discount(
            product,
            "$argument% off",
            quantity * unitPrice * argument / 100.0
        )
    }

}

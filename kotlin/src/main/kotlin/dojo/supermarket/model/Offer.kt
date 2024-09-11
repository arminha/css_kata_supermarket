package dojo.supermarket.model

data class Offer(
    val offerType: SpecialOfferType,
    val product: Product,
    val argument: Double
) {
    fun discount(quantity: Double, unitPrice: Double): Discount? {
        val discount = when (offerType) {
            SpecialOfferType.ThreeForTwo -> discountThreeForTwo(quantity, unitPrice)
            SpecialOfferType.TwoForAmount -> discountTwoForAmount(quantity, unitPrice)
            SpecialOfferType.FiveForAmount -> discountFiveForAmount(quantity, unitPrice)
            SpecialOfferType.TenPercentDiscount -> discountTenPercent(quantity, unitPrice)
        }
        return discount
    }

    private fun discountTenPercent(
        quantity: Double,
        unitPrice: Double
    ) = Discount(
        product,
        "$argument% off",
        quantity * unitPrice * argument / 100.0
    )

    private fun discountFiveForAmount(
        quantity: Double,
        unitPrice: Double
    ): Discount? {
        val x = 5
        return if (quantity.toInt() >= 5) {
            val numberOfXs = getNumberOfXs(quantity.toInt(), x)
            val discountTotal =
                unitPrice * quantity - (argument * numberOfXs + quantity.toInt() % 5 * unitPrice)
            Discount(product, "$x for $argument", discountTotal)
        } else null
    }

    private fun discountTwoForAmount(
        quantity: Double,
        unitPrice: Double
    ): Discount? {
        val x = 2
        return if (quantity.toInt() >= 2) {
            val total = argument * (quantity.toInt() / x) + quantity.toInt() % 2 * unitPrice
            val discountN = unitPrice * quantity - total
            Discount(product, "2 for " + argument, discountN)
        } else null
    }

    private fun discountThreeForTwo(
        quantity: Double,
        unitPrice: Double
    ): Discount? {
        val x = 3
        return if (quantity.toInt() > 2) {
            val numberOfXs = getNumberOfXs(quantity.toInt(), x)
            val discountAmount =
                quantity * unitPrice - (numberOfXs.toDouble() * 2.0 * unitPrice + quantity.toInt() % 3 * unitPrice)
            Discount(product, "3 for 2", discountAmount)
        } else
            null
    }

    private fun getNumberOfXs(quantityAsInt: Int, x: Int): Int {
        val numberOfXs = quantityAsInt / x
        return numberOfXs
    }
    
}

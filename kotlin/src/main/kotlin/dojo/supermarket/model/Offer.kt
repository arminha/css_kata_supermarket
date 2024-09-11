package dojo.supermarket.model

data class Offer(
    val offerType: SpecialOfferType,
    val product: Product,
    val argument: Double
)

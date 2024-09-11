package dojo.supermarket.model

import java.util.ArrayList

class ShoppingCart {

    private val items = mutableListOf<ProductQuantity>()

    fun getItems(): List<ProductQuantity> {
        return ArrayList(items)
    }

    fun addItem(product: Product) {
        this.addItemQuantity(product, 1.0)
    }

    fun addItemQuantity(product: Product, quantity: Double) {
        items.add(ProductQuantity(product, quantity))
    }

    fun handleOffers(offers: Map<Product, Offer>, catalog: SupermarketCatalog) : List<Discount> {
        val discounts : MutableList<Discount> = mutableListOf()
        for (productQuantity in productQuantities()) {
            val product = productQuantity.key
            val quantity = productQuantity.value
            if (offers.containsKey(product)) {
                val offer = offers[product]!!
                val unitPrice = catalog.getUnitPrice(product)
                val quantityAsInt = quantity.toInt()
                var discount: Discount? = null
                var x = 1
                if (offer.offerType === SpecialOfferType.ThreeForTwo) {
                    x = 3

                } else if (offer.offerType === SpecialOfferType.TwoForAmount) {
                    x = 2
                    if (quantityAsInt >= 2) {
                        val total = offer.argument * (quantityAsInt / x) + quantityAsInt % 2 * unitPrice
                        val discountN = unitPrice * quantity - total
                        discount = Discount(product, "2 for " + offer.argument, discountN)
                    }

                }
                if (offer.offerType === SpecialOfferType.FiveForAmount) {
                    x = 5
                }
                val numberOfXs = quantityAsInt / x
                if (offer.offerType === SpecialOfferType.ThreeForTwo && quantityAsInt > 2) {
                    val discountAmount =
                        quantity * unitPrice - (numberOfXs.toDouble() * 2.0 * unitPrice + quantityAsInt % 3 * unitPrice)
                    discount = Discount(product, "3 for 2", discountAmount)
                }
                if (offer.offerType === SpecialOfferType.TenPercentDiscount) {
                    discount =
                        Discount(product, offer.argument.toString() + "% off", quantity * unitPrice * offer.argument / 100.0)
                }
                if (offer.offerType === SpecialOfferType.FiveForAmount && quantityAsInt >= 5) {
                    val discountTotal =
                        unitPrice * quantity - (offer.argument * numberOfXs + quantityAsInt % 5 * unitPrice)
                    discount = Discount(product, x.toString() + " for " + offer.argument, discountTotal)
                }
                if (discount != null)
                    discounts.add(discount)
            }
        }
        return discounts
    }

    private fun productQuantities(): Map<Product, Double> {
        // TODO simplify!
        return items.groupBy { it.product }
            .map { entry -> entry.key to entry.value.sumOf { it.quantity } }
            .toMap()
    }
}

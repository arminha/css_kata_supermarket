package dojo.supermarket.model

import java.util.HashMap


class Teller(private val catalog: SupermarketCatalog) {
    private val offers = HashMap<Product, Offer>()

    fun addSpecialOffer(offerType: SpecialOfferType, product: Product, argument: Double) {
        this.offers[product] = Offer(offerType, product, argument)
    }

    fun checksOutArticlesFrom(shoppingCart: ShoppingCart): Receipt {
        val productQuantities = shoppingCart.getItems()
            .map {
                val unitPrice = this.catalog.getUnitPrice(it.product)
                ReceiptItem(it.product, it.quantity, unitPrice)
            }
        val discounts = handleOfferShoppingCart(shoppingCart = shoppingCart)
        return Receipt(receiptItemList = productQuantities, discountList = discounts)
    }

    private fun handleOfferShoppingCart(shoppingCart: ShoppingCart): List<Discount> {
        return shoppingCart.productQuantities()
            .map {
                handleOfferProduct(it.key, it.value)
            }.filterNotNull()
    }

    private fun handleOfferProduct(
        product: Product,
        quantity: Double
    ): Discount? {
        val offer = offers[product] ?: return null
        val unitPrice = catalog.getUnitPrice(product)
        return discount(quantity, offer, unitPrice, product)
    }

    private fun discount(
        quantity: Double,
        offer: Offer,
        unitPrice: Double,
        product: Product
    ): Discount? {
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
        return discount
    }


}

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
        return offer.discount(quantity, unitPrice)
    }

}

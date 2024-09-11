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
        val discounts = shoppingCart.handleOffers(this.offers, this.catalog)
        return Receipt(receiptItemList = productQuantities, discountList = discounts)
    }

}

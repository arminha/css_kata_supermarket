package dojo.supermarket.model

import java.util.HashMap


class Teller(private val catalog: SupermarketCatalog) {
    private val offers = HashMap<Product, Offer>()

    fun addSpecialOffer(offerType: SpecialOfferType, product: Product, argument: Double) {
        this.offers[product] = Offer(offerType, product, argument)
    }

    fun checksOutArticlesFrom(theCart: ShoppingCart): Receipt {
        val receipt = Receipt()
        val productQuantities = theCart.getItems()
        for (productQuantity in productQuantities) {
            val product = productQuantity.product
            val quantity = productQuantity.quantity
            val unitPrice = this.catalog.getUnitPrice(product)
            receipt.addReceiptItem(ReceiptItem(product, quantity, unitPrice))
        }
        val discounts = theCart.handleOffers(this.offers, this.catalog)
        receipt.addDiscounts(discounts)

        return receipt
    }

}

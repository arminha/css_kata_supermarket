package dojo.supermarket.model

import dojo.supermarket.ReceiptPrinter
import org.approvaltests.Approvals
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test

class SupermarketApprovalTest {

    val cart = ShoppingCart()
    val teller = Teller(catalog)

    @Test
    fun `GIVEN apples in cart without special offer WHEN checkout THEN receipt without discount`() {
        cart.addItemQuantity(apples, 2.5)

        val receipt = teller.checksOutArticlesFrom(cart)

        Approvals.verify(ReceiptPrinter().printReceipt(receipt))
    }

    @Test
    fun `GIVEN apples in cart with 10% discount WHEN checkout THEN receipt with discount`() {
        cart.addItemQuantity(apples, 2.5)
        teller.addSpecialOffer(SpecialOfferType.TenPercentDiscount, apples, 10.0)

        val receipt = teller.checksOutArticlesFrom(cart)

        Approvals.verify(ReceiptPrinter().printReceipt(receipt))
    }

    companion object {

        private const val TOOTHBRUSH_PRICE = 0.99

        private const val APPLE_PRICE = 1.99

        val catalog = FakeCatalog()
        val toothbrush = Product("toothbrush", ProductUnit.Each)
        val apples = Product("apples", ProductUnit.Kilo)

        @JvmStatic
        @BeforeAll
        fun setup() {
            catalog.addProduct(toothbrush, TOOTHBRUSH_PRICE)
            catalog.addProduct(apples, APPLE_PRICE)
        }
    }
}

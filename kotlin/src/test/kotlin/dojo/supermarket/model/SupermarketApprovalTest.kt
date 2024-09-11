package dojo.supermarket.model

import dojo.supermarket.ReceiptPrinter
import org.approvaltests.Approvals
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Nested
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
    fun `GIVEN add toothbrush twice to cart WHEN checkout THEN receipt with two items`() {
        cart.addItem(toothbrush)
        cart.addItem(toothbrush)

        val receipt = teller.checksOutArticlesFrom(cart)

        Approvals.verify(ReceiptPrinter().printReceipt(receipt))
    }

    @Nested
    inner class SpecialOffers {

        @Test
        fun `GIVEN apples in cart with 10% discount WHEN checkout THEN receipt with discount`() {
            cart.addItemQuantity(apples, 2.5)
            teller.addSpecialOffer(SpecialOfferType.TenPercentDiscount, apples, 10.0)

            val receipt = teller.checksOutArticlesFrom(cart)

            Approvals.verify(ReceiptPrinter().printReceipt(receipt))
        }

        @Test
        fun `GIVEN 15 toothbrush in cart with 3 for 2 discount WHEN checkout THEN receipt with discount`() {
            cart.addItemQuantity(toothbrush, 15.0)
            teller.addSpecialOffer(SpecialOfferType.ThreeForTwo, toothbrush, 0.0)

            val receipt = teller.checksOutArticlesFrom(cart)

            Approvals.verify(ReceiptPrinter().printReceipt(receipt))
        }

        @Test
        fun `GIVEN 2 toothbrush in cart with 3 for 2 discount WHEN checkout THEN receipt without discount`() {
            cart.addItemQuantity(toothbrush, 2.0)
            teller.addSpecialOffer(SpecialOfferType.ThreeForTwo, toothbrush, 0.0)

            val receipt = teller.checksOutArticlesFrom(cart)

            Approvals.verify(ReceiptPrinter().printReceipt(receipt))
        }

        @Test
        fun `GIVEN 5 toothbrush in cart with 2 for amount discount WHEN checkout THEN receipt with discount`() {
            cart.addItemQuantity(toothbrush, 5.0)
            teller.addSpecialOffer(SpecialOfferType.TwoForAmount, toothbrush, 1.49)

            val receipt = teller.checksOutArticlesFrom(cart)

            Approvals.verify(ReceiptPrinter().printReceipt(receipt))
        }

        @Test
        fun `GIVEN 1 toothbrush in cart with 2 for amount discount WHEN checkout THEN receipt without discount`() {
            cart.addItemQuantity(toothbrush, 1.0)
            teller.addSpecialOffer(SpecialOfferType.TwoForAmount, toothbrush, 1.49)

            val receipt = teller.checksOutArticlesFrom(cart)

            Approvals.verify(ReceiptPrinter().printReceipt(receipt))
        }

        @Test
        fun `GIVEN 11 toothbrush in cart with 2 for amount discount WHEN checkout THEN receipt with discount`() {
            cart.addItemQuantity(toothbrush, 11.0)
            teller.addSpecialOffer(SpecialOfferType.FiveForAmount, toothbrush, 4.1)

            val receipt = teller.checksOutArticlesFrom(cart)

            Approvals.verify(ReceiptPrinter().printReceipt(receipt))
        }

        @Test
        fun `GIVEN 4 toothbrush in cart with 5 for amount discount WHEN checkout THEN receipt without discount`() {
            cart.addItemQuantity(toothbrush, 4.0)
            teller.addSpecialOffer(SpecialOfferType.FiveForAmount, toothbrush, 4.1)

            val receipt = teller.checksOutArticlesFrom(cart)

            Approvals.verify(ReceiptPrinter().printReceipt(receipt))
        }

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

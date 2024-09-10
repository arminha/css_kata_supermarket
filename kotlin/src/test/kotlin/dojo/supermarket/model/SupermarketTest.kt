package dojo.supermarket.model

import dojo.supermarket.ReceiptPrinter
import org.approvaltests.Approvals
import org.junit.jupiter.api.Test

class SupermarketTest {

    @Test
    fun testSomething() {
        val catalog = FakeCatalog()
        val toothbrush = Product("toothbrush", ProductUnit.Each)
        catalog.addProduct(toothbrush, 0.99)
        val apples = Product("apples", ProductUnit.Kilo)
        catalog.addProduct(apples, 1.99)

        val cart =
            ShoppingCart()
        cart.addItemQuantity(apples, 2.5)

        val teller = Teller(catalog)
        teller.addSpecialOffer(SpecialOfferType.TenPercentDiscount, toothbrush, 10.0)

        val receipt = teller.checksOutArticlesFrom(cart)

        // Todo: complete this test
    }

    @Test
    fun an_empty_shopping_cart_should_cost_nothing() {
        val catalog: SupermarketCatalog = FakeCatalog()
        val teller = Teller(catalog)
        val cart = ShoppingCart()

        val receipt = teller.checksOutArticlesFrom(cart)

        Approvals.verify(ReceiptPrinter(40).printReceipt(receipt))
    }
}

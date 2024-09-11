package dojo.supermarket.model

import java.util.*

class Receipt {
    private val receiptItemList = ArrayList<ReceiptItem>()
    private val discountList = ArrayList<Discount>()

    val totalPrice: Double
        get() {
            var total = 0.0
            for (item in this.receiptItemList) {
                total += item.getTotalPrice()
            }
            for (discount in this.discountList) {
                total -= discount.discountAmount
            }
            return total
        }

    fun addReceiptItem(product: Product, quantity: Double, price: Double) {
        this.receiptItemList.add(ReceiptItem(product, quantity, price))
    }

    fun getItems(): List<ReceiptItem> {
        return ArrayList(this.receiptItemList)
    }

    fun addDiscount(discount: Discount) {
        this.discountList.add(discount)
    }

    fun getDiscounts(): List<Discount> {
        return discountList
    }
}

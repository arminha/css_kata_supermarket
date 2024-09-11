package dojo.supermarket.model

import java.util.*

class Receipt {
    private val receiptItemList = ArrayList<ReceiptItem>()
    private var discountList = emptyList<Discount>()

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

    fun addReceiptItem(receiptItem: ReceiptItem) {
        this.receiptItemList.add(receiptItem)
    }

    fun getItems(): List<ReceiptItem> {
        return ArrayList(this.receiptItemList)
    }

    fun getDiscounts(): List<Discount> {
        return discountList
    }

    fun addDiscounts(discounts: List<Discount>) {
        this.discountList = discounts
    }
}

package dojo.supermarket.model

data class Receipt(
    val receiptItemList: List<ReceiptItem>,
    val discountList: List<Discount>
) {

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

}

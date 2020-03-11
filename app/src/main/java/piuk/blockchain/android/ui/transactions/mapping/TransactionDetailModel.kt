package piuk.blockchain.android.ui.transactions.mapping

data class TransactionDetailModel(
    var address: String,
    val value: String = "",
    val displayUnits: String = "",
    var addressDecodeError: Boolean = false
)

data class TransactionInOutDetails(
    val inputs: List<TransactionDetailModel>,
    val outputs: List<TransactionDetailModel>
)
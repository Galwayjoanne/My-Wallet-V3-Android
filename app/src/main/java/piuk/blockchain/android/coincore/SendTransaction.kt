package piuk.blockchain.android.coincore

import info.blockchain.balance.Money
import io.reactivex.Completable
import io.reactivex.Single

open class TransferError(msg: String) : Exception(msg)

class SendValidationError(errorCode: Int) : TransferError("Invalid Send Tx: code $errorCode") {
    companion object {
        const val HAS_TX_IN_FLIGHT = 1000
        const val INVALID_AMOUNT = 1001
        const val INSUFFICIENT_FUNDS = 1002
        const val INSUFFICIENT_GAS = 1003
        const val INVALID_ADDRESS = 1004
    }
}

enum class FeeLevel {
    None,
    Regular,
    Priority,
    Custom
}

data class PendingSendTx(
    val amount: Money,
    val feeLevel: FeeLevel = FeeLevel.Regular,
    val notes: String = ""
)

interface SendProcessor {
    val sendingAccount: CryptoAccount
    val sendTarget: ReceiveAddress

    val feeOptions: Set<FeeLevel>
    val isNoteSupported: Boolean

    fun availableBalance(pendingTx: PendingSendTx): Single<Money>

    fun absoluteFee(pendingTx: PendingSendTx): Single<Money>

    // Check the tx is complete, well formed and possible. Complete if it is, throw an error if
    // it is not. Since the UI and Address objects should validate where possible, an error should
    // be the exception, rather than the expected case.
    fun validate(pendingTx: PendingSendTx): Completable

    // Execute the transaction.
    // Ideally, I'd like to return the Tx id/hash. But we get nothing back from the
    // custodial APIs (and are not likely to, since the tx is batched and not executed immediately)
    fun execute(pendingTx: PendingSendTx, secondPassword: String = ""): Completable
}

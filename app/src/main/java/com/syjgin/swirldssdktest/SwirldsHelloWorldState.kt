package com.syjgin.swirldssdktest

import android.util.Log
import com.swirlds.platform.*
import java.nio.charset.StandardCharsets
import java.time.Instant

/**
 * Created by user1 on 18.03.18.
 */
class SwirldsHelloWorldState : SwirldState {

    private val strings = mutableListOf<String>()
    private var addressBook = AddressBook()

    private fun log(what: String) {
        Log.d(javaClass.canonicalName, what)
    }

    override fun getAddressBookCopy(): AddressBook {
        log("getAddressBookCopy")
        return addressBook.copy()
    }

    override fun handleTransaction(p0: Long, p1: Boolean, p2: Instant?, p3: ByteArray?, p4: Address?) {
        if(p3 == null)
            return
        log("handleTransaction")
        strings.add(String(p3, StandardCharsets.UTF_8))
    }

    override fun copyTo(p0: FCDataOutputStream?) {
        try {
            log("copy to data output stream")
            Utilities.writeStringArray(p0, strings.toTypedArray())
        } catch (e : Throwable) {
            e.printStackTrace()
        }
    }

    override fun init(p0: Platform?, p1: AddressBook?) {
        if(p1 == null)
            return
        log("init")
        addressBook = p1
    }

    override fun copy(): FastCopyable {
        log("copying this state")
        val copy = SwirldsHelloWorldState()
        copy.copyFrom(this)
        return copy
    }

    override fun copyFrom(p0: SwirldState?) {
        log("copy from another state")
        val newState = p0 as SwirldsHelloWorldState
        strings.clear()
        strings.addAll(newState.strings)
    }

    override fun copyFrom(p0: FCDataInputStream?) {
        log("copy from data input stream")
        try {
            strings.clear()
            strings.addAll(Utilities.readStringArray(p0))
        } catch (e: Throwable) {
            e.printStackTrace()
        }
    }

    override fun noMoreTransactions() {

    }

    fun getReceived(): String {
        var result = ""
        for(element in strings) {
            result += element
        }
        return result
    }

    override fun toString(): String {
        return getReceived()
    }
}
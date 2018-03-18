package com.syjgin.swirldssdktest

import android.util.Log
import com.swirlds.platform.Console
import com.swirlds.platform.Platform
import com.swirlds.platform.SwirldMain
import com.swirlds.platform.SwirldState
import java.nio.charset.StandardCharsets
import java.util.concurrent.Executors

/**
 * Created by user1 on 18.03.18.
 */

class SwirldsNode : SwirldMain {
    var platform : Platform? = null
    var id : Long? = null
    var console : Console? = null
    val sleepPeriod = 100L

    override fun run() {
        val myName = platform?.state?.addressBookCopy?.getAddress(id!!)?.selfName
        console?.out?.println("hello swirld from $myName")
        val transaction = myName?.toByteArray(StandardCharsets.UTF_8)
        platform?.createTransaction(transaction)
        var lastReceived : String? = ""
        val executor = Executors.newSingleThreadExecutor()
        executor.execute({
            while (true) {
                val currentState = platform?.state as SwirldsHelloWorldState?
                val received = currentState?.getReceived()
                if(lastReceived != received) {
                    lastReceived = received
                    Log.d("NODE", "received: $received")
                }
                try {
                    Thread.sleep(sleepPeriod)
                } catch (e: Throwable) {}
            }
        })
    }

    override fun preEvent() {

    }

    override fun newState(): SwirldState {
        return SwirldsHelloWorldState()
    }

    override fun init(p0: Platform?, p1: Long) {
        platform = p0
        id = p1
        console = platform?.createConsole(true)
        platform?.about = "Hello swirlds 4 android"
        platform?.sleepAfterSync = sleepPeriod
    }
}
package com.syjgin.swirldssdktest

import android.os.Bundle
import android.support.v7.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //Browser.main(arrayOf())
        val node = SwirldsNode()
        node.run()
    }
}
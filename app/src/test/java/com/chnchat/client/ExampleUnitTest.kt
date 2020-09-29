package com.chnchat.client

import org.junit.Test

import org.junit.Assert.*
import kotlin.reflect.KProperty

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    val test by bind()

    @Test
    fun addition_isCorrect() {
        println(test)
    }
}

fun Any.bind() = Delegate()

class Delegate{
    operator fun getValue(thisRef: Any?, property: KProperty<*>): String {
        println("getValue")
        return "$thisRef, thank you for delegating '${property.name}' to me!"
    }

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: String) {
        println("setValue")
        println("$value has been assigned to '${property.name}' in $thisRef.")
    }
}
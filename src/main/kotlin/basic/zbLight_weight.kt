package basic

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

/**
 * It launches 100K coroutines and, after a second, each coroutine prints a dot. Now, try that with threads. What would happen? (Most likely your code will produce some sort of out-of-memory error)
 */

fun main() = runBlocking {
    repeat(100_000) { // launch a lot of coroutines
        launch {
            delay(1000L)
            print(".")
        }
    }
}
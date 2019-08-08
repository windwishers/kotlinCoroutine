package d_composeingSuspendingFunction

import kotlinx.coroutines.*
import kotlin.system.*


/**
 * Let us take Concurrent using async example and extract a function that concurrently performs doSomethingUsefulOne and doSomethingUsefulTwo and returns the sum of their results. Because async coroutines builder is defined as extension on CoroutineScope we need to have it in the scope and that is what coroutineScope function provides:
 *
 * This way, if something goes wrong inside the code of concurrentSum function and it throws an exception, all the coroutines that were launched in its scope are cancelled.
 * 이방법에서 concurrentSum 펑션의 내부에서 익셉션이 던져지면 스코프내의 모든 코루틴이 취소 됩니다.
 * 취소 되는 것은 하단의 주석된 메인에서 확인 가능합니다.
 */
fun main() = runBlocking<Unit> {
    //sampleStart
    val time = measureTimeMillis {
        println("The answer is ${concurrentSum()}")
    }
    println("Completed in $time ms")
//sampleEnd
}

suspend fun concurrentSum(): Int = coroutineScope {
    val one = async { doSomethingUsefulOne() }
    val two = async { doSomethingUsefulTwo() }
    one.await() + two.await()
}

//suspend fun doSomethingUsefulOne(): Int {
//    delay(1000L) // pretend we are doing something useful here
//    return 13
//}
//
//suspend fun doSomethingUsefulTwo(): Int {
//    delay(1000L) // pretend we are doing something useful here, too
//    return 29
//}


//fun main() = runBlocking<Unit> {
//    try {
//        failedConcurrentSum()
//    } catch(e: ArithmeticException) {
//        println("Computation failed with ArithmeticException")
//    }
//}
//
//suspend fun failedConcurrentSum(): Int = coroutineScope {
//    val one = async<Int> {
//        try {
//            delay(Long.MAX_VALUE) // Emulates very long computation
//            42
//        } finally {
//            println("First child was cancelled")
//        }
//    }
//    val two = async<Int> {
//        println("Second child throws an exception")
//        throw ArithmeticException()
//    }
//    one.await() + two.await()
//}
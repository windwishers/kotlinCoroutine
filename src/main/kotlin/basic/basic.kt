package basic

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


fun firstCoroutine(){

    // 글로벌 스코프 안에서만 제한 됨.
    GlobalScope.launch {
        kotlinx.coroutines.delay(1000L)
        println("world")
    }
    println("Hello,")
    Thread.sleep(2000L)
}

//
fun bridgingAndBlockingMixed(){
    GlobalScope.launch { // launch a new coroutine in background and continue
        delay(1000L)
        println("World!")
    }
    println("Hello,") // main thread continues here immediately
    runBlocking {     // but this expression blocks the main thread
        delay(2000L)  // ... while we delay for 2 seconds to keep JVM alive
    }
}

fun bridgingAndBlockingFullWrapp() = runBlocking<Unit>{
    GlobalScope.launch {
        delay(1000L)
        println("World!")
    }
    println("Hello,")
    delay(2000L)
}

fun waitingJob() = runBlocking {
    val job = GlobalScope.launch {
        kotlinx.coroutines.delay(1000L)
        println("world")
    }
    println("hello")
    job.join()  // 차일드 코루틴이 끝날때 까지 기다린다.
}


fun main() {
//    firstCoroutine()
//    bridgingAndBlockingMixed()
//    bridgingAndBlockingFullWrapp()
    waitingJob()
}
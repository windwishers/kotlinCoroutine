package cancellation

import kotlinx.coroutines.*

/**
 * 취소가 가능한 서스팬딩 함수는 일반적인 방법으로 제어 가능한 취소 시에 CancellationException 을 발생 시킵니다.
 * 예를 들어 try {} finally{} 과 Kotlin use 함수는 코틀린이 취소 될때 정상적으로 파이널라이제이션이 동작합니다.
 */
fun main() = runBlocking {
    //sampleStart
    val job = launch {
        try {
            repeat(1000) { i ->
                println("job: I'm sleeping $i ...")
                delay(500L)
            }
        } finally {
            println("job: I'm running finally")
        }
    }
    delay(1300L) // delay a bit
    println("main: I'm tired of waiting!")
    job.cancelAndJoin() // cancels the job and waits for its completion
    println("main: Now I can quit.")
//sampleEnd
}
/**
 * usb 는
 * inline fun <T : Closeable?, R> T.use(block: (T) -> R): R (source)
 * 의 형태로 되어 있고.  따라서 상기한 인터럽트에 사용하기 보다는. closeable 에 사용되어야 합니다.
 *  closeable.run{closeable -> ... } 의형태로 사용하며. 내부적으로 블록내에서 익셉션 발생시 T의 클로즈를 자동으로 호출 합니다.
 */
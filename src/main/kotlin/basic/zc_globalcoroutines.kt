package basic
import kotlinx.coroutines.*

// Global coroutines are like daemon threads
/** 해당 코드는 아주 긴 시간 동안 실행 되는 GlobalScope 입니다.
 * 하지만 실제 실행 시켜 보면 2번정도만 실행 되고 종료 됩니다.
 * 이것은 글로벌 스코프가 데몬스레드처럼 프로세스가 종료되지 않도록 만들지 않기 때문입니다.
 * */
fun main() = runBlocking {
    //sampleStart
    GlobalScope.launch {
        repeat(1000) { i ->
            println("I'm sleeping $i ...")
            delay(500L)
        }
    }
    delay(1300L) // just quit after delay
//sampleEnd
}
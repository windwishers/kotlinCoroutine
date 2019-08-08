package basic

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
//  빌더가 제공 하는 코루틴 범위 외에 coroutineScope 빌더를 사용하여 고유한 범위를 선언할 수 있습니다.
// coroutineScope를 사용 하여 작성 시 모든 하위 항목이 완료 될 때 까지 완료 되지 않 습니다.
// runBlocking 과 coroutineScope 의 주요한 차이점은 모든 하위 항목이 완료 되기를 기다리는 동안 coroutineScope 는 현제 쓰레드를 차단하지 않는다는 것 입니다.
fun main() = runBlocking {
    launch {
        delay(200L)
        println("Task from runBlocking")
    }

    coroutineScope() {
        launch {
            delay(500L)
            println("Task from nested launch")
        }

        delay(100L)
        println("Take from coroutine scope")
    }
    println("Take from coroutine scope is over")
}
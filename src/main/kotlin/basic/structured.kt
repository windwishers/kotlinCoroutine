package basic

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

// structured concurrency
// (런블록킹을 포함한)모든 코루틴 빌더는 범위내의 모든 블록에 코루틴스코프를 더합니다.
// 코드 블럭 범위에서 실행 한 모든 블록이 종료 될때 까지 외부 코루틴이 완료 되지 않기 때문에
// 명시적으로 join 을 사용하지 않고도 외부가 살아 있음.
fun main() = runBlocking {
    launch {
        kotlinx.coroutines.delay(1000L)
        println("world")
    }
    println("Hello")
}
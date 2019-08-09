package eCoroutineContextAndDispatchers

/**
 * unConfined vs confined dispatcher  미확정 vs 확정 디스패처.
 *
 *  Dispatchers.Unconfined 코루틴 디스페처는 호출 한 쓰레드에서 시작 합니다.
 *  단 첫번째 서스팬딩 포인트 까지만 실행 됩니다.
 *  서스팬딩에서 돌아 올때는  실행 된 서스팬딩 함수에 의해서 환전히 판별 된 쓰레드에서 코루틴을 실행 합니다.
 *  # 시작 쓰레드 -> 서프팬딩 - 리줌 -> 다른  쓰레드. (즉 시작한 쓰레드에서 다시 시작 하도록 제한 되지 않음.)
 *  확정 되지 않은 디스페처는 시피유 시간을 소모하지 않거나 특정 쓰레드에 국한한 공유데이터(예:UI) 를 업데이트 하지 않는 코루틴에 적합 합니다. (# 뭐가 있지?)
 *  # 하여간 특정 쓰레드에서 실행 되어야하는 코드에 적합 하지 않음.
 *
 *  다른 경우에,  디스페처는 기본적으로 바깥 코루틴스코프를 상속 받습니다. runBlocking을 위한 기본 디스패처는 , 특히, 실행 한 쓰레드에 갇혀-제한 되어 있음으로 이를 상속 하면  해당  쓰레드의 실행이 FIFO  스케줄링 될 것은 예상 할 수 있습니다 ?
 *
 *  하단 예제에서.
 *  runBlocking 을 상속 한 컨텍스트에서 실행 된 코루틴은 , 메인쓰레드에서 실행 되며,
 *  미확정 코루틴은 delay 함수가 사용하는 기본 디폴트익스큐터에서 resume  되었습니다.
 *
 *  # 뭔말인지 모르겠음.
 *  The unconfined dispatcher is an advanced mechanism that can be helpful in certain corner cases where dispatching of a coroutine for its execution later is not needed or produces undesirable side-effects, because some operation in a coroutine must be performed right away. The unconfined dispatcher should not be used in general code.
 *
 *
 */

import kotlinx.coroutines.*

fun main() = runBlocking<Unit> {
    //sampleStart
    launch(Dispatchers.Unconfined) { // not confined -- will work with main thread
        println("Unconfined      : I'm working in thread ${Thread.currentThread().name}")
        delay(500)
        println("Unconfined      : After delay in thread ${Thread.currentThread().name}")
    }
    launch { // context of the parent, main runBlocking coroutine
        println("main runBlocking: I'm working in thread ${Thread.currentThread().name}")
        delay(1000)
        println("main runBlocking: After delay in thread ${Thread.currentThread().name}")
    }
//sampleEnd
}
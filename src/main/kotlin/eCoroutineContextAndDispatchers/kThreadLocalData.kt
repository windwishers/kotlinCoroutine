package eCoroutineContextAndDispatchers

/**
 *  Thread Local Data
 *
 * 때때로 코루틴 간에 로컬 데이타를 전달 하는 기능이 있는 편이 편리 합니다.
 * 그러나 이러한 기능을 직접 구현 한다면 특정 쓰레드에 구속 되지 않는 코루틴에서는 많은 보일러플레이트가 필요하게 됩니다.
 * ThreadLocal 을 위하여 asContextElement 확장 함수가 여기에 있습니다.
 * # 여기서 ThreadLocal 은 코틀린의 기능은 아니고. java.lang.ThreadLocal<T> 입니다.
 * # 이것은 쓸레드 범위의 로컬 변수를 지원하기 위한 객체입니다.
 * 추가적인 컨텍스트의 구성요소로서 ThreadLocal 의 값을 유지하고. 코루틴이 그 컨텍스트를 바꿀 때 마다 그 값을 복원 합니다.*
 *
*/

import kotlinx.coroutines.*

val threadLocal = ThreadLocal<String?>() // declare thread-local variable

fun main() = runBlocking<Unit> {
    //sampleStart
    threadLocal.set("main")
    println("Pre-main, current thread: ${Thread.currentThread()}, thread local value: '${threadLocal.get()}'")
    val job = launch(Dispatchers.Default + threadLocal.asContextElement(value = "launch")) {
        println("Launch start, current thread: ${Thread.currentThread()}, thread local value: '${threadLocal.get()}'")
        yield()
        println("After yield, current thread: ${Thread.currentThread()}, thread local value: '${threadLocal.get()}'")
    }
    job.join()
    println("Post-main, current thread: ${Thread.currentThread()}, thread local value: '${threadLocal.get()}'")
//sampleEnd
}

/**
 * 이 예제에서 Dispatchers.Default 를 이용하여 백그라운드  쓰레드 풀에서 새 코루틴을 만들 었습니다.
 * 그래서 쓰레드 풀로 부터 다른 쓰레드에서 동작 합니다.
 * 그러나 코루틴이 실행 되는 쓰레드와 관계없이  쓰레드 로컬 밸류는  threadLocal.asContextElement(value = "launch") 에서 지정 한 특정 값으로 유지 됩니다.
 *
 *  해당 하는 콘텍스트 요소를 설정하는 것은 잊어버리기 쉽습니다.
 *  만약 코루틴을 실행하는 쓰레드가 다른 경우 코루틴에서 접근하는 쓰레드 로컬 변수는 예기치 않은 변수를 가질 수 있습니다.
 *  이러한 상황을 피하려면,
 *  ensurePresent 를 사용하고 부적절한 사용에 대하여서는 fail-fast 하여야 합니다.
 *
 * ensurePresent : https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/java.lang.-thread-local/ensure-present.html
 *     - ThreadLocal<*>.ensurePresent 쓰레드로컬이 코루틴 컨텍스트안에 있는지 체크하는 함수 입니다.
 *       확장함수로 ThreadLocal<T>.getSafely() : T 가 있습니다.
 *     -  fail fast : 실패를 누락 하거나 장애의 복구를 시도하거나. 실패를 알리지 않는 것이 아니라. 모든 실패를 알리고 잘못된 어거먼트는 빠르고 신속하게 실패처리 하는 것.
 *
 * ThreadLocal 은 퍼스트클래스를 지원하며 Kotlinx.coroutines 가 제공하는 모든 프리미티브 또 한 사용 할 수 있습니다.
 * 하지만 하나의 주용한 제한 사항이 있습니다.
 * 스레드로컬이 변경 되었을 떄, 코루틴 호출자에게 전파되지 않습니다.
 * ( 컨텍스트 엘리먼트가 모든 쓰레드 로컬 오부젝트이 접근을 트레킹 하지 않기 때문입니다. )
 * 따라서 다름 서스팬딩시에 업데이트 된 값이 손실 됩니다.
 * withContext 를 사용하면 코루틴에서 쓰레드 로컬변수를 업데이트 할 수 있습니다.
 * 자세한 사항은 asContextElement 를 참고하세요.
 *
 * 대안으로는 Counter(var i) 같은 수정 가능한 박싱 클래스를 이용하여 쓰레드 로컬변수에 저장하는 방법이 있습니다.
 * 하지만 이 경우에는 박싱 클래스의 모든 동시 수정을  Synchronize-동기호 하여야 합니다.
 *
 * For advanced usage, for example for integration with logging MDC, transactional contexts or any other libraries which internally use thread-locals for passing data, see documentation of the ThreadContextElement interface that should be implemented.
 *
 *
*/

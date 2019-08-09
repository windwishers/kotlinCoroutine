package eCoroutineContextAndDispatchers

/**
 *
 * * 코루틴은 항상 코틀린 라이브러리에 정의 된 CoroutineContext 타입의 context 위에서 실행 됩니다.
 *
 * 코루틴 컨텍스트는 다양한 요소의 집합입니다. (코루틴의) Job과 이번 장에서 다루는 Dispatcher 가 주요 요소 입니다.
 *
 *
 * Dispatchers adn threads
 *
 * 코루틴 컨텍스트는  코루틴을 실행 하는데 사용하는 쓰레드를 결정하는  coroutine dispatcher를 포함하고 있습니다.
 * 코루틴 디스페처는 코루틴 실행을 특정 쓰레드에서 하거나. 스레드풀에서 디스페치 하거나. 제한을 하지 않은 상태로 실행 할 수 있습니다.
 *
 * Launch 나 async 같은 모든 코루틴 빌더는 새로운 코루틴이나 콘텍스트 요소들을 위한 디스페처를 명시적으로 지정하는 파라미터를 받을 수있습니다.
 * 이파일의 코드 처럼요.
 *
 * 파라미터 없이 실행 된 launch 는  코루틴스코프로 부터 컨텍스트를 상속 받습니다. (하단의 코드에서는 main을 받습니다)
 * Dispatchers.Unconfined 는 main thread  에서 실행 되는 것 처럼 보이는 특별 한 디스페처 입니다. 하지만 사실  다른 파일에서 다룰 조금 다른 메커니즘을 가지고 있습니다.
 *
 * GlobalScope 에서 코루틴이 실행 될때 기본 디스페처는 Dispatchers.Default 에서 사용 되는 백그라운드 공유 쓰레드 풀을 사용합니다. 그래서 launch(Dispatchers.Default) {..} 와  GlobalScope.Launch{..} 는 같은 디스페처를 사용합니다.
 *
 * newSingleThreadContext 는 코루틴을 실행 할 쓰레드를 새로 생성 합니다. 전용쓰레드는 메우 비싼 리소스 입니다. 실제 어플리케이션에서는  긴시간 더이상 사용 되지 않는 경우 클로즈기능을 이용하여 릴리즈-해제 되어야 합니다. 혹은 최 상위 변수에 저장하여 어플리케이션 전체에 걸쳐서 재활용 되어야 합니다.
 */
import kotlinx.coroutines.*

fun main() = runBlocking<Unit> {
    //sampleStart
    launch { // context of the parent, main runBlocking coroutine
        println("main runBlocking      : I'm working in thread ${Thread.currentThread().name}")
    }
    launch(Dispatchers.Unconfined) { // not confined -- will work with main thread
        println("Unconfined            : I'm working in thread ${Thread.currentThread().name}")
    }
    launch(Dispatchers.Default) { // will get dispatched to DefaultDispatcher
        println("Default               : I'm working in thread ${Thread.currentThread().name}")
    }
    launch(newSingleThreadContext("MyOwnThread")) { // will get its own new thread
        println("newSingleThreadContext: I'm working in thread ${Thread.currentThread().name}")
    }
//sampleEnd
}
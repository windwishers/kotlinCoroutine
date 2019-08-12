package fExceptionHandeling

/**
 * Exception handling
 *
 * 이 섹션에서는 예외 처리 및 예외 취소에 대해 설명 합ㄴ디ㅏ.
 * 우리는 취소 된 코틀린이 서스팬딩 포인트 안에서 CancellationException 을 던진다는 것을 이미 알고 있습니다.
 * 그리고 그러한 익셉션이 코루틴 메카니즘 안에서 무시 된 다는 것도 알고 있습니다.
 * 그러나 취소 중 예외가 발생하면 어떤 일이 일어 날까요? 혹은 한 코루틴의 여러 자식 코루틴이 익셉션을 발생 시킨다면 어떻게 될까요?
 *
 * 예외 전파.
 *  코루틴 빌더는 두가지 플레이버가 있습니다.
 *  예외를 자동으로 전파(launch 와  actor)하거나 사용자에게 노출 하여 사용자에게 처리를 일임하는(async and produce) 것입니다.
 *  전자는 자바의  Thread.uncaughtException 과 같이 에러를 핸들링 하지 않는 것으로 간주 합니다.
 *  후자는 final exception에 의해서 유저가 최종적으로 소비해야 합니다. (?)
 *  예를 들어 awat 나  receive 같습니다. (produce 와  receive 는 후에 Channels 섹션에서 다룹니다)
 *
 *
 */

import kotlinx.coroutines.*

fun main() = runBlocking {
    val job = GlobalScope.launch {
        println("Throwing exception from launch")
        throw IndexOutOfBoundsException() // Will be printed to the console by Thread.defaultUncaughtExceptionHandler
    }
    job.join()
    println("Joined failed job")
    val deferred = GlobalScope.async {
        println("Throwing exception from async")
        throw ArithmeticException() // Nothing is printed, relying on user to call await
    }
    try {
        deferred.await()
        println("Unreached")
    } catch (e: ArithmeticException) {
        println("Caught ArithmeticException")
    }
}

/*
Throwing exception from launch
Exception in thread "DefaultDispatcher-worker-2" java.lang.IndexOutOfBoundsException
	at fExceptionHandeling.AExceptionPropagationKt$main$1$job$1.invokeSuspend(aExceptionPropagation.kt:26)
	at kotlin.coroutines.jvm.internal.BaseContinuationImpl.resumeWith(ContinuationImpl.kt:33)
	at kotlinx.coroutines.DispatchedTask.run(Dispatched.kt:241)
	at kotlinx.coroutines.scheduling.CoroutineScheduler.runSafely(CoroutineScheduler.kt:594)
	at kotlinx.coroutines.scheduling.CoroutineScheduler.access$runSafely(CoroutineScheduler.kt:60)
	at kotlinx.coroutines.scheduling.CoroutineScheduler$Worker.run(CoroutineScheduler.kt:740)
Joined failed job
Throwing exception from async
Caught ArithmeticException

Process finished with exit code 0

으로 나옴 문서상으로는 글로벌 익셉션에서 처리 된다고 했는데 그냥 익셉션이 터져버림.
다만 그 이후 상위 에러가 터지진 않고 이후 처리가 이어짐. <- 글로벌 스코프이기 때문에 상위로 전파 되지 않음.

 */
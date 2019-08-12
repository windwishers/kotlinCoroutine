package fExceptionHandeling

/**
 * Supervision
 *
 * 이전에 배운바와 같이 취소는 코루틴 상속에 따라 양방향으로 전파 됩니다.
 * 하지만 단방향 취소가 필요 할때 어떻게 해야 할까요?
 *
 * 이러한 요구사항의 좋은 예는 해당 범위에 작업이 정의 된 UI 구성요소 입니다.
 * 만약 어떤 UI 자식 테스크가 실패 한 경우 전체 UI 컴포넌트를 취소 할 필요는 없습니다.
 * 그러나 UI 컴포넌트가 삭제 되고 ( 잡이 취소되면) 모든 자식 잡의 결과가 필요 없음으로 모두 실패 해야합니다.
 *
 * 다른 예제는 서버 프로세스 입니다.  여러 자식 잡을 생성사고, 그들의 실행과 실패, 그리고 실패로 인한 재 실행을 트레킹할  supervise 가 가 필요합니다.
 *
 * Supervision Job
 *
 * 이러한 목적으로 SupervisorJob을 사용 할 수 있습니다. 슈퍼바이저잡은 익셉션이 아래로만 전파 된다는 점을 제외 하면 일반 Job 과 비슷합니다.
 */
import kotlinx.coroutines.*

fun main() = runBlocking {
    val supervisor = SupervisorJob()
    with(CoroutineScope(coroutineContext + supervisor)) {
        // launch the first child -- its exception is ignored for this example (don't do this in practice!)
        val firstChild = launch(CoroutineExceptionHandler { _, _ ->  }) {
            println("First child is failing")
            throw AssertionError("First child is cancelled")
        }
        // launch the second child
        val secondChild = launch {
            firstChild.join()
            // Cancellation of the first child is not propagated to the second child
            println("First child is cancelled: ${firstChild.isCancelled}, but second one is still active")
            try {
                delay(Long.MAX_VALUE)
            } finally {
                // But cancellation of the supervisor is propagated
                println("Second child is cancelled because supervisor is cancelled")
            }
        }
        // wait until the first child fails & completes
        firstChild.join()
        println("Cancelling supervisor")
        supervisor.cancel()
        secondChild.join()
    }
}
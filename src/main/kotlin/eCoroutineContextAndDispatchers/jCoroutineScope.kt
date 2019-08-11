package eCoroutineContextAndDispatchers

import kotlinx.coroutines.*

/**
 * Coroutine Scope  코루틴 스코프
 * 이제 컨텍스트와 차일드, 그리고 잡에 대한 지식을 모아 봅시다. 어플리케이션에 수명주기가 있지만 해당 객체가 코루틴이 아니라고 가정 하여 봅시다.
 * 예를 들어  애니메이션을 실행 하고, 데이타를 업데이트 하기 위한 작업을 비동기 적으로 수행 하는등의 작업을 하는 다양한 코루틴을 시작 한다고 하였을 때.
 * 메모리 릭을 피하기 위해서는 엑티비티가 디스트로이 되었을 때 모든 코루틴이 취소 되어야 합니다.
 * 물론 우리는 코루틴 컨텍스트와 잡을 그 라이프사이클과 묶기 위한 작업을 직접 조작 할 수 있습니다.
 * 하지만  kotlin.coroutine 은 추상 캡슐화를 제공 합니다. : CoroutineScope .
 * 모든 코루틴 빌더 들은 코루틴 스포크의 확장으로 선언 되어야 함으로 우리는 이미 코루틴 스코프를 잘 알고 있습니다.
 *
 * 우리는 우리는 엑티비티의 라이프 사이클에 연결된 CoroutineScope의 인스턴스에 의해서 만들어진 코루틴을 이용하여 라이프 사이클을 관리합니다.
 *
 * CoroutineScope 인스턴스는 CoroutineScope() 나 MainScope() 팩토리 함수로 만들 수 있습니다.
 * 전자는 일반적인 범위의 생성에, 후자는  UI 어플리케이션용 이고.  Dispatchers.Main 의 기본 디스패처로 사용 됩니다.
 *
 * */

class Activity {
    private val mainScope = MainScope()

    fun destroy() {
        mainScope.cancel()
    }
}
// to be continued ...
/**
 * 다른방법으로는 우리의 엑티비티가 CoroutineScope Interface를 구현 하게 할 수 도 있습니다.
 * 사장 좋은 방법은 기본 펙토리 메소드와 함께 delegation- 위임을 사용 하는 것 입니다.
 * 또한 원하는 디스패처를 스코프와 결합 할 수 잇습니다, 아래 코드에서는 Dispatchers.Default 를 사용합니다.
 *
 * 이제 우리는 명시적으로 컨텍스트를 사용하지 않고도  이 엑티비티에서 코루틴을 시작 할 수 있습니다. 예제에서는 각기 다른 시간 만큼 지연 되는 10개의 코루틴을 시작합니다.
 */

class Activity2 : CoroutineScope by CoroutineScope(Dispatchers.Default) {

    fun destroy() {
        cancel() // Extension on CoroutineScope
    }

    fun doSomething() {
        // launch ten coroutines for a demo, each working for a different time
        repeat(10) { i ->
            launch {
                delay((i + 1) * 200L) // variable delay 200ms, 400ms, ... etc
                println("Coroutine $i is done")
            }
        }
    }

}
/**
 *  메인 함수 안에서 우리는 엑티비티를 생성 하고, doSomeThing  함수를 호출 하고, 500ms 후에 엑티비티를 파괴합니다. 이것은  doSomething 에서 에작한 모든 코루틴을 취소 합니다. 엑티비티가 파괴 된 이후에 조금 더 기다리더라도 더이상 메시지가 발생 하지 않기 때문에 이러한 사실을 볼 수 있습니다.
*/

fun main() = runBlocking<Unit> {
    //sampleStart
    val activity2 = Activity2()
    activity2.doSomething() // run test function
    println("Launched coroutines")
    delay(500L) // delay for half a second
    println("Destroying activity!")
    activity2.destroy() // cancels all coroutines
    delay(1000) // visually confirm that they don't work
//sampleEnd
}
/**
 * 처음 두 프린트 메시지만 출력 되고,  다른 것들은 job.cancel에 의해서 모두 취소 됩니다.
 * */
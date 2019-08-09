package eCoroutineContextAndDispatchers

import kotlinx.coroutines.*


/**
 * 코루틴은 한 쓰레드에서 suspend 되고 다른 쓰레드에서 resume  됩니다.
 * 단일 쓰레드 디스페처를 사용 하더라도 코루틴이 무엇을, 어느 시기에 하는지 알아 보기 어려울 수 있습니다.
 * 쓰레드를 사용 하는 어플리케이션을 디버깅 하는 일반적인 접근법은 로그 파일이나 로그문에 쓰레드 이름을 표시 하는 것 입니다.
 * 이러한 기능은 로깅 프레임워크에 보편적으로 지원 됩니다.
 * 코루틴을 사용할때 쓰레드이름 만으로는 충분한 정보가 제공 되지 않을 수 있음으로, 코틀린엑스에서는 디버깅을 위한 기능이 포함되어 있어서 디버깅을 쉽게 만들어 줍니다.
 *
 *   JVM 옵션에 -Dkotlinx.coroutines.debug 를 넣고 다음 코드를 실행 시켜 보자.
 */

fun log(msg: String) = println("[${Thread.currentThread().name}] $msg")

fun main() = runBlocking<Unit> {
    //sampleStart
    val a = async {
        log("I'm computing a piece of the answer")
        6
    }
    val b = async {
        log("I'm computing another piece of the answer")
        7
    }
    log("The answer is ${a.await() * b.await()}")
//sampleEnd
}

/*
// 옵션이 들어간 경우.
[main @coroutine#2] I'm computing a piece of the answer
[main @coroutine#3] I'm computing another piece of the answer
[main @coroutine#1] The answer is 42
// 옵션이 안들어간 경우.
[main] I'm computing a piece of the answer
[main] I'm computing another piece of the answer
[main] The answer is 42
 */
/**
 * 이 로깅 함수는 쓰레드 이름을 대괄호로 인쇄하며 실행 중인 코루틴의 식별자가 추가되어 있습니다. 이 식별자는 디버깅 모드가 켜져 있을때 생성된 모든 코루틴에 연속적으로 할당 됩니다.
 */


package basic

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
/**
 * launch {} 속의 코드 블록을 함수로 분리 해보자.
 * 'extract function' 으로 리펙토링을 하면 suspend 수정자가 붙은 함수가 생성 된다.
 * 서스펜딩 펑션은 코루틴 안에서 일반 함수와 동일 하게  사용 할  수 있습니다. 또한 추가적으로 딜레이와 같은 추가적인 서스펜딩 함 수 들을 사용 할 수 있습니다.
 *
 */

fun main() =runBlocking {
    launch {
//        delay(1000L)
//        println("World!")
        doWorld()
    }
    println("Hello,")
}

/**
 * // 뭐라는지 모르겠음.
 * But what if the extracted function contains a coroutine builder which is invoked on the current scope? In this case suspend modifier on the extracted function is not enough. Making doWorld an extension method on CoroutineScope is one of the solutions, but it may not always be applicable as it does not make API clearer. The idiomatic solution is to have either an explicit CoroutineScope as a field in a class containing the target function or an implicit one when the outer class implements CoroutineScope. As a last resort, CoroutineScope(coroutineContext) can be used, but such approach is structurally unsafe because you no longer have control on the scope of execution of this method. Only private APIs can use this builder.
 * 그러나 추출 된 함수 내에서 현재 스코프 안에서 호출 되는 코루틴 빌더가 포함 되어 있다면 어떨까요? 이경우 서스펜드 수젇정자로는 부족합니다. doWorld  함수를 확장 메소드로 만드는 방안은 솔루션 중 하나 지만 api 가 명확확 해지지는 않음으로 항상 적용 가능한 것은 아닙니다.
 * 통사적으로는 명시적인 코틀린 스코프를 필드로 가지거나 외부 클래스가 코루틴 스코프를 암시적으로 구현하는 것입니다 (해석이 맞나요?)
 * 최후의 수단으로 CoroutineScope 가 있지만  실행 범위를 더이상 제어 할 수 없음으로 구조적으로 안전하지 않습니다. 프라이빗 api 만이 빌더를 사용할 수 있습니다.
 */



suspend fun doWorld() {
    delay(1000L)
    println("World!")
}

Coroutine basic
----

```kotlin
import kotlinx.coroutines.*

fun main() {
    GlobalScope.launch { // launch a new coroutine in background and continue
        delay(1000L) // non-blocking delay for 1 second (default time unit is ms)
        println("World!") // print after delay
    }
    println("Hello,") // main thread continues while coroutine is delayed
    Thread.sleep(2000L) // block main thread for 2 seconds to keep JVM alive
}
```

Essentially, coroutines are light-weight threads. 
본질적으로, 코루틴은 경량 쓰레드 입니다. 
They are launched with launch coroutine builder in a context of some CoroutineScope.
??? - 모르겠음.  코루틴스코프의 문맥안의 코루틴 빌더에 의해서 실행- 런치된다. 
 Here we are launching a new coroutine in the GlobalScope, meaning that the lifetime of the new coroutine is limited only by the lifetime of the whole application.
 여기서 GlobalScope에서 새로운 코루틴을 시작합니다, 이것은 새로운 코루틴의 라이프타임이 전체 어플리케이션의 수명에 의해서 만 제한 됨을 의미합니다. 
 
 _즉  _ 
 
 
 https://eso0609.tistory.com/73
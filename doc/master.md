Kotlin, as a language, provides only minimal low-level APIs in its standard library to enable various other libraries to utilize coroutines. 
_코틀린 랭귀지는 스탠다드라이브러리에서  최소한의 로우레벨 API를 제공하여 다양한 다른 라이블러리가 코루틴을 활용 할 수 있도록 합니다._ 
Unlike many other languages with similar capabilities, async and await are not keywords in Kotlin and are not even part of its standard library.
_비슷한 기능을 가진 다른 언어들과 다르게,  async 와 await 는 키워드가 아니며, 기본 라이브러리에 포함 되어 잇지도 않습니다._  
 Moreover, Kotlin's concept of suspending function provides a safer and less error-prone abstraction for asynchronous operations than futures and promises.
 _또한, 서스팬딩 펑션의 개념은 퓨처나 프로미스보다 안전하고 에러가 덜 나는 추상화를 제공합니다._ 
 
 
 kotlinx.coroutines is a rich library for coroutines developed by JetBrains. It contains a number of high-level coroutine-enabled primitives that this guide covers, including launch, async and others.
 kotlinx.coroutines 은 젯브레인에서 개발 된 풍부한 코루틴 라이블러리 입니다. 런치와 어싱크 및 다른 것들을 은 포함한 고급 코루틴 지원 라이브러리 입니다.
 
 This is a guide on core features of kotlinx.coroutines with a series of examples, divided up into different topics.
 이 가이드는 토익으로 나누어진 일련의 예제로 구성된 핵심 기능 예제 입니다. 
 
 In order to use coroutines as well as follow the examples in this guide, you need to add a dependency on kotlinx-coroutines-core module as explained in the project README.
 코 루틴을 사용하고이 안내서의 예를 따르려면 README 프로젝트에 설명 된대로 kotlinx-coroutines-core 모듈에 대한 종속성을 추가해야합니다.
 
 Table of contents
 ---
 - Coroutine basics
 - Cancellation and timeouts
 - Composing suspending functions
 - Coroutine context and dispatchers
 - Exception handling and supervision
 - Channels (experimental)
 - Shared mutable state and concurrency
 - Select expression (experimental)
 
 Additional references
 ---
 - Guide to UI programming with coroutines
 - Guide to reactive streams with coroutines
 - Coroutines design document (KEEP)
 - Full kotlinx.coroutines API reference
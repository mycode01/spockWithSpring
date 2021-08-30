junit 에서 @ExtendWith 사용하듯이 spock에서는 클래스를 extends함

```groovy
class StandardUserMailServiceTest extends Specification {...}
```

field로 선언된 값들은 테스트 케이스마다 값을 공유하지 않음

```groovy
def obj = new SomeClass()
MailService service = new MailService()
```

공유 해야 할 경우도 있음. 그럴땐

```groovy
@Shared res = new Resources()
```

spock에서는 미리 정의 된 메서드명으로 라이프사이클 역할을 함. junit이 어노테이션으로 @Before 등을 사용하는것처럼

```groovy
def setupSpec() {} // 모든 테스트 케이스 실행 전 
def setup(){} // 각 테스트 실행 전
def cleanup() {} // 각 테스트 실행 후
def cleanupSpec() {} // 모든 테스트 실행 후 
```

setupSpec 과 cleanupSpec에서만 @Shared 세팅 가능

실제 테스트 케이스는 다음과 같이 기술함

```groovy
def "테스트명"() {...} 
```

"로 감싸는 메서드 명은 자유롭게 정할수 있음.

세미콜론 필요 없음

정적타입, 동적타입 둘다 지원함.

```groovy
def "테스트"(){
	given:
	String hello = "hello"
	def world = " world!" //동적
```

일반적인 BDD에서는 given when then을 사용하는것처럼 각 페이즈를 블록으로 만듬.

given(setup) when then expect cleanup where 사용가능함

given:은 내용의 셋업을 위해 사용하는 블록, optional

제일 앞에 있어야 함

when, then은 함께 사용하게 됨. when에서는 조건 실행을, then에서는 검증을 함. then에서는 검증을 위한 정의만 올수 있음.

```groovy
when:
	def result = Calculator.multiply(x, y)
then:
	result == 10
	// thrown(NullPointException) // 예외에 대한 검증 
	//혹은
  def e = thrown()
	e.cause == ...
```

```groovy
given:
    def userRepo = Mock(UserRepository)
    def mailSender = Mock(EMailSender)
    def mailService = new MailService(userRepo, mailSender)
when:
    mailService.send("userId", "msg")
then:
    1 * userRepo.findByUserId("userId") 
    1 * mailService.sendMail("userId@domain.com", "msg")
```

mock에 대한 interation도 체크 가능함

위 예제에서는 mailService의 send를 이용하면 userRepo와 mailSender의 각 메소드가 1회씩 호출됨을 테스트 함

when: then: 은 번갈아가며 여러번 사용할수 있음.

```groovy
when:
    def user = userRepository.findById(1);
then:
    with(user) {
        name == "anything"
        email == "nowhere@void.com"
        id > 10
    }
    with(userRepository){
        1 * findById()
    }
```

with를 이용해서 좀 더 쉽게 접근 가능함. mock에도 적용 가능함.

when: then: 을 합친 expect: 블록도 있음

```groovy
when:
	def x = Math.max(1,2)
then:
  x == 2
// 혹은
expect:
  Math.max(1,2) == 2
```

cleanup: 은 테스트 마무리에 해당

예를들어 파일을 생성한 후 삭제를 해야한다던지.

where: 는 항상 마지막에 옴.

junit에서 테스트를 반복하기 위해서는 코드를 추가로 작성해야하는데, spock에서는 여기서 필드의 데이터만 바꾸는게 가능함

```groovy
def "where 에 대한 값을 수정 테스트 "() {
    expect:
    Math.max(a, b) == c
    where:
    a << [5, 3]
    b << [1, 9]
    c << [5, 9]
//혹은
//		where:
//		a | b | c
//		5 | 1 | 5
//		3 | 9 | 9
  }
```

위의 예제에서 주석처리 된 where: 절과 위의 where 절은 같은 역할을 한다.

두번의 Math.max() 테스트를 진행하는데, 첫번째는 각각

a 가 5, b가 1, 그 결과 값은 c로 5가 들어오게 됨. 그다음

a == 3, b == 9, c == 9 가 된다.


추가로 각 블록에는 라벨을 달수 있음

```java
given: "필요한 목 생성"
	def xxRepository = Mock(XxRepository)
and: "생성된 목을 테스트 대상에 주입"
	def zzService = new ZzService(xxRepository)
```

and : 블록은 별 의미 없고, 블록을 나눠서 설명하기 위한 블록
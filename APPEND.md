mock는 junit과 거의 비슷하다.

```groovy
def xxRepository = Mock(XxRepository)
zzService zzService = Mock()
def ccService = Spy()
```

각 오브젝트의 기본값을 리턴하게 됨. boolean false, number 0, 객체타입은 null

스텁

junit 과 유사함

```groovy
//고정된 값, junit의 thenReturn()
zzRepository.findById(_) >> new Zz("내용") 
// zzRepository.findById()에 어떤값이 들어와도 new Zz() 수행후 리턴

// 고정된 값이 차례대로, thenReturn().thenReturn()..
zzRepository.findById(_) >> [new Zz("내용"), new Zz("없는데")]
// 다음번 3번째 호출은 null 반환 

// 계산이 되어야 할때, thenAnswer()
zzRepository.findByName(_) >> { args -> args[0].name() == "name" ? new Zz("name") : null}

// 에러가 출력되어야 할때, thenThrow()
zzRepository.findById(_) >> { throw new NotImplementedException("msg")}

// junit처럼 체인으로 섞어서 사용도 가능
zzRepository.findById(_) >> [new Zz("내용"), new Zz("없는데")] >> {throw new NotFound()}

```

mock을 검증하는 방법

```groovy
1 * xxRepository.findById(1) // 1번 호출
(1..10) * xxRepository.findById(1) // 1~10회
(1.._) * .. // 최소 1번
(_..10) * .. // 최대 10번
_ * xxRepository.findById(1) // 1번 이상 
// 괄목할만한 방법들 
1 * _.findById(1) // any mock의 findbyId 1회 호출
1 * xxRepository./f.*id/(1) // f로 시작, id로 끝나는 메소드 1회 
1 * zzService.xxRepository.findById // zzService의 멤버인 xxRepository.findById 1회 호출

```

호출될때의 파라미터값

```groovy
1 * xxRepository.findById(1) // 파라미터 값 1을 가지는 메서드 1회 호출
1 * xxRepository.findById(!1) // 1이 아닌.. 1회호출
1 * xxRepository.findById(_) // any, but list 는 포함하지 않음
1 * xxRepository.findById(*_) // any, list 포함
1 * xxReposiotry.findById(!null) //null이 아닌
1 * xxRepository.findById(_ as String) // anyString()

```

mock에 전달된 파라메터 값 검증

![Untitled](https://raw.githubusercontent.com/mycode01/linkimages/master/spock/append1.png)

104L: 전달받은 예상 파라메터를 정의하고,  
105L: assert 문을 이용하여 값을 검증한다.  

맞는 생성자가 없는데도 생성이 가능함.

![Untitled](https://raw.githubusercontent.com/mycode01/linkimages/master/spock/append2.png)

setter가 있기 때문인거같은데.. groovy 특성인지?

interactions

where: 블록에서 사용하는 데이터 테이블은 다음과 같이 사용함

다만 각 변수값은 테스트 독립적이므로 다음번 테스트에 사용되지 않음.

미리 static 변수로 만들어 두던지 @Shared로 만들어 둬야함.

```groovy
where:
	a|b|c
	1|2|2
	3|8|8
// 혹은 resp값과 차이를 두기 위해서(보기좋게) 
where:
	a|b||c
	1|2||2
	3|8||8
// 위는 1개의 파라메터값은 테스트를 못하는데, 1개의 파라미터만 테스트할땐 다음과 같이 쓴다
where:
	a | _
	1 | _
	2 | _
	9 | _

```
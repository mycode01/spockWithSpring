spock는 groovy를 사용하기 때문에 gradle에 플러그인을 추가해준다.

스프링 dsl을 사용하고 있다면

```groovy
plugins {
    id 'org.springframework.boot' version '2.5.4'
    id 'io.spring.dependency-management' version '1.0.11.RELEASE'
    id 'java'
    id 'groovy'
}
```

아니면

```groovy
apply plugin: 'groovy' 
```

다음 디펜던시 추가 - 둘째줄은 스프링사용중일때

```groovy
testImplementation group: 'org.spockframework', name: 'spock-core', version: '2.0-groovy-3.0'
testImplementation group: 'org.spockframework', name: 'spock-spring', version: '2.0-groovy-3.0'
```

테스트 클래스 생성시 패키지는

src.test.**java**.com.example.. 이 아니라

src.test.**groovy**.com.example.. 이 되어야 함

![Untitled](https://raw.githubusercontent.com/mycode01/linkimages/master/spock/setup1.png)
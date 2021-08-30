# spock framework 

## spock framework test with spring boot 2

## Installation
### Requirement
* java 11
#### Option
* [gradle](https://gradle.org/install/)

  Use an build tool [gradle](https://gradle.org/install/) to managing dependencies and build.

```bash
$ brew install gradle
```    
or

manually [download gradle](https://gradle.org/releases/) and unzip binaries.

## Build

build using gradle.
```bash
$ gradlew clean build 
```
or

```bash
$ ./gradlew clean build
# use gradle including project
```

## test 
해당 프로젝트의 중점은 spock를 이용한 테스트이므로 구동보다는 
src/test 밑의 테스트에 중점 두길 바람

## spock 
[설치 및 세팅](./SETUP.md)

[spock 기초](./BASIC.md)

[mock과 stub](./APPEND.md)
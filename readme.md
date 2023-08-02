## 1. 사용 기술

Java 11, Spring Boot, Spring Data JPA, Gradle, PostgreSQL

## 2. 엔티티 설계

![erd](https://github.com/chrismrkr/toyproject-user-item-promotion-example/assets/62477958/313c280d-d956-48ec-a5e8-d6b90fdd3030)

## 3. API

### 3.1 회원(User)

+ POST /user : 회원 생성
+ PUT /user/{id} : 회원 정보 업데이트 
+ DELETE /user/{id} : 회원 삭제

### 3.2 상품(Item)

+ GET /item?userId=... : 상품 조회
+ POST /item : 상품 생성
+ PUT /item/{id} : 상품 정보 수정
+ DELETE /item/{id} : 상품 삭제

### 3.3 프로모션(Promotion)

+ GET /promotion?itemId={itemId} : item({itemId})에 등록된 프로모션 조회
프로모션이 존재할 때만 결과를 반환하고, 프로모션이 여러 개인 경우 가장 할인이 큰 것을 조회함

+ 등록, 수정, 삭제는 미구현

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

+ POST /promotion : 프로모션 생성

promotion을 등록한 item이 존재해야 생성 가능

+ DELETE /promotion/{promotionId} : 프로모션 삭제

## 4. JPA에서의 N:M 엔티티 매핑

Item과 Promotion 엔티티는 N:N 관계이므로 이를 1:N, N:1로 풀어내기 위해 PromotionItem 엔티티를 생성했다.

PromotionItem 엔티티의 PK는 (item_id, promotion_id)로 구성된 복합키이다.

JPA에서 복합키를 생성하기 위해서는 EmbeddedId 또는 IdClass를 사용할 수 있으나 여기서는 IdClass를 사용했다.

IdClass를 사용한다면 @IdClass로 지정되는 큻래스는 반드시 아래의 조건을 만족해야 한다.

+ IdClass 클래스에는 EntityClass의 Id 객체와 동일한 필드가 있어야 한다. 이름도 동일해야 한다.
+ Serializable를 구현해야 한다.
+ 기본생성자, equals, hashcode 함수가 구현되어야 한다.

```java
@IdClass(PromotionItem.JoinTableId.class)
public class PromotionItem {
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "promotion_id")
    private Promotion promotion;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;
    ...
    public static class JoinTableId implements Serializable {
        private Long item;
        private Long promotion;
    }
}
```

StackOverFlow에 정리된 @IdClass, @EmbeddedId에 대한 의견은 아래와 같다. (https://stackoverflow.com/questions/212350/which-annotation-should-i-use-idclass-or-embeddedid)

**1. @EmbeddedId가 @IdClass보다 더 장황하다는 의견**

@EmbeddedId의 경우에는 복합키의 일부 필드에 접근할 때 @IdClass와 달리 2번의 접근이 필요하기 때문에 더 장황하다는 입장.

```java
Employee.getEmployeeId().getEmployerId(); // 이런 방식으로 2번의 접근이 필
```

그러나, 특정 필드가 복합 키의 일부라는 점을 바로 보여주므로 오히려 더 가독성이 높다는 입장.

**2. FK가 다른 엔티티의 PK로만 이루어진 경우, @IdClass를 사용하는 것이 더 가독성이 좋다는 입장**

**3. 가능하다면 @EmbeddedId를 사용하되, 어노테이션 적용이 불가능한 레거시 모듈에만 @IdClass를 사용하라는 입장**

**4. @EmbeddedId가 더 장황하다고는 하나, 특정 상황에서는 쿼리가 더 간결해질 수 있다는 입장**

```sql
FROM Entity WHERE id IN :ids
```

```sql
FROM Entity WHERE idPartA = :idPartA0 AND idPartB = :idPartB0 .... OR idPartA = :idPartAN AND idPartB = :idPartBN**
```







## Installation
* JDK 11 / Spring Boot 2.5.6
* MySql 5.7
* `sample` database 추가해야함
  * `create database sample default character set utf8 collate utf8_general_ci;`
* 환경변수에 mysql password 를 추가해야함 `SPRING_DATASOURCE_PASSWORD`
  * ex) `export SPRING_DATASOURCE_PASSWORD=pas$w0rd`

## Description
* 총 5개의 엔티티, 4개의 서비스로 이루어져있는 어플리케이션
  * User, Item, Cart, Order, OrderDetail
* User는 단순가입과 조회만 있다. 유일하게 구현한 controller인 UserController에는 argument resolver를 사용하여 유저를 찾는 방식을 구현했다.
* Item은 생성은 테스트코드에만 존재하며, 잔여수량 체크, 수량 반영이 주요 코드이다. 수량 반영시 동시성 체크를 위해서 exclusive lock을 사용하였다.
* Cart는 User가 구매를 원하는 목록을 추가 및 삭제할 수 있다. 실제 환경과 어울리진 않지만, soft delete를 구현하였다.
* Order는 OrderDetail과 짝을 이루며, 주문서와 내역으로 이해할 수 있다. 단건주문과 카트의 모든 목록을 주문하는 기능이 있다. N+1 쿼리와 성능을 향상시킬 수 있는 fetch join을 사용하였다.
* 스키마 마이그레이션은 entity 코드로 자동으로 만드는 것보다 ddl 로 관리하는 것이 nullable이나 index를 꼼꼼하게 쓸수 있어 liquibase를 사용하였다.
* 엔티티에 조인 쿼리 없이 볼 수 있도록 해당 필드와 객체를 동시에 사용할 수 있도록 선언하였으며, 객체는 setter의 사용을 막아서 변경할 수 없도록 했다.
* 도메인 객체들은 롬복의 `@Data`를 활용하는 동시에 `@Setter(AccessLevel.NONE)` 으로 중간에 변경할 수 없도록 했고, 생성자나 스태틱 메서드를 통해서만 객체를 만들수 있도록 했다.
* 테스트는 모킹 데이터를 사용하지 않고 직접 db에 쓰고 지우는 방식으로 구현했다.


## Sample Curl
* 가입
```bash
curl -XPOST http://localhost:8080/users -H 'Content-Type: application/json' -d '{"email":"bwjhj1030@gmail.com"}'
>> {"email":"bwjhj1030@gmail.com","createdAt":"2021-11-09T18:26:42.882583"}
```
* 조회
```bash
curl -XGET http://localhost:8080/users/me -H 'Content-Type: application/json' -H 'sample-email: bwjhj1030@gmail.com'
{"email":"bwjhj1030@gmail.com","createdAt":"2021-11-09T18:26:42.882"}
```

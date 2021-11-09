## Installation
* JDK 11 / SPRING BOOT 2.5.6
* MYSQL 5.7

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

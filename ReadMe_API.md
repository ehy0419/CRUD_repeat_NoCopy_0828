# RESTFul API
## Login, Logout
| Method | 엔드포인트              | 설명          | 파라미터 | RequestBody | Response          | 정상 코드 | 에러 코드 |
|--------|--------------------|-------------|------|-------------|-------------------|------------------|---|
| POST   | /login             | 로그인         | -    | {<br/>”email”: string, “paasword”: string<br/>}  | "로그인 성공"          | 201 CREATED  |400 BAD REQUEST <br/>403 FORBIDDEN|
| POST   | /logout            | 로그아웃        | -    | -           | "로그아웃 성공"          | 200 OK  |400 BAD REQUEST <br> 401 UNAUTHORIZED <br> 404 NOT FOUND|


## Login, Logout
| Method | 엔드포인트 | 설명 | 파라미터 | RequestBody | Response | 정상 코드 | 에러 코드 |
|---|---------------------|---|------|---|---|------------------|---|
| GET | /api/v1/health      | 서비스 헬스체크 | -    | - | - | 200 OK           |---|
| POST | /api/v1/auth/signup | 회원가입 | -    | - | {email, password, ...} | 201 Created      |---|
| POST | /api/v1/auth/login  | 로그인(JWT 발급) | -    | - | {email, password} | 200 OK {token}   |---|
| GET | /api/v1/users/{id}  | 사용자 단건 조회 | USER | id | - | 200 OK {UserDTO} |---|

# SQL DDL with Explanations

```sql
CREATE TABLE users (
//users 라는 테이블 생성 - ( 컬럼(열) 정의와 제약 조건 나열 )
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  //BIGINT         : 정수형 데이터 타입(8byte) - 무제한 수 표현할 수 있다.
  //AUTO_INCREMENT : 새 행이 들어올 때 DB가 자동으로 1씩 증가한 값을 넣어준다.
  //PRIMARY KEY    : 테이블의 기본 키. 중복 금지 + NULL 금지
  user_name VARCHAR(255) NOT NULL UNIQUE,
  //사용자 이름을 저장
  //VARCHAR(255)  : 최대 255자까지의 가변 길이 문자열.
  //NOT NULL      : 반드시 값이 있어야 한다.
  //UNIQUE        : 중복을 허용하지 않음. 동일한 사용자 이름일 경우 에러.
  email VARCHAR(255) NOT NULL UNIQUE,
  //로그인에 필요한 이메일을 저장
  //NOT NULL      : 반드시 값이 있어야 한다.
  //UNIQUE        : 중복을 허용하지 않음. 동일한 사용자 이름일 경우 에러.
  password VARCHAR(255) NOT NULL,
  //로그인에 필요한 비밀번호를 저장
  //비밀번호 암호화에 따른 글자 수
  //NOT NULL      : 반드시 값이 있어야 한다.
  //UNIQUE        : 중복을 허용하지 않음. 동일한 사용자 이름일 경우 에러.
  created_at DATE,
  //작성일 자동 기록 - 연월일까지만
  updated_at DATE
  //수정일 자동 기록
);

CREATE TABLE schedules (
//schedules 라는 테이블 생성
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  //BIGINT         : 정수형 데이터 타입(8byte) - 무제한 수 표현할 수 있다.
  //AUTO_INCREMENT : 새 행이 들어올 때 DB가 자동으로 1씩 증가한 값을 넣어준다.
  //PRIMARY KEY    : 테이블의 기본 키. 중복 금지 + NULL 금지
  user_id BIGINT NOT NULL,
  //일정을 만든 유저의 식별키와 연결된 외래키 FK
  //빈 칸 금지
  //왜?            : 반드시 일정은 유저에 속한다는 의미
  user_name VARCHAR(255) NOT NULL,
  //작성 유저명을 저장하는 것
  //users 테이블의 유저명과 동일한데, 이게 되는 건가...
  title VARCHAR(255) NOT NULL,
  content VARCHAR(255) NOT NULL,
  //일정 제목, 일정 내용
  //빈 칸 금지
  //일정 내용이 많아지면 VARCHAR 말고 다른 것을 사용해야 하나?
  created_at DATE,
  updated_at DATE,
  //작성일, 수정일 날짜 정보 
  //연월일까지만.
  //작성일, 수정일 이라는 단어에 시간은 포함되지 않을것이라 해석.
  FOREIGN KEY (user_id) REFERENCES users(id)
  //일정 테이블의 user_id는 사용자 테이블의 id를 참조한다.
);

CREATE TABLE comments (
//comments 라는 테이블 생성
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  //BIGINT         : 정수형 데이터 타입(8byte) - 무제한 수 표현할 수 있다.
  //AUTO_INCREMENT : 새 행이 들어올 때 DB가 자동으로 1씩 증가한 값을 넣어준다.
  //PRIMARY KEY    : 테이블의 기본 키. 중복 금지 + NULL 금지
  content VARCHAR(255) NOT NULL,
  //댓글 내용
  //255자 제한 및 빈 칸 금지
  schedule_id BIGINT NOT NULL,
  user_id BIGINT NOT NULL,
  //댓글이 작성된 일정의 식별키와 댓글을 작성한 유저의 식별키를 연결하는 FK
  //댓글은 일정과 유저에게 속한다.
  created_at DATE,
  updated_at DATE,
  //작성일, 수정일 날짜 정보 
  //연월일까지만.
  //작성일, 수정일 이라는 단어에 시간은 포함되지 않을것이라 해석.
  FOREIGN KEY (schedule_id) REFERENCES schedules(id),
  //댓글 테이블의 schdule_id 는 일정 테이블의 id를 참조한다.
  FOREIGN KEY (user_id) REFERENCES users(id)
  //댓글 테이블의 user_id 는 유저 테이블의 id를 참조한다.
);
```

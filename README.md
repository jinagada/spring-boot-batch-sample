## 초기 설정 사항
1. 테스트를 위해서는 lombok 설정 필수
2. Spring Batch 전용 테이블 스키마 위치 org.springframework.batch.core.schema-h2>.sql 참고하여 테이블 생성 할 것!!
3. DB 스케줄 관련하여 아래의 테이블 생성이 필요함
4. 예제는 H2 Database를 사용하여 Sample 개발이 되어있음
5. Spring devtools 적용
    1. 설정 방법 참조 : http://haviyj.tistory.com/11
6. 로그인 화면 적용
    1. 계정정보는 SecurityConfig.java 파일 참조
7. 화면 개발은 Thymeleaf, Bootstrap, Jquery 사용
## 스케줄 관리용 테이블 생성 스키마(H2)

    CREATE TABLE T_JOB_SCHEDULES
    (
        JOB_SCHEDULE_ID BIGINT IDENTITY NOT NULL PRIMARY KEY,
        CRON_EXPRESSION VARCHAR(255),
        DESCRIPTION VARCHAR(255),
        JOB_NAME VARCHAR(255),
        JOB_PARAMETER VARCHAR(255),
        SCHEDULED SMALLINT,
        RDATE TIMESTAMP,
        MDATE TIMESTAMP
    );


## 스케줄 관리용 테이블 생성 스키마(MySQL)

    CREATE TABLE T_JOB_SCHEDULES
    (
        JOB_SCHEDULE_ID BIGINT PRIMARY KEY NOT NULL AUTO_INCREMENT,
        CRON_EXPRESSION VARCHAR(255),
        DESCRIPTION VARCHAR(255),
        JOB_NAME VARCHAR(255),
        JOB_PARAMETER VARCHAR(255),
        SCHEDULED SMALLINT,
        RDATE DATETIME,
        MDATE DATETIME
    );


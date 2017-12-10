## 초기 설정 사항
1. 테스트를 위해서는 lombok 설정 필수
2. Spring Batch 전용 테이블 스키마 위치 org.springframework.batch.core.schema-h2.sql 참고하여 테이블 생성 할 것!!
3. DB 스케줄 관련하여 아래의 테이블 생성이 필요함
4. 예제는 H2 Database를 사용하여 Sample 개발이 되어있음
5. 스케줄 조회 및 등록용 화면 대신 RestAPI 형태의 컨트롤러를 제공함
> * http://localhost:8080/batch/schedule/getJobScheduleList : Job 목록 조회
> * http://localhost:8080/batch/schedule/startJob : Job 실행
>> * jobName : Job Name
>> * jobParameter : Job Parameter
> * http://localhost:8080/batch/schedule/saveSchedule : Job 설정 저장
>> * jobName : Job Name
>> * jobParameter : Job Parameter
>> * cronExpression : Job Schedule(Cron Expression)
>> * description : 설명
>> * scheduled : 1 - 배치 스케줄 실행, 0 - 배치 스케줄 중지
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


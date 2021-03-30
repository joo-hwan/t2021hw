--
-- 회원
--
DROP TABLE IF EXISTS MEMBER;

CREATE TABLE MEMBER COMMENT '회원' (
    MBR_ID      VARCHAR(10)     NOT NULL    COMMENT '회원ID'
  , NAME        VARCHAR(100)                COMMENT '회원명'
  , PRIMARY KEY (MBR_ID)
);


--
-- 결제마스터 - 신용카드, 핸드폰, 계좌이체 등
--
DROP TABLE IF EXISTS PAYMENT_MST;

CREATE TABLE PAYMENT_MST COMMENT '결제마스터' (
    PMT_CODE        VARCHAR(5)      NOT NULL    COMMENT '결제코드'
  , PMT_TYPE        VARCHAR(4)                  COMMENT '결제타입'
  , PMT_NAME        VARCHAR(100)                COMMENT '결제코드명'
  , PART_CNCL_YN    VARCHAR(1)                  COMMENT '부분취소가능여부'
);


--
-- 결제내역
--
DROP TABLE IF EXISTS PAYMENT;

CREATE TABLE PAYMENT COMMENT '결제내역' (
    PMT_ID          VARCHAR(10)     NOT NULL    COMMENT '결제ID'
  , MBR_ID          VARCHAR(10)     NOT NULL    COMMENT '회원ID'
  , PMT_CODE        VARCHAR(5)      NOT NULL    COMMENT '결제코드'
  , BF_PMT_CODE     VARCHAR(10)                 COMMENT '이전결제ID'
  , PMT_TYPE        VARCHAR(4)                  COMMENT '결제타입'
  , SUCC_YN         VARCHAR(1)                  COMMENT '성공여부'
  , SUCC_MSG        VARCHAR(100)                COMMENT '성공메세지'
  , APRV_TYPE       VARCHAR(2)                  COMMENT '승인타입'
  , APRV_TIME       TIMESTAMP                   COMMENT '승인일시'
  , PMT_AMT         BIGINT                      COMMENT '결제금액'
  , PRIMARY KEY (PMT_ID)
);


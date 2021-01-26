# 데이터 베이스 생성
DROP DATABASE IF EXISTS lolHiT;
CREATE DATABASE lolHiT;
USE lolHiT;

# 게시물 테이블 생성
CREATE TABLE article (
    id INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
    regDate DATETIME NOT NULL,
    updateDate DATETIME NOT NULL,
    title CHAR(200) NOT NULL,
    `body` TEXT NOT NULL
);

# 게시물 데이터 생성
INSERT INTO article
SET regDate = NOW(),
updateDate = NOW(),
title = '제목1',
`body` = '내용1';

INSERT INTO article
SET regDate = NOW(),
updateDate = NOW(),
title = '제목2',
`body` = '내용2';

# 회원 테이블 생성
CREATE TABLE `member` (
    id INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
    regDate DATETIME NOT NULL,
    updateDate DATETIME NOT NULL,
    loginId CHAR(20) NOT NULL,
    loginPw CHAR(100) NOT NULL,
    `name` CHAR(100) NOT NULL
);

# 회원 생성
INSERT INTO `member`
SET regDate = NOW(),
updateDate = NOW(),
loginId = 'test1',
loginPw = 'test1',
`name` = '홍길동';

INSERT INTO `member`
SET regDate = NOW(),
updateDate = NOW(),
loginId = 'test2',
loginPw = 'test2',
`name` = '홍길순';

# 게시물 테이블에 memberId 칼럼 추가
ALTER TABLE article ADD COLUMN memberId INT(10) UNSIGNED NOT NULL AFTER updateDate;
# 기존 게시물들의 작성자는 1번 회원으로 정한다.
UPDATE article SET memberId = 1 WHERE memberId = 0;

# 게시물 데이터 추가
INSERT INTO article
SET regDate = NOW(),
updateDate = NOW(),
title = '제목3',
`body` = '내용3',
memberId = 2;

INSERT INTO article
SET regDate = NOW(),
updateDate = NOW(),
title = '제목4',
`body` = '내용4',
memberId = 2;

INSERT INTO article
SET regDate = NOW(),
    updateDate = NOW(),
    title = CONCAT('제목_', RAND()),
    `body` = CONCAT('내용_', RAND()),
    memberId = IF(RAND() > 0.5, 2,1);

# 댓글 테이블 생성
CREATE TABLE reply (
    id INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
    regDate DATETIME NOT NULL,
    updateDate DATETIME NOT NULL,
    `body` TEXT NOT NULL,
    memberId INT(10) UNSIGNED NOT NULL,
    relId INT(10) UNSIGNED NOT NULL, # 관련 데이터 ID
    relTypeCode CHAR(50) NOT NULL # 관련 데이터 타입
);

# 댓글 데이터 추가
INSERT INTO reply
SET regDate = NOW(),
updateDate = NOW(),
relTypeCode = 'article',
relId = 1,
`body` = '댓글 1',
memberId = 1;

INSERT INTO reply
SET regDate = NOW(),
updateDate = NOW(),
relTypeCode = 'article',
relId = 1,
`body` = '댓글 2',
memberId = 1;

INSERT INTO reply
SET regDate = NOW(),
updateDate = NOW(),
relTypeCode = 'article',
relId = 1,
`body` = '댓글 3',
memberId = 1;

INSERT INTO reply
SET regDate = NOW(),
updateDate = NOW(),
relTypeCode = 'article',
relId = 1,
`body` = '댓글 4',
memberId = 2;

#게시판 테이블 추가
CREATE TABLE board (
	id INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
	regDate DATETIME NOT NULL,
    updateDate DATETIME NOT NULL,
    `name` CHAR(20) UNIQUE NOT NULL,
    `code` CHAR(20) UNIQUE NOT NULL
);

INSERT INTO board
SET regdATE = NOW(),
updateDate = NOW(),
`name` = '공지사항',
`code` = 'notice';

INSERT INTO board
SET regdATE = NOW(),
updateDate = NOW(),
`name` = '자유게시판',
`code` = 'free';


#회원 테이블 E-Mail 칼럼 추가
ALTER TABLE `member` ADD COLUMN EMAIL CHAR(100) AFTER `name`;

#email 칼럼 업데이트
update `member` 
set email='afpol@naver.com';

#멤버테이블 비밀번호 sha256으로 업데이트, 비밀번호를 평문이 아닌 해싱된 결과를 저장
SELECT SHA2(loginPw,256) FROM `member`

UPDATE `member` SET loginPw = SHA2(loginPw,256)


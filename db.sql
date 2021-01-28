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

/* 파일 테이블 생성 */
CREATE TABLE `file` (
    id INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
    regDate DATETIME,
    updateDate DATETIME,
    delDate DATETIME,
	delStatus TINYINT(1) UNSIGNED NOT NULL DEFAULT 0,
	relTypeCode CHAR(50) NOT NULL,
	relId INT(10) UNSIGNED NOT NULL,
    originFileName VARCHAR(100) NOT NULL,
    fileExt CHAR(10) NOT NULL,
    typeCode CHAR(20) NOT NULL,
    type2Code CHAR(20) NOT NULL,
    fileSize INT(10) UNSIGNED NOT NULL,
    fileExtTypeCode CHAR(10) NOT NULL,
    fileExtType2Code CHAR(10) NOT NULL,
    fileNo TINYINT(2) UNSIGNED NOT NULL,
    `body` LONGBLOB
);

# 파일 테이블에 유니크 인덱스 추가
ALTER TABLE `file` ADD UNIQUE INDEX (`relId`, `relTypeCode`, `typeCode`, `type2Code`, `fileNo`); 


# DB 세팅
DROP DATABASE IF EXISTS `at`;
CREATE DATABASE `at`;
USE `at`;

# article 테이블 세팅
CREATE TABLE article (
    id INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
    regDate DATETIME,
    updateDate DATETIME,
    delDate DATETIME,
	delStatus TINYINT(1) UNSIGNED NOT NULL DEFAULT 0,
	displayStatus TINYINT(1) UNSIGNED NOT NULL DEFAULT 0,
    title CHAR(200) NOT NULL,
    `body` LONGTEXT NOT NULL
);

# article 테이블에 테스트 데이터 삽입
INSERT INTO article
SET regDate = NOW(),
updateDate = NOW(),
title = '제목1',
`body` = '내용1';

INSERT INTO article
SET regDate = NOW(),
updateDate = NOW(),
title = '제목2',
`body` = '내용2',
displayStatus = 1;

INSERT INTO article
SET regDate = NOW(),
updateDate = NOW(),
title = '제목3',
`body` = '내용3',
displayStatus = 1;

# member 테이블 세팅
CREATE TABLE `member` (
    id INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
    regDate DATETIME,
    updateDate DATETIME,
    delDate DATETIME,
	delStatus TINYINT(1) UNSIGNED NOT NULL DEFAULT 0,
	authStatus TINYINT(1) UNSIGNED NOT NULL DEFAULT 0,
    loginId CHAR(20) NOT NULL UNIQUE,
    loginPw CHAR(100) NOT NULL,
    `name` CHAR(20) NOT NULL,
    `nickname` CHAR(20) NOT NULL,
    `email` CHAR(100) NOT NULL,
    `phoneNo` CHAR(20) NOT NULL
);

# member 테이블에 테스트 데이터 삽입
INSERT INTO `member`
SET regDate = NOW(),
updateDate = NOW(),
loginId = 'admin',
loginPw = SHA2('admin', 256),
`name` = '관리자',
`nickname` = '관리자',
`email` = '',
`phoneNo` = '';


# article 테이블 세팅
CREATE TABLE articleReply (
    id INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
    regDate DATETIME,
    updateDate DATETIME,
    memberId INT(10) UNSIGNED NOT NULL,
    articleId INT(10) UNSIGNED NOT NULL,
    delDate DATETIME,
	delStatus TINYINT(1) UNSIGNED NOT NULL DEFAULT 0,
	displayStatus TINYINT(1) UNSIGNED NOT NULL DEFAULT 0,
    `body` LONGTEXT NOT NULL
);

# articleReply 테이블에 테스트 데이터 삽입
INSERT INTO articleReply
SET regDate = NOW(),
updateDate = NOW(),
memberId = 1,
articleId = 1,
displayStatus = 1,
`body` = '내용1';

/* 게시물 댓글을 범용 댓글 테이블로 변경 */
RENAME TABLE `articleReply` TO `reply`;

ALTER TABLE `reply` ADD COLUMN `relTypeCode` CHAR(50) NOT NULL AFTER `memberId`,
CHANGE `articleId` `relId` INT(10) UNSIGNED NOT NULL;
ALTER TABLE `at`.`reply` ADD INDEX (`relId`, `relTypeCode`);
UPDATE reply
SET relTypeCode = 'article'
WHERE relTypeCode = '';

/* 파일 테이블 생성 */
CREATE TABLE `file` (
    id INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
    regDate DATETIME,
    updateDate DATETIME,
    delDate DATETIME,
	delStatus TINYINT(1) UNSIGNED NOT NULL DEFAULT 0,
	relTypeCode CHAR(50) NOT NULL,
	relId INT(10) UNSIGNED NOT NULL,
    originFileName VARCHAR(100) NOT NULL,
    fileExt CHAR(10) NOT NULL,
    typeCode CHAR(20) NOT NULL,
    type2Code CHAR(20) NOT NULL,
    fileSize INT(10) UNSIGNED NOT NULL,
    fileExtTypeCode CHAR(10) NOT NULL,
    fileExtType2Code CHAR(10) NOT NULL,
    fileNo TINYINT(2) UNSIGNED NOT NULL,
    `body` LONGBLOB
);

# 멤버 테이블 칼럼명 변경
ALTER TABLE `member` CHANGE `phoneNo` `cellphoneNo` CHAR(20) NOT NULL; 

# 게시물 테이블에 작성자 정보 추가
ALTER TABLE `article` ADD COLUMN `memberId` INT(10) UNSIGNED NOT NULL AFTER `delStatus`; 

UPDATE article
SET memberId = 1
WHERE memberId = 0;

# 파일 테이블에 유니크 인덱스 추가
ALTER TABLE `file` ADD UNIQUE INDEX (`relId`, `relTypeCode`, `typeCode`, `type2Code`, `fileNo`); 

# 파일 테이블의 기존 인덱스에 유니크가 걸려 있어서 relId가 0 인 동안 충돌이 발생할 수 있다. 그래서 일반 인덱스로 바꾼다.
ALTER TABLE `file` DROP INDEX `relId`, ADD INDEX (`relId` , `relTypeCode` , `typeCode` , `type2Code` , `fileNo`); 

# file에 fileDir 추가
ALTER TABLE `file` ADD COLUMN `fileDir` CHAR(20) NOT NULL AFTER `body`; 

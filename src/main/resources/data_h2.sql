INSERT INTO MEMBER (username, nickname, password, phone_number, phone_auth, provider_type_code, coin, rating, review_count) VALUES ('user1', 'User1', 'password123', '01012345678', 1, 'kakao', 1000, 3.5, 1);
INSERT INTO MEMBER (username, nickname, password, phone_number, phone_auth, provider_type_code, coin, rating, review_count) VALUES ('user2', 'User2', 'password123', '01023456789', 1, 'kakao', 0, 5.0, 1);
INSERT INTO MEMBER (username, nickname, password, phone_number, phone_auth, provider_type_code, coin, rating, review_count) VALUES ('user3', 'User3', 'password123', '01034567890', 1, 'kakao', 0, 1.0, 2);
INSERT INTO MEMBER (username, nickname, password, phone_number, phone_auth, provider_type_code, coin, rating, review_count) VALUES ('user4', 'User4', 'password123', '01045678901', 1, 'kakao', 0, 3.5, 2);
INSERT INTO MEMBER (username, nickname, password, phone_number, phone_auth, provider_type_code, coin, rating, review_count) VALUES ('user5', 'User5', 'password123', '01056789012', 1, 'kakao', 0, 0, 0);
INSERT INTO MEMBER (username, nickname, password, phone_number, phone_auth, provider_type_code, coin, rating, review_count) VALUES ('KAKAO__12345678', 'bigsand', 'password12311', '01053833333', 1, 'kakao', 0, 0, 0);

INSERT INTO NOTIFICATION (requester_id, helper_id, message, type, created_at) VALUES (1, 2, '채팅시작테스트', 'START', NOW());
INSERT INTO NOTIFICATION (requester_id, helper_id, message, type, created_at) VALUES (1, 3, '거래취소테스트', 'CANCEL', NOW());
INSERT INTO NOTIFICATION (requester_id, helper_id, message, type, created_at) VALUES (3, 4, '거래완료테스트', 'END', NOW());
INSERT INTO NOTIFICATION (requester_id, helper_id, message, type, created_at) VALUES (6, 1, '큰모래님과의 채팅이 시작되었습니다.', 'START', NOW());
INSERT INTO NOTIFICATION (requester_id, helper_id, message, type, created_at) VALUES (2, 6, '큰모래님과의 거래가 취소되었습니다.', 'CANCEL', NOW());
INSERT INTO NOTIFICATION (requester_id, helper_id, message, type, created_at) VALUES (6, 3, '큰모래님과의 거래가 완료되었습니다.', 'END', NOW());

INSERT INTO BOARD (reward_coin, x, y, board_type, content, reg_code, title, trade_type, member_id) VALUES (1000, 126.732240699017, 37.5110124805355, '나급해요', '내용1', '2823710500', '제목1', '오프라인', 1);
INSERT INTO BOARD (reward_coin, x, y, board_type, content, reg_code, title, trade_type, member_id) VALUES (1000, 126.732240699017, 37.5110124805355, '나급해요', '내용2', '2823710500', '제목2', '오프라인', 1);
INSERT INTO BOARD (reward_coin, x, y, board_type, content, reg_code, title, trade_type, member_id) VALUES (1000, 126.732240699017, 37.5110124805355, '나급해요', '내용3', '2823710500', '제목3', '오프라인', 1);
INSERT INTO BOARD (reward_coin, x, y, board_type, content, reg_code, title, trade_type, member_id) VALUES (1000, 126.732240699017, 37.5110124805355, '나급해요', '내용4', '2823710500', '제목4', '오프라인', 1);
insert into BOARD (reward_coin, x, y, board_type, content, reg_code, title, trade_type, member_id) VALUES(1000, 126.732240699017, 37.5110124805355, '나급해요', '내용5', '2823710500', '휴지좀', '오프라인', 1);

INSERT INTO TRADE_STATUS (board_id, requester_id, helper_id, status) VALUES (1, 1, 2, 'BEFORE');
INSERT INTO TRADE_STATUS (board_id, requester_id, helper_id, status) VALUES (2, 1, 3, 'CANCELED');
INSERT INTO TRADE_STATUS (board_id, requester_id, helper_id, status) VALUES (3, 2, 3, 'COMPLETE');
INSERT INTO TRADE_STATUS (board_id, requester_id, helper_id, status) VALUES (4, 3, 4, 'COMPLETE');
INSERT INTO TRADE_STATUS (board_id, requester_id, helper_id, status) VALUES (null, 6, 4, 'COMPLETE');
INSERT INTO TRADE_STATUS (board_id, requester_id, helper_id, status) VALUES (null, 3, 6, 'COMPLETE');
INSERT INTO TRADE_STATUS (board_id, requester_id, helper_id, status) VALUES (null, 6, 4, 'BEFORE');
INSERT INTO TRADE_STATUS (board_id, requester_id, helper_id, status) VALUES (null, 3, 6, 'CANCELED');

INSERT INTO REVIEW (content, rating, trade_status_id, writer_id, receiver_id) VALUES ('내용1', 1.0, 1, 1, 2);
INSERT INTO REVIEW (content, rating, trade_status_id, writer_id, receiver_id) VALUES ('내용2', 1.0, 2, 1, 3);
INSERT INTO REVIEW (content, rating, trade_status_id, writer_id, receiver_id) VALUES ('내용3', 2.0, 2, 3, 1);
INSERT INTO REVIEW (content, rating, trade_status_id, writer_id, receiver_id) VALUES ('내용4', 3.0, 3, 2, 3);

INSERT INTO EXCHANGE (member_id, bank_type, account_number, holder_name, money, status) VALUES (1, '국민', '123456789', '홍길동', 1000, 0);
INSERT INTO EXCHANGE (member_id, bank_type, account_number, holder_name, money, status) VALUES (1, '국민', '123456789', '홍길동', 1000, 1);
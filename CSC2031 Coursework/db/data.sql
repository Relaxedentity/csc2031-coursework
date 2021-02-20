CREATE TABLE userAccounts (
Firstname varchar(50) NOT NULL,
Lastname varchar(50) NOT NULL, 
Email varchar(50) NOT NULL,
Phone varchar(50) NOT NULL,
Username varchar(50) NOT NULL,
Pwd varchar(50) NOT NULL,
UserRole varchar(50) NOT NULL,
PRIMARY KEY (Username, Pwd)
);

CREATE TABLE WinningLottery (
LotteryNum1 varchar(50) NOT NULL,
LotteryNum2 varchar(50) NOT NULL,
LotteryNum3 varchar(50) NOT NULL,
LotteryNum4 varchar(50) NOT NULL,
LotteryNum5 varchar(50) NOT NULL,
LotteryNum6 varchar(50) NOT NULL
);

INSERT INTO WinningLottery VALUES
('1','34','42','57','32','23');


INSERT INTO userAccounts VALUES
('Sara','Fletcher','sara.fletcher@myemail.co.uk','44-0191-5678901','sarafletcher','sFletch123','public');





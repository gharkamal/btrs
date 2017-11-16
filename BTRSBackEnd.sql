DROP DATABASE IF EXISTS BTRS; 
CREATE DATABASE BTRS;
USE BTRS;

DROP TABLE IF EXISTS Account_Holder; 
CREATE TABLE Account_Holder(
accountID INT AUTO_INCREMENT,
fullName VARCHAR(50), 
email VARCHAR(30),
password VARCHAR(30), 
creditCard INT,
UNIQUE KEY(email),
PRIMARY KEY(accountID)
);
ALTER TABLE Account_holder AUTO_INCREMENT=0;

DROP TABLE IF EXISTS Passenger;
CREATE TABLE Passenger(
passengerID INT,
accountID INT REFERENCES Account_Holder(accountID), 
startStID INT,
endStID INT,
seatID INT, 
dateTime date,
wifi boolean,
PRIMARY KEY(passengerID)
);


DROP TABLE IF EXISTS Train;
CREATE TABLE Train(
trainID INT PRIMARY KEY,
deptTime TIME,
isFull BOOL
);


DROP TABLE IF EXISTS Station;
CREATE TABLE Station(
stationID INT PRIMARY KEY,
name VARCHAR(64),
orderNumber INT
);


DROP TABLE IF EXISTS Car;
CREATE TABLE Car(
carNumber INT,
seatID VARCHAR(3),
trainID INT,
passengerID INT DEFAULT NULL,
PRIMARY KEY(carNumber, seatID, trainID),
FOREIGN KEY(trainID) REFERENCES Train(trainID) ON UPDATE CASCADE ON DELETE CASCADE,
FOREIGN KEY(passengerID) REFERENCES Passenger(passengerID) ON UPDATE CASCADE ON DELETE CASCADE 
);


DROP TABLE IF EXISTS Banned; 
CREATE TABLE Banned(
accountID INT REFERENCES Account_Holder(accountID) ON UPDATE CASCADE ON DELETE CASCADE
);


#LOAD DATA LOCAL INFILE './data/account_holders.txt' INTO TABLE Account_Holder;
#LOAD DATA LOCAL INFILE './data/passengers.txt' INTO TABLE Passenger;

INSERT INTO Account_Holder(fullName,email,password,creditCard) values('Yimin Mei','tyranny@sjsu.edu','lucky321',44444);
INSERT INTO Account_Holder(fullName,email,password,creditCard) values('Tygus Banana','jerk@sjsu.edu','neverlucky989',23512);
INSERT INTO Account_Holder(fullName,email,password,creditCard) values('Adobe Chen','estupido@sjsu.edu','ilovechickenjk',12345);

INSERT INTO Passenger(passengerID,accountID,startStID,endStID,seatID,dateTime,wifi) values(9,1,0,1,47,'1111-11-11',false ); 
INSERT INTO Passenger(passengerID,accountID,startStID,endStID,seatID,dateTime,wifi) values(8,2,0,1,46,'1111-11-11',false ); 
INSERT INTO Passenger(passengerID,accountID,startStID,endStID,seatID,dateTime,wifi) values(7,3,0,1,45,'1111-11-11',false ); 


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


LOAD DATA LOCAL INFILE './data/account_holders.txt' INTO TABLE Account_Holder(fullName,email,password,creditCard);
#LOAD DATA LOCAL INFILE './data/passengers.txt' INTO TABLE Passenger(accountID,startStID,endStID,seatID,dateTime,wifi);
#LOAD DATA LOCAL INFILE './data/trains.txt' INTO TABLE Train(trainID,deptTime,isFull);
#LOAD DATA LOCAL INFILE './data/stations.txt' INTO TABLE Station(stationID,name,orderNumber);
#LOAD DATA LOCAL INFILE './data/cars.txt' INTO TABLE Car(carNumber,seatID,trainID);
#LOAD DATA LOCAL INFILE './data/banneds.txt' INTO TABLE Banned(accountID);



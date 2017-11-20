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
passengerID INT AUTO_INCREMENT,
accountID INT REFERENCES Account_Holder(accountID), 
startStID INT,
endStID INT,
seatID INT, 
dateTime date,
wifi boolean,
PRIMARY KEY(passengerID)
);
ALTER TABLE Passenger AUTO_INCREMENT=0;

DROP TABLE IF EXISTS Train;
CREATE TABLE Train(
trainID INT PRIMARY KEY AUTO_INCREMENT,
deptTime TIME,
isFull BOOL DEFAULT FALSE
) AUTO_INCREMENT = 100;


DROP TABLE IF EXISTS Station;
CREATE TABLE Station(
stationID INT PRIMARY KEY AUTO_INCREMENT,
name VARCHAR(64),
orderNumber INT
) AUTO_INCREMENT = 1000;


# Cars, I believe are initially empty.
# When a row is added to "Passenger", the corresponding Trigger..
# ..will add rows to this relation.
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
accountID INT REFERENCES Account_Holder(accountID) ON UPDATE CASCADE ON DELETE CASCADE,
PRIMARY KEY(accountID)
);

DROP PROCEDURE IF EXISTS archivePassengers;
DELIMITER //
CREATE PROCEDURE archivePassengers(IN Passenger
last date)
BEGIN
   INSERT INTO archive(passengerID, accountID, startStID ,endStID ,seatID, dateTime, wifi)
   Select passengerID, accountID, startStID ,endStID ,seatID, dateTime, wifi from Passenger where dateTime <= last;
   Delete from Passenger where updatedAt <= last;
END//
DELIMITER ;


LOAD DATA LOCAL INFILE './data/account_holders.txt' INTO TABLE Account_Holder(fullName,email,password,creditCard);
#LOAD DATA LOCAL INFILE './data/passengers.txt' INTO TABLE Passenger(accountID,startStID,endStID,seatID,dateTime,wifi);
LOAD DATA LOCAL INFILE './data/trains.txt' INTO TABLE Train(deptTime);
LOAD DATA LOCAL INFILE './data/stations.txt' INTO TABLE Station(name,orderNumber);
#LOAD DATA LOCAL INFILE './data/cars.txt' INTO TABLE Car(carNumber,seatID,trainID);
#LOAD DATA LOCAL INFILE './data/banneds.txt' INTO TABLE Banned(accountID);



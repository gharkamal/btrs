SET sql_mode='';
DROP DATABASE IF EXISTS BTRS; 
CREATE DATABASE BTRS;
USE BTRS;

DROP TABLE IF EXISTS Account_Holder; 
CREATE TABLE Account_Holder(
accountID INT AUTO_INCREMENT,
firstName VARCHAR(50), 
lastName VARCHAR(50),
email VARCHAR(30),
password VARCHAR(30), 
creditCard VARCHAR(30),
age INT, 
admin boolean,
UNIQUE KEY(email),
PRIMARY KEY(accountID)
);
ALTER TABLE Account_holder AUTO_INCREMENT=1000;


DROP TABLE IF EXISTS Passenger;
CREATE TABLE Passenger(
passengerID INT AUTO_INCREMENT,
accountID INT REFERENCES Account_Holder(accountID),
# startStID INT,
endStID INT REFERENCES Station(stationID),
# seatID INT,
# dateTime date,
wifi boolean,
PRIMARY KEY(passengerID)
);
ALTER TABLE Passenger AUTO_INCREMENT=500;

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
FOREIGN KEY(trainID) REFERENCES Train(trainID) ON UPDATE CASCADE ON DELETE CASCADE
);


DROP TABLE IF EXISTS Banned; 
CREATE TABLE Banned(
accountID INT REFERENCES Account_Holder(accountID) ON UPDATE CASCADE ON DELETE CASCADE,
PRIMARY KEY(accountID)
);

DROP PROCEDURE IF EXISTS archivePassengers;
DELIMITER //
CREATE PROCEDURE archivePassengers(IN last date)
BEGIN
   INSERT INTO archive(passengerID, accountID, startStID ,endStID ,seatID, dateTime, wifi)
   Select passengerID, accountID, startStID ,endStID ,seatID, dateTime, wifi from Passenger where dateTime <= last;
   Delete from Passenger where updatedAt <= last;
END//
DELIMITER ;

DROP PROCEDURE IF EXISTS checkValidUpdate;
DELIMITER //
CREATE PROCEDURE checkValidUpdate(
  IN seatID VARCHAR(3),
  IN carNumber INT,
  IN trainID INT
)
  BEGIN
    IF NOT (seatID REGEXP '^([1-9]|10|11|12|13|14)[A-D]$' # Not very nice regex because of posix style..
            AND carNumber BETWEEN 0 AND 3) THEN
      SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Invalid seat/car number!';
    ELSEIF trainID IN (SELECT Train.trainID FROM Train WHERE isFull) THEN
      SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'The train is full!';
    END IF;
  END;
//
DELIMITER ;


#if a person is passenger and they are banned they will be removed
DROP Trigger if exists PassengerRemoval;
delimiter //
CREATE Trigger PassengerRemoval
After insert on Banned 
for each row
BEGIN
delete from Passenger where new.accountID = accountID;
END; //
delimiter ;

DROP TRIGGER IF EXISTS InsertCarTrigger;
delimiter //
CREATE TRIGGER InsertCarTrigger
  BEFORE INSERT ON Car
  FOR EACH ROW
  CALL checkValidUpdate(NEW.seatID, NEW.carNumber, NEW.trainID); //
  delimiter ;

DROP TRIGGER IF EXISTS UpdateCarTrigger;
delimiter //
CREATE TRIGGER UpdateCarTrigger
  BEFORE UPDATE ON Car
  FOR EACH ROW
  CALL checkValidUpdate(NEW.seatID, NEW.carNumber, NEW.trainID);//
  delimiter ;

# 224 is the total number of seats in each train.
DROP TRIGGER IF EXISTS CarFullTrigger;
DELIMITER //
CREATE TRIGGER CarFullTrigger
  AFTER INSERT ON Car
  FOR EACH ROW
  BEGIN
    IF (SELECT COUNT(*) FROM Car WHERE trainID = NEW.trainID) = 224 THEN
      UPDATE Train SET isFull = 1 WHERE trainID = NEW.trainID;
    END IF;
  END; //
  
delimiter ;

DROP TRIGGER IF Exists CarChangeSeatTrigger;
DELIMITER //
CREATE TRIGGER CarChangeSeatTrigger
  AFTER UPDATE ON Car
  FOR EACH ROW
  BEGIN
    IF (SELECT COUNT(*) FROM Car WHERE trainID = NEW.trainID) = 224 THEN
      UPDATE Train SET isFull = 1 WHERE trainID = NEW.trainID;
    END IF; # ending if here because we want these two ↑↓ checks to be performed.
    IF OLD.trainID IN (SELECT trainID FROM Train WHERE isFull) THEN
      UPDATE Train SET isFull = 0 WHERE trainID = OLD.trainID;
    END IF;
  END ; //
DELIMITER ;


DROP TRIGGER IF EXISTS CarNoFullTrigger;
DELIMITER //
CREATE TRIGGER CarNoFullTrigger
  AFTER DELETE ON Car
  FOR EACH ROW
  BEGIN
    IF OLD.trainID IN (SELECT trainID FROM Train WHERE isFull) THEN
      UPDATE Train SET isFull = 0 WHERE trainID = OLD.trainID;
    END IF;
  END ; //
DELIMITER ;


LOAD DATA LOCAL INFILE '/Users/Grewal/Documents/SJSU/Fall 2017/CS157A/btrs/data/account_holders.txt' INTO TABLE Account_Holder(firstName, lastName,email,password,creditCard);
LOAD DATA LOCAL INFILE '/Users/Grewal/Documents/SJSU/Fall 2017/CS157A/btrs/data/passengers.txt' INTO TABLE Passenger(accountID,endStId,wifi);
LOAD DATA LOCAL INFILE '/Users/Grewal/Documents/SJSU/Fall 2017/CS157A/btrs/data/trains.txt' INTO TABLE Train(deptTime);
LOAD DATA LOCAL INFILE '/Users/Grewal/Documents/SJSU/Fall 2017/CS157A/btrs/data/stations.txt' INTO TABLE Station(name,orderNumber);
LOAD DATA LOCAL INFILE '/Users/Grewal/Documents/SJSU/Fall 2017/CS157A/btrs/data/cars.txt' INTO TABLE Car(carNumber,seatID,trainID,passengerID);
LOAD DATA LOCAL INFILE '/Users/Grewal/Documents/SJSU/Fall 2017/CS157A/btrs/data/banned.txt' INTO TABLE Banned(accountID);




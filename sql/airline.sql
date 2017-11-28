CREATE DATABASE airline;

#create customer table
CREATE TABLE customer (
	customerID MEDIUMINT NOT NULL,
    firstName VARCHAR(15) NOT NULL,
    lastName VARCHAR(20) NOT NULL,
    birthdate DATE NOT NULL,
    member TINYINT(1),
    wheelchair TINYINT(1),
    oxygen TINYINT(1),
    PRIMARY KEY (customerID)
);

#create purchase table
CREATE TABLE purchase (
	reservationID MEDIUMINT NOT NULL,
    customerID MEDIUMINT NOT NULL,
    paymentMethod ENUM('Cash','Credit','Debit') NOT NULL,
    paymentDate DATE NOT NULL,
    PRIMARY KEY (reservationID)
);

#create charge table
CREATE TABLE charge (
	reservationID MEDIUMINT NOT NULL,
    customerID MEDIUMINT NOT NULL,
    memberdiscount FLOAT(4,2),
    refund FLOAT(6,2),
    weightFee FLOAT(6,2),
    insuranceFee FLOAT(6,2),
    ticketPrice FLOAT(6,2) NOT NULL,
    PRIMARY KEY (reservationID)
);

#create passenger table
CREATE TABLE passenger (
	customerID MEDIUMINT NOT NULL,
    reservationID MEDIUMINT NOT NULL,
    carryWeight TINYINT,
    class ENUM('Economy','Premium Economy','Business','First','Family') NOT NULL,
    PRIMARY KEY (customerID, reservationID)
);

#create reservation table
CREATE TABLE reservation (
	reservationID MEDIUMINT NOT NULL,
    flightID MEDIUMINT NOT NULL,
    cancelled TINYINT(1),
    PRIMARY KEY (reservationID)
);

#create flight table
CREATE TABLE flight (
	flightID MEDIUMINT NOT NULL,
    aircraftID MEDIUMINT NOT NULL,
    sourceAirport MEDIUMINT NOT NULL,
    destAirport MEDIUMINT NOT NULL,
    liftOffTime DATETIME NOT NULL,
    landTime DATETIME NOT NULL,
    PRIMARY KEY (flightID)
);

#create flight sequence table
CREATE TABLE flightSequence (
	reservationID MEDIUMINT NOT NULL,
    flightID MEDIUMINT NOT NULL,
    sequenceNum MEDIUMINT NOT NULL,
    PRIMARY KEY (reservationID, flightID)
);

#create class prices table
CREATE TABLE classPrices (
	class ENUM('Economy','Premium Economy','Business','First','Family') NOT NULL,
    flightID MEDIUMINT NOT NULL,
    price FLOAT(6,2) NOT NULL,
    PRIMARY KEY (class)
);

#create flight assignment table
CREATE TABLE flightAssignment (
	flightID MEDIUMINT NOT NULL,
    empID MEDIUMINT NOT NULL,
    PRIMARY KEY (flightID, empID)
);

#create employee table
CREATE TABLE employee (
	empID MEDIUMINT NOT NULL,
    prevFlightID MEDIUMINT NOT NULL,
    positionID ENUM('Staff','Flight Attendant','Pilot','Mechanic') NOT NULL,
    firstName VARCHAR(15) NOT NULL,
    lastName VARCHAR(20) NOT NULL,
    salary FLOAT(10,2) NOT NULL,
    PRIMARY KEY (empID)
);

#create position dress code table
CREATE TABLE positionDC (
	positionID ENUM('Staff','Flight Attendant','Pilot','Mechanic') NOT NULL,
    dressCode VARCHAR(20) NOT NULL,
    PRIMARY KEY (positionName)
);

#create airport assignment table
CREATE TABLE airportAssignment (
	airportID MEDIUMINT NOT NULL,
    empID MEDIUMINT NOT NULL,
    PRIMARY KEY (airportID, empID)
);

#create airport table
CREATE TABLE airport (
	airportID MEDIUMINT NOT NULL,
    airportName VARCHAR(20) NOT NULL,
    latitude TINYINT NOT NULL,
    longitude TINYINT NOT NULL,
    PRIMARY KEY (airportID)
);

#create aircraft table
CREATE TABLE aircraft (
	aircraftID MEDIUMINT NOT NULL,
    mileage MEDIUMINT NOT NULL,
    routingRange SMALLINT NOT NULL,
    firstClassSeats TINYINT,
    businessSeats TINYINT,
    familySeats TINYINT,
    premiumEconSeats TINYINT,
    econSeats TINYINT,
    PRIMARY KEY (aircraftID)
);

#create service table
CREATE TABLE service (
	aircraftID MEDIUMINT NOT NULL,
    serviceType VARCHAR(20) NOT NULL,
    PRIMARY KEY (aircraftID, serviceType)
);
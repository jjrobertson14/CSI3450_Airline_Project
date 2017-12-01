CREATE DATABASE flights; 

use flights;

CREATE TABLE Customer(
    customerID int primary key auto_increment,
    firstName varchar(100) not null, 
    lastName varchar(100) not null,
    birthDate date not null,
    member boolean not null,
    wheelchair boolean not null,
    oxygen boolean not null
);

CREATE TABLE EmployeePosition(
    positionID int primary key auto_increment,
    dressCode varchar(100) not null
);

CREATE TABLE Airport(
    airportID int primary key auto_increment,
    airportName varchar(150) not null,
    latitude decimal not null,
    longitude decimal not null
);

CREATE TABLE Aircraft(
    aircraftID int primary key auto_increment,
    mileage decimal not null,
    routingRange decimal not null,
    firstClassSeats int not null,
    businessSeats int not null,
    familySeats int not null,
    premiumSeats int not null,
    econSeats int not null
);

CREATE TABLE Flight(
    flightID int primary key auto_increment,
    aircraftID int not null,
    sourceAirportID int not null,
    destAirportID int not null,
    departureTime datetime not null,
    arrivalTime datetime not null,
    cancelled boolean not null default false,
    foreign key (aircraftID) references Aircraft(AircraftID),
    foreign key (sourceAirportID) references Airport(AirportID),
    foreign key (destAirportID) references Airport(AirportID)
);

CREATE TABLE Reservation(
    reservationID int primary key auto_increment,
    flightID int not null,
    cancelled boolean not null,
    foreign key (flightID) references Flight(flightID)
);

CREATE TABLE Purchase(
    reservationID int not null primary key,
    customerID int not null,
    paymentMethod enum ('debit', 'credit'),
    paymentDate date,
    foreign key (reservationID) references Reservation(reservationID),
    foreign key (customerID) references Customer(customerID)
);

CREATE TABLE Charge(
    reservationID int,
    customerID int not null,
    memberDiscount decimal,
    childDiscount decimal,
    multiWayDiscount decimal,
    refund decimal,
    weightFee decimal,
    insuranceFee decimal,
    ticketPrice decimal not null,
    foreign key (reservationID) references Reservation(reservationID),
    foreign key (customerID) references Customer(customerID),
    primary key (reservationID, customerID)
);

CREATE TABLE Passenger(
    reservationID int not null,
    customerID int not null,
    carryWeight decimal not null,
    class enum (
        'first',
        'business',
        'family',
        'premium',
        'economy'
    ),
    foreign key (reservationID) references Reservation(reservationID),
    foreign key (customerID) references Customer(customerID),
    primary key (reservationID, customerID)
);

CREATE TABLE FlightSequence(
    reservationID int not null,
    flightID int not null,
    sequenceNum int not null,
    foreign key (reservationID) references Reservation(reservationID),
    foreign key (flightID) references Flight(flightID)
);

CREATE TABLE FlightDeparted(
	flightID int primary key auto_increment,
    departTime datetime not null,
    foreign key (flightID) references Flight(flightID)
);

CREATE TABLE FlightArrived(
	flightID int primary key auto_increment,
    arriveTime datetime not null,
    foreign key (flightID) references Flight(flightID)
);

CREATE TABLE ClassPrices(
    class enum (
        'first',
        'business',
        'family',
        'premium',
        'economy'
    ) not null,
    flightID int not null,
    price decimal not null,
    foreign key (flightID) references Flight(flightID),
    primary key(class, flightID)
);

CREATE TABLE Employee(
    empID int primary key auto_increment,
    prevFlightID int null,
    positionID int not null,
    firstName varchar(100) not null,
    lastName varchar(100) not null,
    foreign key (prevFlightID) references Flight(flightID),
    foreign key (positionID) references EmployeePosition(positionID)
);

CREATE TABLE FlightAssignment(
    flightID int not null,
    empID int not null,
    foreign key (flightID) references Flight(flightID),
    foreign key (empID) references Employee(empID)
);

CREATE TABLE AirportAssignment(
    airportID int not null,
    empID int not null,
    foreign key (airportID) references Airport(airportID),
    foreign key (empID) references Employee(empID)
);

CREATE TABLE `Service` (
    aircraftID int not null,
    serviceType enum(
        'wifi',
        'drinks',
        'dining',
        'power outlets',
        'snacks',
        'movies'
    ) not null,
    foreign key (aircraftID) references Aircraft(aircraftID)
);


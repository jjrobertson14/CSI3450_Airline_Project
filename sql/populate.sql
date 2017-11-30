#populate customer table
INSERT INTO Customer (firstName, lastName, birthDate, member, wheelchair, oxygen)
VALUES
('John', 'Muhehe', DATE '1981-06-25', true, true, false),
('Sally', 'Johnston', DATE '1985-05-02', false, false, false),
('Mike', 'George', DATE '1966-01-31', true, true, true),
('Miki', 'Temari', DATE '2001-04-26', false, false, false),
('Olga', 'Hayward', DATE '1970-11-09', true, true, false),
('Robert', 'White', DATE '1977-09-14', false, false, false),
('Jun', 'Haruno', DATE '1989-07-13', true, false, false),
('Hans', 'Bronislav', DATE '1969-02-17', true, false, true),
('John', 'French', DATE '1980-01-20', false, false, false),
('Samuel', 'Oak', DATE '1996-02-27', true, false, false),
('Maka', 'Albarn', DATE '2004-05-12', true, false, false),
('Gene', 'Starwind', DATE '1996-09-20', true, false, false);

#populate aircraft table
INSERT INTO Aircraft (mileage, routingRange, firstClassSeats, businessSeats, familySeats, premiumSeats, econSeats)
VALUES
(789000, 7800, 12, 24, 60, 12, 20),
(13790000, 9000, 20, 20, 30, 20, 30),
(465000, 8000, 12, 15, 20, 12, 20),
(678550, 9000, 16, 22, 30, 10, 25),
(1798000, 10000, 20, 20, 30, 30, 30),
(123900, 12000, 15, 15, 15, 15, 15),
(145000, 5500, 12, 12, 15, 12, 15),
(1900000, 8000, 20, 30, 30, 20, 30),
(1235000, 7500, 15, 30, 0, 30, 10),
(1800000, 9000, 10, 15, 15, 15, 25);

#populate airport table
INSERT INTO Airport (airportName, latitude, longitude)
VALUES
('Hollister Municipal', 36.89, -121.41),
('Abraham Lincoln Capital', 39.84, -89.68),
('Airglades', 26.44, -81.03),
('Acadiana Regional', 30.03, -91.88),
('Minot International', 48.26, -101.28),
('Barth', 54.34, 12.71),
('Auxerre-Branches', 47.85, 3.50),
('Belfast International', 54.66, -6.22),
('Cancún International', 21.04, -86.88),
('Altamira', -3.25, -52.25); 

#populate flights table
INSERT INTO Flight (aircraftID, sourceAirportID, destAirportID, liftOffTime, landTime)
VALUES
(1, 9, 10, '10:00:00', '20:00:00'),
(2, 3, 8, '08:00:00', '16:30:00'),
(3, 5, 1, '03:30:00', '07:00:00'),
(4, 6, 5, '01:00:00', '11:30:00'),
(5, 7, 4, '05:00:00', '13:00:00'),
(6, 8, 5, '09:30:00', '18:30:00'),
(7, 7, 1, '12:30:00', '21:00:00'),
(8, 8, 9, '05:00:00', '12:30:00'),
(9, 10, 2, '04:30:00', '08:30:00'),
(10, 1, 2, '03:30:00', '07:30:00');


#populate employee position table
INSERT INTO EmployeePosition (dressCode)
VALUES
('Dress shirt, slacks'),
('Flight attendant uniform'),
('Pilot uniform'),
('Mechanic uniform');

#populate employee table
INSERT INTO Employee (prevFlightID, positionID, firstName, lastName)
VALUES
(1, 2, 'Mary', 'Read'),
(2, 2, 'Yumi', 'Matsurika'),
(3, 2, 'Amy', 'Greenfield'),
(4, 1, 'Sarah', 'Sterling'),
(5, 1, 'Mark', 'Meadows'),
(6, 1, 'Gary', 'Romero'),
(7, 3, 'Adam', 'Mitchell'),
(8, 3, 'Martha', 'Croswell'),
(9, 4, 'Steve', 'Gregg'),
(10, 4, 'Mark', 'Kelly');

#populate airport assignment table
INSERT INTO AirportAssignment (airportID, empID)
VALUES
(1, 10),
(2, 9),
(3, 8),
(4, 7),
(5, 6),
(6, 5),
(7, 4),
(8, 3),
(9, 2),
(10, 1);

#populate flight assignment table
INSERT INTO FlightAssignment (flightID, empID)
VALUES
(1, 9),
(2, 10),
(3, 1),
(4, 2),
(5, 3),
(6, 4),
(7, 5),
(8, 6),
(9, 7),
(10, 8);

#populate flight departed table
INSERT INTO FlightDeparted (flightID, departTime)
VALUES
(1, '10:08:33'),
(2, '08:01:05'),
(3, '03:33:03'),
(4, '01:00:19'),
(5, '05:07:00'),
(6, '09:32:20'),
(7, '12:35:01'),
(8, '04:59:03'),
(9, '04:38:12'),
(10, '03:40:17');

#populate flight arrived table
INSERT INTO FlightArrived (flightID, arriveTime)
VALUES
(1, '20:04:12'),
(2, '16:35:00'),
(3, '06:57:11'),
(4, '11:23:59'),
(5, '13:04:09'),
(6, '18:31:44'),
(7, '21:00:55'),
(8, '12:32:16'),
(9, '08:25:06'),
(10, '07:31:30');

#populate class prices table
INSERT INTO ClassPrices (class, flightID, price)
VALUES
('first', 1, 150.00),
('business', 1, 125.00),
('family', 1, 110.00),
('premium', 1, 100.00),
('economy', 1, 85.00),
('first', 2, 150.00),
('business', 2, 125.00),
('family', 2, 110.00),
('premium', 2, 100.00),
('economy', 2, 85.00),
('first', 3, 150.00),
('business', 3, 125.00),
('family', 3, 110.00),
('premium', 3, 100.00),
('economy', 3, 85.00),
('first', 4, 150.00),
('business', 4, 125.00),
('family', 4, 110.00),
('premium', 4, 100.00),
('economy', 4, 85.00),
('first', 5, 150.00),
('business', 5, 125.00),
('family', 5, 110.00),
('premium', 5, 100.00),
('economy', 5, 85.00),
('first', 6, 150.00),
('business', 6, 125.00),
('family', 6, 110.00),
('premium', 6, 100.00),
('economy', 6, 85.00),
('first', 7, 150.00),
('business', 7, 125.00),
('family', 7, 110.00),
('premium', 7, 100.00),
('economy', 7, 85.00),
('first', 8, 150.00),
('business', 8, 125.00),
('family', 8, 110.00),
('premium', 8, 100.00),
('economy', 8, 85.00),
('first', 9, 150.00),
('business', 9, 125.00),
('family', 9, 110.00),
('premium', 9, 100.00),
('economy', 9, 85.00),
('first', 10, 150.00),
('business', 10, 125.00),
('family', 10, 110.00),
('premium', 10, 100.00),
('economy', 10, 85.00);

#populate service table
INSERT INTO `Service` (aircraftID, serviceType)
VALUES
(1, 'wifi'),
(2, 'dining'),
(3, 'drinks'),
(4, 'snacks'),
(5, 'wifi'),
(6, 'power outlets'),
(7, 'wifi'),
(8, 'movies'),
(9, 'snacks'),
(10, 'drinks');

#populate reservation table
INSERT INTO Reservation (flightID, cancelled)
VALUES
(6, false),
(7, false),
(8, false),
(9, false),
(10, false),
(1, false),
(2, false),
(3, false),
(4, false),
(5, true);

#populate purchase table
INSERT INTO Purchase (reservationID, customerID, paymentMethod, date)
VALUES
(1, 4, 'credit', DATE '2017-12-07'),
(2, 9, 'credit', DATE '2017-12-07'),
(3, 1, 'debit', DATE '2017-12-07'),
(4, 12, 'credit', DATE '2017-12-07'),
(5, 3, 'debit', DATE '2017-12-07'),
(6, 5, 'credit', DATE '2017-12-08'),
(7, 2, 'credit', DATE '2017-12-07'),
(8, 6, 'credit', DATE '2017-12-08'),
(9, 9, 'debit', DATE '2017-12-08'),
(10, 7, 'debit', DATE '2017-12-06');

#populate passenger table
INSERT INTO Passenger (customerID, reservationID, carryWeight, class)
VALUES
(4, 1, 10.5, 'business'),
(9, 2, 5.1, 'economy'),
(1, 3, 4.2, 'premium'),
(12, 4, 9.1, 'family'),
(3, 5, 6.6, 'first'),
(5, 6, 1.1, 'economy'),
(2, 7, 12.0, 'first'),
(6, 8, 8.3, 'business'),
(9, 9, 5.1, 'premium'),
(7, 10, 3.3, 'family');

#populate charge table
INSERT INTO Charge (reservationID, customerID, memberDiscount, refund, weightFee, insuranceFee, ticketPrice)
VALUES
(1, 4, null, 125.00, 15.00, 12.50, 125.00),
(2, 9, null, 85.00, 15.00, 8.50, 85.00),
(3, 1, 00.15, 100.00, 15.00, 10.00, 100.00),
(4, 12, 00.15, 110.00, 15.00, 11.00, 110.00),
(5, 3, 00.15, 150.00, 15.00, 15.00, 150.00),
(6, 5, 00.15, 85.00, 15.00, 8.50, 85.00),
(7, 2, null, 150.00, 15.00, 15.00, 150.00),
(8, 6, null, 125.00, 15.00, 12.50, 125.00),
(9, 9, null, 100.00, 15.00, 10.00, 100.00),
(10, 7, 00.15, 110.00, 15.00, 11.00, 110.00);

#populate flight sequence table
INSERT INTO FlightSequence (reservationID, flightID, sequenceNum)
VALUES
(1, 2, 1),
(1, 3, 2),
(1, 7, 3),
(2, 5, 1),
(2, 6, 2),
(3, 6, 1),
(3, 9, 2),
(4, 10, 1),
(4, 5, 2),
(4, 2, 3),
(5, 1, 1),
(5, 9, 2),
(5, 6, 3);
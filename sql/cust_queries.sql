#CUSTOMER ITINERARY LOOKUP
#find source airport
SELECT airportName FROM Airport
JOIN Flight ON Airport.airportID = Flight.sourceAirportID
JOIN Reservation ON Flight.flightID = Reservation.flightID
JOIN Passenger ON Reservation.reservationID = Passenger.reservationID
WHERE Passenger.customerID = ?;

#find destination airport
SELECT airportName FROM Airport
JOIN Flight ON Airport.airportID = Flight.destAirportID
JOIN Reservation ON Flight.flightID = Reservation.flightID
JOIN Passenger ON Reservation.reservationID = Passenger.reservationID
WHERE Passenger.customerID = ?;

#find lift-off time
SELECT liftOffTime FROM Flight
JOIN Reservation ON Flight.flightID = Reservation.flightID
JOIN Passenger ON Reservation.reservationID = Passenger.reservationID
WHERE Passenger.customerID = ?;

#find landing time
SELECT landTime FROM Flight
JOIN Reservation ON Flight.flightID = Reservation.flightID
JOIN Passenger ON Reservation.reservationID = Passenger.reservationID
WHERE Passenger.customerID = ?;

#CUSTOMER MEMBERSHIP ENROLLMENT
UPDATE Customer
SET member = 0
WHERE customerID = ?;

#CUSTOMER PAYMENT FOR RESERVATION
#determine price
SELECT ticketPrice FROM Charge
WHERE customerID = ? AND ReservationID = ?;

#add insurance if applicable
SELECT insuranceFee FROM Charge
WHERE customerID = ? AND ReservationID = ?;

#add carry weight fee if applicable
SELECT weightFee FROM Charge
WHERE customerID = ? AND ReservationID = ?;

#subtract member discount amount
SELECT memberDiscount FROM Charge
WHERE customerID = 7 AND ReservationID = 10;

#record payment information
INSERT INTO Purchase
VALUES (?, ?, ?, CURDATE());

#CUSTOMER CANCELLATION
#cancel reservation
DELETE FROM Reservation
WHERE customerID = ?

#fee if cancelling more than 10 hours before flight

#fee if cancelling less than 10 hours before flight
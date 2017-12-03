#A collection of 'non-trivial' queries taken from src/main/java/AirlineSQLExecutor.java
#These queries may not be suitable for execution independently, as many are intended to 
#be executed in conjunction with related queries
#Name: application_queries.java
#Location: sql/
#Date: Dec 1st, 2017

USE Flights

# Get all customers who have seats reserved on a given flight.
SELECT DISTINCT customerID,firstName,lastName,birthDate,member,wheelchair,oxygen FROM ( 
                    SELECT Customer.customerID,Customer.firstName,Customer.lastName,Customer.birthDate,Customer.member,Customer.wheelchair,Customer.oxygen FROM Flight
                    JOIN Reservation ON Flight.flightID = 1<flightNo to check for>
                    JOIN Passenger USING (ReservationID)
                    JOIN Customer USING (customerID)
) AS sub

# Get all the details about the crew members on that flight.
SELECT empID,prevFlightID,positionID,firstName,lastName,dressCode FROM (
	SELECT FlightAssignment.empID,Employee.prevFlightID,Employee.positionID,Employee.firstName,Employee.lastName,EmployeePosition.dressCode FROM Flight JOIN FlightAssignment ON Flight.flightID = 1
	JOIN Employee USING (empID)
	JOIN EmployeePosition USING (positionID)
) AS sub1


#departure time is scheduled departure time
#depart time is recorded depart time

# Get all flights for a given airport.
SELECT flightID,aircraftID,destAirportID,sourceAirportID,liftOffTime,landTime,departTime,arriveTime FROM (
	SELECT flightID,aircraftID,destAirportID,sourceAirportID,liftOffTime,landTime,departTime,arriveTime FROM Flight 
	JOIN FlightDeparted USING (flightID)
	JOIN FlightArrived USING (flightID)
	WHERE Flight.sourceAirportID = 9 <airport number to check for>
) AS sub2

# View flight rooster, schedule. 
# Note: assuming this means the schedule of all flights
# TODO: have this return airport names
SELECT Flight.flightID,aircraftIDsourceAirportID,destAirportID,departureTime,departTime,arrivalTime,arriveTime FROM Flight 
	JOIN FlightDeparted USING (flightID)
	JOIN FlightArrived USING (flightID)

# Get all flights whose arrival and departure times are on time.
SELECT Flight.flightID,aircraftID,sourceAirportID,destAirportID,departureTime,departTime,arrivalTime,arriveTime FROM Flight 
	JOIN FlightDeparted USING (flightID)
	JOIN FlightArrived USING (flightID)
	WHERE departTime <= departureTime AND arriveTime <= arrivalTime

#Get all flights whose arrival and departure times are delayed.
SELECT Flight.flightID,aircraftID,sourceAirportID,destAirportID,departureTime,departTime,arrivalTime,arriveTime FROM Flight 
	JOIN FlightDeparted USING (flightID)
	JOIN FlightArrived USING (flightID)
	WHERE departTime > departureTime OR arriveTime > arrivalTime

# Calculate total sales for a given flight.
# only the refund sum on the row that is cancelled is subtracted from total sales
SELECT SUM(refund),SUM(ticketPrice),SUM(insuranceFee),SUM(weightFee),Reservation.cancelled FROM Flight
	JOIN Reservation USING (flightID)
	JOIN Charge USING (reservationID)
	WHERE flightID = 2#<flight number to check for>;
GROUP BY Reservation.cancelled;

#Update Customer membership
UPDATE flights.Customer SET member=isMember   
WHERE customerID=customerID

 #Get available flights
SELECT * FROM flights.Flight 
WHERE flightID NOT IN ( SELECT flightID FROM flights.FlightDeparted ) 
AND cancelled=false

#Get available flights with room in the given seat class
SELECT * FROM flights.flight f WHERE aircraftID IN (SELECT aircraftID FROM 
flights.aircraft WHERE   column   - (SELECT count(*) FROM flights.passenger WHERE reservationID 
IN (SELECT reservationID FROM flights.reservation WHERE flightID = f.flightID AND 
class='  ticketClass ')) >= seats ) AND cancelled = false 
AND f.flightID NOT IN (SELECT flightID from flights.FlightDeparted)

#Get pending flights
SELECT * FROM flights.flight WHERE flightID NOT IN 
(SELECT flightID FROM flights.FlightDeparted) AND 
flightID IN (SELECT flightID FROM flights.Reservation 
WHERE reservationID IN (SELECT reservationID FROM flights.Passenger 
WHERE customerID=  customerID)) 
AND cancelled=false;

#Get departed flights
SELECT * FROM flights.Flight 
WHERE flightID IN ( SELECT flightID FROM flights.FlightDeparted ) 
AND flightID NOT IN ( SELECT flightID FROM flights.FlightArrived) 
AND cancelled=false

#update a flight schedule
UPDATE flights.Flight SET 
departureTime=Timestamp('  depart  '), 
arrivalTime=Timestamp('  arrive  ') 
WHERE flightID=  flightID;

#Drop passengers from the flight
DELETE FROM flights.Passenger 
WHERE reservationID IN (SELECT reservationID FROM flights.Reservation 
WHERE flightID=  flightID)

#Get crew at airport
SELECT * FROM flights.Employee WHERE empID in 
(SELECT empID FROM flights.AirportAssignment WHERE airportID=
airportID  ) AND positionID <> 3

#Get available crew at airport
SELECT * FROM flights.Employee WHERE empID IN 
(SELECT empID FROM flights.AirportAssignment WHERE airportID=airportID) 
AND positionID <> 3 AND empID NOT IN (SELECT empID FROM flights.FlightAssignment 
WHERE flightID NOT IN ( SELECT flightID FROM flights.FlightArrived))

#Get pilots at airport
SELECT * FROM flights.Employee WHERE empID in 
(SELECT empID FROM flights.AirportAssignment WHERE airportID=
airportID  ) AND positionID in (SELECT positionID from 
flights.EmployeePosition WHERE dressCode='Pilot uniform')

#Get available pilots at airport


#Assign employees from a flight to an airport
UPDATE flights.airportassignment SET airportID = airportID   
WHERE empID IN (SELECT empID FROM flights.flightassignment WHERE flightID = flightID  )

#Get crew on flight
SELECT * FROM flights.Employee WHERE 
empID IN (SELECT empID FROM flights.FlightAssignment 
WHERE flightID = flightID  ) 
AND positionID <> 3

#Get Pilot on flight
SELECT * FROM flights.Employee WHERE 
positionID=3 AND empID IN (SELECT empID from flights.FlightAssignment 
WHERE flightID=flightID )

# Get 'pending' reservations - i.e. those where the flight has not taken off
SELECT * FROM flights.reservation WHERE reservationID IN 
(SELECT reservationID from flights.Purchase WHERE customerID=  customerID  ) 
AND flightID NOT IN (SELECT flightID from flights.FlightDeparted) 
AND cancelled=false;

# Apply a cancellation fee to all charges associated with the given reservationID
UPDATE flights.Charge SET 
refund=ticketPrice  insuranceFee  weightFee - memberDiscount - childDiscount 
- multiwayDiscount, cancellationFee=  cancellationFee  * ticketPrice 
WHERE reservationID=  reservationID

# Apply refunds to cancelled flights
UPDATE flights.Charge SET 
refund=ticketPrice  insuranceFee  weightFee - memberDiscount - childDiscount 
- multiwayDiscount WHERE reservationID IN (SELECT reservationID FROM flights.Reservation 
WHERE flightID=  flightID 

)
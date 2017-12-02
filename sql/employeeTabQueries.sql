USE Flights

# Get all customers who have seats reserved on a given flight.
#SELECT DISTINCT customerID,firstName,lastName,birthDate,member,wheelchair,oxygen FROM ( 
#                    SELECT Customer.customerID,Customer.firstName,Customer.lastName,Customer.birthDate,Customer.member,Customer.wheelchair,Customer.oxygen FROM Flight
#                    JOIN Reservation ON Flight.flightID = 1#<flightNo to check for>
#                    JOIN Passenger USING (ReservationID)
#                    JOIN Customer USING (customerID)
#) AS sub

# Get all the details about the crew members on that flight.
#SELECT empID,prevFlightID,positionID,firstName,lastName,dressCode FROM (
#	SELECT FlightAssignment.empID,Employee.prevFlightID,Employee.positionID,Employee.firstName,Employee.lastName,EmployeePosition.dressCode FROM Flight JOIN FlightAssignment ON Flight.flightID = 1
#	JOIN Employee USING (empID)
#	JOIN EmployeePosition USING (positionID)
#) AS sub1


#departure time is scheduled departure time
#depart time is recorded depart time

# Get all flights for a given airport.
#SELECT flightID,aircraftID,destAirportID,sourceAirportID,liftOffTime,landTime,departTime,arriveTime FROM (
#	SELECT flightID,aircraftID,destAirportID,sourceAirportID,liftOffTime,landTime,departTime,arriveTime FROM Flight 
#	JOIN FlightDeparted USING (flightID)
#	JOIN FlightArrived USING (flightID)
#	WHERE Flight.sourceAirportID = 9 #<airport number to check for>
#) AS sub2

# View flight rooster, schedule. 
# Note: assuming this means the schedule of all flights
# TODO: have this return airport names
#SELECT Flight.flightID,aircraftIDsourceAirportID,destAirportID,departureTime,departTime,arrivalTime,arriveTime FROM Flight 
#	JOIN FlightDeparted USING (flightID)
#	JOIN FlightArrived USING (flightID)

# Get all flights whose arrival and departure times are on time.
#SELECT Flight.flightID,aircraftID,sourceAirportID,destAirportID,departureTime,departTime,arrivalTime,arriveTime FROM Flight 
#	JOIN FlightDeparted USING (flightID)
#	JOIN FlightArrived USING (flightID)
#	WHERE departTime <= departureTime AND arriveTime <= arrivalTime

# Get all flights whose arrival and departure times are delayed.
#SELECT Flight.flightID,aircraftID,sourceAirportID,destAirportID,departureTime,departTime,arrivalTime,arriveTime FROM Flight 
#	JOIN FlightDeparted USING (flightID)
#	JOIN FlightArrived USING (flightID)
#	WHERE departTime > departureTime OR arriveTime > arrivalTime

# Calculate total sales for a given flight.
# only the refund sum on the row that is cancelled is subtracted from total sales
SELECT SUM(refund),SUM(ticketPrice),SUM(insuranceFee),SUM(weightFee),Reservation.cancelled FROM Flight
	JOIN Reservation USING (flightID)
	JOIN Charge USING (reservationID)
	#WHERE flightID = 1#<flight number to check for>;
GROUP BY Reservation.cancelled;

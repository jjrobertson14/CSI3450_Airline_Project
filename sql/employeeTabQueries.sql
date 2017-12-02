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

# Get all flights for a given airport.
SELECT flightID,aircraftID,destAirportID,sourceAirportID,liftOffTime,landTime,departTime,arriveTime FROM (
	SELECT flightID,aircraftID,destAirportID,sourceAirportID,liftOffTime,landTime,departTime,arriveTime FROM Flight 
	JOIN FlightDeparted USING (flightID)
	JOIN FlightArrived USING (flightID)
	WHERE Flight.sourceAirportID = 9 #<airport number to check for>
) AS sub2



# View flight rooster, schedule. 
# Note: assuming this means the schedule of all flights
# TODO: have this return airport names
#SELECT flightID,sourceAirportID,destAirportID,liftOffTime,departTime,landTime,arriveTime FROM (
#	SELECT Flight.flightID,sourceAirportID,destAirportID,liftOffTime,departTime,landTime,arriveTime FROM Flight 
#		JOIN FlightDeparted USING (flightID)
#		JOIN FlightArrived USING (flightID)
#) AS sub3# WHERE flightID = 4#<flight number to check for>;

#Get all flights whose arrival and departure times are on time/delayed.
#SELECT flightID FROM (
#	SELECT flightID FROM Flight JOIN FlightArrived USING (flightID)#
#		JOIN FlightDeparted USING (flightID)
#) AS sub4
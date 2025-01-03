package com.projects.plane;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
		info = @Info(
				title = "Plane Management Application",
				version = "1.0.0",
				description = """
            Faculty project made to prove Spring Boot backend capabilities.

            Business Requirements:
            
            1. Manage Airplanes: 
               - Allow users to add, update, delete, and retrieve details about generic airplanes, including manufacturer, model, MTOW, seating capacity, and fuel capacity.
            
            2. Manage Airlines: 
               - Enable CRUD operations for airlines, including details such as name, headquarters, and fleet size.
            
            3. Manage Airports: 
               - Allow users to register, update, and retrieve airport information, including location, runways, and capacity.
            
            4. Link Aircraft to Airlines: 
               - Allow association of specific airplanes with airlines, including assigned fleet numbers and maintenance schedules.
            
            5. Manage Flights: 
               - Facilitate creation and management of flight records, including origin, destination, departure time, arrival time, and assigned aircraft.
            
            6. Ticketing System: 
               - Enable ticket creation and management, allowing association with flights and passengers.
            
            7. Assign Hubs to Airlines: 
               - Provide functionality for associating multiple airports with airlines to represent operational hubs.
            
            8. Search and Filter: 
               - Allow searching and filtering of flights, tickets, and aircraft by various criteria such as dates, locations, or airline.
            
            9. Analytics: 
               - Provide basic analytics on fleet usage, flight punctuality, and ticket sales.

            MVP Features:

            1. Aircraft Management Module:
               - Add, update, delete, and view details about airplanes.
               - Assign airplanes to airlines, converting them into operational aircraft.
               - Track airplane-specific information like MTOW, seating capacity and range.
               - Business Requirement Coverage: Addresses requirements 1 and 4.
            
            2. Flight Scheduling and Management:
               - Create and manage flight schedules, including details like departure, arrival, and the aircraft assigned to the flight.
               - Ensure flights are associated with valid airlines and airports.
               - Business Requirement Coverage: Addresses requirements 5 and 9.
            
            3. Ticket Management System:
               - Allow creation, management, and retrieval of tickets for specific flights.
               - Enable linking of tickets to passengers.
               - Provide basic validation to ensure tickets are issued only for available flights.
               - Business Requirement Coverage: Addresses requirements 6 and 8.
            
            4. Airline Hubs Management:
               - Allow airlines to associate with multiple airports as hubs.
               - Provide validation to ensure airports exist in the system before they can be linked.
               - Offer a feature to view all hubs associated with a given airline.
               - Business Requirement Coverage: Addresses requirement 7.
            
            5. Analytics:
               - Offer a feature to view informations such as number as number of flight per aircraft or airline.
               - The obtained data could be consumed by BI Tools such as Power BI.
               - Business Requirement Coverage: Addresses requirement 9.
        """
		)
)


public class PlaneBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(PlaneBackendApplication.class, args);
	}

}

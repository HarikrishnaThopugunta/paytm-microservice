package com.example.demo.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class TrakingEntity {

	String trainName;

	String trainNumber;

	String locationName;

	String latitude;
	String langitude;

}

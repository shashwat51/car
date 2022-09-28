package com.example.car;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.vinaudit.model.Result;
import com.vinaudit.model.vinmaster;

@RestController
public class VinAuditController {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@RequestMapping(value = "/allcars", method = RequestMethod.GET)
	public List<vinmaster> findAll() {
		String sql = "SELECT TOP (100) *  FROM [master].[dbo].[test]";

		List<vinmaster> cars = jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(vinmaster.class));
		return cars;
	}

	@RequestMapping(value = "/carsbyMake", method = RequestMethod.GET)
	public List<vinmaster> findAllCarsbyMake(@Param("make") String make) {
		String sql = "SELECT TOP 100 * FROM [master].[dbo].[test] where make='" + make + "';";

		List<vinmaster> cars = jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(vinmaster.class));
		System.out.println("Total CARS");
		System.out.println(cars.size());
		System.out.println("Total size");

		return cars;
	}

	@RequestMapping(value = "/carsbyMakeAndModel", method = RequestMethod.GET)
	public List<vinmaster> findAllCarsbyMakeAndModel(@Param("make") String make, @Param("model") String model) {
		String sql = "SELECT TOP 100 * FROM [master].[dbo].[test] where make='" + make + "' and model = '" + model + "';";

		List<vinmaster> cars = jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(vinmaster.class));
	
		return cars;
	}

	@RequestMapping(value = "/carsbyMakeAndModelAndYear", method = RequestMethod.GET)
	public List<vinmaster> findAllCarsbyMakeAndModelAndYear(@Param("make") String make, @Param("model") String model,
			@Param("year") String year) {
		String sql = "SELECT TOP 100 * FROM [master].[dbo].[test] where make='" + make + "' and model = '" + model
				+ "' and year ='" + year + "';";

		List<vinmaster> cars = jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(vinmaster.class));
		System.out.println("Total CARS");
		System.out.println(cars.size());
		System.out.println("Total size");

		return cars;
	}

	@RequestMapping(value = "/pricebyMakeAndModelAndYear", method = RequestMethod.GET)
	public Result pricebyMakeAndModelAndYear(@Param("make") String make, @Param("model") String model,
			@Param("year") String year) {
		String sql = "SELECT * FROM [master].[dbo].[test] where make='" + make + "' and model = '" + model
				+ "' and year ='" + year + "';";

		List<vinmaster> cars = jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(vinmaster.class));
		int totalSellingPrice = 0;
		int totalCars = 0;
		for (vinmaster car : cars) {
			if (car.getListing_price() != null && (car.getListing_price().length() >0)) {
				totalSellingPrice += Integer.parseInt(car.getListing_price());
				totalCars++;
			}

		}

		return new Result(cars,  (float) totalSellingPrice/totalCars);
	}
	
	
	@RequestMapping(value = "/pricebyMakeAndModelAndYearMileage", method = RequestMethod.GET)
	public Result pricebyMakeAndModelAndYearMileage(@Param("make") String make, @Param("model") String model,
			@Param("year") String year, @Param("mileage") String mileage) {
		String sql = "SELECT * FROM [master].[dbo].[test] where make='" + make + "' and model = '" + model
				+ "' and year ='" + year + "';";
		int standardDeviation = 10000;
		List<vinmaster> cars = jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(vinmaster.class));
		int totalSellingPrice = 0;
		int totalCars = 0;
		int mileages = Integer.parseInt(mileage);
		for (vinmaster car : cars) {
			// Figure out mileage
			if (car.getListing_mileage() != null && ((car.getListing_mileage().length() >0))) {
				int mileagefromDB = Integer.parseInt(car.getListing_mileage());
				// only consider mileage which is closer to standard deviation
				if (Math.abs(mileagefromDB - mileages)<= standardDeviation) {
					
					if (car.getListing_price() != null && (car.getListing_price().length() >0)) {
						totalSellingPrice += Integer.parseInt(car.getListing_price());
						totalCars++;
					}
					
				}
				
			}	
		}
		
		// in case no match try to find without mileage
		if (totalCars == 0) {
			return pricebyMakeAndModelAndYear(make,model,year);
		}

		return new Result(cars,  (float) totalSellingPrice/totalCars);
	}
}

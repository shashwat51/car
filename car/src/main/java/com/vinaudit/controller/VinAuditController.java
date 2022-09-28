package com.vinaudit.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.vinaudit.model.vinmaster;


@RestController
public class VinAuditController {
	
	@Autowired
    private JdbcTemplate jdbcTemplate;

	@RequestMapping(value = "/airports", method = RequestMethod.GET)
	public List<vinmaster> findByTailnum(@Param("iata") String iata) {
		String sql = "SELECT * FROM [master].[dbo].[test]";
//      List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);
//      for (Map row : rows) {
//          System.out.println(row.get("vin"));
//      }

      List<vinmaster> cars = jdbcTemplate.query(sql,
              BeanPropertyRowMapper.newInstance(vinmaster.class));
      System.out.println("Total CARS");
       System.out.println(cars.size());
       System.out.println("Total size");
       for(vinmaster c:cars) {
//      	System.out.println(c.getCertified());
//      	System.out.println(c.getVin());
      }
	return cars;
	}

}

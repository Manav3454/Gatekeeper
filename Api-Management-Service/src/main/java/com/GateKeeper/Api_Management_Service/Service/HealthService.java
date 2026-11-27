package com.GateKeeper.Api_Management_Service.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HealthService
{
    private final JdbcTemplate jdbcTemplate;
    public  String getHealth()
    {
        String databaseStatus = "DOWN";
        try{
            Integer result = jdbcTemplate.queryForObject("SELECT 1",Integer.class);

            if(Integer.valueOf(1).equals(result))
            {
                databaseStatus = "Up";
            }
        }
        catch(Exception e)
        {
            System.out.println("MySql Health check Failed"+e.getMessage());
        }
        return databaseStatus;
    }
}

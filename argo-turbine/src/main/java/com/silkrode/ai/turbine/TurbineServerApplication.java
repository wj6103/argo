package com.silkrode.ai.turbine;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.netflix.turbine.EnableTurbine;


/**
 * Turbine server monitors all micro service of argo.
 */
@EnableTurbine
@SpringBootApplication
@EnableHystrixDashboard
public class TurbineServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(TurbineServerApplication.class, args);
    }


}

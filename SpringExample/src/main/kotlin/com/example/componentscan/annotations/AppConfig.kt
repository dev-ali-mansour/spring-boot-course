package com.example.componentscan.annotations

import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration

@Configuration
@ComponentScan(basePackages = ["com.example.componentscan.annotations"])
class AppConfig {

}

package com.project.app

import com.project.app.model.Employee
import com.project.app.repository.EmployeeRepository
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component

@Component
class DataLoader(private val employeeRepository: EmployeeRepository) : CommandLineRunner {

    override fun run(vararg args: String) {
        employeeRepository.save(Employee("Alice", "Johnson", "Engineering", 95000.0))
        employeeRepository.save(Employee("Bob", "Smith", "Engineering", 88000.0))
        employeeRepository.save(Employee("Carol", "Williams", "Engineering", 102000.0))
        employeeRepository.save(Employee("David", "Brown", "Engineering", 91000.0))
        employeeRepository.save(Employee("Eve", "Jones", "Engineering", 87000.0))
        employeeRepository.save(Employee("Frank", "Garcia", "Marketing", 72000.0))
        employeeRepository.save(Employee("Grace", "Martinez", "Marketing", 68000.0))
        employeeRepository.save(Employee("Henry", "Davis", "Marketing", 75000.0))
        employeeRepository.save(Employee("Iris", "Lopez", "Marketing", 71000.0))
        employeeRepository.save(Employee("Jack", "Wilson", "Marketing", 69000.0))
        employeeRepository.save(Employee("Karen", "Anderson", "HR", 65000.0))
        employeeRepository.save(Employee("Leo", "Thomas", "HR", 63000.0))
        employeeRepository.save(Employee("Mia", "Taylor", "HR", 67000.0))
        employeeRepository.save(Employee("Noah", "Jackson", "HR", 64000.0))
        employeeRepository.save(Employee("Olivia", "White", "HR", 66000.0))
        employeeRepository.save(Employee("Paul", "Harris", "Finance", 85000.0))
        employeeRepository.save(Employee("Quinn", "Martin", "Finance", 89000.0))
        employeeRepository.save(Employee("Rachel", "Thompson", "Finance", 92000.0))
        employeeRepository.save(Employee("Sam", "Garcia", "Finance", 86000.0))
        employeeRepository.save(Employee("Tina", "Robinson", "Finance", 83000.0))
        employeeRepository.save(Employee("Uma", "Clark", "Legal", 98000.0))
        employeeRepository.save(Employee("Victor", "Lewis", "Legal", 105000.0))
        employeeRepository.save(Employee("Wendy", "Lee", "Legal", 97000.0))
        employeeRepository.save(Employee("Xander", "Walker", "Legal", 101000.0))
        employeeRepository.save(Employee("Yara", "Hall", "Legal", 99000.0))
    }
}

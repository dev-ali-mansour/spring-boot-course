package com.project.app.controller

import com.project.app.model.Employee
import com.project.app.service.EmployeeService
import org.junit.jupiter.api.Test
import org.mockito.Mockito.`when`
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest
import org.springframework.test.context.bean.override.mockito.MockitoBean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@WebMvcTest(EmployeeController::class)
class EmployeeControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockitoBean
    private lateinit var employeeService: EmployeeService

    @Test
    fun `getEmployees returns list of employees`() {
        val employees = listOf(
            Employee("Alice", "Johnson", "Engineering", 95000.0, 1L),
            Employee("Bob", "Smith", "Engineering", 88000.0, 2L)
        )
        `when`(employeeService.getAllEmployees()).thenReturn(employees)

        mockMvc.perform(get("/api/employees"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.length()").value(2))
            .andExpect(jsonPath("$[0].firstName").value("Alice"))
            .andExpect(jsonPath("$[0].lastName").value("Johnson"))
            .andExpect(jsonPath("$[0].department").value("Engineering"))
            .andExpect(jsonPath("$[0].salary").value(95000.0))
            .andExpect(jsonPath("$[1].firstName").value("Bob"))
    }
}

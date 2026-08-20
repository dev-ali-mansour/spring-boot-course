package com.project.app.controller

import com.project.app.model.Grade
import com.project.app.projection.StudentGradeSummary
import com.project.app.service.GradeService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import kotlin.jvm.optionals.getOrElse

@RestController
@RequestMapping("/api/grades")
class GradeController(private val gradeService: GradeService) {

    @GetMapping
    fun getAllGrades(): ResponseEntity<List<Grade>> =
        ResponseEntity.ok(gradeService.getAllGrades())

    @GetMapping("/average")
    fun getAverage(@RequestParam(required = true) student: String): ResponseEntity<Map<String, Any>> =
        ResponseEntity.ok(
            mapOf(
                "student" to student,
                "average" to gradeService.getAverageScoreForStudent(student)
            )
        )

    @GetMapping("/top")
    fun getTopScore(@RequestParam(required = true) subject: String): ResponseEntity<Grade> =
        gradeService.getTopScoreForSubject(subject).map { ResponseEntity.ok(it) }.getOrElse {
            ResponseEntity.notFound().build()
        }

    @GetMapping("/averages")
    fun getAllAverages(): ResponseEntity<List<Array<Any>>> =
        ResponseEntity.ok(gradeService.getAllStudentAverages())

    @GetMapping("/summary")
    fun getSummary(): ResponseEntity<List<StudentGradeSummary>> =
        ResponseEntity.ok(gradeService.getStudentAverages())
}

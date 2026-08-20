package com.project.app.service

import com.project.app.model.Grade
import com.project.app.projection.StudentGradeSummary
import com.project.app.repository.GradeRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class GradeService(private val gradeRepository: GradeRepository) {

    fun getAllGrades(): List<Grade> = gradeRepository.findAll()

    fun getAverageScoreForStudent(name: String): Double =
        gradeRepository.findAverageScoreByStudentName(name) ?: 0.0

    fun getTopScoreForSubject(subject: String): Optional<Grade> =
        gradeRepository.findTopScoreBySubject(subject)

    fun getAllStudentAverages(): List<Array<Any>> =
        gradeRepository.findAllStudentAverages()

    fun getStudentAverages(): List<StudentGradeSummary> =
        gradeRepository.findStudentAverages()


}

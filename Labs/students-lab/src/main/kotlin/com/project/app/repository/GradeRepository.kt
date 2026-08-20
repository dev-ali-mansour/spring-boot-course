package com.project.app.repository

import com.project.app.model.Grade
import com.project.app.projection.StudentGradeSummary
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface GradeRepository : JpaRepository<Grade, Long> {
    @Query("SELECT AVG(g.score) FROM Grade g WHERE g.student.name = :name")
    fun findAverageScoreByStudentName(name: String): Double?

    @Query("SELECT g FROM Grade g WHERE g.subject=:subject ORDER BY g.score DESC LIMIT 1")
    fun findTopScoreBySubject(subject: String): Optional<Grade>

    @Query(
        nativeQuery = true,
        value = """SELECT s.name, ROUND(AVG(g.score)) as average FROM grade g 
            JOIN student s ON g.student_id = s.id GROUP BY s.name ORDER BY average DESC"""
    )
    fun findAllStudentAverages(): List<Array<Any>>

    @Query("SELECT g.student.name AS name, AVG(g.score) AS average FROM Grade g GROUP BY g.student.name")
    fun findStudentAverages(): List<StudentGradeSummary>
}

package com.project.app

import com.project.app.model.Grade
import com.project.app.model.Student
import com.project.app.repository.GradeRepository
import com.project.app.repository.StudentRepository
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component

@Component
class DataLoader(
    private val studentRepository: StudentRepository,
    private val gradeRepository: GradeRepository
) : CommandLineRunner {

    override fun run(vararg args: String) {
        val alice = studentRepository.save(Student(name = "Alice", email = "alice@school.com"))
        val bob = studentRepository.save(Student(name = "Bob", email = "bob@school.com"))
        val carol = studentRepository.save(Student(name = "Carol", email = "carol@school.com"))
        val david = studentRepository.save(Student(name = "David", email = "david@school.com"))
        val eve = studentRepository.save(Student(name = "Eve", email = "eve@school.com"))

        gradeRepository.save(Grade(alice, "Math", 92))
        gradeRepository.save(Grade(alice, "Science", 88))
        gradeRepository.save(Grade(alice, "History", 76))
        gradeRepository.save(Grade(alice, "English", 95))

        gradeRepository.save(Grade(bob, "Math", 78))
        gradeRepository.save(Grade(bob, "Science", 84))
        gradeRepository.save(Grade(bob, "History", 91))
        gradeRepository.save(Grade(bob, "English", 73))

        gradeRepository.save(Grade(carol, "Math", 95))
        gradeRepository.save(Grade(carol, "Science", 97))
        gradeRepository.save(Grade(carol, "History", 89))
        gradeRepository.save(Grade(carol, "English", 93))

        gradeRepository.save(Grade(david, "Math", 65))
        gradeRepository.save(Grade(david, "Science", 70))
        gradeRepository.save(Grade(david, "History", 80))
        gradeRepository.save(Grade(david, "English", 68))

        gradeRepository.save(Grade(eve, "Math", 88))
        gradeRepository.save(Grade(eve, "Science", 79))
        gradeRepository.save(Grade(eve, "History", 94))
        gradeRepository.save(Grade(eve, "English", 85))
    }
}

package com.leverx.lms.learningmanagementsystem.student.service;

import com.leverx.lms.learningmanagementsystem.course.entity.Course;
import com.leverx.lms.learningmanagementsystem.course.repository.CourseRepository;
import com.leverx.lms.learningmanagementsystem.student.entity.Student;
import com.leverx.lms.learningmanagementsystem.student.repository.StudentRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class StudentServiceTest {

    @Autowired
    private StudentService studentService;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Test
    void onlyOneThreadShouldSucceedInBuyingCourse() throws Exception {
        UUID studentId = createStudentWithCoins(1000);
        UUID courseId = createCourse("Test Course", 50);

        int threads = 10;
        ExecutorService executor = Executors.newFixedThreadPool(threads);

        CountDownLatch start = new CountDownLatch(1);
        CountDownLatch finish = new CountDownLatch(threads);
        AtomicInteger success = new AtomicInteger(0);

        for (int i = 0; i < threads; i++) {
            executor.execute(() -> {
                try {
                    start.await(); // all threads wait here
                    studentService.buyCourseWithLockAnnotation(studentId, courseId);
                    success.incrementAndGet();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    finish.countDown();
                }
            });
        }

        // start all at the same time
        start.countDown();
        finish.await();
        executor.shutdown();

        assertEquals(1, success.get(), "Only one thread should succeed");
    }

    private UUID createStudentWithCoins(int coins) {
        Student student = new Student();
        student.setFirstName("Test");
        student.setCoins(BigDecimal.valueOf(coins));
        return studentRepository.save(student).getId();
    }

    private UUID createCourse(String title, int price) {
        Course course = new Course();
        course.setTitle(title);
        course.setPrice(BigDecimal.valueOf(price));
        return courseRepository.save(course).getId();
    }
}

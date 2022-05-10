import domain.Grade;
import domain.Homework;
import domain.Student;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import repository.GradeXMLRepository;
import repository.HomeworkXMLRepository;
import repository.StudentXMLRepository;
import validation.*;

class ServiceTest {

    static service.Service service;

    @BeforeAll
    public static void beforeTests() {
        Validator<Student> studentValidator = new StudentValidator();
        Validator<Homework> homeworkValidator = new HomeworkValidator();
        Validator<Grade> gradeValidator = new GradeValidator();

        StudentXMLRepository fileRepository1 = new StudentXMLRepository(studentValidator, "students.xml");
        HomeworkXMLRepository fileRepository2 = new HomeworkXMLRepository(homeworkValidator, "homework.xml");
        GradeXMLRepository fileRepository3 = new GradeXMLRepository(gradeValidator, "grades.xml");

        service = new service.Service(fileRepository1, fileRepository2, fileRepository3);
    }

    @org.junit.jupiter.api.Test
    void findAllStudents() {
        Iterable<Student> students = service.findAllStudents();
        Assertions.assertNotEquals(students,null);
        for (Student student : students) {
            Assertions.assertNotEquals(student,null);
        }
    }

    @org.junit.jupiter.api.Test
    void findAllGrades() {
        Iterable<Grade> grades = service.findAllGrades();
        Assertions.assertNotEquals(grades,null);
        for (Grade grade : grades) {
            Assertions.assertNotEquals(grade,null);
        }
    }

    @org.junit.jupiter.api.Test
    void findAllHomeworks() {
        Iterable<Homework> homeworks = service.findAllHomework();
        Assertions.assertNotEquals(homeworks,null);
        for (Homework homework : homeworks) {
            Assertions.assertNotEquals(homework,null);
        }
    }

    @ParameterizedTest
    @ValueSource(strings={"Dummy1","Dummy2"})
    void saveStudent(String name) {
        int res = service.saveStudent("9999", name, 500);
        Assertions.assertEquals(res,0);
    }

    @org.junit.jupiter.api.Test
    void saveInvalidStudent() {
        Assertions.assertThrows(ValidationException.class,()->service.saveStudent("9999", "Dummy", -50));
    }
}
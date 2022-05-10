import domain.Homework;
import domain.Student;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import repository.GradeXMLRepository;
import repository.HomeworkXMLRepository;
import repository.StudentXMLRepository;

import java.util.ArrayList;
import java.util.stream.StreamSupport;

import static org.mockito.Mockito.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ServiceMockTest {

    static service.Service service;

    @Mock
    static StudentXMLRepository fileRepository1;
    @Mock
    static HomeworkXMLRepository fileRepository2;
    @Mock
    static GradeXMLRepository fileRepository3;

    @BeforeAll
    public void beforeTests() {
        MockitoAnnotations.initMocks(this);

        ArrayList<Student> dummyStudents = new ArrayList<>();
        dummyStudents.add(new Student("9999", "Dummy", 555));
        when(fileRepository1.findAll()).thenReturn(dummyStudents);

        when(fileRepository1.save(any(Student.class))).thenAnswer(invocationOnMock -> invocationOnMock.getArgument(0,Student.class));

        when(fileRepository2.findAll()).thenReturn(new ArrayList<>());

        service = new service.Service(fileRepository1, fileRepository2, fileRepository3);
    }

    @org.junit.jupiter.api.Test
    void findAllStudents() {
        Iterable<Student> students = service.findAllStudents();
        verify(fileRepository1, times(1)).findAll();
        Assertions.assertNotEquals(students, null);
        Assertions.assertEquals(1, StreamSupport.stream(students.spliterator(), false).count());
    }

    @org.junit.jupiter.api.Test
    void findAllHomeworks() {
        Iterable<Homework> homeworks = service.findAllHomework();
        verify(fileRepository2, times(1)).findAll();
        Assertions.assertNotEquals(homeworks, null);
        Assertions.assertEquals(0, StreamSupport.stream(homeworks.spliterator(), false).count());
    }

    @ParameterizedTest
    @ValueSource(strings = {"Dummy1", "Dummy2"})
    void saveStudent(String name) {
        int res = service.saveStudent("9999", name, 500);
        Assertions.assertEquals(res, 0);
    }
}
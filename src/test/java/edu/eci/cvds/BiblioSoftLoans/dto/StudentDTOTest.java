package edu.eci.cvds.BiblioSoftLoans.dto;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class StudentDTOTest {

    @Test
    public void testStudentDTOConstructor() {
        StudentDTO student = new StudentDTO();
        student.setId(1L);
        student.setFullName("John Doe");

        assertNotNull(student);
        assertEquals(Long.valueOf(1), student.getId());
        assertEquals("John Doe", student.getFullName());
    }

    @Test
    public void testStudentDTOWithNullId() {
        StudentDTO student = new StudentDTO();
        student.setId(null);
        student.setFullName("Jane Smith");

        assertNotNull(student);
        assertNull(student.getId());
        assertEquals("Jane Smith", student.getFullName());
    }

    @Test
    public void testStudentDTOWithNullFullName() {
        StudentDTO student = new StudentDTO();
        student.setId(2L);
        student.setFullName(null);

        assertNotNull(student);
        assertEquals(Long.valueOf(2), student.getId());
        assertNull(student.getFullName());
    }

    @Test
    public void testStudentDTOWithEmptyFullName() {
        StudentDTO student = new StudentDTO();
        student.setId(3L);
        student.setFullName("");

        assertNotNull(student);
        assertEquals(Long.valueOf(3), student.getId());
        assertEquals("", student.getFullName());
    }

    @Test
    public void testStudentDTOWithValidData() {
        StudentDTO student = new StudentDTO();
        student.setId(4L);
        student.setFullName("Alice Cooper");

        assertNotNull(student);
        assertEquals(Long.valueOf(4), student.getId());
        assertEquals("Alice Cooper", student.getFullName());
    }

    @Test
    public void testStudentDTOWithNullValues() {
        StudentDTO student = new StudentDTO();
        student.setId(null);
        student.setFullName(null);

        assertNotNull(student);
        assertNull(student.getId());
        assertNull(student.getFullName());
    }

    @Test
    public void testStudentDTOEquality() {
        StudentDTO student1 = new StudentDTO();
        student1.setId(5L);
        student1.setFullName("Bob Marley");

        StudentDTO student2 = new StudentDTO();
        student2.setId(5L);
        student2.setFullName("Bob Marley");

        assertEquals(student1, student2);
    }
}

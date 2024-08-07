package org.example.lab5.web;

import org.example.lab5.entities.Student;
import org.example.lab5.repositories.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.hasSize;

@ExtendWith({SpringExtension.class, MockitoExtension.class})
@SpringBootTest
@AutoConfigureMockMvc
class StudentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentRepository studentRepository;

    private Student student;

    @BeforeEach
    void setup() throws ParseException {
        student = new Student();
        student.setId(1L);
        student.setName("John");

        String sDate1 = "2012/11/11";
        Date date1 = new SimpleDateFormat("yyyy/MM/dd").parse(sDate1);
        student.setDob(date1);
        student.setPassed(true);
        student.setGpa(3.5);
    }


    // Step 8
    @Test
    void findAll_ListView() throws Exception {
        List<Student> list = new ArrayList<>();
        list.add(student);
        list.add(student);

        when(studentRepository.findAll()).thenReturn(list);

        mockMvc.perform(get("/index"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("listStudents", list))
                .andExpect(view().name("students"))
                .andExpect(model().attribute("listStudents", hasSize(2)));

        verify(studentRepository, times(1)).findAll();
        verifyNoMoreInteractions(studentRepository);
    }


    // Step 10
    @Test
    void findbyid() throws Exception {

        List<Student> list = new ArrayList<Student>();
        list.add(student);

        when(studentRepository.findStudentById(1L)).thenReturn(list);

        mockMvc.perform(get("/index").param("keyword", "1"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("listStudents", list))
                .andExpect(view().name("students"))
                .andExpect(model().attribute("listStudents", hasSize(1)));

        verify(studentRepository, times(1)).findStudentById(anyLong());
        verifyNoMoreInteractions(studentRepository);

    }


    // Step 11
    @Test
    void delete() {

        ArgumentCaptor<Long> idCapture = ArgumentCaptor.forClass(Long.class);

        doNothing().when(studentRepository).deleteById(idCapture.capture());

        studentRepository.deleteById(1L);

        assertEquals(1L, idCapture.getValue());

        verify(studentRepository, times(1)).deleteById(1L);

    }


    // Step 12
    @Test
    void editStudents() throws Exception {

        Student s2 = new Student();
        s2.setId(1L);
        s2.setName("John Mast");

        String sDate1 = "2012/11/11";
        Date date1 = new SimpleDateFormat("yyyy/MM/dd").parse(sDate1);
        s2.setDob(date1);
        s2.setPassed(true);
        s2.setGpa(3.5);

        Long iid = 1l;

        when(studentRepository.findById(iid)).thenReturn(Optional.of(s2));

        mockMvc.perform(get("/editStudents").param("id", String.valueOf(1L)))
                .andExpect(status().isOk())
                .andExpect(model().attribute("student", s2))
                .andExpect(view().name("editStudents"));

        verify(studentRepository, times(1)).findById(anyLong());
        verifyNoMoreInteractions(studentRepository);

    }


    // Step 13
    @Test
    void formStudents() throws Exception {

        mockMvc.perform(get("/formStudents"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("student", new Student()))
                .andExpect(view().name("formStudents"));

    }

    @Test
    void save() throws Exception {

        when(studentRepository.save(student)).thenReturn(student);

        studentRepository.save(student);

        verify(studentRepository, times(1)).save(student);

    }


}

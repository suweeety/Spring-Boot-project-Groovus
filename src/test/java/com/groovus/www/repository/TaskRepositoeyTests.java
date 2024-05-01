package com.groovus.www.repository;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Log4j2
public class TaskRepositoeyTests {


    @Autowired
    TaskRepository taskRepository;

    @Test
    public void getCount(){

        taskRepository.countTask(10L);
        
    }


}


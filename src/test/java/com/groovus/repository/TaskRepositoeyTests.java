package com.groovus.repository;

import com.groovus.www.entity.Task;
import com.groovus.www.repository.StatusHistoryRepository;
import com.groovus.www.repository.TaskRepository;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@SpringBootTest
@Log4j2
public class TaskRepositoeyTests {


    @Autowired
    TaskRepository taskRepository;

    @Autowired
    StatusHistoryRepository statusHistoryRepository;

    @Test
    public void getCount(){

        taskRepository.countTask(10L);
        
    }

    @Test
    public void getHistory(){

       Optional<Task> result =  taskRepository.getTaskByTid(23L);

        statusHistoryRepository.getStatusHistoryBytid(result.get());
    }


}


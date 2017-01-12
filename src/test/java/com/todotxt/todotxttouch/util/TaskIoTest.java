package com.todotxt.todotxttouch.util;

import com.todotxt.todotxttouch.task.Task;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

/**
 * Created by andre_000 on 14/02/2016.
 */
public class TaskIoTest {

    @Test
    public void testLoadTasksFromFile() throws Exception {

        ArrayList<Task> tasks = TaskIo.loadTasksFromFile(new File("src/test/resources/todo-test.txt"));
        ArrayList<Task> tasksFiltered = new ArrayList<>();
        for(Task task : tasks){
           if(task.getContexts().contains("now")){
               tasksFiltered.add(task);
           }
        }
        int expectedSize = 1;
        assertEquals(expectedSize, tasksFiltered.size());

        assertEquals( "must complete work on +theproject @pomo-0 @now",tasksFiltered.get(0).getText());
        
    }
}
package com.andrewfarrell.pomodoro;

import com.todotxt.todotxttouch.task.Task;
import com.todotxt.todotxttouch.util.TaskIo;
import org.tomighty.Phase;
import org.tomighty.bus.messages.timer.TimerFinished;
import org.tomighty.bus.messages.timer.TimerInterrupted;
import org.tomighty.bus.messages.timer.TimerStarted;
import org.tomighty.time.Time;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static com.todotxt.todotxttouch.util.TaskIo.*;

/**
 * Created by andre_000 on 14/02/2016.
 */
public class TomightyEventHandlerImpl implements TomightyEventHandler {
    public void handleTimerFinished(TimerFinished timerFinished) {

        toTodoDotTxt(timerFinished.getPhase(), timerFinished.getTime(),"FINISHED");
    }

    public void handleTimerStarted(TimerStarted timerStarted) {

        toTodoDotTxt(timerStarted.getPhase(), timerStarted.getTime(), "STARTED");
    }

    public void handleTimerInterrupted(TimerInterrupted timerInterrupted) {

        toTodoDotTxt(timerInterrupted.getPhase(), timerInterrupted.getTime(), "INTERRUPTED");
    }

    private void toTodoDotTxt(Phase phase, Time time, String label) {
        System.out.println(label);

        JFileChooser fr = new JFileChooser();
        FileSystemView fw = fr.getFileSystemView();

        String fileStr = fw.getDefaultDirectory()+"\\todo.txt";
        File theFile = new File(fileStr);
        try {
            ArrayList<Task> tasks = loadTasksFromFile(theFile);

            boolean updated = false;

            for(Task task : tasks){
                if(task.getContexts().contains("now")) {
                    if(task.getText().contains("∑=")){
                        StringBuilder newText = new StringBuilder();
                        for(String token : task.toString().split(" ")){
                            if(token.startsWith("pomo-phase="))
                                newText.append(" "+"pomo-phase="+phase) ;
                            else if(token.startsWith("pomo-state="))
                                newText.append(" "+"pomo-state="+label) ;
                            else if(token.startsWith("pomo-time"))
                                newText.append(" "+"pomo-time="+time);
                            else if(token.startsWith("∑"))
                                newText.append(" "+"∑="+getPCount(token,label,phase));
                            else
                                newText.append(" "+token);
                        }
                        task.update(newText.toString());
                    }
                    else{
                        task.update(task.toString()+String.format(" pomodoro{  ∑=%s  }", (label.equals("FINISHED") && phase.equals(Phase.POMODORO))?"1":"0"));
                    }

                }
                //tasks.add(new Task(System.currentTimeMillis(),format));
            }

            TaskIo.writeToFile(tasks, theFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getPCount(String token, String label, Phase phase) {
        int count = Integer.parseInt(token.split("=")[1]);
        if(label.equals("FINISHED") && phase.equals(Phase.POMODORO)){
            return Integer.toString(++count);
        }
        else{
            return token.split("=")[1];
        }
    }
}

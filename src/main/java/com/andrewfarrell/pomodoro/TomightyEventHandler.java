package com.andrewfarrell.pomodoro;

import org.tomighty.bus.messages.timer.TimerFinished;
import org.tomighty.bus.messages.timer.TimerInterrupted;
import org.tomighty.bus.messages.timer.TimerStarted;

/**
 * Created by andre_000 on 14/02/2016.
 */
public interface TomightyEventHandler {
    public void handleTimerFinished(TimerFinished timerFinished);

    public void handleTimerStarted(TimerStarted timerStarted);

    public void handleTimerInterrupted(TimerInterrupted timerInterrupted);
}

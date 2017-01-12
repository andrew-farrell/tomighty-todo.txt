package com.andrewfarrell.pomodoro;

import org.junit.Before;
import org.junit.Test;
import org.tomighty.Phase;
import org.tomighty.bus.Bus;
import org.tomighty.bus.Subscriber;
import org.tomighty.bus.messages.timer.TimerFinished;
import org.tomighty.bus.messages.timer.TimerInterrupted;
import org.tomighty.bus.messages.timer.TimerStarted;
import org.tomighty.time.Time;

import static org.junit.Assert.assertTrue;

/**
 * Created by andre_000 on 13/02/2016.
 */
public class TodoTxtTomightyPluginTest {


    private TodoTxtTomightyPlugin todoTxPlugin;

    private Bus busMock = new Bus() {
        public <T> void subscribe(Subscriber<T> subscriber, Class<T> aClass) {
            Phase phase = Phase.POMODORO;

            if (aClass == TimerFinished.class) {
                subscriber.receive((T) new TimerFinished(phase));
            } else if (aClass == TimerInterrupted.class) {
                subscriber.receive((T) new TimerInterrupted(new Time(1), phase));
            } else if (aClass == TimerStarted.class) {
                subscriber.receive((T) new TimerStarted(new Time(1), phase));
            }
        }

        public <T> void unsubscribe(Subscriber<T> subscriber, Class<T> aClass) {

        }

        public void publish(Object o) {

        }
    };

    private TomightyEventHandler eventHandlerSpy = new TomightyEventHandler() {
        public void handleTimerFinished(TimerFinished timerFinished) {
            finishedEventReceived = true;
        }

        public void handleTimerStarted(TimerStarted timerStarted) {
            startedEventReceived = true;
        }

        public void handleTimerInterrupted(TimerInterrupted timerInterrupted) {
            interruptedEventReceived = true;
        }
    };

    private boolean startedEventReceived;
    private boolean interruptedEventReceived;
    private boolean finishedEventReceived;

    @Before
    public void onBefore() {
        this.todoTxPlugin = new TodoTxtTomightyPlugin(busMock);
        this.todoTxPlugin.setEventHandler(eventHandlerSpy);
        this.todoTxPlugin.init();
    }

    @Test
    public void testPluginBusFeedBack() {
        assertTrue(this.startedEventReceived);
        assertTrue(this.finishedEventReceived);
        assertTrue(this.interruptedEventReceived);
    }

}

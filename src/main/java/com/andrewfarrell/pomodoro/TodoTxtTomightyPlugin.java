package com.andrewfarrell.pomodoro;

import org.tomighty.bus.Bus;
import org.tomighty.bus.Subscriber;
import org.tomighty.bus.messages.timer.TimerFinished;
import org.tomighty.bus.messages.timer.TimerInterrupted;
import org.tomighty.bus.messages.timer.TimerStarted;
import org.tomighty.plugin.Plugin;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

/**
 * Created by andre_000 on 13/02/2016.
 */
public class TodoTxtTomightyPlugin implements Plugin {

    private String name = "TodoTxtTomightyPlugin";
    private TomightyEventHandler eventHandler;
    private Bus bus;
    @Inject
    public TodoTxtTomightyPlugin(Bus bus) {
        this.bus = bus;
        this.eventHandler = new TomightyEventHandlerImpl();
    }

    public TomightyEventHandler getEventHandler() {
        return eventHandler;
    }

    public void setEventHandler(TomightyEventHandler eventHandler) {
        this.eventHandler = eventHandler;
    }

    public String getName() {
        return name;
    }

    @PostConstruct
    public void init() {
        bus.subscribe(new Subscriber<TimerFinished>() {
            public void receive(TimerFinished timerFinished) {
                eventHandler.handleTimerFinished(timerFinished);
            }
        }, TimerFinished.class);

        bus.subscribe(new Subscriber<TimerInterrupted>() {
            public void receive(TimerInterrupted timerInterrupted) {
                eventHandler.handleTimerInterrupted(timerInterrupted);
            }
        }, TimerInterrupted.class);

        bus.subscribe(new Subscriber<TimerStarted>() {
            public void receive(TimerStarted timerStarted) {
                eventHandler.handleTimerStarted(timerStarted);
            }
        }, TimerStarted.class);


    }


}

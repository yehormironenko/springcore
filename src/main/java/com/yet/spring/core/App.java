package com.yet.spring.core;

import java.util.Map;

import com.yet.spring.core.beans.Client;
import com.yet.spring.core.beans.EventType;
import com.yet.spring.core.beans.Event;
import com.yet.spring.core.loggers.EventLogger;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {

    private Client client;
    private EventLogger defaultLogger;
    private Map<EventType, EventLogger> loggers;
    private String startupMessage;

    public App(Client client, EventLogger defaultLogger, Map<EventType, EventLogger> loggers) {
        super();
        this.client = client;
        this.defaultLogger = defaultLogger;
        this.loggers = loggers;
    }

    public void setStartupMessage(String startupMessage) {
        this.startupMessage = startupMessage;
    }

    private void logEvent(EventType eventType, Event event, String msg) {
        String message = msg.replaceAll(client.getId(), client.getFullName());
        event.setMsg(message);
        EventLogger logger = loggers.get(eventType);
        if (logger == null) {
            logger = defaultLogger;
        }

        logger.logEvent(event);
    }

    public static void main(String[] args) {

        //      ApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");
        ConfigurableApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");

        App app = (App) context.getBean("app");
/*      app.logEvent("Some event for 1");
        app.logEvent("Some event for 2");
*/
        System.out.println(app.startupMessage);

        Event event = context.getBean(Event.class);
        app.logEvent(EventType.INFO, event, "Some event for 1");


        event = context.getBean(Event.class);
        app.logEvent(EventType.ERROR, event, "Some event for 2");


        event = context.getBean(Event.class);
        app.logEvent(null, event, "Some event 3");

        context.close();
    }


}

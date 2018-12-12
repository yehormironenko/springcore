package com.yet.spring.core.loggers;

import com.yet.spring.core.beans.Event;

import java.util.ArrayList;
import java.util.List;

public class CacheFileLogger extends FileEventLogger {
    private int cacheSize;
    private List<Event> cache;

    public CacheFileLogger(int cacheSize, String filename) {
        super(filename);
        this.cacheSize = cacheSize;
        this.cache = new ArrayList<Event>(cacheSize);
    }

    @Override
    public void logEvent(Event event) {
        cache.add(event);

        if (cache.size() == cacheSize) {
            writeEventsFromCache();
            cache.clear();
        }
    }


    public void destroy() {
        if (!cache.isEmpty())
            writeEventsFromCache();
    }

    private void writeEventsFromCache() {
        cache.stream().forEach(super::logEvent);
    }

}

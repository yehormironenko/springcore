package com.yet.spring.core.loggers;

import com.yet.spring.core.beans.Event;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

public class FileEventLogger implements EventLogger {

    public FileEventLogger(String filename) {
        this.filename = filename;
    }

    public void init() {
        this.file = new File(filename);
        if (file.exists() && !file.canWrite()) {
            throw new IllegalArgumentException("Can't write to file");
        } else if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (Exception e) {
                throw new IllegalArgumentException("Can't write ");
            }
        }

    }

    private String filename;
    private File file;

    public void logEvent(Event event) {
        try {
            FileUtils.writeStringToFile(file, event.getMsg(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}

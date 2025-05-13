package pl.edu.pja.tpo08;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

public class Code implements Serializable {
    private String id;
    private String raw;
    private String formatted;

    private Integer keepFor;
    private TimeUnit timeUnit;

    private LocalDateTime timestamp;

    public Code() {}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRaw() {
        return raw;
    }

    public void setRaw(String raw) {
        this.raw = raw;
    }

    public String getFormatted() {
        return formatted;
    }

    public void setFormatted(String formatted) {
        this.formatted = formatted;
    }

    public Integer getKeepFor() {
        return keepFor;
    }

    public void setKeepFor(Integer keepFor) {
        this.keepFor = keepFor;
    }

    public void setTimeUnit(TimeUnit timeUnit) {
        this.timeUnit = timeUnit;
    }

    public TimeUnit getTimeUnit() {
        return timeUnit;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public boolean isExpired() {
        long secondsLeft = timeUnit.toSeconds(keepFor);
        return timestamp.plusSeconds(secondsLeft).isBefore(LocalDateTime.now());
    }
}

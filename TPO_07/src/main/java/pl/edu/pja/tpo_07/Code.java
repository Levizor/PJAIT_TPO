package pl.edu.pja.tpo_07;

import java.time.Duration;

public class Code {
    private String id;
    private String raw;
    private String formatted;

    private Integer keepFor;
    private TimeUnit timeMeasure;

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
}

package it.chiarani.otl.model;

public class Device {
    private String id;
    private String name;
    private String topic;
    private String location;
    private String type;
    private String configuration;
    private boolean state;
    private boolean accessible;

    public Device() {}

    public Device(String id, String name, String topic, String location, String type, String configuration, boolean state, boolean accessible) {
        this.id             = id;
        this.name           = name;
        this.topic          = topic;
        this.location       = location;
        this.type           = type;
        this.configuration  = configuration;
        this.state          = state;
        this.accessible     = accessible;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isAccessible() {
        return accessible;
    }

    public void setAccessible(boolean accessible) {
        this.accessible = accessible;
    }

    public String getConfiguration() {
        return configuration;
    }

    public void setConfiguration(String configuration) {
        this.configuration = configuration;
    }
}

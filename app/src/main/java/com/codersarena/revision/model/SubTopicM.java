package com.codersarena.revision.model;

public class SubTopicM {
    String group;
    String topic;

    public SubTopicM() {
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public SubTopicM(String group, String topic) {
        this.group = group;
        this.topic = topic;
    }
}

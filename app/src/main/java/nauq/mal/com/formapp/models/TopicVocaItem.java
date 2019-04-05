package nauq.mal.com.formapp.models;

import java.io.Serializable;

public class TopicVocaItem implements Serializable {
    private int id;
    private String topic = "";
    private Boolean checked = false;

    public TopicVocaItem(int id, String topic) {
        this.id = id;
        this.topic = topic;
    }

    @Override
    public String toString() {
        return "id=" + id +
                ", topic='" + topic;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public Boolean getChecked() {
        return checked;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }
}

package nauq.mal.com.formapp.models;

import java.io.Serializable;

public class TopicItem implements Serializable {
    private int id;
    private String image = "";
    private String topic = "";
    private Boolean checked = false;
    private int type;

    public TopicItem(int id, String topic) {
        this.id = id;
        this.topic = topic;
    }

    public TopicItem(int id, String topic, int type) {
        this.id = id;
        this.topic = topic;
        this.type = type;
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

    public TopicItem(int id, String topic, String image) {
        this.id = id;
        this.image = image;
        this.topic = topic;
    }

    public TopicItem(int id,String topic, String image,  int type) {
        this.id = id;
        this.image = image;
        this.topic = topic;
        this.type = type;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}

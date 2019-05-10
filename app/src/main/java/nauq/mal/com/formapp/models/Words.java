package nauq.mal.com.formapp.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Words implements Serializable {
    @SerializedName("_id")
    private String id;
    @SerializedName("name")
    private String name;
    @SerializedName("mean")
    private String mean;
    @SerializedName("spell")
    private String spell;
    @SerializedName("example")
    private String example;
    @SerializedName("usage")
    private String usage;
    @SerializedName("category")
    private String category;
    @SerializedName("isBookmark")
    private boolean isBookmark  =false;
    @SerializedName("form")
    private String form;
    private boolean isFlip = false;

    public Words() {
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

    public boolean isBookmark() {
        return isBookmark;
    }

    public void setBookmark(boolean bookmark) {
        isBookmark = bookmark;
    }

    public String getMean() {
        return mean;
    }

    public void setMean(String mean) {
        this.mean = mean;
    }

    public String getSpell() {
        return spell;
    }

    public void setSpell(String spell) {
        this.spell = spell;
    }

    public String getExample() {
        return example;
    }

    public void setExample(String example) {
        this.example = example;
    }

    public String getUsage() {
        return usage;
    }

    public void setUsage(String usage) {
        this.usage = usage;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getForm() {
        return form;
    }

    public void setForm(String form) {
        this.form = form;
    }

    public boolean isFlip() {
        return isFlip;
    }

    public void setFlip(boolean flip) {
        isFlip = flip;
    }
}

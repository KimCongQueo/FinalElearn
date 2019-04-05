package nauq.mal.com.formapp.api.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import nauq.mal.com.formapp.models.Answer;
import nauq.mal.com.formapp.models.Question;

public class GetPracticeOutput extends BaseOutput {
    @SerializedName("result")
    public ArrayList<Question> result;
//    @SerializedName("result")
//    public ArrayList<Question1> result;
//    @SerializedName("success")
//    public Boolean success;

    public GetPracticeOutput() {
    }

//    class Question1 {
//        @SerializedName("id")
//        private int id;
//        @SerializedName("image")
//        private String image;
//        @SerializedName("question")
//        private String question;
//        @SerializedName("arrAns")
//        private ArrayList<Answer> arrAns;
//
//        public int getId() {
//            return id;
//        }
//
//        public void setId(int id) {
//            this.id = id;
//        }
//
//        public String getImage() {
//            return image;
//        }
//
//        public void setImage(String image) {
//            this.image = image;
//        }
//
//        public String getQuestion() {
//            return question;
//        }
//
//        public void setQuestion(String question) {
//            this.question = question;
//        }
//
//        public ArrayList<Answer> getArrAns() {
//            return arrAns;
//        }
//
//        public void setArrAns(ArrayList<Answer> arrAns) {
//            this.arrAns = arrAns;
//        }
//    }
//
//    public ArrayList<Question1> getResult() {
//        return result;
//    }
//
//    public void setResult(ArrayList<Question1> result) {
//        this.result = result;
//    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }
}


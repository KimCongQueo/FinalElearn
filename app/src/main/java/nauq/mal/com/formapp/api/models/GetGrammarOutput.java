package nauq.mal.com.formapp.api.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import nauq.mal.com.formapp.models.Grammar;
import nauq.mal.com.formapp.models.Words;

public class GetGrammarOutput extends  BaseOutput {
    @SerializedName("grammars")
    public ArrayList<Grammar> grammars;
    public boolean hasNextPage = true;

    public boolean isHasNextPage() {
        return hasNextPage;
    }

    public void setHasNextPage(boolean hasNextPage) {
        this.hasNextPage = hasNextPage;
    }
}

package nauq.mal.com.formapp.activities;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import nauq.mal.com.formapp.R;
import nauq.mal.com.formapp.models.PostItem;

public class AddQuesActivity extends BaseActivity implements View.OnClickListener {
    private EditText etQuestion;
    private Button btnSEnd, btnAttachImg;
    private ImageView imgBack;
    @Override
    protected int initLayout() {
        return R.layout.activity_add_ques;
    }

    @Override
    protected void initComponents() {
        setTitle(getString(R.string.txt_write_post));
        etQuestion = findViewById(R.id.et_ques);
        btnAttachImg = findViewById(R.id.btn_attach_img);
        btnSEnd = findViewById(R.id.btn_send_ques);
        imgBack = findViewById(R.id.imv_nav_left);
    }

    @Override
    protected void addListener() {
        btnAttachImg.setOnClickListener(this);
        btnSEnd.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_attach_img:
                break;
            case R.id.btn_send_ques:
                Intent returnIntent = new Intent();
                returnIntent.putExtra(getString(R.string.txt_et_question), new PostItem(14, etQuestion.getText().toString()));
                setResult(Activity.RESULT_OK, returnIntent);
                finish();
                break;
            case R.id.imv_nav_left:
                finish();
        }
    }
}

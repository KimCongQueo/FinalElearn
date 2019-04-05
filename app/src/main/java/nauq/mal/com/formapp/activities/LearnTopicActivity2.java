package nauq.mal.com.formapp.activities;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import nauq.mal.com.formapp.R;

public class LearnTopicActivity2 extends BaseActivity implements View.OnClickListener {
    private TextView tvOptions;
    private ImageView imvArrow;

    @Override
    protected int initLayout() {
        return R.layout.activity_learn_topic_2;
    }

    @Override
    protected void initComponents() {
        tvOptions = findViewById(R.id.tv_options);
        tvOptions.setVisibility(View.VISIBLE);
        imvArrow = findViewById(R.id.imv_arrow_down);
        imvArrow.setVisibility(View.VISIBLE);
        TextView tvTitle = findViewById(R.id.tv_title);
        tvTitle.setVisibility(View.GONE);
        //123
        //456
        //123
    }

    @Override
    protected void addListener() {
        tvOptions.setOnClickListener(this);
        imvArrow.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_options:
                //khonong break để dùng chung function với arrow down
            case R.id.imv_arrow_down:

                break;
        }
    }
}

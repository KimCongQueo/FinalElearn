package nauq.mal.com.formapp.fragments.menulearn;

import android.content.Intent;
import android.view.View;
import android.widget.Button;

import nauq.mal.com.formapp.R;
import nauq.mal.com.formapp.activities.LearnTopicActivity;
import nauq.mal.com.formapp.fragments.BaseFragment;

public class CourseFragment extends BaseFragment implements View.OnClickListener {
    private Button btnB1, btnToeic, btnIelts;
    @Override
    protected int initLayout() {
        return R.layout.fragment_menu_learn;
    }

    @Override
    protected void initComponents() {
        btnB1 = mView.findViewById(R.id.btn_b1);
        btnToeic = mView.findViewById(R.id.btn_toeic);
        btnIelts = mView.findViewById(R.id.btn_ielts);
    }

    @Override
    protected void addListener() {
        btnB1.setOnClickListener(this);
        btnIelts.setOnClickListener(this);
        btnToeic.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_b1:
                Intent i = new Intent(getActivity(), LearnTopicActivity.class);
                i.putExtra(getString(R.string.txt_course_name), "B1");
                startActivity(i);
                break;
            case R.id.btn_toeic:
                Intent ii = new Intent(getActivity(), LearnTopicActivity.class);
                ii.putExtra(getString(R.string.txt_course_name), "Toeic");
                startActivity(ii);
                break;
            case R.id.btn_ielts:
                Intent iii = new Intent(getActivity(), LearnTopicActivity.class);
                iii.putExtra(getString(R.string.txt_course_name), "Ielts");
                startActivity(iii);
                break;
        }
    }
}

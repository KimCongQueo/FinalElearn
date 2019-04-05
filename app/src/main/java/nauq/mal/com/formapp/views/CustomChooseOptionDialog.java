package nauq.mal.com.formapp.views;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import nauq.mal.com.formapp.R;
import nauq.mal.com.formapp.fragments.home.HomeFragment;
import nauq.mal.com.formapp.interfaces.ReturnTextFromDialog;
import nauq.mal.com.formapp.models.PostItem;

public class CustomChooseOptionDialog extends Dialog implements View.OnClickListener {
    public CustomChooseOptionDialog(@NonNull Context context) {
        super(context);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_choose_option);
        Window window = this.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.CENTER;
        wlp.width = WindowManager.LayoutParams.MATCH_PARENT;
        wlp.flags &= WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        window.setAttributes(wlp);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        this.getWindow().setAttributes(wlp);
        init();
        addListener();
    }


    private void init() {

    }
    private void addListener() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
        }
    }

//    @Override
//    public void setOnDismissListener(@Nullable OnDismissListener listener) {
//        super.setOnDismissListener(listener);
//        homeFragment.onResume();
//    }
}
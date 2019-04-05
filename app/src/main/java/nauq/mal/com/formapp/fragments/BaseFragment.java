package nauq.mal.com.formapp.fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;

import java.io.IOException;

import nauq.mal.com.formapp.R;
import nauq.mal.com.formapp.api.exception.ApiException;

/**
 * Created by Admin on 3/22/2017.
 */

public abstract class BaseFragment extends Fragment {
    protected View mView;
    protected int mViewId;
    protected Context mContext;
    protected ProgressDialog mProgressDialog;
    private MaterialDialog mAlertDialog;

    protected abstract int initLayout();

    protected abstract void initComponents();

    protected abstract void addListener();

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mView != null) {
            ViewGroup parent = (ViewGroup) mView.getParent();
            if (parent != null)
                parent.removeView(mView);
        }
        int layoutId = initLayout();
        if (layoutId != 0) {
            mViewId = layoutId;
        }
        try {
            mView = LayoutInflater.from(getActivity()).inflate(mViewId, container, false);
        } catch (InflateException e) {
        /* map is already there, just return view as it is */
        }
        return mView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mProgressDialog = new ProgressDialog(mContext);
        mProgressDialog.setCancelable(false);
        mProgressDialog.setMessage(getString(R.string.txt_waiting));
        initComponents();
        addListener();
    }

    public void toast(String message) {
        Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
    }

    public void toast(int messageId) {
        Toast.makeText(mContext, getString(messageId), Toast.LENGTH_SHORT).show();
    }

    public void showLoading(boolean isShow) {
        if (isShow) {
            mProgressDialog.show();
        } else {
            if (mProgressDialog.isShowing()) {
                mProgressDialog.dismiss();
            }
        }
    }

    public void showAlert(Exception e) {
        if (e instanceof ApiException)
            showAlert(((ApiException) e).getMessage(mContext));
        else if (e instanceof IOException)
            showAlert(R.string.err_network_available);
        else
            showAlert(R.string.err_unexpected_exception);
    }

    public void showAlert(int messageId) {
        showAlert(getString(messageId));
    }

    public void showAlert(String message) {
        if (mAlertDialog != null && mAlertDialog.isShowing()) {
            mAlertDialog.dismiss();
        }

        mAlertDialog = new MaterialDialog.Builder(mContext)
                .content(message)
                .positiveText(getString(R.string.txt_ok))
                .cancelable(false)
                .build();
        mAlertDialog.show();
    }

    protected void hideKeyBoard() {
        try {
            ((Activity)mContext).runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    try {
                        InputMethodManager inputManager = (InputMethodManager) mContext
                                .getSystemService(Context.INPUT_METHOD_SERVICE);
                        inputManager.hideSoftInputFromWindow(
                                ((Activity)mContext).getCurrentFocus().getApplicationWindowToken(),
                                InputMethodManager.HIDE_NOT_ALWAYS);
                    } catch (IllegalStateException e) {
                    } catch (Exception e) {
                    }
                }
            });

        } catch (IllegalStateException e) {
            // TODO: handle exception
        } catch (Exception e) {
        }
    }
}

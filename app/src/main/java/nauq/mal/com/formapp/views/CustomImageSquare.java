package nauq.mal.com.formapp.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

public class CustomImageSquare extends ImageView {

	public CustomImageSquare(Context context) {
		super(context);
	}

	public CustomImageSquare(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public CustomImageSquare(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		setMeasuredDimension(widthMeasureSpec, widthMeasureSpec);
	}

}

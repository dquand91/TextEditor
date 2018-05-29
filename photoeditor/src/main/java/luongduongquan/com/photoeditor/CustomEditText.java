package luongduongquan.com.photoeditor;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.EditText;

@SuppressLint("AppCompatCustomView")
public class CustomEditText extends EditText {
	Paint paint;

	public CustomEditText(Context context) {
		super(context);
		init();
	}

	public CustomEditText(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public void init(){
		paint = new Paint();
		paint.setAntiAlias(true);
		paint.setStyle(Paint.Style.STROKE);
		paint.setStrokeWidth(4);
		paint.setColor(Color.BLUE);
	}

	@Override
	protected void onDraw(Canvas canvas)
	{
		super.onDraw(canvas);
		canvas.drawRect(0, 0, getWidth(), getHeight(), paint);
	}

}

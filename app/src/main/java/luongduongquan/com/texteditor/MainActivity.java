package luongduongquan.com.texteditor;

import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import luongduongquan.com.photoeditor.OnPhotoEditorListener;
import luongduongquan.com.photoeditor.PhotoEditor;
import luongduongquan.com.photoeditor.PhotoEditorView;
import luongduongquan.com.photoeditor.ViewType;

public class MainActivity extends AppCompatActivity implements OnPhotoEditorListener {

	private static final String TAG = MainActivity.class.getSimpleName();
	private PhotoEditor mPhotoEditor;
	private PhotoEditorView mPhotoEditorView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		makeFullScreen();
		setContentView(R.layout.activity_main);
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

		initViews();

		mPhotoEditor = new PhotoEditor.Builder(this, mPhotoEditorView)
				.setPinchTextScalable(true)
				.build();
		mPhotoEditorView.getSource().setImageDrawable(getResources().getDrawable(R.drawable.ccc));
		mPhotoEditor.setOnPhotoEditorListener(this);
		mPhotoEditor.addText("Hello Quan", ContextCompat.getColor(MainActivity.this, R.color.red_color_picker));


	}

	private void initViews() {

		TextView btnSave;
		TextView btnCancel;

		mPhotoEditorView = (PhotoEditorView) findViewById(R.id.photoEditorView);
		btnSave = findViewById(R.id.btnSave_ActionBar);
		btnCancel = findViewById(R.id.btnCancel_ActionBar);

	}

	public void makeFullScreen() {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			View v = getCurrentFocus();
			if ( v instanceof EditText) {
				Rect outRect = new Rect();
				v.getGlobalVisibleRect(outRect);
				if (!outRect.contains((int)event.getRawX(), (int)event.getRawY())) {
					v.clearFocus();
					InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
					imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
				}
			}
		}
		return super.dispatchTouchEvent( event );
	}


	/*
	*
	* Start call back of OnPhotoEditorListener
	*
	* */
	@Override
	public void onEditTextChangeListener(View rootView, String text, int colorCode) {



	}

	@Override
	public void onAddViewListener(ViewType viewType, int numberOfAddedViews) {
		Log.d(TAG, "onAddViewListener() called with: viewType = [" + viewType + "], numberOfAddedViews = [" + numberOfAddedViews + "]");
	}

	@Override
	public void onRemoveViewListener(int numberOfAddedViews) {
		Log.d(TAG, "onRemoveViewListener() called with: numberOfAddedViews = [" + numberOfAddedViews + "]");
	}

	@Override
	public void onStartViewChangeListener(ViewType viewType) {
		Log.d(TAG, "onStartViewChangeListener() called with: viewType = [" + viewType + "]");
	}

	@Override
	public void onStopViewChangeListener(ViewType viewType) {
		Log.d(TAG, "onStopViewChangeListener() called with: viewType = [" + viewType + "]");
	}
	/*
	 *
	 * End call back of OnPhotoEditorListener
	 *
	 * */
}

package luongduongquan.com.texteditor;

import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import luongduongquan.com.photoeditor.OnPhotoEditorListener;
import luongduongquan.com.photoeditor.PhotoEditor;
import luongduongquan.com.photoeditor.PhotoEditorView;
import luongduongquan.com.photoeditor.ViewType;

public class MainActivity extends AppCompatActivity implements OnPhotoEditorListener {

	private static final String TAG = MainActivity.class.getSimpleName();
	private PhotoEditor mPhotoEditor;
	private PhotoEditorView mPhotoEditorView;
	private int current_width = 1;
	private int current_height = 1;

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

//		final View activityRootView = findViewById(R.id.actionBar_main);
//		activityRootView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
//			@Override
//			public void onGlobalLayout() {
//				int heightDiff = activityRootView.getRootView().getHeight() - activityRootView.getHeight();
//				Log.d(TAG, "QUAN --- RootHeight = " + activityRootView.getRootView().getHeight() + " --- "
//						+ "Height = " + activityRootView.getHeight() +  " --- " + "heightDiff = " + heightDiff );
//
//
//			}
//		});

		final View root  = findViewById(android.R.id.content);

		root.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
			Rect r = new Rect();
			{
				root.getWindowVisibleDisplayFrame(r);
			}
			@Override
			public void onGlobalLayout() {
				Rect r2 = new Rect();
				root.getWindowVisibleDisplayFrame(r2);
				int keyboardHeight = r.height() - r2.height();


					final RelativeLayout actionBarMain = findViewById(R.id.actionBar_main);
					Log.d(TAG, "current_height: " + current_height + " ---- " +  "r2.height()" + r2.height());
					float xScale = (float)(r2.width() - actionBarMain.getWidth()) / (current_width - actionBarMain.getWidth());
					float yScale = (float)(r2.height() - actionBarMain.getHeight()) / (current_height - actionBarMain.getHeight());
					float scale = Math.min(xScale, yScale);

					mPhotoEditor.scaleTextView(scale);

					current_height = r2.height();
					current_width = r2.width();

//				if (keyboardHeight > 100) {
//					root.scrollTo(0, keyboardHeight);
//				}
//				else {
//					root.scrollTo(0, 0);
//				}

//				actionBarMain.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
//					@Override
//					public void onGlobalLayout() {
//						actionBarMain.getViewTreeObserver().removeOnGlobalLayoutListener(this);
//						actionBarMain.getHeight(); //height is ready
//						Log.d(TAG, "QUAN --- BarHeight = " + actionBarMain.getHeight() );
//					}
//				});


//


				Log.d(TAG, "QUAN --- keyboardHeight = " + keyboardHeight + " --- "
						+ "FullScreen = " + r.height() +  " --- " + "r2.height() = " + r2.height() );
			}
		});


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

		Log.d(TAG, "QUAN123    onEditTextChangeListener: ");

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

package it.sephiroth.android.library.uigestures.demo;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import it.sephiroth.android.library.uigestures.UIGestureRecognizer;
import it.sephiroth.android.library.uigestures.UIGestureRecognizerDelegate;

public class BaseTest extends AppCompatActivity implements UIGestureRecognizer.OnActionListener {

    public UIGestureRecognizerDelegate delegate;
    private TextView mTextView;
    private TextView mTextView2;
    private UIGestureRecognizer.State mCurrentState;
    private long timeSpan = 0;

    public TextView getTextView() {
        return mTextView;
    }

    public TextView getTextView2() {
        return mTextView2;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timeSpan = System.currentTimeMillis();

        UIGestureRecognizer.Companion.setLogEnabled(true);
        delegate = new UIGestureRecognizerDelegate(null);
        findViewById(R.id.activity_main).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(final View view, final MotionEvent motionEvent) {
                long time = System.currentTimeMillis() - timeSpan;
                CharSequence currentText = mTextView2.getText();
                mTextView2.setText(time + "ms, action:" + actionToString(motionEvent.getActionMasked()));
                mTextView2.append("\n");
                mTextView2.append(currentText);

                return delegate.onTouchEvent(view, motionEvent);
            }
        });
    }

    private static String actionToString(final int action) {
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                return "ACTION_DOWN";
            case MotionEvent.ACTION_UP:
                return "ACTION_UP";
            case MotionEvent.ACTION_CANCEL:
                return "ACTION_CANCEL";
            case MotionEvent.ACTION_MOVE:
                return "ACTION_MOVE";
            case MotionEvent.ACTION_POINTER_DOWN:
                return "ACTION_POINTER_DOWN";
            case MotionEvent.ACTION_POINTER_UP:
                return "ACTION_POINTER_UP";
            default:
                return "ACTION_OTHER";
        }
    }

    public UIGestureRecognizer.State getCurrentState() {
        return mCurrentState;
    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();
        mTextView = findViewById(R.id.text);
        mTextView2 = findViewById(R.id.text2);
    }

    @Override
    public void onGestureRecognized(@NonNull final UIGestureRecognizer recognizer) {
        Log.i(getClass().getSimpleName(), "onGestureRecognized: " + recognizer);
        mCurrentState = recognizer.getState();
        mTextView.setText(recognizer.getTag() + ": " + recognizer.getState().name());
        Log.v(getClass().getSimpleName(), mTextView.getText().toString());
    }
}

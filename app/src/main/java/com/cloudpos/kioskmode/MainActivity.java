package com.cloudpos.kioskmode;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.Spannable;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.TextView;


public class MainActivity extends Activity implements View.OnClickListener {
    private TextView message;
    private Handler handler;
    private HandleCallBack callBack;
    private TextView mStart, mStop;
    private Handler.Callback handleCallBack = msg -> {
        switch (msg.what) {
            case HandleCallbackImpl.SUCCESS_CODE:
                setTextcolor(msg.obj.toString(), Color.BLUE);
                break;
            case HandleCallbackImpl.ERROR_CODE:
                setTextcolor(msg.obj.toString(), Color.RED);
                break;
            default:
                setTextcolor(msg.obj.toString(), Color.BLACK);
                break;
        }
        return false;
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUI();
    }

    private void initUI() {
        handler = new Handler(handleCallBack);
        callBack = new HandleCallbackImpl(this, handler);
        message = (TextView) findViewById(R.id.message);
        mStart = (TextView) findViewById(R.id.start);
        mStop = (TextView) findViewById(R.id.stop);
        mStop.setOnClickListener(this);
        mStart.setOnClickListener(this);
        message.setText("");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.start:
                startLockTask();
                callBack.sendResponse("start lock task");
                break;
            case R.id.stop:
                stopLockTask();
                callBack.sendResponse("stop lock task success");
                break;
        }
    }

    private void setTextcolor(String msg, int color) {
        Spannable span = Spannable.Factory.getInstance().newSpannable(msg);
        ForegroundColorSpan colorSpan = new ForegroundColorSpan(color);
        span.setSpan(colorSpan, 0, span.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        message.append(span);
    }
}

package com.aachaerandio.slotmachine.views;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.aachaerandio.slotmachine.Listeners.OnSpinnerEndListener;
import com.aachaerandio.slotmachine.Utils;
import com.aachaerandio.slotmachine.data.State;

/**
 * Created by Araceli on 27/08/2014.
 */
public class SpinnerView extends LinearLayout implements Runnable{

    private Context mContext;
    ImageView upperImage;
    ImageView selectedImage;
    ImageView lowerImage;
    State.SlotIcon selected;

    public static final int SPEED = 50;
    public int counter;

    Handler mHandler = new Handler();
    OnSpinnerEndListener listener;

//    View mView;

    public SpinnerView(Context context) {
        super(context);
        mContext = context;
        init();
    }

    public SpinnerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init();
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void init() {
        setOrientation(VERTICAL);
        upperImage = new ImageView(getContext());
        selectedImage = new ImageView(getContext());
        lowerImage = new ImageView(getContext());

        selected = State.SlotIcon.values()[Utils.getRandomInt3()];
        upperImage.setImageResource(selected.getPrevious().slotId);
        selectedImage.setImageResource(selected.slotId);
        lowerImage.setImageResource(selected.getNext().slotId);

        upperImage.setAlpha(0.5f);
        lowerImage.setAlpha(0.5f);

        this.addView(upperImage);
        this.addView(selectedImage);
        this.addView(lowerImage);
    }

    public void startSpinning(int delay, int counter){
        this.counter = counter;
        mHandler.postDelayed(this, delay);

    }

    @Override
    public void run() {
        // Call to spin once
        spin();

        if(--counter > 0) {
            mHandler.postDelayed(this, 100-SPEED);
        }
        else{
            listener.checkAndSave();
        }

    }

    private void spin() {
        selected = selected.getPrevious();

        upperImage.setImageResource(selected.getPrevious().slotId);
        selectedImage.setImageResource(selected.slotId);
        lowerImage.setImageResource(selected.getNext().slotId);

        upperImage.invalidate();
        selectedImage.invalidate();
        lowerImage.invalidate();
    }

    public State.SlotIcon getSelected() {
        return selected;
    }

    public void setListener(OnSpinnerEndListener listener){
        this.listener = listener;

    }
}

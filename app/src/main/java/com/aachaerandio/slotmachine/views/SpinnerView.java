package com.aachaerandio.slotmachine.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.aachaerandio.slotmachine.Utils;
import com.aachaerandio.slotmachine.data.State;

/**
 * Created by Araceli on 27/08/2014.
 */
public class SpinnerView extends LinearLayout implements Runnable{

    ImageView upperImage;
    ImageView selectedImage;
    ImageView lowerImage;
    State.SlotIcon selected;

//    View mView;

    public SpinnerView(Context context) {
        super(context);
        init();
    }

    public SpinnerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        setOrientation(VERTICAL);
        upperImage = new ImageView(getContext());
        selectedImage = new ImageView(getContext());
        lowerImage = new ImageView(getContext());

        selected = State.SlotIcon.values()[Utils.getRandomInt3()];
        upperImage.setImageResource(selected.getPrevious().slotId);
        selectedImage.setImageResource(selected.slotId);
        lowerImage.setImageResource(selected.getNext().slotId);

//        mView = View.inflate(this.getContext(), R.id.spinnerView, this);

//        mView.findViewById(upperImage);

        this.addView(upperImage);
        this.addView(selectedImage);
        this.addView(lowerImage);
    }

    public void startSpinning(){

    }

    @Override
    public void run() {
        for (int i = 0; i < 50; i++) {
            spin();
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
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
}

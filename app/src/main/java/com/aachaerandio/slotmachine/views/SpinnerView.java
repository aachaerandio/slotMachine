package com.aachaerandio.slotmachine.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.aachaerandio.slotmachine.R;

/**
 * Created by Araceli on 27/08/2014.
 */
public class SpinnerView extends LinearLayout {

    ImageView upperImage;
    ImageView selectedImage;
    ImageView lowerImage;
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

        upperImage.setImageResource(R.drawable.slot_icon_01);
        selectedImage.setImageResource(R.drawable.slot_icon_02);
        lowerImage.setImageResource(R.drawable.slot_icon_03);

//        mView = View.inflate(this.getContext(), R.id.spinnerView, this);

//        mView.findViewById(upperImage);

        this.addView(upperImage);
        this.addView(selectedImage);
        this.addView(lowerImage);
    }


}

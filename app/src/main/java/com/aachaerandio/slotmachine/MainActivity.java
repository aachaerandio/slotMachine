package com.aachaerandio.slotmachine;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;

import com.aachaerandio.slotmachine.data.SlotService;
import com.aachaerandio.slotmachine.data.State;
import com.aachaerandio.slotmachine.views.SpinnerView;

import java.util.Random;


public class MainActivity extends ActionBarActivity {

    private int [] images = {
         R.drawable.slot_icon_01,
         R.drawable.slot_icon_02,
         R.drawable.slot_icon_03
    };

    Random random = new Random();
    Animation animation;
    SlotService slotService;
    protected SpinnerView mSpinnerView1;
    protected SpinnerView mSpinnerView2;
    protected SpinnerView mSpinnerView3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button start = (Button)findViewById(R.id.start_button);
        Button history = (Button)findViewById(R.id.list_button);

        //animation = AnimationUtils.loadAnimation(this, R.anim.scale);
        slotService = new SlotService(this);

        mSpinnerView1 = (SpinnerView) findViewById(R.id.spinnerView);
        mSpinnerView2 = (SpinnerView) findViewById(R.id.spinnerView2);
        mSpinnerView3 = (SpinnerView) findViewById(R.id.spinnerView3);


        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                int randBuffer[] = new int [3];
//                ImageView[] ivFruit = {
//                        (ImageView)findViewById(R.id.imageView1),
//                        (ImageView)findViewById(R.id.imageView2),
//                        (ImageView)findViewById(R.id.imageView3)
//                };
//                for(int i=0;i<3;i++)
//                {
//                    randBuffer[i] = random.nextInt(images.length);
//                    ivFruit[i].setImageResource(images[randBuffer[i]]);
//                    ivFruit[i].startAnimation(animation);
//                }

                startGame();

            }
        });


        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ListStateActivity.class);
                startActivity(intent);
            }
        });
    }

    public void startGame(){
        Handler handler = new Handler();

        handler.post(mSpinnerView1);
        handler.postDelayed(mSpinnerView2, 500);
        handler.postDelayed(mSpinnerView3, 1000);

        State.SlotIcon[] slotIcons = getSelectedCombination();
        slotService.insert(new State(slotIcons));

    }

    public State.SlotIcon[] getSelectedCombination(){
        State.SlotIcon[] result = new State.SlotIcon[3];

        result[0] = mSpinnerView1.getSelected();
        result[1] = mSpinnerView2.getSelected();
        result[2] = mSpinnerView3.getSelected();

        return result;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

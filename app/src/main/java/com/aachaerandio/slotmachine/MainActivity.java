package com.aachaerandio.slotmachine;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import com.aachaerandio.slotmachine.data.SlotService;
import com.aachaerandio.slotmachine.data.State;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button start = (Button)findViewById(R.id.start_button);
        animation = AnimationUtils.loadAnimation(this, R.anim.scale);
        slotService = new SlotService(this);

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

                State.SlotIcon[] slotIcons = Utils.generateRandomResults();
                slotService.insert(new State(slotIcons));


            }
        });
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

package com.aachaerandio.slotmachine;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;

import com.aachaerandio.slotmachine.Listeners.OnSpinnerEndListener;
import com.aachaerandio.slotmachine.data.SlotService;
import com.aachaerandio.slotmachine.data.State;
import com.aachaerandio.slotmachine.views.SpinnerView;

import java.util.Random;



public class MainActivity extends ActionBarActivity implements OnSpinnerEndListener{

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
    public int semaphore;

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

        mSpinnerView1.setListener(this);
        mSpinnerView2.setListener(this);
        mSpinnerView3.setListener(this);

        // Start button
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startGame();
            }
        });

        // History button to show listView
        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ListStateActivity.class);
                startActivity(intent);
            }
        });
    }

    public void startGame(){
        semaphore = 3;

        // Get ramdom number of iterations for every column
        int iteration1 = Utils.getRandomInt3() + 50;
        int iteration2 = Utils.getRandomInt3() + 50;
        int iteration3 = Utils.getRandomInt3() + 50;
//        int iteration1 = (random + 30);
//        int iteration2 = (random + 35);
//        int iteration3 = (random + 40);
        //int[] iterations = {iteration1, iteration2, iteration3};

        int numIt1;
        int numIt2;
        int numIt3;
        int itemArray = Utils.getRandomInt3();
        //numIt1 = iterations[itemArray];
/*        if (itemArray == 0) {
            numIt2 = iterations[1];
            numIt3 = iterations[2];
        } else if(itemArray == 1) {
            numIt2 = iterations[2];
            numIt3 = iterations[0];
        } else {
            numIt2 = iterations[0];
            numIt3 = iterations[1];
        }*/

        // Start spinning
        mSpinnerView1.startSpinning(0, iteration1);
        mSpinnerView2.startSpinning(100, iteration2);
        mSpinnerView3.startSpinning(150, iteration3);


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

    @Override
    public void checkAndSave() {
        if (--semaphore <= 0) {
            State.SlotIcon[] slotIcons = getSelectedCombination();
            slotService.insert(new State(slotIcons));

            if ((slotIcons[0].slotId == slotIcons[1].slotId) && (slotIcons[1].slotId == slotIcons[2].slotId)) {
                new ClaimDialogFragment().show(getSupportFragmentManager(), "Prize");
            }
        }
    }

}

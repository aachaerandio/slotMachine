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

import com.aachaerandio.slotmachine.Listeners.OnSpinnerEndListener;
import com.aachaerandio.slotmachine.data.SlotService;
import com.aachaerandio.slotmachine.data.State;
import com.aachaerandio.slotmachine.views.SpinnerView;

import java.util.Random;



public class MainActivity extends ActionBarActivity implements OnSpinnerEndListener{

    public static final String WINNER_ICON = "WINNER_ICON";
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
        Random random = new Random();
        int iteration1 = random.nextInt(5) + 50;
        int iteration2 = random.nextInt(5) + 50;
        int iteration3 = random.nextInt(5) + 50;

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
            final State.SlotIcon[] slotIcons = getSelectedCombination();
            slotService.insert(new State(slotIcons));

            if ((slotIcons[0].slotId == slotIcons[1].slotId) && (slotIcons[1].slotId == slotIcons[2].slotId)) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Bundle bundle = new Bundle();
                        bundle.putInt(WINNER_ICON, slotIcons[0].ordinal());
                        ClaimDialogFragment dialog = new ClaimDialogFragment();
                        dialog.setArguments(bundle);
                        dialog.show(getSupportFragmentManager(), "Prize");
                    }
                }, 250);

            }
        }
    }

}

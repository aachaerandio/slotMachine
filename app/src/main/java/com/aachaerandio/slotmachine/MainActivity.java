package com.aachaerandio.slotmachine;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;

import com.aachaerandio.slotmachine.Listeners.OnSpinnerEndListener;
import com.aachaerandio.slotmachine.adapter.SpinnerViewAdapter;
import com.aachaerandio.slotmachine.data.SlotService;
import com.aachaerandio.slotmachine.data.State;
import com.aachaerandio.slotmachine.views.SpinnerView2;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class MainActivity extends ActionBarActivity implements OnSpinnerEndListener{

    public static final String WINNER_ICON = "WINNER_ICON";
    public static final String LOOSE = "LOOSE";
    private int [] images = {
         R.drawable.slot_icon_01,
         R.drawable.slot_icon_02,
         R.drawable.slot_icon_03
    };

    Random random = new Random();
    SlotService slotService;
    protected SpinnerView2 mSpinnerView1;
    protected SpinnerView2 mSpinnerView2;
    protected SpinnerView2 mSpinnerView3;
    public int semaphore;

    public SpinnerViewAdapter mAdapter1;
    public SpinnerViewAdapter mAdapter2;
    public SpinnerViewAdapter mAdapter3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button start = (Button)findViewById(R.id.start_button);
        Button history = (Button)findViewById(R.id.list_button);

        slotService = new SlotService(this);

        mAdapter1 = new SpinnerViewAdapter(this, R.layout.spin_list_item, initList());
        mAdapter2 = new SpinnerViewAdapter(this, R.layout.spin_list_item, initList());
        mAdapter3 = new SpinnerViewAdapter(this, R.layout.spin_list_item, initList());

        mSpinnerView1 = (SpinnerView2) findViewById(R.id.spinnerView);
        mSpinnerView2 = (SpinnerView2) findViewById(R.id.spinnerView2);
        mSpinnerView3 = (SpinnerView2) findViewById(R.id.spinnerView3);

        mSpinnerView1.setAdapter(mAdapter1);
        mSpinnerView2.setAdapter(mAdapter2);
        mSpinnerView3.setAdapter(mAdapter3);

        mSpinnerView1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_MOVE) {
                    return true;
                    }
                return false;
            }
        });
        mSpinnerView2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_MOVE) {
                    return true;
                }
                return false;
            }
        });
        mSpinnerView3.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_MOVE) {
                    return true;
                }
                return false;
            }
        });

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

        final View mHorizontalView = findViewById(R.id.horizontalView);

        mHorizontalView.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {

                    @Override
                    public void onGlobalLayout() {
                        // make sure it is not called anymore
                        mHorizontalView.getViewTreeObserver().removeGlobalOnLayoutListener(this);

                       int heigth = mHorizontalView.getMeasuredHeight();
                    }
                });
    }

    /**
     *  Each time the spinner starts it initialises the semaphore and
     *  generates the number of iterations of each spinner
     */
    public void startGame(){
        semaphore = 3;

        // Get ramdom number of iterations for every column
        Random random = new Random();
        int iteration1 = random.nextInt(5) + 160;
        int iteration2 = random.nextInt(5) + 160;
        int iteration3 = random.nextInt(5) + 160;

        // Start spinning
        mSpinnerView1.startSpinning(0, iteration1);
        mSpinnerView2.startSpinning(100, iteration2);
        mSpinnerView3.startSpinning(150, iteration3);
    }

    private List<State.SlotIcon> initList() {

        List<State.SlotIcon> list = new ArrayList<State.SlotIcon>();
        int random = Utils.getRandomInt3();
        State.SlotIcon slotIcon = State.SlotIcon.values()[random];
        list.add(slotIcon);

        for (int i=0; i<250; i++) {
            slotIcon = slotIcon.getNext();
            list.add(slotIcon);
        }

        return list;
    }

    /**
     *  Return the winner combination
     * @return State.SlotIcon[]
     */
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

    /**
     * Callback implementation invoked by the spinners when they finish.
     * A semaphore is orchestrating the spinners processes.
     */
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
            else {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Bundle bundle = new Bundle();
                        bundle.putBoolean(LOOSE, true);
                        ClaimDialogFragment dialog = new ClaimDialogFragment();
                        dialog.setArguments(bundle);
                        dialog.show(getSupportFragmentManager(), "Prize");
                    }
                }, 250);
            }
        }
    }

}

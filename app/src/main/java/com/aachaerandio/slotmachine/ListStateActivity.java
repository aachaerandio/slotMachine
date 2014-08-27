package com.aachaerandio.slotmachine;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.aachaerandio.slotmachine.adapter.StateItemAdapter;
import com.aachaerandio.slotmachine.data.SlotService;
import com.aachaerandio.slotmachine.data.State;

import java.util.ArrayList;

public class ListStateActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_state);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new ListStateFragment())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.list_state, menu);
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
     * A placeholder fragment containing a simple view.
     */
    public static class ListStateFragment extends ListFragment {

        private SlotService slotService;
        private ArrayList<State> states;
        private StateItemAdapter mAdapter;

        public ListStateFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_list_state, container, false);
            ListView list = (ListView)rootView.findViewById(R.id.list);

            list.setAdapter(mAdapter);
            return rootView;
        }


        private void readData() {
            states = slotService.read();

            if (mAdapter == null){
                //custom adapter:
                //array adapter to bind the array to the listview
                mAdapter = new StateItemAdapter(getActivity(), R.layout.list_item_state, states);
            }
            else{
                mAdapter.clear();
                for(State state : states){
                    mAdapter.add(state);
                }
            }
        }

        // Close database
        @Override
        public void onDestroy() {
            slotService.destroy();
            super.onDestroy();
        }

        @Override
        public void onResume() {
            readData();
            mAdapter.notifyDataSetChanged();

            super.onResume();
        }


    }
}

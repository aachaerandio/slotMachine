package com.aachaerandio.slotmachine;

import java.util.Random;

import static com.aachaerandio.slotmachine.data.State.SlotIcon;

/**
 * Created by Araceli on 27/08/2014.
 */
public class Utils {

    public static SlotIcon[] generateRandomResults() {

        SlotIcon[] results = new SlotIcon[3];
        Random random = new Random();

        for(int i=0; i<3; i++) {
            results[i] = SlotIcon.values()[random.nextInt(3)];
        }

        return results;
    }
}

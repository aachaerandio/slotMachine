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

    public static Integer getRandomInt3(){
        Random random = new Random();
        Integer result = 0;
        Integer i = random.nextInt(100);
        if (i > 0 && i < 33){
            result = 0;
        }
        else if (i > 33 && i < 66){
            result = 1;
        }
        else if (i > 66){
            result = 2;
        }

        return result;
    }
}

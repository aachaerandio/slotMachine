package com.aachaerandio.slotmachine.data;

import com.aachaerandio.slotmachine.R;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Araceli on 27/08/2014.
 */
public class State {
    public enum SlotIcon {
        CHERRY(R.drawable.slot_icon_01, R.drawable.list_icon_01), //0
        LEMON(R.drawable.slot_icon_02, R.drawable.list_icon_02), //1
        PEAR(R.drawable.slot_icon_03, R.drawable.list_icon_03); //2

        public int slotId;

        public int listId;

        private SlotIcon(int slotId, int listId) {
            this.slotId = slotId;
            this.listId = listId;
        }

        public SlotIcon getNext() {
            return SlotIcon.values()[(this.ordinal() + 1)%SlotIcon.values().length];
        }

        public SlotIcon getPrevious() {
            return (this.ordinal() > 0)
                    ? SlotIcon.values()[this.ordinal() - 1]
                    : SlotIcon.values()[SlotIcon.values().length-1];
        }
        };

    private Long id;
    private SlotIcon iconA;
    private SlotIcon iconB;
    private SlotIcon iconC;
    private Date created;

    public State() {
    }

    public State(SlotIcon[] icons) {
        iconA = icons[0];
        iconB = icons[1];
        iconC = icons[2];
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SlotIcon getIconA() {
        return iconA;
    }

    public void setIconA(SlotIcon iconA) {
        this.iconA = iconA;
    }

    public SlotIcon getIconB() {
        return iconB;
    }

    public void setIconB(SlotIcon iconB) {
        this.iconB = iconB;
    }

    public SlotIcon getIconC() {
        return iconC;
    }

    public void setIconC(SlotIcon iconC) {
        this.iconC = iconC;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    @Override
    public String toString() {
        Calendar c = Calendar.getInstance();
        String date = "";
        if (created != null) {
           c.setTime(created);
           date = String.format("[%d:%d:%d]", c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE), c.get(Calendar.SECOND));
        }
        return String.format("%s %s %s %s", date, iconA.name(), iconB.name(), iconC.name());
    }
}

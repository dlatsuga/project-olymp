package org.pantheon.robot;

import lombok.Getter;
import lombok.Setter;
import org.pantheon.spares.Hand;
import org.pantheon.spares.Head;
import org.pantheon.spares.Leg;

@Getter
@Setter
public abstract class BaseModel implements Robot {
    private Head head;
    private Hand hand;
    private Leg leg;

    public BaseModel() {
        System.out.println(this + " Model t500 constructor()");
    }

    public BaseModel(Head head, Hand hand, Leg leg) {
        this.head = head;
        this.hand = hand;
        this.leg = leg;
    }

    @Override
    public void action() {

    }

    @Override
    public void dance() {

    }
}

package org.pantheon;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ModelT1000 implements Robot {
    private Head head;
    private Hand hand;
    private Leg leg;

    private String color;
    private int year;
    private boolean soundEnabled;

    public ModelT1000() {
    }

    public ModelT1000(Head head, Hand hand, Leg leg) {
        this.head = head;
        this.hand = hand;
        this.leg = leg;
    }

    public ModelT1000(String color, int year, boolean soundEnabled) {
        this.color = color;
        this.year = year;
        this.soundEnabled = soundEnabled;
    }

    public ModelT1000(Head head, Hand hand, Leg leg, String color, int year, boolean soundEnabled) {
        this.head = head;
        this.hand = hand;
        this.leg = leg;
        this.color = color;
        this.year = year;
        this.soundEnabled = soundEnabled;
    }

    @Override
    public void action() {
        head.speak();
        hand.catchSomething();
        leg.go();
        System.out.println("color " + color);
        System.out.println("year " + year);
        System.out.println("soundEnabled " + soundEnabled);
    }

    @Override
    public void dance() {
        System.out.println("ModelT1000 dancing...");
    }
}

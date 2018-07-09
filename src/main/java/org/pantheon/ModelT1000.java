package org.pantheon;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ModelT1000 extends BaseModel {

    private String color;
    private int year;
    private boolean soundEnabled;

    public ModelT1000() {
    }

    public ModelT1000(Head head, Hand hand, Leg leg) {
        super(head, hand, leg);
    }

    public ModelT1000(String color, int year, boolean soundEnabled) {
        this.color = color;
        this.year = year;
        this.soundEnabled = soundEnabled;
    }

    public ModelT1000(Head head, Hand hand, Leg leg, String color, int year, boolean soundEnabled) {
        super(head, hand, leg);
        this.color = color;
        this.year = year;
        this.soundEnabled = soundEnabled;
        System.out.println(this + " created");
    }

    @Override
    public void action() {
        getHead().speak();
        getHand().catchSomething();
        getLeg().go();
        System.out.println("color " + color);
        System.out.println("year " + year);
        System.out.println("soundEnabled " + soundEnabled);
    }

    @Override
    public void dance() {
        System.out.println("ModelT1000 dancing...");
    }
}

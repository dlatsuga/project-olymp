package org.pantheon;

public class ModelT1000 implements Robot {
    private Head head;
    private Hand hand;
    private Leg leg;

    public ModelT1000() {
    }

    public ModelT1000(Head head, Hand hand, Leg leg) {
        this.head = head;
        this.hand = hand;
        this.leg = leg;
    }

    @Override
    public void action() {
        head.speak();
        hand.catchSomething();
        leg.go();
    }

    @Override
    public void dance() {
        System.out.println("ModelT1000 dancing...");
    }
}

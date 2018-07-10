package org.pantheon.robot;

import lombok.Getter;
import lombok.Setter;
import org.pantheon.spares.Hand;
import org.pantheon.spares.Head;
import org.pantheon.spares.Leg;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import static org.springframework.beans.factory.config.ConfigurableBeanFactory.*;

@Getter
@Setter
@Component
public class ModelT1000 extends BaseModel {

    private String color;
    private int year;
    private boolean soundEnabled;

    public ModelT1000() {
    }

    public ModelT1000(Head head, Hand hand, Leg leg, String color, int year, boolean soundEnabled) {
        super(head, hand, leg);
        this.color = color;
        this.year = year;
        this.soundEnabled = soundEnabled;
        System.out.println(this + " created");
    }

    @Bean
    @Scope(value = SCOPE_PROTOTYPE)
    private ModelT1000 model1 (){
        return new ModelT1000();
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

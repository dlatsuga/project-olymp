package org.pantheon;

import org.pantheon.robot.Robot;

import java.util.Collection;

public class T1000Pool implements RobotPool {
    private Collection<Robot> robotCollection;

    @Override
    public Collection<Robot> getRobotCollection() {
        return null;
    }

    public void setRobotCollection(Collection<Robot> robotCollection) {
        this.robotCollection = robotCollection;
    }

    void action() {
        for (Robot robot : robotCollection) {
            robot.action();
            System.out.println("********************");
        }
    }
}

package com.roomfinder;

/**
 * Class which represents a single set of Directions to any given room
 * Connor Reeder
 */

public class Directions {
    private Entrance buildingEntrance;
    private Building destBuilding;
    private Room destRoom;
    private double estTravelTime;
    private NavigationInstruction[] stepTypes;
    private double[] stepDistances;

    private final static String[] COMMANDS = {
        "Turn Left",
            "Turn Right",
            "Continue Straight",
            "Go Upstairs",
            "Go Downstairs"
    };

    public Directions(Entrance buildingEntrance, Building destBuilding, Room destRoom, double estTravelTime, NavigationInstruction[] stepTypes, double[] stepDistances) {
        this.buildingEntrance = buildingEntrance;
        this.destBuilding = destBuilding;
        this.destRoom = destRoom;
        this.estTravelTime = estTravelTime;
        this.stepTypes = stepTypes;
        this.stepDistances = stepDistances;
    }

    public Entrance getBuildingEntrance() {
        return buildingEntrance;
    }

    public void setBuildingEntrance(Entrance buildingEntrance) {
        this.buildingEntrance = buildingEntrance;
    }

    public Building getDestBuilding() {
        return destBuilding;
    }

    public void setDestBuilding(Building destBuilding) {
        this.destBuilding = destBuilding;
    }

    public Room getDestRoom() {
        return destRoom;
    }

    public void setDestRoom(Room destRoom) {
        this.destRoom = destRoom;
    }

    public double getEstTravelTime() {
        return estTravelTime;
    }

    public void setEstTravelTime(double estTravelTime) {
        this.estTravelTime = estTravelTime;
    }

    public NavigationInstruction[] getStepTypes() {
        return stepTypes;
    }

    public void setStepTypes(NavigationInstruction[] stepTypes) {
        this.stepTypes = stepTypes;
    }

    public double[] getStepDistances() {
        return stepDistances;
    }

    public void setStepDistances(double[] stepDistances) {
        this.stepDistances = stepDistances;
    }

    public String[] getSteps() {
        String[] steps = new String[stepTypes.length];
        for (int i = 0; i < steps.length; i++) {
            String command = "";
            if (stepTypes[i] == NavigationInstruction.TurnLeft)
                command = "Turn Left in ";
            else if (stepTypes[i] == NavigationInstruction.TurnRight)
                command = "Turn Right in";
            else if (stepTypes[i] == NavigationInstruction.ContinueStraight)
                command = "Continue Straight for";
            else if (stepTypes[i] == NavigationInstruction.Upstairs)
                command = "Go upstairs in";
            else if (stepTypes[i] == NavigationInstruction.Downstairs)
                command = "Go Downstairs in";
            steps[i] = command + " " + stepTypes[i] + " feet";
        }
        return steps;
    }
}

package Simulation;

import java.util.ArrayList;

public class testorg {

    private String ID;

    public testorg(String ID) {
        this.ID = ID;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    @Override
    public String toString() {
        return "testorg{" +
                "ID='" + ID + '\'' +
                '}';
    }

}



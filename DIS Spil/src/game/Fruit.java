package game;

import java.io.DataOutputStream;

public class Fruit {

    pair location;

    DataOutputStream outToClient;

    public Fruit(pair location) {
        this.location = location;
    }

    public void setOutToClient(DataOutputStream outToClient) {
        this.outToClient = outToClient;
    }

    public pair getLocation() {
        return location;
    }

    public void setLocation(pair location) {
        this.location = location;
    }

    public int getXpos() {
        return location.x;
    }
    public int getYpos() {
        return location.y;
    }

}

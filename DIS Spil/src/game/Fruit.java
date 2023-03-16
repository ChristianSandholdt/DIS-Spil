package game;

import java.io.DataOutputStream;

public class Fruit {

    static pair location;

    DataOutputStream outToClient;

    public Fruit(pair location) {
        this.location = location;
    }

    public void setOutToClient(DataOutputStream outToClient) {
        this.outToClient = outToClient;
    }

    public synchronized void sendFruit(String message){
        try {
            outToClient.writeBytes(message + '\n');
        } catch (Exception e) {
            e.printStackTrace();
        }
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

    public void setXpos(int xpos) {
        this.location.x = xpos;
    }

    public int getYpos() {
        return location.y;
    }

    public void setYpos(int ypos) {
        this.location.y = ypos;
    }

}

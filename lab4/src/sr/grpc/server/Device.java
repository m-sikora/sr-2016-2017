package sr.grpc.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by zero on 2017-05-03.
 */
public abstract class Device {
    public static HashMap<String, Device> nameMap = new HashMap<>();

    private String name;
    private User user;
    private ArrayList<User> observers = new ArrayList<>();

    public Device() {
        this("unnamed");
    }

    public Device(String name) {
        if (name == null || name.equals("unnamed")) {
            this.name = "unnamed";
            return;
        }

        if (nameMap.containsKey(name)) {
            this.name = null;
            return;
        }

        this.name = name;
        nameMap.put(name, this);
    }

    public String getName() {
        return name;
    }

    public User getUser() {
        return user;
    }

    public String getOutput() {
        return "undefined device output";
    }

    public void setController(User controller) {
        if (this.user != null)
            this.user.setDevice(null);
        if (controller != null)
            controller.setDevice(this);
        this.user = controller;
    }

    public void addObserver(User observer) {
        this.observers.add(observer);
        observer.setObservedDevice(this);
    }

    public void removeObserver(User observer) {
        Iterator<User> iterator = this.observers.iterator();
        while(iterator.hasNext())
        {
            User currentObserver = iterator.next();
            if (currentObserver.getId() == observer.getId())
            {
                iterator.remove();
                currentObserver.getResponseObserver().onCompleted();
                currentObserver.setObservedDevice(null);
                break;
            }
        }
    }

    public ArrayList<User> getObservers() {
        return observers;
    }

    public abstract String getInstruction();
    public abstract void parseAction(String actionParams) throws IncorrectActionParams;
}

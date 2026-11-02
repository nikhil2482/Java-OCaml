package org.cis1200;

import java.util.*;

public class Channel {

    // Create a string for owner
    private String owner;

    // Create a string for the Channel name
    private final String nameChannel;

    // Create a boolean to check if its invite only
    private final boolean peopleVar;

    // Create a string Set for the users in the channel
    private final Set<String> peopleChannel;


    // Create a constructor for the class channel
    public Channel(String owner, String nameChannel, boolean peopleVar) {
        this.owner = owner;
        this.nameChannel = nameChannel;
        this.peopleVar = peopleVar;
        peopleChannel = new TreeSet<>();
        peopleChannel.add(owner);

    }

    // Create a method to get users in the channel
    public Set<String> getUsers() {
        return peopleChannel;
    }

    // Create a method to get owners in the channel
    public String getOwner() {
        return this.owner;
    }

    // Create a method to add users in the channel
    public void addUser(String user) {
        peopleChannel.add(user);
    }

    // Create a method to remove users in the channel
    public void removeUser(String user) {
        peopleChannel.remove(user);
    }

    // Create a method to set owners in the channel
    public void setOwner(String user) {
        this.owner = user;
    }

    // Create a method to get check if its private in the channel
    public boolean checkPrivate() {
        return peopleVar;
    }

    // Create a method to get usernames in the channel
    public Set<String> getUserName() {
        TreeSet<String> update = new TreeSet<>();
        for (String entered : peopleChannel) {
            update.add(entered);
        }
        return update;
    }

}

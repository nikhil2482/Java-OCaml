package org.cis1200;

import java.util.*;

/*
 * Make sure to write your own tests in ServerModelTest.java.
 * The tests we provide for each task are NOT comprehensive!
 */

/**
 * The {@code ServerModel} is the class responsible for tracking the
 * state of the server, including its current users and the channels
 * they are in.
 * This class is used by subclasses of {@link Command} to:
 * 1. handle commands from clients, and
 * 2. handle commands from {@link ServerBackend} to coordinate
 * client connection/disconnection.
 */
public final class ServerModel {

    /**
     * Constructs a {@code ServerModel}. Make sure to initialize any collections
     * used to model the server state here.
     */
    private TreeMap<Integer, String> people = new TreeMap<>();
    private TreeMap<String, Channel> channel = new TreeMap<>();


    // =========================================================================
    // == Task 2: Basic Server model queries
    // == These functions provide helpful ways to test the state of your model.
    // == You may also use them in later tasks.
    // =========================================================================

    /**
     * Gets the user ID currently associated with the given
     * nickname. The returned ID is -1 if the nickname is not
     * currently in use.
     *
     * @param nickname The nickname for which to get the associated user ID
     * @return The user ID of the user with the argued nickname if
     * such a user exists, otherwise -1
     */
    public int getUserId(String nickname) {
        for (Map.Entry<Integer, String> mapE : people.entrySet()) {
            if (mapE.getValue().equals(nickname)) {
                int userID = mapE.getKey();
                return userID;
            }
        }
        return -1;
    }

    /**
     * Gets the nickname currently associated with the given user
     * ID. The returned nickname is null if the user ID is not
     * currently in use.
     *
     * @param userId The user ID for which to get the associated
     *               nickname
     * @return The nickname of the user with the argued user ID if
     * such a user exists, otherwise null
     */
    public String getNickname(int userId) {
        for (Map.Entry<Integer, String> mapE : people.entrySet()) {
            if (mapE.getKey() == userId) {
                String name = mapE.getValue();
                return name;
            }
        }
        return null;
    }

    /**
     * Gets a collection of the nicknames of all users who are
     * registered with the server. Changes to the returned collection
     * should not affect the server state.
     * <p>
     * This method is provided for testing.
     *
     * @return The collection of registered user nicknames
     */
    public Collection<String> getRegisteredUsers() {
        TreeSet<String> registeredUsers = new TreeSet(people.values());
        for (Map.Entry<Integer, String> mapE : people.entrySet()) {
            registeredUsers.add(mapE.getValue());
        }
        return registeredUsers;
    }

    /**
     * Gets a collection of the names of all the channels that are
     * present on the server. Changes to the returned collection
     * should not affect the server state.
     * <p>
     * This method is provided for testing.
     *
     * @return The collection of channel names
     */
    public Collection<String> getChannels() {
        TreeSet<String> link = new TreeSet<>();
        for (Map.Entry<String, Channel> mapE : channel.entrySet()) {
            link.add(mapE.getKey());
        }
        return link;
    }

    /**
     * Gets a collection of the nicknames of all the users in a given
     * channel. The collection is empty if no channel with the given
     * name exists. Modifications to the returned collection should
     * not affect the server state.
     * <p>
     * This method is provided for testing.
     *
     * @param channelName The channel for which to get member nicknames
     * @return A collection of all user nicknames in the channel
     */
    public Collection<String> getUsersInChannel(String channelName) {
        if (channel.get(channelName) == null) {
            return new TreeSet<String>();
        }
        Set allUser = channel.get(channelName).getUserName();
        return allUser;
    }

    /**
     * Gets the nickname of the owner of the given channel. The result
     * is {@code null} if no channel with the given name exists.
     * <p>
     * This method is provided for testing.
     *
     * @param channelName The channel for which to get the owner nickname
     * @return The nickname of the channel owner if such a channel
     * exists; otherwise, return null
     */
    public String getOwner(String channelName) {
        boolean checker = channel.get(channelName) == null;
        if (checker) {
            return null;
        }
        return channel.get(channelName).getOwner();
    }

    // ===============================================
    // == Task 3: Connections and Setting Nicknames ==
    // ===============================================

    /**
     * This method is automatically called by the backend when a new client
     * connects to the server. It should generate a default nickname with
     * {@link #generateUniqueNickname()}, store the new user's ID and username
     * in your data structures for {@link ServerModel} state, and construct
     * and return a {@link Broadcast} object using
     * {@link Broadcast#connected(String)}}.
     *
     * @param userId The new user's unique ID (automatically created by the
     *               backend)
     * @return The {@link Broadcast} object generated by calling
     * {@link Broadcast#connected(String)} with the proper parameter
     */
    public Broadcast registerUser(int userId) {
        String nickname = generateUniqueNickname();
        // We have taken care of generating the nickname and returning
        // the Broadcast for you. You need to modify this method to
        // store the new user's ID and username in this model's internal state.
        people.put(userId, nickname);
        return Broadcast.connected(nickname);
    }

    /**
     * Helper for {@link #registerUser(int)}. (Nothing to do here.)
     * <p>
     * Generates a unique nickname of the form "UserX", where X is the
     * smallest non-negative integer that yields a unique nickname for a user.
     *
     * @return The generated nickname
     */
    private String generateUniqueNickname() {
        int suffix = 0;
        String nickname;
        Collection<String> existingUsers = getRegisteredUsers();
        do {
            nickname = "User" + suffix++;
        } while (existingUsers.contains(nickname));
        return nickname;
    }

    /**
     * This method is automatically called by the backend when a client
     * disconnects from the server. This method should take the following
     * actions, not necessarily in this order:
     * <p>
     * (1) All users who shared a channel with the disconnected user should be
     * notified that they left
     * (2) All channels owned by the disconnected user should be deleted
     * (3) The disconnected user's information should be removed from
     * {@link ServerModel}'s internal state
     * (4) Construct and return a {@link Broadcast} object using
     * {@link Broadcast#disconnected(String, Collection)}.
     *
     * @param userId The unique ID of the user to deregister
     * @return The {@link Broadcast} object generated by calling
     * {@link Broadcast#disconnected(String, Collection)} with the proper
     * parameters
     */

    public Broadcast deregisterUser(int userId) {

        String name = getNickname(userId);
        String empty = "";
        Collection<String> now = getUsersInChannel(empty);
        Set<String> all = new TreeSet<>();

        for (Map.Entry<String, Channel> mapE : channel.entrySet()) {
            if (mapE.getValue().getOwner().equals(name)) {
                all.add(mapE.getKey());
            }
            if (mapE.getValue().getUsers().contains(name)) {
                mapE.getValue().getUsers().remove(name);
                Set<String> whileRunning = mapE.getValue().getUsers();
                for (String a : whileRunning) {
                    now.add(a);
                }
            }
        }
        channel.keySet().removeAll(all);
        now.remove(name);
        people.remove(userId);
        return Broadcast.disconnected(name, now);

    }

    /**
     * This method is called when a user wants to change their nickname.
     *
     * @param nickCommand The {@link NicknameCommand} object containing
     *                    all information needed to attempt a nickname change
     * @return The {@link Broadcast} object generated by
     * {@link Broadcast#okay(Command, Collection)} if the nickname
     * change is successful. The command should be the original nickCommand
     * and the collection of recipients should be any clients who
     * share at least one channel with the sender, including the sender.
     * <p>
     * If an error occurs, use
     * {@link Broadcast#error(Command, ServerResponse)} with either:
     * (1) {@link ServerResponse#INVALID_NAME} if the proposed nickname
     * is not valid according to
     * {@link ServerModel#isValidName(String)}
     * (2) {@link ServerResponse#NAME_ALREADY_IN_USE} if there is
     * already a user with the proposed nickname
     */
    public Broadcast changeNickname(NicknameCommand nickCommand) {
        if (!(isValidName(nickCommand.getNewNickname()))) {
            return Broadcast.error(nickCommand, ServerResponse.INVALID_NAME);
        }
        if (people.containsValue(nickCommand.getNewNickname())) {
            return Broadcast.error(nickCommand, ServerResponse.NAME_ALREADY_IN_USE);
        }
        Map<Integer, String> switchNew = new TreeMap<Integer, String>();
        String userName = people.get(nickCommand.getSenderId());
        people.put(nickCommand.getSenderId(), nickCommand.getNewNickname());

        for (Channel a : channel.values()) {
            if (a.getUsers().contains(userName)) {
                a.removeUser(userName);
                a.addUser(nickCommand.getNewNickname());
                for (String b : a.getUsers()) {
                    switchNew.put(getUserId(b), b);
                }
            }
            if (a.getOwner().equals(userName)) {
                a.setOwner(nickCommand.getNewNickname());
            }
        }
        return Broadcast.okay(nickCommand, switchNew.values());
    }

    /**
     * Determines if a given nickname is valid or invalid (contains at least
     * one alphanumeric character, and no non-alphanumeric characters).
     * (Nothing to do here.)
     *
     * @param name The channel or nickname string to validate
     * @return true if the string is a valid name
     */
    public static boolean isValidName(String name) {
        if (name == null || name.isEmpty()) {
            return false;
        }
        for (char c : name.toCharArray()) {
            if (!Character.isLetterOrDigit(c)) {
                return false;
            }
        }
        return true;
    }

    // ===================================
    // == Task 4: Channels and Messages ==
    // ===================================

    /**
     * This method is called when a user wants to create a channel.
     * You can ignore the privacy aspect of this method for task 4, but
     * make sure you come back and implement it in task 5.
     *
     * @param createCommand The {@link CreateCommand} object containing all
     *                      information needed to attempt channel creation
     * @return The {@link Broadcast} object generated by
     * {@link Broadcast#okay(Command, Collection)} if the channel
     * creation is successful. The only recipient should be the new
     * channel's owner.
     * <p>
     * If an error occurs, use
     * {@link Broadcast#error(Command, ServerResponse)} with either:
     * (1) {@link ServerResponse#INVALID_NAME} if the proposed
     * channel name is not valid according to
     * {@link ServerModel#isValidName(String)}
     * (2) {@link ServerResponse#CHANNEL_ALREADY_EXISTS} if there is
     * already a channel with the proposed name
     */
    public Broadcast createChannel(CreateCommand createCommand) {
        String nameChannel = createCommand.getChannel();
        if (!(isValidName(nameChannel))) {
            return Broadcast.error(createCommand, ServerResponse.INVALID_NAME);
        }
        if (channel.containsKey(nameChannel)) {
            return Broadcast.error(createCommand, ServerResponse.CHANNEL_ALREADY_EXISTS);
        }

        Channel newest = new Channel(
                createCommand.getSender(), nameChannel, createCommand.isInviteOnly()
        );

        channel.put(nameChannel, newest);
        return Broadcast.okay(createCommand, newest.getUserName());
    }

    /**
     * This method is called when a user wants to join a channel.
     * You can ignore the privacy aspect of this method for task 4, but
     * make sure you come back and implement it in task 5.
     *
     * @param joinCommand The {@link JoinCommand} object containing all
     *                    information needed for the user's join attempt
     * @return The {@link Broadcast} object generated by
     * {@link Broadcast#names(Command, Collection, String)} if the user
     * joins the channel successfully. The recipients should be all
     * people in the joined channel (including the sender).
     * <p>
     * If an error occurs, use
     * {@link Broadcast#error(Command, ServerResponse)} with either:
     * (1) {@link ServerResponse#NO_SUCH_CHANNEL} if there is no
     * channel with the specified name
     * (2) (after Task 5) {@link ServerResponse#JOIN_PRIVATE_CHANNEL} if
     * the sender is attempting to join a private channel
     */
    public Broadcast joinChannel(JoinCommand joinCommand) {
        String empty = "";
        Collection<String> change = new TreeSet<>();
        if (!(channel.containsKey(joinCommand.getChannel()))) {
            return Broadcast.error(joinCommand, ServerResponse.NO_SUCH_CHANNEL);
        }
        for (Map.Entry<String, Channel> mapE : channel.entrySet()) {
            if (mapE.getKey().equals(joinCommand.getChannel())) {
                empty = mapE.getValue().getOwner();
                change = mapE.getValue().getUsers();
                change.add(joinCommand.getSender());
            }
        }
        return Broadcast.names(joinCommand, change, empty);
    }

    /**
     * This method is called when a user wants to send a message to a channel.
     *
     * @param messageCommand The {@link MessageCommand} object containing all
     *                       information needed for the messaging attempt
     * @return The {@link Broadcast} object generated by
     * {@link Broadcast#okay(Command, Collection)} if the message
     * attempt is successful. The recipients should be all clients
     * in the channel.
     * <p>
     * If an error occurs, use
     * {@link Broadcast#error(Command, ServerResponse)} with either:
     * (1) {@link ServerResponse#NO_SUCH_CHANNEL} if there is no
     * channel with the specified name
     * (2) {@link ServerResponse#USER_NOT_IN_CHANNEL} if the sender is
     * not in the channel they are trying to send the message to
     */
    public Broadcast sendMessage(MessageCommand messageCommand) {
        String method = messageCommand.getChannel();
        String sending = messageCommand.getSender();

        if (!channel.containsKey(method)) {
            return Broadcast.error(messageCommand, ServerResponse.NO_SUCH_CHANNEL);
        }
        if (!channel.get(method).getUserName().contains(sending)) {
            return Broadcast.error(messageCommand, ServerResponse.USER_NOT_IN_CHANNEL);
        }
        return Broadcast.okay(messageCommand, channel.get(method).getUserName());
    }

    /**
     * This method is called when a user wants to leave a channel.
     *
     * @param leaveCommand The {@link LeaveCommand} object containing all
     *                     information about the user's leave attempt
     * @return The {@link Broadcast} object generated by
     * {@link Broadcast#okay(Command, Collection)} if the user leaves
     * the channel successfully. The recipients should be all clients
     * who were in the channel, including the user who left.
     * <p>
     * If an error occurs, use
     * {@link Broadcast#error(Command, ServerResponse)} with either:
     * (1) {@link ServerResponse#NO_SUCH_CHANNEL} if there is no
     * channel with the specified name
     * (2) {@link ServerResponse#USER_NOT_IN_CHANNEL} if the sender is
     * not in the channel they are trying to leave
     */
    public Broadcast leaveChannel(LeaveCommand leaveCommand) {
        if (!(channel.containsKey(leaveCommand.getChannel()))) {
            return Broadcast.error(leaveCommand, ServerResponse.NO_SUCH_CHANNEL);
        }
        if (!(channel.get(leaveCommand.getChannel()).getUsers()
                .contains(leaveCommand.getSender()))) {
            return Broadcast.error(leaveCommand, ServerResponse.USER_NOT_IN_CHANNEL);
        }

        Collection<String> update = new TreeSet<>(
                channel.get(leaveCommand.getChannel()).getUsers()
        );
        if ((channel.get(leaveCommand.getChannel()).getOwner().equals(leaveCommand.getSender()))) {
            channel.get(leaveCommand.getChannel()).removeUser(leaveCommand.getSender());
            channel.remove(leaveCommand.getChannel());
        } else {
            channel.get(leaveCommand.getChannel()).removeUser(leaveCommand.getSender());
        }
        return Broadcast.okay(leaveCommand, update);
    }

    // =============================
    // == Task 5: Channel Privacy ==
    // =============================

    // Go back to createChannel and joinChannel and add
    // all privacy-related functionalities, then delete this when you're done.

    /**
     * This method is called when a channel's owner adds a user to that channel.
     *
     * @param inviteCommand The {@link InviteCommand} object containing all
     *                      information needed for the invite attempt
     * @return The {@link Broadcast} object generated by
     * {@link Broadcast#names(Command, Collection, String)} if the user
     * joins the channel successfully as a result of the invite.
     * The recipients should be all people in the joined channel
     * (including the new user).
     * <p>
     * If an error occurs, use
     * {@link Broadcast#error(Command, ServerResponse)} with either:
     * (1) {@link ServerResponse#NO_SUCH_USER} if the invited user
     * does not exist
     * (2) {@link ServerResponse#NO_SUCH_CHANNEL} if there is no channel
     * with the specified name
     * (3) {@link ServerResponse#INVITE_TO_PUBLIC_CHANNEL} if the
     * invite refers to a public channel
     * (4) {@link ServerResponse#USER_NOT_OWNER} if the sender is not
     * the owner of the channel
     */
    public Broadcast inviteUser(InviteCommand inviteCommand) {
        String com = inviteCommand.getChannel();
        if (!channel.containsKey(com)) {
            return Broadcast.error(inviteCommand, ServerResponse.NO_SUCH_CHANNEL);
        }
        if (!channel.get(com).checkPrivate()) {
            return Broadcast.error(inviteCommand, ServerResponse.INVITE_TO_PUBLIC_CHANNEL);
        }
        if (!people.containsValue(inviteCommand.getUserToInvite())) {
            return Broadcast.error(inviteCommand, ServerResponse.NO_SUCH_USER);
        }
        if (!(channel.get(com).getOwner().equals(inviteCommand.getSender()))) {
            return Broadcast.error(inviteCommand, ServerResponse.USER_NOT_OWNER);
        }

        channel.get(com).addUser(inviteCommand.getUserToInvite());
        return Broadcast
                .names(inviteCommand, channel.get(com).getUserName(), inviteCommand.getSender());
    }

    /**
     * This method is called when a channel's owner removes a user from
     * that channel.
     *
     * @param kickCommand The {@link KickCommand} object containing all
     *                    information needed for the kick attempt
     * @return The {@link Broadcast} object generated by
     * {@link Broadcast#okay(Command, Collection)} if the user is
     * successfully kicked from the channel. The recipients should be
     * all clients who were in the channel, including the user
     * who was kicked.
     * <p>
     * If an error occurs, use
     * {@link Broadcast#error(Command, ServerResponse)} with either:
     * (1) {@link ServerResponse#NO_SUCH_USER} if the user being kicked
     * does not exist
     * (2) {@link ServerResponse#NO_SUCH_CHANNEL} if there is no channel
     * with the specified name
     * (3) {@link ServerResponse#USER_NOT_IN_CHANNEL} if the
     * user being kicked is not a member of the channel
     * (4) {@link ServerResponse#USER_NOT_OWNER} if the sender is not
     * the owner of the channel
     */
    public Broadcast kickUser(KickCommand kickCommand) {
        if (!(channel.containsKey(kickCommand.getChannel()))) {
            return Broadcast.error(kickCommand, ServerResponse.NO_SUCH_CHANNEL);
        }
        if (!(people.containsValue(kickCommand.getUserToKick()))) {
            return Broadcast.error(kickCommand, ServerResponse.NO_SUCH_USER);
        }
        if (!(channel.get(kickCommand.getChannel()).getOwner().equals(kickCommand.getSender()))) {
            return Broadcast.error(kickCommand, ServerResponse.USER_NOT_OWNER);
        }
        if (!(channel.get(kickCommand.getChannel()).getUsers().contains(
                kickCommand.getUserToKick()))) {
            return Broadcast.error(kickCommand, ServerResponse.USER_NOT_IN_CHANNEL);
        }

        Collection<String> update = new TreeSet<>(channel.get(kickCommand.getChannel()).getUsers());
        channel.get(kickCommand.getChannel()).getUsers().remove(kickCommand.getUserToKick());
        return Broadcast.okay(kickCommand, update);
    }

}

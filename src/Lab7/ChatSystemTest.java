package src.Lab7;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.TreeSet;

class NoSuchRoomException extends Exception{
    public NoSuchRoomException(String roomName) {
        super(roomName);
    }
}

class NoSuchUserException extends Exception{
    public NoSuchUserException(String userName) {
        super(userName);
    }
}

class ChatRoom{

    private String name;
    private TreeSet<String> users;

    public ChatRoom(String name) {
        this.name = name;
        this.users = new TreeSet<>();
    }

    public void addUser(String username){
        this.users.add(username);
    }

    public void removeUser(String username){
        this.users.removeIf(username::contains);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        if (numUsers()==0) {
            stringBuilder.append(this.name).append('\n');
            stringBuilder.append("EMPTY").append('\n');
        }
        else {
            stringBuilder.append(this.name).append('\n');
            this.users.forEach(s -> stringBuilder.append(s).append('\n'));
        }
        return stringBuilder.toString();
    }

    public boolean hasUser(String username){
        return this.users.contains(username);
    }

    public int numUsers(){
        return this.users.size();
    }

}

class ChatSystem{
    private TreeMap<String,ChatRoom> chatRooms;
    private TreeSet<String> users;

    public ChatSystem() {
        this.chatRooms = new TreeMap<>();
        this.users = new TreeSet<>();
    }

    public void addRoom(String roomName){
        this.chatRooms.put(roomName, new ChatRoom(roomName));
    }

    public void removeRoom(String roomName){
        this.chatRooms.remove(roomName);
    }

    public ChatRoom getRoom(String roomName) throws NoSuchRoomException {
        if ( !chatRooms.containsKey(roomName)) throw new NoSuchRoomException(roomName);
        return chatRooms.get(roomName);
    }
    public String getUser(String user) throws NoSuchUserException {
        if ( !users.contains(user)) throw new NoSuchUserException(user);
        return user;
    }

    public void register(String userName){
        this.users.add(userName);
        LinkedList<ChatRoom> minRoom = new LinkedList<ChatRoom>();
        int min = Integer.MAX_VALUE;
        for(ChatRoom cr : this.chatRooms.values()){
            if(cr.numUsers() < min){
                minRoom = new LinkedList<ChatRoom>();
                min = cr.numUsers();
            }
            if ( cr.numUsers() == min )minRoom.add(cr);
        }
        if(minRoom.isEmpty())return;
        minRoom.getFirst().addUser(userName);
    }

    public void registerAndJoin(String userName, String roomName) throws NoSuchRoomException, NoSuchUserException {
        users.add(userName);
        joinRoom(userName,roomName);
    }

    public void joinRoom(String userName, String roomName) throws NoSuchRoomException, NoSuchUserException {
        getRoom(roomName).addUser(getUser(userName));
    }

    public void leaveRoom(String userName, String roomName) throws NoSuchRoomException, NoSuchUserException {
        getRoom(roomName).removeUser(getUser(userName));
    }

    public void followFriend(String username, String friendUsername) throws NoSuchUserException, NoSuchRoomException {
        for (Map.Entry<String,ChatRoom> cr : chatRooms.entrySet()){
            if(cr.getValue().hasUser(getUser(friendUsername)))
                joinRoom(getUser(username),cr.getKey());
        }
    }
}

public class ChatSystemTest {

    public static void main(String[] args) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException, NoSuchRoomException {
        Scanner jin = new Scanner(System.in);
        int k = jin.nextInt();
        if ( k == 0 ) {
            ChatRoom cr = new ChatRoom(jin.next());
            int n = jin.nextInt();
            for ( int i = 0 ; i < n ; ++i ) {
                k = jin.nextInt();
                if ( k == 0 ) cr.addUser(jin.next());
                if ( k == 1 ) cr.removeUser(jin.next());
                if ( k == 2 ) System.out.println(cr.hasUser(jin.next()));
            }
            System.out.println("");
            System.out.println(cr.toString());
            n = jin.nextInt();
            if ( n == 0 ) return;
            ChatRoom cr2 = new ChatRoom(jin.next());
            for ( int i = 0 ; i < n ; ++i ) {
                k = jin.nextInt();
                if ( k == 0 ) cr2.addUser(jin.next());
                if ( k == 1 ) cr2.removeUser(jin.next());
                if ( k == 2 ) cr2.hasUser(jin.next());
            }
            System.out.println(cr2.toString());
        }
        if ( k == 1 ) {
            ChatSystem cs = new ChatSystem();
            Method mts[] = cs.getClass().getMethods();
            while ( true ) {
                String cmd = jin.next();
                if ( cmd.equals("stop") ) break;
                if ( cmd.equals("print") ) {
                    System.out.println(cs.getRoom(jin.next())+"\n");continue;
                }
                for ( Method m : mts ) {
                    if ( m.getName().equals(cmd) ) {
                        String params[] = new String[m.getParameterTypes().length];
                        for ( int i = 0 ; i < params.length ; ++i ) params[i] = jin.next();
                        m.invoke(cs,params);
                    }
                }
            }
        }
    }

}



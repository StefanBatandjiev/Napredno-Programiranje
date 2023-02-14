package src.Lab6;

import java.util.*;
import java.util.stream.Collectors;

class SuperString {
    private LinkedList<String> list;
    private LinkedList<Integer> undoList;

    public SuperString() {
        this.list = new LinkedList<String>();
        this.undoList = new LinkedList<Integer>();
    }


    public void append(String s) {
        list.addLast(s);
        undoList.addFirst(1);
    }

    public void insert(String s) {
        list.addFirst(s);
        undoList.addFirst(-1);
    }

    public boolean contains(String s) {
        return toString().contains(s);
    }

    public void reverse() {
        for (ListIterator<String> it = list.listIterator(); it.hasNext(); )
            it.set(new StringBuilder(it.next()).reverse().toString());
        Collections.reverse(list);
        for (ListIterator<Integer> it = undoList.listIterator(); it.hasNext(); )
            it.set(it.next() * (-1));

    }

    public void removeLast(int k) {
        for (int i = 0; i < k; i++) {
            if (undoList.removeFirst() < 0) list.removeFirst();
            else list.removeLast();
        }
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (String s : list) {
            stringBuilder.append(s);
        }
        return stringBuilder.toString();
    }
}


public class SuperStringTest {

    public static void main(String[] args) {
        Scanner jin = new Scanner(System.in);
        int k = jin.nextInt();
        if (k == 0) {
            SuperString s = new SuperString();
            while (true) {
                int command = jin.nextInt();
                if (command == 0) {//append(String s)
                    s.append(jin.next());
                }
                if (command == 1) {//insert(String s)
                    s.insert(jin.next());
                }
                if (command == 2) {//contains(String s)
                    System.out.println(s.contains(jin.next()));
                }
                if (command == 3) {//reverse()
                    s.reverse();
                }
                if (command == 4) {//toString()
                    System.out.println(s);
                }
                if (command == 5) {//removeLast(int k)
                    s.removeLast(jin.nextInt());
                }
                if (command == 6) {//end
                    break;
                }
            }
        }
    }

}

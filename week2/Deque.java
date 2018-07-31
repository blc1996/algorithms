import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private int front;
    private int back;
    private int numOfItems;
    private Item[] items;


    public Deque(){
        items = (Item[]) new Object[10];
        front = 5;
        back  = 5;
        numOfItems = 0;
    }                           // construct an empty deque


    public boolean isEmpty(){
        return front == back;
    }                 // is the deque empty?


    public int size(){
        return back - front;
    }                        // return the number of items on the deque


    public void addFirst(Item item){
        if(item == null)
            throw new IllegalArgumentException("can't add null to it");
        if(front > 1){
            items[--front] = item;
        }else{
            items[0] = item;
            Item[] temp = (Item[]) new Object[2*items.length];
            for(int i = items.length / 2; i < back + items.length / 2; i++){
                temp[i] = items[i - items.length / 2];
            }
            front = items.length / 2;
            back = back + items.length / 2;
            items = temp;
        }
    }          // add the item to the front


    public void addLast(Item item){
        if(item == null)
            throw new IllegalArgumentException("can't add null to it");
        if(back < items.length - 1){
            items[back++] = item;
        }else{
            items[back++] = item;
            Item[] temp = (Item[]) new Object[2*items.length];
            for(int i = front + items.length / 2; i < back + items.length / 2; i++){
                temp[i] = items[i - items.length / 2];
            }
            front = front + items.length / 2;
            back = back + items.length / 2;
            items = temp;
        }
    }           // add the item to the end


    public Item removeFirst(){
        if(isEmpty()){
            throw new NoSuchElementException("can't remove when is empty");
        }else{
            return items[front++];
        }
    }                // remove and return the item from the front


    public Item removeLast(){
        if(isEmpty()){
            throw new NoSuchElementException("can't remove when is empty");
        }else{
            return items[--back];
        }
    }                 // remove and return the item from the end


    public Iterator<Item> iterator(){
        return new DequeIterator();
    }         // return an iterator over items in order from front to end

    private class DequeIterator implements Iterator<Item>{
        private int current = front;
        private int end = back;
        // private Item[] iterItems = items;

        public boolean hasNext(){
            return current != end;
        }

        public void remove(){
            throw new UnsupportedOperationException("remove is not supported");
        }

        public Item next(){
            if(current == end){
                throw new NoSuchElementException("no next element");
            }else{
                return items[current++];
            }
        }
    }


    public static void main(String[] args){
        Deque<Integer> test = new Deque<Integer>();
        test.addLast(123);
        test.addLast(22);
        test.addLast(33);
        test.addLast(44);
        test.addFirst(55);
        test.addFirst(66);
        test.addFirst(77);
        test.addFirst(88);
        test.addLast(99);
        for (Iterator iter = test.iterator(); iter.hasNext();) {
            Integer str = (Integer)iter.next();
            System.out.println(str);
        }
    }   // unit testing (optional)
}

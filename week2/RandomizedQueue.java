import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] items;
    private int numOfItems;

    public RandomizedQueue(){
        items = (Item[]) new Object[100];
        numOfItems = 0;
    }                 // construct an empty randomized queue

    private RandomizedQueue(Item[] a, int b){
        items = (Item[]) new Object[a.length];
        for(int i = 0; i < b; i++){
            items[i] = a[i];
        }
        numOfItems = b;
    }


    public boolean isEmpty(){
        return numOfItems == 0;
    }                 // is the randomized queue empty?


    public int size(){
        return numOfItems;
    }                        // return the number of items on the randomized queue


    public void enqueue(Item item){
        if(item == null)
            throw new IllegalArgumentException("null is not acceptable");
        if(numOfItems < items.length){
            items[numOfItems++] = item;
        }else{
            Item[] temp = (Item[]) new Object[2*items.length];
            for(int i = 0; i < items.length; i++){
                temp[i] = items[i];
            }
            items = temp;
            items[numOfItems++] = item;
        }
    }           // add the item


    public Item dequeue(){
        if(numOfItems == 0)
            throw new NoSuchElementException("empty");
        int index = StdRandom.uniform(numOfItems);
        Item output = items[index];
        items[index] = items[--numOfItems];
        return output;
    }                    // remove and return a random item


    public Item sample(){
        if(numOfItems == 0)
            throw new NoSuchElementException("empty");
        int index = StdRandom.uniform(numOfItems);
        return items[index];
    }                     // return a random item (but do not remove it)

    private Item seque(int num){
        return items[num];
    }


    public Iterator<Item> iterator(){
        return new RdmQueIterator();
    }         // return an independent iterator over items in random order

    private class RdmQueIterator implements Iterator<Item>{
        private int current = 0;
        private int size = numOfItems;
        private RandomizedQueue<Item> temp = new RandomizedQueue<Item>(items, numOfItems);

        public boolean hasNext(){
            return current < size;
        }

        public void remove(){
            throw new UnsupportedOperationException("remove is not supported");
        }

        public Item next(){

            if(current < size){
                current++;
                return temp.dequeue();
            }else{
                throw new NoSuchElementException("no next element");
            }
        }
    }


    public static void main(String[] args){
        RandomizedQueue<Integer> test = new RandomizedQueue<Integer>();
        test.enqueue(1);
        test.enqueue(2);
        test.enqueue(3);
        test.enqueue(4);
        test.enqueue(5);
        test.enqueue(6);
        test.enqueue(7);
        test.enqueue(8);
        test.enqueue(9);
        for (Iterator iter = test.iterator(); iter.hasNext();) {
            Integer str = (Integer)iter.next();
            System.out.println(str);
        }

        while(test.size() > 0){
            System.out.println(test.dequeue());
        }
        // System.out.println(test.seque(0));
        // System.out.println(test.seque(1));
        // System.out.println(test.seque(2));
        // System.out.println(test.seque(3));
        //      System.out.println(test.numOfItems);
    }   // unit testing (optional)
}

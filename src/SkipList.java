import javax.swing.plaf.synth.SynthUI;

public class SkipList implements Dictionary {

    private Node head;
    private Node tail;

    private int height;

    private int listSize;

    public int getListSize() {
        return listSize;
    }

    public void setListSize(int listSize) {
        this.listSize = listSize;
    }

    public Node getHead() {
        return head;
    }

    public void setHead(Node head) {
        this.head = head;
    }

    public Node getTail() {
        return tail;
    }

    @Override
    public int closestKeyBefore(int key) {
        return 0;
    }

    public void setTail(Node tail) {
        this.tail = tail;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public SkipList(){

        //creating infinity nodes
        Node h = new Node(Node.negativeInfinity);
        Node t = new Node(Node.positiveInfinity);

        //pointing head and tail to infinity nodes
        head = h;
        tail = t;

        //pointing negative infinity to positive infinity when list is first built
        h.setNext(t);
        t.setPrevious(h);

        //start with height 0. We will update as needed.
        setHeight(0);
        setListSize(0);
    }


    @Override
    public void insertElement(int key, boolean printFlag) {


        double random;
        int h;

        Node insert;

        Node element = findElement(key, false);

        //Checking if key is already present in list, if it is, we don't insert it again
        if(element.getKey() == key){
            if(printFlag)
                System.out.println("Key already exists in list");
        }
        else{

            //1. create new Node with value to insert
            insert = new Node(key);
            //2. point the previous value of new element to next lowest value
            insert.setPrevious(element);
            //3. point new value next to previous value next
            insert.setNext(element.getNext());
            //4. point previously next to lowest value to new insert element
            element.getNext().setPrevious(insert);
            //5. point value before next to newly inserted value
            element.setNext(insert);

            //we are at lower level, we will start adding levels based on random flip

            h = 0;

            setListSize(getListSize() + 1);

            if(printFlag)
                System.out.println("Succesfully inserted key " + key + " in level " + h);

            random = Math.random();

            while(random < 0.5){

                //if current insert level is higher than overall level, make new level
                if(h >= getHeight()){

                    setHeight(getHeight() + 1);

                    Node newHead = new Node(Node.negativeInfinity);
                    Node newTail = new Node(Node.positiveInfinity);

                    newHead.setNext(newTail);
                    newHead.setBelow(head);

                    newTail.setPrevious(newHead);
                    newTail.setBelow(tail);

                    head.setAbove(newHead);
                    tail.setAbove(newTail);

                    head = newHead;
                    tail = newTail;

                }

                while(element.getAbove() == null){
                    element = element.getPrevious();
                }

                element = element.getAbove();

                Node insertAbove = new Node(key);

                insertAbove.setPrevious(element);
                insertAbove.setNext(element.getNext());
                insertAbove.setBelow(insert);

                insert.setAbove(insertAbove);

                element.getNext().setPrevious(insertAbove);
                element.setNext(insertAbove);

                insert = insertAbove;

                h++;

                random = Math.random();

                if(printFlag)
                    System.out.println("Succesfully inserted key " + key + " in level " + h);



            }

        }
    }

    @Override
    public void removeElement(int key) {

        Node remove = findElement(key, false);

        if(remove.getKey() != key){
            System.out.println("Element does not exist in list.");
        }
        else{
            Node auxRemove = remove;

            auxRemove.getPrevious().setNext(auxRemove.getNext());
            auxRemove.getNext().setPrevious(auxRemove.getPrevious());

            setListSize(getListSize() - 1);

            do {

                remove = remove.getAbove();

                //Following part of the code is to fix correct height of list after removing an element that is the only element in a given level.
                if(remove.getPrevious().getKey() == Node.negativeInfinity && remove.getNext().getKey() == Node.positiveInfinity){
                    Node newHead = moveLeft(auxRemove);
                    Node newTail = moveRight(auxRemove);

                    head = newHead;
                    tail = newTail;

                    int floorsToBeRemoved = 1;
                    Node r = remove;

                    while(r.getAbove() != null){
                        r = r.getAbove();
                        floorsToBeRemoved++;
                    }

                    setHeight(getHeight() - floorsToBeRemoved);

                    break;
                }

                auxRemove = remove;
                auxRemove.getPrevious().setNext(auxRemove.getNext());
                auxRemove.getNext().setPrevious(auxRemove.getPrevious());


            } while (remove.getAbove() != null);



        }

    }

    private Node moveLeft(Node x){

        while(x.getKey() != Node.negativeInfinity){
            x = x.getPrevious();
        }

        return x;

    }

    private Node moveRight(Node x){

        while(x.getKey() != Node.positiveInfinity){
            x = x.getNext();
        }

        return x;

    }

    @Override
    public Node findElement(int key, boolean printFlag) {

        Node element = head;
        boolean found = false;

        long startTime = System.nanoTime();

        while(!found){

            //move right while value is less or equal  than desired key
            while(element.getNext() != tail && element.getNext().getKey() <= key){
                element = element.getNext();
            }

            //after we kept going right and stopped because of an element less, we move down one level
            if(element.getBelow() != null){
                element = element.getBelow();
            }
            //if we are at bottom level, we break, since we have found element or closest one
            else
                found = true;

        }

        long endTime = System.nanoTime();

        long total = endTime - startTime;


        if(printFlag) {
            if (element.getKey() == key)
                System.out.println("Found element " + key + ". Time taken: " + total + " nanoseconds");
            else
                System.out.println("Element not found. Time taken: " + total + " nanoseconds");
        }

        return element;

    }

    public void printList(){

        Node h = head;
        Node h2 = h;
        int height1 = getHeight();

        printLevel(h2, height1--);

        while (h.getBelow() != null) {

            h = h.getBelow();
            h2 = h;
            printLevel(h2, height1--);

        }

    }

    private void printLevel(Node h2, int height){
        System.out.print("Level " + height+ ": -oo -> ");
        while (h2.getNext().getKey() != Node.positiveInfinity) {
            h2 = h2.getNext();
            System.out.print(h2.getKey() + " -> ");
        }
        System.out.print("+oo");
        System.out.println("\n");
    }

    @Override
    public void closestKeyAfter(int key) {

        long start = System.nanoTime();
        Node current = findElement(key, false);
        long end = System.nanoTime();
        long total = end - start;

        if (current.getKey() != key){
            System.out.println("Key is not in list");
        }
        else{
            if(current.getNext().getKey() == Node.positiveInfinity)
                System.out.println("Closest key after is +oo ");
            else
                System.out.println("Closest key after " + key + " is " + current.getNext().getKey() +". Time taken: " + total + " nanoseconds");

        }
    }

}

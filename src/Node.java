public class Node {

    //actual value
    private int key;

    //reference to next, above and below, needed for search and insertion
    private Node next;
    private Node above;
    private Node below;
    private Node previous;

    public static int negativeInfinity = Integer.MIN_VALUE;
    public static int positiveInfinity = Integer.MAX_VALUE;

    public Node(int key) {
        this.key = key;
        this.above = null;
        this.next = null;
        this.below = null;
        this.previous = null;
    }

    public Node() {
    }

    public Node getPrevious() {
        return previous;
    }

    public void setPrevious(Node previous) {
        this.previous = previous;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public Node getNext() {
        return next;
    }

    public void setNext(Node next) {
        this.next = next;
    }

    public Node getAbove() {
        return above;
    }

    public void setAbove(Node above) {
        this.above = above;
    }

    public Node getBelow() {
        return below;
    }

    public void setBelow(Node below) {
        this.below = below;
    }

    public void printNode(){

        System.out.println("Node key is: " + this.key + " and next item is: " + this.next + " and below is: " + this.below + " and above is: " + this.above);
    }
}

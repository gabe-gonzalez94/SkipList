public interface Dictionary {

    public long insertElement(int key, boolean printFlag);
    public long removeElement(int key);
    public Node findElement(int key, boolean printFlag);
    public void closestKeyAfter(int key);
    public int closestKeyBefore(int key);

}



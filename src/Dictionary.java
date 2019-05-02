public interface Dictionary {

    public void insertElement(int key, boolean printFlag);
    public void removeElement(int key);
    public Node findElement(int key, boolean printFlag);
    public void closestKeyAfter(int key);
    public int closestKeyBefore(int key);

}



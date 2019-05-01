import java.util.ArrayList;
import java.util.Random;

public class Main {



    public static void main(String[] args){

        int inputSize  = 10240000;

        System.out.println("TEST GIT");

        long start = System.nanoTime();
        SkipList list = new SkipList();
        ArrayList<Integer> itemsToSearch = new ArrayList<>();
        Random random = new Random();


        int size= 0;
        int y = 0;

        while(size != inputSize){

            int randomNumber = random.nextInt(inputSize * 10);
            list.insertElement(randomNumber, false);
            size = list.getListSize();
            if(y == 1000000) {
                itemsToSearch.add(randomNumber);
                y = 0;
            }
            y++;

        }

        long end = System.nanoTime();
        long total = end - start;
        System.out.println("Time taken for insert: " + total);

        System.out.println(list.getListSize());

        for(Integer i:itemsToSearch){
            list.findElement(i, true);
        }





//        list.printList();

//        list.insertElement(3 );
//        list.insertElement(10 );
//        list.insertElement(15 );
//        list.insertElement(13);
//        list.insertElement(7);
//        list.insertElement(19);
//        list.insertElement(25);
//        list.insertElement(4);
//        list.insertElement(4);
//        list.insertElement(1);
//        list.insertElement(5);
//        list.insertElement(30);
////        System.out.println(list.getHead().getKey() + " " + list.getHead().getNext().getKey());
//        list.printList();
//        System.out.println("after removal");
//        list.removeElement(10);
//        list.removeElement(1);
//        list.removeElement(2);
//        list.removeElement(3);
//        list.printList();
//        list.insertElement(10);
//        list.printList();
//        int closest = list.closestKeyAfter(5);
//        System.out.println("Closest key after 3 is: " + closest);



    }

}

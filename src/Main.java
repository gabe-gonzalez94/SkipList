import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Main {



    public static void main(String[] args){

        SkipList list = new SkipList();
        Scanner input = new Scanner(System.in);

        boolean exitFlag = false;


        while(!exitFlag) {
            printMenu();

            int choice = input.nextInt();

            switch (choice) {
                case 1:
                    insertMenu(list);
                    break;
                case 2:
                    removeMenu(list);
                    break;
                case 3:
                    if(list.getListSize() > 50)
                        System.out.println("Unable to print. List is too big.");
                    else
                        list.printList();
                    break;
                case 4:
                    closestAfterMenu(list);
                    break;
                case 5:
                    findMenu(list);
                    break;
                case 6:
                    list = new SkipList();
                    generateRandomList(list);
                    break;
                case 7:
                    exitFlag = true;
                    break;
                default:
                    printMenu();
            }
        }




//        int inputSize  = 10240000;
//
//        System.out.println("TEST GIT");
//
//        long start = System.nanoTime();
//        SkipList list = new SkipList();
//        ArrayList<Integer> itemsToSearch = new ArrayList<>();
//        Random random = new Random();
//
//
//        int size= 0;
//        int y = 0;
//
//        while(size != inputSize){
//
//            int randomNumber = random.nextInt(inputSize * 10);
//            list.insertElement(randomNumber, false);+
//            size = list.getListSize();
//            if(y == 1000000) {
//                itemsToSearch.add(randomNumber);
//                y = 0;
//            }
//            y++;
//
//        }
//
//        long end = System.nanoTime();
//        long total = end - start;
//        System.out.println("Time taken for insert: " + total);
//
//        System.out.println(list.getListSize());
//
//        for(Integer i:itemsToSearch){
//            list.findElement(i, true);
//        }
//
    }

    private static void generateRandomList(SkipList list){

        Scanner input = new Scanner(System.in);

        System.out.println("How many items: (Range 1-50: PRINT / Range 50+: NO PRINT)");
        int desiredSize = input.nextInt();


        int size = 0;

        Random random = new Random();

        long start = System.nanoTime();

        while (size != desiredSize) {

            int randomNumber = random.nextInt(desiredSize * 10);
            list.insertElement(randomNumber, false);
            size = list.getListSize();

        }
        long end = System.nanoTime();
        long total = end - start;

        System.out.println("Created list with " + size + " keys. Time taken: " + total + " nanoseconds");

        if(desiredSize <= 50)
            list.printList();


    }

    private static void closestAfterMenu(SkipList list){

        Scanner input = new Scanner(System.in);

        boolean keepSearching = false;
        while(!keepSearching) {

            System.out.print("Please enter number to be searched: ");
            int number = input.nextInt();

            if (number > Integer.MAX_VALUE)
                System.out.println("Value is out of range");
            else {
                list.closestKeyAfter(number);

                System.out.println("Search for another number? [Y] [N] ");
                String another = input.next();
                if(another.equalsIgnoreCase("Y"))
                    continue;
                else
                    keepSearching = true;

            }
        }

    }

    private static void findMenu(SkipList list){

        Scanner input = new Scanner(System.in);

        boolean keepFinding = false;
        while(!keepFinding) {

            System.out.print("Please enter number to be searched: ");
            int number = input.nextInt();

            if (number > Integer.MAX_VALUE)
                System.out.println("Value is out of range");
            else {
                list.findElement(number, true);

                System.out.println("Search for another number? [Y] [N] ");
                String another = input.next();
                if(another.equalsIgnoreCase("Y"))
                    continue;
                else
                    keepFinding = true;

            }
        }

    }
    private static void insertMenu(SkipList list){

        Scanner input = new Scanner(System.in);

        boolean keepInserting = false;
        while(!keepInserting) {

            System.out.print("Please enter number to be inserted: ");
            int number = input.nextInt();

            if (number > Integer.MAX_VALUE)
                System.out.println("Value is out of range");
            else {
                list.insertElement(number, false);
                list.printList();
                System.out.println("Insert another number? [Y] [N] ");
                String another = input.next();
                if(another.equalsIgnoreCase("Y"))
                    continue;
                else
                    keepInserting = true;

            }
        }

    }

    private static void removeMenu(SkipList list){
        {

            Scanner input = new Scanner(System.in);

            boolean keepRemoving = false;

            list.printList();

            while(!keepRemoving) {

                System.out.print("Please enter number to be removed: ");
                int number = input.nextInt();

                list.removeElement(number);
                list.printList();

                System.out.println("Remove another number? [Y] [N] ");
                String another = input.next();
                if(another.equalsIgnoreCase("Y"))
                    continue;
                else
                    keepRemoving = true;



                }
            }

        }

    private static void printMenu(){

        System.out.println("[1] Insert Element");
        System.out.println("[2] Remove Element");
        System.out.println("[3] Print List");
        System.out.println("[4] Closest Key After");
        System.out.println("[5] Find Element");
        System.out.println("[6] Generate Random List");
        System.out.println("[7] Exit");
        System.out.print(">");


    }

}

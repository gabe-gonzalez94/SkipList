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
                    System.out.println("Insert Element");
                    break;
                case 2:
                    System.out.println("Remove Element");
                    break;
                case 3:
                    System.out.println("Print List");
                    break;
                case 4:
                    System.out.println("Closest Key After");
                    break;
                case 5:
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
//            list.insertElement(randomNumber, false);
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

    private static void printMenu(){

        System.out.println("[1] Insert Element");
        System.out.println("[2] Remove Element");
        System.out.println("[3] Print List");
        System.out.println("[4] Closest Key After");
        System.out.println("[5] Exit");
        System.out.print(">");


    }

}

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Main {

    public static List<Integer> itemsToSearch = null;
    public static List<Integer> itemsForFailedSearch = null;
    public static List<Integer> itemsForClosestKeyAfter = null;
    public static List<Long> average = null;

    public static void main(String[] args){


        SkipList list = new SkipList();
        Scanner input = new Scanner(System.in);

        boolean exitFlag = false;

        System.out.println("Please select what mode do you want to use:");
        System.out.println("[1] Input Mode");
        System.out.println("[2] Statistics Mode");

        int mode = input.nextInt();

        if(mode == 1){
            while (!exitFlag) {
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
                        if (list.getListSize() > 50)
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

        }
        else{

            int analysisSize;
            long generateTime;
            long averageSearchTime, averageFailedSearchTime, averageClosestAfterTime;
            long startTime, endTime, total;

            System.out.println("Select size for statistics:");
            System.out.println("[1] 128");
            System.out.println("[2] 512");
            System.out.println("[3] 1024");
            System.out.println("[4] 2048");
            System.out.println("[5] 4096");
            System.out.println("[6] 8192");
            System.out.println("[7] 16384");
            System.out.println("[8] 32768");
            System.out.println("[9] 65536");
            System.out.println("[0] 131072");

            int analysisChoice = input.nextInt();
            switch(analysisChoice){

                case 1:
                    analysisSize = 128;
                    break;
                case 2:
                    analysisSize = 512;
                    break;
                case 3:
                    analysisSize = 1024;
                    break;
                case 4:
                    analysisSize = 2048;
                    break;
                case 5:
                    analysisSize = 4096;
                    break;
                case 6:
                    analysisSize = 8192;
                    break;
                case 7:
                    analysisSize = 16384;
                    break;
                case 8:
                    analysisSize = 32768;
                    break;
                case 9:
                    analysisSize = 65536;
                    break;
                case 0:
                    analysisSize = 131072;
                    break;
                default:
                    analysisSize = 0;
            }

            try {
                generateTime = generateList(list, analysisSize);
                System.out.println("Time taken to generate list: " + generateTime + " nanoseconds.");
            }
            catch(IOException e){
                e.printStackTrace();
            }


                System.out.println("Starting successful search operations...");

                average = new ArrayList<>();


                for(int x: itemsToSearch){
                    startTime = System.nanoTime();
                    list.findElement(x, true);
                    endTime = System.nanoTime();
                    total = endTime - startTime;
                    average.add(total);
                }

                averageSearchTime = findAverage(average);

                System.out.println("Average time taken for 25 searches: " + averageSearchTime + " nanoseconds");

                System.out.println("Starting failed search operations...");

                average = new ArrayList<>();

                for(int x: itemsForFailedSearch){
                    startTime = System.nanoTime();
                    list.findElement(x, true);
                    endTime = System.nanoTime();
                    total = endTime - startTime;
                    average.add(total);
                }

                averageFailedSearchTime = findAverage(average);

                System.out.println("Average time taken for 25 failed searches: " + averageFailedSearchTime + " nanoseconds");




        }

    }

    private static long findAverage(List<Long> average){

        long totalSum = 0;

        for(long time : average){
            totalSum += time;
        }

        return totalSum/average.size();

    }

    private static void generateRandomList(SkipList list){

        Scanner input = new Scanner(System.in);

        System.out.println("How many items: (Range 1-50: PRINT / Range 50+: NO PRINT)");
        int desiredSize = input.nextInt();

        try {
            generateList(list, desiredSize);
        }
        catch(IOException e){
            e.printStackTrace();
        }

        if(desiredSize <= 50)
            list.printList();


    }

    private static long generateList(SkipList list, int desiredSize) throws IOException {

        Random random = new Random();
        itemsToSearch = new ArrayList<>();
        itemsForFailedSearch = new ArrayList<>();
        itemsForClosestKeyAfter = new ArrayList<>();
        long start = System.nanoTime();

        //itemSpread is used to capture 50 items while list is getting created
        int itemSpread = desiredSize / 25;
        int itemSpread2 = desiredSize / 50;
        int size = 0;
        int searchSize = 0;
        int closestSearchSize = 0;

        for(int i=0; i<25; i++){
            itemsForFailedSearch.add(random.nextInt(desiredSize * 2));
        }

        while (size != desiredSize) {

            int randomNumber = random.nextInt(desiredSize * 2);
            if(itemsForFailedSearch.contains(randomNumber))
                continue;
            if(searchSize == itemSpread){
                itemsToSearch.add(randomNumber);
                searchSize = 0;
            }
            if(closestSearchSize == itemSpread2){
                itemsForClosestKeyAfter.add(randomNumber);
                closestSearchSize = 0;
            }
            list.insertElement(randomNumber, false);
            size = list.getListSize();
            searchSize++;
            closestSearchSize++;

        }

//        writer.close();
        long end = System.nanoTime();
        long total = end - start;

        System.out.println("Created list with " + size + " keys.");

        return total;

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

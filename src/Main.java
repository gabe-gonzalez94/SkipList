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
    public static List<Integer> itemsForRemoval = null;
    public static List<Integer> itemsToInsert = null;

    public static List<Long> finalAverageSearchTime = null;
    public static List<Long> finalAverageFailedSearchTime = null;
    public static List<Long> finalAverageClosestKeyAfterTime = null;
    public static List<Long> finalAverageRemovalTime = null;
    public static List<Long> finalAverageInsertTime = null;


    public static void main(String[] args) {

        SkipList list = new SkipList();
        Scanner input = new Scanner(System.in);

        boolean exitFlag = false;

        System.out.println("Please select what mode do you want to use:");
        System.out.println("[1] Input Mode");
        System.out.println("[2] Statistics Mode");

        int mode = input.nextInt();

        if (mode == 1) {
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
                        exitFlag = true;
                        break;
                    default:
                        printMenu();
                }
            }

        } else {

            int analysisSize, iterations;
            long generateTime;
            long averageSearchTime, averageFailedSearchTime, averageClosestAfterTime, averageRemovalTime, averageInsertionTime;
            long startTime, endTime, total;

            System.out.println("Input size for complexity analysis:");
            analysisSize = input.nextInt();
            System.out.println("Input size for number of iterations for accuracy:");
            iterations = input.nextInt();

            finalAverageSearchTime = new ArrayList<>();
            finalAverageInsertTime = new ArrayList<>();
            finalAverageRemovalTime = new ArrayList<>();
            finalAverageClosestKeyAfterTime = new ArrayList<>();
            finalAverageFailedSearchTime = new ArrayList<>();

            for(int i=0; i<iterations; i++) {
                try {

                    list = new SkipList();
                    generateTime = generateList(list, analysisSize);
                    System.out.println("Time taken to generate list: " + generateTime + " nanoseconds.");
                } catch (IOException e) {
                    e.printStackTrace();
                }


                System.out.println("Starting successful search operations...");

                List<Long> average = new ArrayList<>();

                for (int x : itemsToSearch) {
                    startTime = System.nanoTime();
                    list.findElement(x, true);
                    endTime = System.nanoTime();
                    total = endTime - startTime;
                    average.add(total);
                }

                averageSearchTime = findAverage(average);


                finalAverageSearchTime.add(averageSearchTime);

//                System.out.println("Average time taken for 25 searches: " + averageSearchTime + " nanoseconds");

                System.out.println("Starting failed search operations...");

                List<Long> averageFailed = new ArrayList<>();

                for (int x : itemsForFailedSearch) {
                    startTime = System.nanoTime();
                    list.findElement(x, true);
                    endTime = System.nanoTime();
                    total = endTime - startTime;
                    averageFailed.add(total);
                }

                averageFailedSearchTime = findAverage(averageFailed);

                finalAverageFailedSearchTime.add(averageFailedSearchTime);

//                System.out.println("Average time taken for 25 failed searches: " + averageFailedSearchTime + " nanoseconds");

                System.out.println("Starting closest key after operations...");

                List<Long> averageClosest = new ArrayList<>();

                for (int x : itemsForClosestKeyAfter) {
                    long totalTime = list.closestKeyAfter(x);
                    averageClosest.add(totalTime);
                }

                averageClosestAfterTime = findAverage(averageClosest);
                finalAverageClosestKeyAfterTime.add(averageClosestAfterTime);

//                System.out.println("Average time taken for 50 closest key after operations is: " + averageClosestAfterTime + " nanoseconds");

                System.out.println("Starting removal operations...");

                List<Long> averageRemoval = new ArrayList<>();

                for (int x : itemsForRemoval) {
                    long totalRemovalTime = list.removeElement(x);
                    averageRemoval.add(totalRemovalTime);
                    list.insertElement(x, false);
                }

                averageRemovalTime = findAverage(averageRemoval);
                finalAverageRemovalTime.add(averageRemovalTime);

//                System.out.println("Average time taken for 50 removal key operations is: " + averageRemovalTime + " nanoseconds");

                System.out.println("Starting inserting key operations...");

                List<Long> averageInsert = new ArrayList<>();

                for (int x : itemsToInsert) {
                    long insertTime = list.insertElement(x, true);
                    averageInsert.add(insertTime);
                    list.removeElement(x);
                }

                averageInsertionTime = findAverage(averageInsert);
                finalAverageInsertTime.add(averageInsertionTime);
//                System.out.println("Average time taken for 50 insert operations is: " + averageInsertionTime + " nanoseconds");
            }


            System.out.println("\n");
            System.out.println("FINAL RESULTS FOR LIST SIZE " + analysisSize);
            System.out.println("                    | AVERAGE NANOSECONDS");
            System.out.println("-----------------------------------------");
            System.out.println("SEARCH              | "+ findAverage(finalAverageSearchTime));
            System.out.println("-----------------------------------------");
            System.out.println("FAILED SEARCH       | "+ findAverage(finalAverageFailedSearchTime));
            System.out.println("-----------------------------------------");
            System.out.println("CLOSEST KEY AFTER   | "+ findAverage(finalAverageClosestKeyAfterTime));
            System.out.println("-----------------------------------------");
            System.out.println("REMOVAL             | "+ findAverage(finalAverageRemovalTime));
            System.out.println("-----------------------------------------");
            System.out.println("INSERT              | "+ findAverage(finalAverageInsertTime));
            System.out.println("-----------------------------------------");






        }

    }

    private static long findAverage(List<Long> average) {

        long totalSum = 0;

        for (long time : average) {
            totalSum += time;
        }

        return totalSum / average.size();

    }

    private static long generateList(SkipList list, int desiredSize) throws IOException {

        Random random = new Random();
        itemsToSearch = new ArrayList<>();
        itemsForFailedSearch = new ArrayList<>();
        itemsForClosestKeyAfter = new ArrayList<>();
        itemsForRemoval = new ArrayList<>();
        itemsToInsert = new ArrayList<>();

        long start = System.nanoTime();

        //itemSpread is used to capture 50 items while list is getting created
        int itemSpread = desiredSize / 50;
        int itemSpread2 = desiredSize / 50;
        int itemSpread3 = desiredSize / 50;

        int size = 0;
        int searchSize = 0;
        int closestSearchSize = 0;
        int removalSize = 1;

        for (int i = 0; i < 50; i++) {
            itemsForFailedSearch.add(random.nextInt(desiredSize * 2));
        }

        int y = 0;
        while(y != 50){
            int key = random.nextInt(desiredSize * 2);
            if(itemsForFailedSearch.contains(key) || itemsToInsert.contains(key))
               continue;
            else {
                itemsToInsert.add(key);
                y++;
            }
        }

        while (size != desiredSize) {

            int randomNumber = random.nextInt(desiredSize * 2);
            if (itemsForFailedSearch.contains(randomNumber) || itemsToInsert.contains(randomNumber))
                continue;
            if (searchSize == itemSpread) {
                itemsToSearch.add(randomNumber);
                searchSize = 0;
            }
            if (closestSearchSize == itemSpread2) {
                itemsForClosestKeyAfter.add(randomNumber);
                closestSearchSize = 0;
            }
            if(removalSize == itemSpread3){
                if(itemsForRemoval.contains(randomNumber))
                    continue;
                else {
                    itemsForRemoval.add(randomNumber);
                    removalSize = 0;
                }
            }
            list.insertElement(randomNumber, false);
            size = list.getListSize();
            searchSize++;
            closestSearchSize++;
            removalSize++;

        }

//        writer.close();
        long end = System.nanoTime();
        long total = end - start;

        System.out.println("Created list with " + size + " keys.");

        return total;

    }

    private static void closestAfterMenu(SkipList list) {

        Scanner input = new Scanner(System.in);

        boolean keepSearching = false;
        while (!keepSearching) {

            System.out.print("Please enter number to be searched: ");
            int number = input.nextInt();

            if (number > Integer.MAX_VALUE)
                System.out.println("Value is out of range");
            else {
                list.closestKeyAfter(number);

                System.out.println("Search for another number? [Y] [N] ");
                String another = input.next();
                if (another.equalsIgnoreCase("Y"))
                    continue;
                else
                    keepSearching = true;

            }
        }

    }

    private static void findMenu(SkipList list) {

        Scanner input = new Scanner(System.in);

        boolean keepFinding = false;
        while (!keepFinding) {

            System.out.print("Please enter number to be searched: ");
            int number = input.nextInt();

            if (number > Integer.MAX_VALUE)
                System.out.println("Value is out of range");
            else {
                list.findElement(number, true);

                System.out.println("Search for another number? [Y] [N] ");
                String another = input.next();
                if (another.equalsIgnoreCase("Y"))
                    continue;
                else
                    keepFinding = true;

            }
        }

    }

    private static void insertMenu(SkipList list) {

        Scanner input = new Scanner(System.in);

        boolean keepInserting = false;
        while (!keepInserting) {

            System.out.print("Please enter number to be inserted: ");
            int number = input.nextInt();

            if (number > Integer.MAX_VALUE)
                System.out.println("Value is out of range");
            else {
                list.insertElement(number, false);
                list.printList();
                System.out.println("Insert another number? [Y] [N] ");
                String another = input.next();
                if (another.equalsIgnoreCase("Y"))
                    continue;
                else
                    keepInserting = true;

            }
        }

    }

    private static void removeMenu(SkipList list) {
        {

            Scanner input = new Scanner(System.in);

            boolean keepRemoving = false;

            list.printList();

            while (!keepRemoving) {

                System.out.print("Please enter number to be removed: ");
                int number = input.nextInt();

                list.removeElement(number);
                list.printList();

                System.out.println("Remove another number? [Y] [N] ");
                String another = input.next();
                if (another.equalsIgnoreCase("Y"))
                    continue;
                else
                    keepRemoving = true;


            }
        }

    }

    private static void printMenu() {

        System.out.println("[1] Insert Element");
        System.out.println("[2] Remove Element");
        System.out.println("[3] Print List");
        System.out.println("[4] Closest Key After");
        System.out.println("[5] Find Element");
//        System.out.println("[6] Generate Random List");
        System.out.println("[6] Exit");
        System.out.print(">");


    }

}

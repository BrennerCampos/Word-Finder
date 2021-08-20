import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class WordFinder {

    static int counter = 0;
    //static String originalInputFile = "alice.txt";
    static String originalInputFile = "fox.txt";


    public static int binarySearch(String wordSearched, String[] words, String[] indices)
    {

        int start =0;
        int end = words.length;


        while(start<end)       // keep going this until start reaches or passes end
        {
            int mid=(start+end)/2;

            if(words[mid].equals(wordSearched))     // if we found our target value...
            {
               // String[] parsedIndices = indices[mid].split(" ");

                return mid;
            }

            // haven't found it

            if(words[mid].compareTo(wordSearched)<0) // are going to look up or down?
            {
                start = mid + 1;
            }
            else
            {
                end = mid;
            }
        }

        return -1;      //if we don't find the word at all
    }



    public static String[] mergeSortRecursive(String[] words, int[] index)  //this takes an array of nums and returns a second sorted array of nums
    {
        String[] mergeSorted = new String[words.length]; // takes in an array of numbers
        int[] indexSorted = new int[words.length];

        // BASE CASE:

        if (words.length == 1) //once you reach only 1 item, (or if it has none at all)
        {                       // assumes that an array with 1 item is already sorted
            mergeSorted[0] = words[0];  // set total array at start (0) to that first num
        }

        if (words.length <= 1)  //if it is down to 1 or 0 (either are "sorted")
            return mergeSorted;       // you can return the updated totalArray

        // "Merge" step ---V

        int midPoint = words.length / 2;   //< --- could also do that to replace every 'nums.length/2'

        String[] left = new String[midPoint]; //set the left side by cutting the total array in half

        for (int i = 0; i < midPoint; i++)    // copies stuff from nums array into new leftArray from 0 to the midPoint
        {
            left[i] = words[i];
            //   indexSorted[i] = i;
            //  System.out.println("index: "+index[i]+ " left of "+i+" = "+left[i]);
        }


        //now
        String[] right = new String[words.length - midPoint];  //length - other.length/2  so we get the remaining in case not even number

        for (int i = midPoint; i < words.length; i++)    // now for the right, start at midPoint (length/2) and goes till the end of array
        {
            right[i - midPoint] = words[i];  // i - midPoint so we don't go out of bounds (in this case if i starts at mid, i-mid = 0 (starting point)
            //    indexSorted[i] = i;
            // System.out.println("index: "+index[i]);
        }

        //Now we call recursive functions for both sides.
        left = mergeSortRecursive(left, indexSorted);          // new left is now sorted
        // System.out.println(Arrays.toString(left));
        right = mergeSortRecursive(right, indexSorted);        // now right is sorted
        // System.out.println(Arrays.toString(right));

        //System.out.println(Arrays.toString(left));
        // System.out.println(Arrays.toString(right));

        // and now we are at the point that assumes the two starting sets are already sorted
        //    ------V


        int leftPointer = 0, rightPointer = 0, totalPointer = 0;      // initialize left pointer, right pointer, and total pointer


        while (leftPointer < left.length && rightPointer < right.length)  // while both sides still have items in them (above 0)
        {

            if (left[leftPointer].compareTo(right[rightPointer]) < 0) // if left side number is smaller than right side number...
            {
                mergeSorted[totalPointer] = left[leftPointer];
                //  indexSorted[totalPointer] = leftPointer;
                leftPointer++;
                totalPointer++;// pull it down to total array and move the left pointer
            } else {
                mergeSorted[totalPointer] = right[rightPointer];    // otherwise, smaller is in the right, so move down from right to total and move right pointer
                //     indexSorted[totalPointer] = rightPointer;
                rightPointer++;
                totalPointer++;
            }
        }

        // once you reach an end in either side, move all other numbers down into the the sorted array ---V
        // only one of these loops will run because the other will be empty

        while (leftPointer < left.length)      // if the left has stuff, copy the rest to total
        {
            mergeSorted[totalPointer] = left[leftPointer];
            // indexSorted[totalPointer] = leftPointer;
            totalPointer++;
            leftPointer++;
        }

        while (rightPointer < right.length)     // if the right has stuff, copy the rest to total
        {
            mergeSorted[totalPointer] = right[rightPointer];
            //indexSorted[totalPointer] = rightPointer;
            totalPointer++;
            rightPointer++;
        }

        // by this point, total is now sort it, so you can return it!

   /*
        for (int i = 0; i < mergeSorted.length; i++)
        {
            index[i] = mergeSorted[i];
        }
*/

        return mergeSorted;
    }





    public static String[] removeDuplicates(String[] sortedArray, int[] indicesArray) // checks previous word to see if it's the same
    {
        try {

            FileReader myInFile = new FileReader(originalInputFile+"_sorted.txt");
            String[] wordsArray = new String[sortedArray.length];
            Scanner sc = new Scanner(myInFile);
/*
        for (int i = 0; i < sortedArray.length; i++)
        {
            sortedArray[i].split(" ", 0);
            //wordsArray[i] =
            System.out.println(sortedArray[i]);
            //System.out.println(wordsArray[i]);
        }
*/
            int uniqueWords = 0;


           sc.next();  // skips first line contain number of total words


            //String[] parsedIndicesArray = new String[sortedArray.length];

          //  for (int m = 1; m < indicesArray.length; m++)
           // {
           //     int idk = Integer.parseInt(parsedIndicesArray[m]);
          //  }



            for (int i = 1; i < sortedArray.length; i++)     // separates the indices from the words strings
            {
                wordsArray[i] = sc.next();
                indicesArray[i] = sc.nextInt();
            }


            myInFile.close();


            PrintWriter myOutFile;

            myOutFile = new PrintWriter(originalInputFile+"_index.txt");


            for (int j = 1; j < sortedArray.length; j++) {
                if (!wordsArray[j].equals(wordsArray[j - 1])) // if it IS NOT equal
                {
                    if (j > 1) {
                        myOutFile.print("\n");
                       // System.out.print("\n");
                    }
                    myOutFile.print(wordsArray[j] + " " + indicesArray[j]);
                    uniqueWords++;

                } else       // if it IS equal
                {
                    myOutFile.println(" " + indicesArray[j]);

                }

            }
            wordsArray[0] = Integer.toString(uniqueWords);

            /*
           myOutFile.println(counter);

            for (int j = 0; j < counter; j++)
            {
                myOutFile.println(wordsArray[j]+" "+ j);
            }

            for (int j = 0; j < counter; j++)
            {
                myOutFile.println(sortedArray[j]);
            }
*/

            myOutFile.close();


        } catch (IOException e) {
            e.printStackTrace();
        }
        return sortedArray;
    }


    public static void main(String[] args) {

        // STEP ONE: -------------------------------------------------------------------------


        try
        {
            FileReader myInFile = new FileReader(originalInputFile);
            Scanner sc = new Scanner(myInFile);


            while (sc.hasNext())
            {
                counter++;
                sc.next();
            }


            String[] wordsArray = new String[counter];


            myInFile.close();
            myInFile = new FileReader(originalInputFile);
            Scanner sc2 = new Scanner(myInFile);


            for (int i = 0; i < wordsArray.length; i++)
            {
                wordsArray[i] = sc2.next().toLowerCase().replaceAll("[^a-z,A-Z]","");   // fill the newArray, but first set
            }                                                                                   // everything to lowercase and get rid of other symbols


            myInFile.close();

            //  sc = new Scanner(myInFile);

            // close file
            // reopen file
            // remake scanner, and now when you start reading again you'll be reading from beginning of the book



            PrintWriter myOutFile;

            myOutFile = new PrintWriter(originalInputFile+"_word.txt");

            myOutFile.println(counter);

                for (int j = 0; j < counter; j++)
                {
                    myOutFile.println(wordsArray[j]+" "+ j);
                }

             myOutFile.close();          //call with file name

        }

        catch (IOException e)
        {
        e.printStackTrace();
        }
        

        // STEP TWO: -------------------------------------------------------------------------

        counter = 0;

        try {
            FileReader myInFile = new FileReader(originalInputFile+"_word.txt");   // open file

            Scanner sc = new Scanner(myInFile);


            while (sc.hasNextLine())
            {          // figure out how many total words array will hold max
                counter++;
                sc.nextLine();
            }


            String[] wordsArray = new String[counter];          // String array to hold words
            int[] indicesArray = new int[counter];              // Integer array to hold indices

            myInFile.close();


            myInFile = new FileReader(originalInputFile+"_word.txt");
            Scanner sc2 = new Scanner(myInFile);

            // String lines[] = wordsArray.split("\\r?\\n");

            for (int i = 0; i < wordsArray.length; i++) {
                wordsArray[i] = sc2.nextLine();     // copy content from one file to the other
                indicesArray[i] = i;            // remember the indices from each
            }


            String[] sortedArray;


            // MERGE SORT ___________________________________V
            sortedArray = mergeSortRecursive(wordsArray, indicesArray);


/*
            String[] parsedIndices = indices[index].split(" ");

            for (int m = 1; m < parsedIndices.length; m++) {
                parsedIndices = indices[index].split(" ");
                newIndex = Integer.parseInt(parsedIndices[m]);
 */


            Pattern p = Pattern.compile("\\d");     // separates numbers and returns as int to array

            for (int i = 0; i < sortedArray.length; i++) {
                Matcher m = p.matcher(sortedArray[i]);
                while (m.find()) {
                    indicesArray[i] = Integer.parseInt(m.group());
                }
            }

            myInFile.close();


            String wordSearched;



            PrintWriter myOutFile;

            myOutFile = new PrintWriter(originalInputFile+"_sorted.txt");

            //myOutFile.println(words);

/*
        for (int j = 0; j < counter; j++) {
            myOutFile.println(wordsArray[j] + " " + j);
        }
*/
        for (int j = 0; j < counter; j++) {
            myOutFile.println(sortedArray[j]);
        }

            myOutFile.close();


            // STEP THREE ------------------------------------------------------------------------


            removeDuplicates(sortedArray, indicesArray);        // concatenates duplicates and prints them to new _index file


            // STEP FOUR -------------------------------------------------------------------------


            myInFile = new FileReader(originalInputFile+"_index.txt");
            Scanner sc3 = new Scanner(myInFile);



            int uniqueWordCount = 0;

           //sc3.nextLine();

            while (sc3.hasNextLine()) {
                sc3.nextLine();
                uniqueWordCount++;
            }


            String[] words = new String[uniqueWordCount];
            String[] indices = new String[uniqueWordCount];


           // for (int n = 0; n < uniqueWordCount; n++)   // setting every spot so it's not null
           // {
           //     words[n] = "";
          //  }

            myInFile.close();

            myInFile = new FileReader(originalInputFile+"_index.txt");

            Scanner sc4 = new Scanner(myInFile);


            for (int i = 0; i < uniqueWordCount; i++) {
                if (sc4.hasNext()) {
                    words[i] = sc4.next();
                    indices[i] = sc4.nextLine();
                }
            }

           // System.out.println(Arrays.toString(words));
           // System.out.println(Arrays.toString(indices));

            myInFile.close();


            int wordCounter = 0;
            FileReader myOriginalFile = new FileReader(originalInputFile);      //open ORIGINAL file to read in for reference
            Scanner sc7 = new Scanner(myOriginalFile);

            while (sc7.hasNext()) {     // counts how many words are in the ORIGINAL
                wordCounter++;
                sc7.next();
            }

            String[] originalText = new String[wordCounter];    //creates reference array for original text

            myOriginalFile.close();


            myOriginalFile = new FileReader(originalInputFile); // opens ORIGINAL again
            Scanner sc8 = new Scanner(myOriginalFile);

            for (int i = 0; i < wordCounter; i++) {     // transfers words onto array we made for ORIGINAL file
                if (sc8.hasNext()) {
                    originalText[i] = sc8.next();
                    //indices[i] = sc8.nextLine();
                }
            }

            myOriginalFile.close();


            while (true) {          //will keep prompting the user indefinitely for a new word to search

                int startSurrounding = 0;
                int endSurrounding = 1;

                myInFile = new FileReader(originalInputFile+"_index.txt");


                Scanner sc5 = new Scanner(myInFile);
                Scanner sc6 = new Scanner(System.in);

                System.out.print("Enter a word: ");
                wordSearched = sc6.next();


                // CALL BINARY SEARCH ----------------------------------------------


                int index = binarySearch(wordSearched, words, indices);

               // System.out.println("indices: "+ Arrays.toString(indices));
               // System.out.println("words: "+Arrays.toString(words));
/*
                int[] results = new int[items.length];

                for (int i = 0; i < items.length; i++) {
                    try {
                        results[i] = Integer.parseInt(items[i]);
                    } catch (NumberFormatException nfe) {
                        //NOTE: write something here if you need to recover from formatting errors
                    };
                }

  */


                // STEP FIVE -------------------------------------------------------------------------
                

                int newIndex = 0;

                if (index >= 0) {

                    String[] parsedIndices = indices[index].split(" ");

                    for (int m = 1; m < parsedIndices.length; m++)
                    {
                        parsedIndices = indices[index].split(" ");
                        newIndex = Integer.parseInt(parsedIndices[m]);
                        //System.out.println(newIndex);


/*
                int[] parsedIndices = new int[indices.length];

                for (int k = 0; k < indices.length; k++)
                {
                    try
                    {
                        parsedIndices = Integer.parseInt(indices[k]);
                    }
                    catch (NumberFormatException nfe)
                    {
                            //NOTE: write something here if you need to recover from formatting errors
                    }
                }
               // Integer.parseInt(indices[6]);

                //indices[index].split(" ");
                //System.out.println("Indices array = "+Arrays.toString(indicesArray));
*/


                       // System.out.println("Parsed: " + Arrays.toString(parsedIndices));


                        // System.out.println(position);
                        // System.out.println("Index = " + index);
                        // System.out.println("Indices index = " + indices[index]);
                        //  System.out.println("New index = " + newIndex);




                        if (newIndex == 0)       // if it's the very FIRST word in the text
                        {
                            startSurrounding = 0;

                            if ((newIndex + 2) < (words.length))  // if there are more than 2 indexes AFTER available
                            {
                                endSurrounding = newIndex + 2;
                            }
                        }


                        if (newIndex == words.length)        //if it's the very LAST word in the text
                        {
                            endSurrounding = words.length;

                            if ((newIndex - 2) >= (0))  // if there are more than 2 indexes BEFORE available
                            {
                                startSurrounding = newIndex - 2;
                            }
                            //else {
                            //     startSurrounding = 1;
                            // }
                        }

                        if (newIndex + 1 == words.length) //if there's only 1 word before last word
                        {
                            endSurrounding = newIndex + 1;
                            startSurrounding = newIndex - 2;
                        }

                        if (newIndex - 1 == 0)    //if there's only one word before first word
                        {
                            startSurrounding = newIndex - 1;
                            endSurrounding = newIndex + 2;
                        }


                        if (((newIndex - 2) >= 0) && (newIndex + 2 <= words.length))      // if there IS enough space on both ends
                        {

                            if (newIndex + 2 <= words.length) {
                                endSurrounding = newIndex + 2;
                            }

                            startSurrounding = newIndex - 2;


                            //endSurrounding = newIndex + 2;
                        }


                        // if (newIndex >= 0) {
                        System.out.print(newIndex + ": ");

                        for (int i = startSurrounding; i <= endSurrounding; i++) {
                            System.out.print(originalText[i] + " ");
                        }
                        System.out.println();
                    }
                }
                    // System.out.println(words[index] + ": " + indices[index]);
                else{
                        System.out.println("Can't find " + wordSearched + " in the text");
                }
                System.out.println();


                myInFile.close();


                myOutFile = new PrintWriter(originalInputFile+"_index.txt");

                //myOutFile.println(words);

                for (int j = 0; j < words.length; j++) {                // prints out words array to "_index" file
                    myOutFile.println(words[j] + indices[j]);
                }
                myOutFile.close();

            }

 /*
        for (int j = 0; j < counter; j++) {
            myOutFile.println(wordsArray[j] + " " + j);
        }

        for (int j = 0; j < counter; j++) {
            myOutFile.println(sortedArray[j]);
        }
*/

        }

         catch (IOException e)
         {
            e.printStackTrace();
         }
    }
}


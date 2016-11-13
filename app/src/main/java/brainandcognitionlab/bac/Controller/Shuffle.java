package brainandcognitionlab.bac.Controller;

import java.util.Random;

/**
 * Created by hojaeson on 4/4/16.
 */
public class Shuffle {
        // Implementing Fisher–Yates shuffle
        public < E >  E[] shuffleArray(E[] ar)
        {
            Random random = new Random();

            for (int i = ar.length - 1; i > 0; i--) {
                int index = random.nextInt(i + 1);
                // Simple swap
                E a = ar[index];
                ar[index] = ar[i];
                ar[i] = a;
            }
            return ar;
        }
        public int[] shuffleIndexArrayFromPool(int lenOfDestArray,int lenOfPoolArray)
        {
            Random random = new Random();
            int []indexArray = new int[lenOfDestArray];
            for(int i=0; i<lenOfDestArray; i++){
                int randomIndex = random.nextInt(lenOfPoolArray);
                indexArray[i] = randomIndex;
                for(int j=0; j<i; j++){
                    if(randomIndex == indexArray[j]){
                        i-=1;
                        break;
                    }
                }
            }
            return indexArray;
        }
}

package Simulation;

import java.util.ArrayList;
import java.util.Random;

public class testchoose {



     public ArrayList<testorg> createpop(int size) {

         ArrayList<testorg> testpop = new ArrayList<>();

         for (int i = 0; i < size; i++) {

             String ID = String.valueOf(i);

             testorg ind = new testorg(ID);

             testpop.add(ind);

         }

         return testpop;


    }

    public ArrayList<testorg> choosererer (ArrayList<testorg> testpop) {

        ArrayList<testorg> parentList = new ArrayList<>();

        int sizelist = testpop.size();



        for ( int i =0; i < sizelist; i++) {

            int target = new Random().nextInt(0, sizelist);

            parentList.add(testpop.get(target));

        }

        return parentList;
    }
}

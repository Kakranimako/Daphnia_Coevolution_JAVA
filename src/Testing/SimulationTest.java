package Testing;
import static org.junit.Assert.*;

import Simulation.Parentpicker;
import Simulation.Simulation;
import org.junit.Test;

import java.sql.Struct;
import java.util.ArrayList;
import java.util.Random;

public class SimulationTest {


        @Test
        public void chooseParent() {

            ArrayList<Double> cumulList = new ArrayList<>();
            ArrayList<String> parentCumulList = new ArrayList<>();

            double sumfit = 0;
            for (int i = 0; i < 10; i++) {
                sumfit += new Random().nextDouble(5);
                cumulList.add(sumfit);
                parentCumulList.add("Parent_" + sumfit);
            }

            Parentpicker testpickList = new Parentpicker(cumulList, parentCumulList, new ArrayList<>());

            testpickList = new Simulation().chooseParent(testpickList);



            boolean expected = true;
            assertTrue(testpickList.getParentList().size() == 10);

            for (int i = 0; i < 10; i++) {
               // assertTrue(testpickList.getCumulFitList());

            }

            assertEquals(expected, true);
        }

    }




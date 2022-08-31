

import Experiment.Experiment;
import Experiment.ModeArgs;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.stream.Stream;


public class Main {

    public static void main(String[] args) throws IOException {

        HashMap<String, Double> modeArgs = new HashMap<>();
        modeArgs.put("slope", 0.002);
        modeArgs.put("horizonShift", 0.2);
        modeArgs.put("period", 1.0);
        modeArgs.put("phases", 5.0);
        modeArgs.put("vertShift", 0.0);
        modeArgs.put("mean", 0.4);
        modeArgs.put("variance", 1.0);


        //"different modes are: linear, static, sinus, step, random"
        long start = System.currentTimeMillis();
        Experiment exp1 = new Experiment("testppl1", 100, 0.0, 0.5, 0.5, 0.6, 1500, 1000,
                2000, 0.01, 0.01, 0.6, 0.2, 1.0, 1.0,
                "scarcity", "step",
                ModeArgs.getModeArgs(0.0, 0.2, 1.0, 5.0, 0.0, 0.4, 1.0));

        System.out.println("done");

        Experiment exp2 = new Experiment("testppl2", 100, 0.0, 0.5, 0.5,0.2, 1500, 1000,
                2000, 0.01, 0.01, 0.5, 0.4, 1.0, 1.0,
                "fitPen", "static", ModeArgs.getModeArgs(0.0, 0.2, 1.0, 5.0, 0.0, 0.4, 1.0));



        Experiment exp3 = new Experiment("testppl3", 100, 0.0, 0.5, 0.5, 0.4, 1500, 1000,
                2000, 0.01, 0.01, 0.5, 0.4, 1.0, 1.0,
                "fitPen", "static", ModeArgs.getModeArgs(0.0, 0.2, 1.0, 5.0, 0.0, 0.4, 1.0));



        Experiment exp4 = new Experiment("testppl4", 100, 0.0, 0.5 ,0.5, 0.6, 1500, 1000,
                2000, 0.01, 0.01, 0.5, 0.4, 1.0, 1.0,
                "fitPen", "static", ModeArgs.getModeArgs(0.0, 0.2, 1.0, 5.0, 0.0, 0.4, 1.0));



        Experiment exp5 = new Experiment("testppl5", 100, 0.0, 0.5, 0.5, 0.8, 1500, 1000,
                2000, 0.01, 0.01, 0.5, 0.4, 1.0, 1.0,
                "fitPen", "static", ModeArgs.getModeArgs(0.0, 0.2, 1.0, 5.0, 0.0, 0.4, 1.0));



        Experiment exp6 = new Experiment("testppl6", 100, 0.0, 0.5, 0.5, 1.0, 1500, 1000,
                2000, 0.01, 0.01, 0.5, 0.4, 1.0, 1.0,
                "fitPen", "static", ModeArgs.getModeArgs(0.0, 0.2, 1.0, 5.0, 0.0, 0.4, 1.0));


       ArrayList<Experiment> expList = new ArrayList<>(Arrays.asList(exp1, exp2, exp3, exp4, exp5, exp6));



       expList.parallelStream().forEach(experiment -> experiment.startExp());
       long stop = System.currentTimeMillis();

       System.out.println("parrelisme " +  String.valueOf(stop-start));



    }

}


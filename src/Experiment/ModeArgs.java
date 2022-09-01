package Experiment;


import java.util.HashMap;

public class ModeArgs {

    public static HashMap<String, Double> getModeArgs(Double slope, Double horizonShift, Double period, Double phases,
                                                      Double vertShift, Double mean, Double variance){
        return new HashMap<String, Double>(){{
        put("slope", slope == null ? 0.002: slope);
        put("horizonShift", horizonShift == null ? 0.2: horizonShift);
        put("period", period == null ? 1.0: period);
        put("phases", phases == null ? 5.0: phases);
        put("vertShift", vertShift == null ? 0.0: vertShift);
        put("mean", mean == null ? 0.4: mean);
        put("variance", variance == null ? 1.0: variance);

    }};
    }


}

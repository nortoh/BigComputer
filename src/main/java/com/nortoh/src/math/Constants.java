package com.nortoh.src.math;

import java.util.HashMap;
import java.util.Map;

public class Constants {

    private static final int ITERATIONS = 10000000;

    public static Map<String, Double> constants = new HashMap<String, Double>();

    //0.5772156
    public static final double EulerMascheroniConstant() {
        int max = ITERATIONS;
        double ln = -Math.log(max);
        double hermonicSum = Series.HERMONIC_SERIES.setUpperLimit(max).sum();
        return ln + hermonicSum;
    }

    public static final double AperysConstant() {
        InfiniteSeries.ZETA_FUNCTION.getSequence().setVariable("s", 3);
        return InfiniteSeries.ZETA_FUNCTION.sum();
    }

    public static final double Pi() {
        return Math.PI;
    }

    public static final double Phi() {
        return (1 + Math.sqrt(5)) / 2;
    }

    public static final double SquartRootTwo() {
        return Math.sqrt(2);
    }

    static {
        constants.put("euler_mascheroni_constant", EulerMascheroniConstant());
        constants.put("aperys_constant", AperysConstant());
        constants.put("phi", Phi());
    }
}

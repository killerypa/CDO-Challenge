package com.avances.challenge;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class Challenge {
    private static final String ANALYZE_1 = "What are the time and space complexity of your solution?";
    private static final String ANALYZE_2 = "What are the limitations of your solution?";
    private static final String ANALYZE_3 = "Why you have decided to implement on that way?";
    private static final String POSSIBLE_IMPROVEMENTS = "What do you think we could do to get a better implementation?";
    private static final String SEND = "Send MySequence class to javi(at)11paths(dot)com";
    private static final String SOLVE_CHALLENGE = "Implement class MySequence. Replace Object type from method signatures with the more suitable type you consider";

    private class MySequence {
        private MySequence() {
        }

        public void add(Object n) {
            Log.e("Help", "DOC : " + "This method adds a number to the 'sequence'");
        }

        public Object getFirstNonDuplicated() {
            Log.e("Help", "DOC : " + "This method return the first non duplicated number of the 'sequence'");
            return null;
        }
    }

    private void steps() {
        List<String> stepsOfTheChallenge = new ArrayList();
        stepsOfTheChallenge.add(SOLVE_CHALLENGE);
        stepsOfTheChallenge.add(ANALYZE_1);
        stepsOfTheChallenge.add(ANALYZE_2);
        stepsOfTheChallenge.add(ANALYZE_3);
        stepsOfTheChallenge.add(POSSIBLE_IMPROVEMENTS);
        stepsOfTheChallenge.add(SEND);
    }

    public void main() {
        Log.e("Help", "DOC : " + "This is an example of a possible MySequence instance use");
        MySequence s = new MySequence();
        s.add(Integer.valueOf(5));
        s.add(Integer.valueOf(3));
        s.add(Integer.valueOf(5));
        s.add(Integer.valueOf(6));
        Object thisShouldBeNumber_3 = s.getFirstNonDuplicated();
        Log.e("Output", "Previous operation returns 3");
        s.add(Integer.valueOf(3));
        Object thisShouldBeNumber_6 = s.getFirstNonDuplicated();
        Log.e("Output", "Previous operation returns 6");
    }
}

package com.tsystems.javaschool.tasks.subsequence;

import java.util.List;

public class Subsequence {

    /**
     * Checks if it is possible to get a sequence which is equal to the first
     * one by removing some elements from the second one.
     *
     * @param x first sequence
     * @param y second sequence
     * @return <code>true</code> if possible, otherwise <code>false</code>
     */
    @SuppressWarnings("rawtypes")
    public boolean find(List x, List y) {

        boolean flag=true;
        if(y == null)
        {
            throw new IllegalArgumentException();
        }
        int k=0;
        try{  for (int i=0;i<x.size();i++)
        {
            if (!flag) {break;}
            flag=false;
            for (int j=k;j<y.size();j++)
            {
                if (y.get(j).equals(x.get(i)))
                {
                    k=j+1;
                    flag=true;
                    break;

                }
            }
        }}
        catch (Exception e){
            throw new IllegalArgumentException();
        }

        return flag;
    }
}

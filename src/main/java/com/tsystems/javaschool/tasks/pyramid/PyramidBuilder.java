package com.tsystems.javaschool.tasks.pyramid;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class PyramidBuilder {

    /**
     * Builds a pyramid with sorted values (with minumum value at the top line and maximum at the bottom,
     * from left to right). All vacant positions in the array are zeros.
     *
     * @param inputNumbers to be used in the pyramid
     * @return 2d array with pyramid inside
     * @throws {@link CannotBuildPyramidException} if the pyramid cannot be build with given input
     */
    public int[][] buildPyramid(List<Integer> inputNumbers) {
        if(inputNumbers.size()>10000)
            throw new CannotBuildPyramidException();
        Integer [] mas =  new Integer[inputNumbers.size()];
        for (int i = 0; i < mas.length; i++) {
            if(Objects.isNull(inputNumbers.get(i)))
                throw new CannotBuildPyramidException();
            mas[i]=inputNumbers.get(i);
        }

        int k = mas.length;
        System.out.println(k);//6
        int i = 1;
        int j = 0;
        while (k>0){
            j++;//1 2 3
            k = k-i;//5 3 0
            i++;//2 3 4
        }
        i--;//3
        if (k == 0){
            int [][] mas2 = new int[j][2*i-1];
            //в матрице столбцов будет  2*i-1
            //строк будет j
            for (int a=0;a<j;a++){
                for(int b=0;b<(2*i-1);b++){
                    mas2[a][b]=0;
                }
            }
            Arrays.sort(mas);
            int r=1;
            int el = 0;
            int im = i-1;//2
            int sr = im; //2
            int count = r;//1
            for (int a=0;a<j;a++){
                for(int b=0;b<(2*i-1);b++){
                    while(count>0 && count<=i){//1>0
                        count--;//0
                        int t = mas[el];//1
                        mas2[a][sr] = t;//0,2 = 1
                        sr +=2;//4
                        el++;//1
                    }
                }
                r++;//2
                im--;//1
                count=r;//2
                sr = im;//1

            }
            return mas2;
        } else {
            throw new CannotBuildPyramidException();
        }
    }


}

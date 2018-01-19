package com.henri.code;

// this class' purpose is to hold state for possible change
public class Change {
    StringBuilder sb;
    public Integer count$20;
    public Integer count$10;
    public Integer count$5;
    public Integer count$2;
    public Integer count$1;
    public Integer totalValue;

    public Change(){
        sb = new StringBuilder();
        count$20 = 0;
        count$10 = 0;
        count$5 = 0 ;
        count$2 = 0;
        count$1 = 0;
        totalValue = 0;
    }

    //tale from register, put in change
    public Integer take(int denomination, int count){
        switch (denomination){
            case 20:
                count$20 += count;
                break;
            case 10:
                count$10 += count;
                break;
            case 5:
                count$5 += count;
                break;
            case 2:
                count$2 += count;
                break;
            case 1:
                count$1 += count;
                break;
        }
        return updateValue();
    }

    //put in register, from change
    public Integer put(int denomination, int count){
        switch(denomination){
            case 20:
                count$20 -= count;
                break;
            case 10:
                count$10 -= count;
                break;
            case 5:
                count$5 -= count;
                break;
            case 2:
                count$2 -= count;
                break;
            case 1:
                count$1 -= count;
                break;
        }
        return updateValue();
    }

    public Integer updateValue(){
        totalValue += count$20 * 20;
        totalValue += count$10 * 10;
        totalValue += count$5 * 5;
        totalValue += count$2 * 2;
        totalValue += count$1 * 1;

        return totalValue;
    }

    @Override
    public String toString() {
        sb.append(count$20).append(" ")
                .append(count$10).append(" ")
                .append(count$5).append(" ")
                .append(count$2).append(" ")
                .append(count$1).append(" ");
        return sb.toString();
    }

}

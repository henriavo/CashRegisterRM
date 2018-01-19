package com.henri.code;

// this class' purpose is to hold state for possible change
public class Change {
    StringBuilder sb;
    public Integer count$20;
    public Integer count$10;
    public Integer count$5;
    public Integer count$2;
    public Integer count$1;
    public Integer currentValue; // how much we have in our possible change.
    public Integer changeGoal; // is is what customer gave, asking for change
    public Integer totalRemaining; // still need change for this

    public Change(Integer changeGoal){
        sb = new StringBuilder();
        count$20 = 0;
        count$10 = 0;
        count$5 = 0 ;
        count$2 = 0;
        count$1 = 0;
        currentValue = 0;

        this.changeGoal = changeGoal;
        this.totalRemaining = changeGoal;
    }

    public Integer getBillCount(int value){
        switch (value){
            case 20:
                return count$20;
            case 10:
                return count$10;
            case 5:
                return count$5;
            case 2:
                return count$2;
            case 1:
                return count$1;
            default:
                return null;
        }
    }

    //takeBillsFromRegister from register, putBillsBackInRegister in change
    public void takeBillsFromRegister(int denomination, int count){
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
        updateValue();
    }

    //putBillsBackInRegister in register, from change
    public void putBillsBackInRegister(int denomination, int count){
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
        updateValue();
    }

    public void updateValue(){
        currentValue = count$20 * 20;
        currentValue += count$10 * 10;
        currentValue += count$5 * 5;
        currentValue += count$2 * 2;
        currentValue += count$1 * 1;

        this.totalRemaining = changeGoal - currentValue;

    }

    @Override
    public String toString() {
        sb = new StringBuilder();
        sb.append(count$20).append(" ")
                .append(count$10).append(" ")
                .append(count$5).append(" ")
                .append(count$2).append(" ")
                .append(count$1).append(" ");
        return sb.toString();
    }

}

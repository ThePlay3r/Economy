package me.pljr.economy.objects;

public class CorePlayer {
    private double money;

    public CorePlayer(double amount){
        this.money = amount;
    }

    public CorePlayer(){
        this.money = 0;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }
}

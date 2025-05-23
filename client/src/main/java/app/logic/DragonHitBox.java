package app.logic;

import seClasses.Dragon;

class DragonHitBox {
    private Dragon dragon;
    private double x;
    private double y;
    private double radius;

    public DragonHitBox(Dragon dragon, double x, double y, double radius) {
        this.dragon = dragon;
        this.x = x;
        this.y = y;
        this.radius = radius;
    }

    public boolean contains(double mouseX, double mouseY) {
        double dx = mouseX - this.x;
        double dy = mouseY - this.y;
        return dx * dx + dy * dy <= this.radius * this.radius;
    }

    public Dragon getDragon(){
        return this.dragon;
    }
}
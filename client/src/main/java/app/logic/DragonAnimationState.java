package app.logic;

public class DragonAnimationState {
    private double y;
    private double velocity;
    private final double gravity = 0.2;
    private final double bounceFactor = 0.7;

    public DragonAnimationState(double initialY) {
        this.y = initialY;
        this.velocity = 0;
    }

    public void update() {
        velocity += gravity;
        y += velocity;


        if (y >= 0) {
            y = 0;
            velocity = -velocity * bounceFactor;
        }
    }

    public double getY() {
        return y;
    }
}
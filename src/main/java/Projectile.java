public class Projectile {


    private double x, y;
    private double speedX, speedY;
    private double damage;
    private String color;
    private boolean active;
    private double size;
    private double angle;

    private boolean isPiercing;
    private boolean isExplosive;
    private double explosionRadius;
    private int maxHits;
    private int currentHits;

    private long creationTime;
    private double lifetime;

    private boolean isHoming;
    private Fighter target;
    private double homingStrength;


    public Projectile(double x, double y, double speed, double damage,
                      String color, double size) {
        this.x = x;
        this.y = y;
        this.speedX = speed;
        this.speedY = 0;
        this.damage = damage;
        this.color = color;
        this.size = size;
        this.active = true;
        this.angle = 0;


        this.isPiercing = false;
        this.isExplosive = false;
        this.explosionRadius = 0;
        this.maxHits = 1;
        this.currentHits = 0;


        this.creationTime = System.currentTimeMillis();
        this.lifetime = 10.0;

        this.isHoming = false;
        this.target = null;
        this.homingStrength = 0;
    }


    public Projectile(double x, double y, double speed, double damage,
                      String color, double size, double angle) {
        this(x, y, speed, damage, color, size);
        setAngle(angle);
    }


    public void move() {

        if (isHoming && target != null && target.isAlive()) {
            updateHomingDirection();
        }


        x += speedX;
        y += speedY;
    }

    private void updateHomingDirection() {

        double dx = target.getX() - x;
        double dy = target.getY() - y;
        double distance = Math.sqrt(dx * dx + dy * dy);

        if (distance > 0) {

            dx /= distance;
            dy /= distance;


            double speed = Math.sqrt(speedX * speedX + speedY * speedY);
            speedX += dx * homingStrength;
            speedY += dy * homingStrength;


            double newSpeed = Math.sqrt(speedX * speedX + speedY * speedY);
            if (newSpeed > 0) {
                speedX = (speedX / newSpeed) * speed;
                speedY = (speedY / newSpeed) * speed;
            }
        }
    }


    public void setAngle(double angle) {
        this.angle = angle;
        double speed = Math.sqrt(speedX * speedX + speedY * speedY);
        double radians = Math.toRadians(angle);



        speedX = Math.cos(radians) * speed;
        speedY = -Math.sin(radians) * speed;
    }


    public void reverseDirection() {
        speedX = -speedX;
        angle = 180 - angle;
    }


    public boolean checkCollision(Fighter fighter) {
        if (!active || !fighter.isAlive()) {
            return false;
        }


        double fighterSize = fighter.getSize();
        double distance = Math.sqrt(
                Math.pow(x - fighter.getX(), 2) +
                        Math.pow(y - fighter.getY(), 2)
        );


        boolean collision = distance < (size + fighterSize) / 2;

        if (collision) {
            currentHits++;


            if (currentHits >= maxHits && !isPiercing) {
                active = false;
            }


            if (isExplosive) {
                System.out.println("💥 Explosion at (" +
                        String.format("%.0f", x) + ", " +
                        String.format("%.0f", y) + ")!");
            }
        }

        return collision;
    }


    public boolean isOutOfBounds(double screenWidth, double screenHeight) {
        return x < -size || x > screenWidth + size ||
                y < -size || y > screenHeight + size;
    }


    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getSpeedX() {
        return speedX;
    }

    public double getDamage() {
        return damage;
    }

    public String getColor() {
        return color;
    }

    public double getSize() {
        return size;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }


    public void setColor(String color) {
        this.color = color;
    }


    public Projectile clone() {
        Projectile copy = new Projectile(x, y, speedX, damage, color, size);
        copy.speedY = this.speedY;
        copy.angle = this.angle;
        copy.isPiercing = this.isPiercing;
        copy.isExplosive = this.isExplosive;
        copy.explosionRadius = this.explosionRadius;
        copy.maxHits = this.maxHits;
        return copy;
    }


    @Override
    public String toString() {
        return "Projectile{" +
                "pos=(" + String.format("%.0f", x) + "," + String.format("%.0f", y) + ")" +
                ", damage=" + damage +
                ", active=" + active +
                "}";
    }
}
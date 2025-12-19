public abstract class Fighter {

    protected final String name;
    protected final double maxHealth;
    protected final double speed;

    protected double health;
    protected double x, y;

    protected Weapon currentWeapon;
    protected long lastShotTime = 0;

    public Fighter(String name, double maxHealth, double x, double y, double speed) {
        this.name = name;
        this.maxHealth = maxHealth;
        this.health = maxHealth;
        this.x = x;
        this.y = y;
        this.speed = speed;
    }

    public double getHealth() { return health; }

    public double getMaxHealth() { return maxHealth; }

    public double getHealthPercentage() {
        return health / maxHealth;
    }

    public double getX() { return x; }

    public double getY() { return y; }

    public double getSpeed() { return speed; }

    public Weapon getCurrentWeapon() { return currentWeapon; }


    protected void move(double dx, double dy,
                        double minX, double maxX,
                        double minY, double maxY) {

        x = clamp(x + dx, minX, maxX);
        y = clamp(y + dy, minY, maxY);
    }

    private double clamp(double value, double min, double max) {
        return Math.max(min, Math.min(max, value));
    }


    public void setCurrentWeapon(Weapon weapon) {
        if (weapon == null) return;
        this.currentWeapon = weapon;
        System.out.println(name + " equipped " + weapon.getName());
    }

    public boolean canShoot() {
        return currentWeapon != null &&
                System.currentTimeMillis() - lastShotTime >= currentWeapon.getCooldown();
    }


    public Projectile shoot() {
        if (!canShoot()) return null;

        lastShotTime = System.currentTimeMillis();
        System.out.println(name + " fired " + currentWeapon.getName());
        return currentWeapon.createProjectile(x, y);
    }

    public void takeDamage(double damage) {
        health = Math.max(0, health - damage);
        System.out.println(name + " took " + damage + " damage (HP: " + health + ")");

        if (!isAlive()) {
            System.out.println(name + " has been defeated!");
        }
    }

    public void heal(double amount) {
        health = Math.min(maxHealth, health + amount);
        System.out.println(name + " healed " + amount + " HP (HP: " + health + ")");
    }

    public boolean isAlive() {
        return health > 0;
    }



    public abstract String getColor();

    public abstract double getSize();

    public abstract void useSpecialAbility();

    @Override
    public String toString() {
        return name + " (HP: " + health + "/" + maxHealth + ")";
    }
}

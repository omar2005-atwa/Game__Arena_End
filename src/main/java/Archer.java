public class Archer extends Fighter {

    private static final String COLOR = "GREEN";
    private static final double SIZE = 38;
    private static final double MAX_HEALTH = 100;
    private static final double SPEED = 5.0;
    private static final String SPECIAL_ABILITY = "Multi-Shot";

    private static final long MAX_AIM_TIME = 2000;
    private static final int MAX_ARROWS = 3;
    private static final long ARROW_REGEN_TIME = 3000;

    private static final double BASE_CRIT_CHANCE = 0.15;
    private static final double CRIT_MULTIPLIER = 2.0;

    private boolean aimingMode;
    private long aimStartTime;

    private int arrowsCount;
    private long lastArrowRegenTime;

    private double criticalChance;

    public Archer(double x, double y) {
        super("Archer", MAX_HEALTH, x, y, SPEED);
        this.currentWeapon = Weapon.createPistol();

        this.arrowsCount = MAX_ARROWS;
        this.lastArrowRegenTime = System.currentTimeMillis();

        this.aimingMode = false;
        this.aimStartTime = 0;

        this.criticalChance = BASE_CRIT_CHANCE;
    }

    @Override
    public String getColor() {
        return aimingMode ? "YELLOW" : COLOR;
    }

    @Override
    public double getSize() {
        return SIZE;
    }


    @Override
    public void useSpecialAbility() {
        if (arrowsCount <= 0) return;
        multiShot();
    }

    public void startAiming() {
        if (!aimingMode) {
            aimingMode = true;
            aimStartTime = System.currentTimeMillis();
        }
    }

    public void stopAiming() {
        aimingMode = false;
    }

    public double getAimMultiplier() {
        if (!aimingMode) return 1.0;
        long duration = System.currentTimeMillis() - aimStartTime;
        return Math.min(1.0 + duration / (double) MAX_AIM_TIME, 2.0);
    }

    @Override
    public Projectile shoot() {
        Projectile projectile = super.shoot();
        if (projectile == null) return null;

        double aimBonus = getAimMultiplier() - 1.0;
        double totalCritChance = criticalChance + (aimBonus * 0.2);

        if (Math.random() < totalCritChance) {
        }

        stopAiming();
        return projectile;
    }

    public Projectile[] multiShot() {
        if (arrowsCount <= 0 || !canShoot()) return new Projectile[0];

        Projectile[] arrows = new Projectile[arrowsCount];

        double spread = 15;
        double startAngle = -(arrowsCount - 1) * spread / 2;

        for (int i = 0; i < arrowsCount; i++) {
            double angle = startAngle + i * spread;
            double offsetY = Math.tan(Math.toRadians(angle)) * 50;
            arrows[i] = currentWeapon.createProjectile(x, y + offsetY);
        }

        arrowsCount = 0;
        lastArrowRegenTime = System.currentTimeMillis();
        return arrows;
    }

    public Projectile chargedShot() {
        if (arrowsCount < 2 || !canShoot()) return null;

        Projectile arrow = shoot();
        if (arrow != null) {
            arrowsCount -= 2;
        }
        return arrow;
    }

    public void regenerateArrows() {
        if (arrowsCount >= MAX_ARROWS) return;

        long now = System.currentTimeMillis();
        if (now - lastArrowRegenTime >= ARROW_REGEN_TIME) {
            arrowsCount++;
            lastArrowRegenTime = now;
        }
    }

    public void increaseCritChance(long duration) {
        criticalChance = BASE_CRIT_CHANCE * 2;

        new Thread(() -> {
            try {
                Thread.sleep(duration);
                criticalChance = BASE_CRIT_CHANCE;
            } catch (InterruptedException ignored) {}
        }).start();
    }

    public String getArrowsStatus() {
        if (arrowsCount == MAX_ARROWS) return arrowsCount + "/" + MAX_ARROWS;
        if (arrowsCount == 0) {
            long remaining = ARROW_REGEN_TIME -
                    (System.currentTimeMillis() - lastArrowRegenTime);
            return "0/" + MAX_ARROWS + " (" + remaining / 1000 + "s)";
        }
        return arrowsCount + "/" + MAX_ARROWS;
    }

    @Override
    public void move(double dx, double dy, double minX, double maxX, double minY, double maxY) {
        if (aimingMode) {
            dx *= 0.5;
            dy *= 0.5;
        }
        super.move(dx, dy, minX, maxX, minY, maxY);
    }

    @Override
    public String toString() {
        return "🏹 Archer | Arrows: " + getArrowsStatus() +
                " | Crit: " + String.format("%.0f%%", criticalChance * 100);
    }
}
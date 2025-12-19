public class Mage extends Fighter {

    private static final String COLOR = "PURPLE";
    private static final double SIZE = 35;
    private static final double MAX_HEALTH = 80;
    private static final double SPEED = 6.5;
    private static final String SPECIAL_ABILITY = "Teleport";

    private long lastTeleportTime;
    private static final long TELEPORT_COOLDOWN = 5000;
    private static final double TELEPORT_DISTANCE = 150;

    private int manaPoints;
    private static final int MAX_MANA = 100;
    private static final int TELEPORT_MANA_COST = 30;

    private boolean magicShieldActive = false;

    public Mage(double x, double y) {
        super("Mage", MAX_HEALTH, x, y, SPEED);

        this.currentWeapon = Weapon.createLaser();
        this.lastTeleportTime = 0;
        this.manaPoints = MAX_MANA;

        System.out.println("🔮 Mage created at position (" + x + ", " + y + ")");
        System.out.println("   HP: " + MAX_HEALTH + " | Speed: " + SPEED + " | Weapon: Laser");
        System.out.println("   Mana: " + manaPoints + "/" + MAX_MANA);
    }

    @Override
    public String getColor() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastTeleportTime < 500) {
            return "CYAN";
        }
        return COLOR;
    }

    @Override
    public double getSize() {
        return SIZE;
    }



    @Override
    public void useSpecialAbility() {
        long currentTime = System.currentTimeMillis();

        if (currentTime - lastTeleportTime < TELEPORT_COOLDOWN) {
            long remainingCooldown = TELEPORT_COOLDOWN - (currentTime - lastTeleportTime);
            System.out.println("⏳ Teleport cooldown: " + (remainingCooldown / 1000) + " seconds");
            return;
        }

        if (manaPoints < TELEPORT_MANA_COST) {
            System.out.println("❌ Not enough mana! Need " + TELEPORT_MANA_COST + ", have " + manaPoints);
            return;
        }

        teleport();
    }

    private void teleport() {
        double angle = Math.random() * 2 * Math.PI;
        double newX = x + Math.cos(angle) * TELEPORT_DISTANCE;
        double newY = y + Math.sin(angle) * TELEPORT_DISTANCE;

        double oldX = x;
        double oldY = y;

        x = newX;
        y = newY;

        manaPoints -= TELEPORT_MANA_COST;
        lastTeleportTime = System.currentTimeMillis();

        System.out.println("✨ " + name + " teleported from (" +
                String.format("%.0f", oldX) + ", " +
                String.format("%.0f", oldY) + ") to (" +
                String.format("%.0f", x) + ", " +
                String.format("%.0f", y) + ")!");
        System.out.println("   Mana: " + manaPoints + "/" + MAX_MANA);
    }


    public void regenerateMana() {
        if (manaPoints < MAX_MANA) {
            manaPoints += 1;
            if (manaPoints > MAX_MANA) {
                manaPoints = MAX_MANA;
            }
        }
    }

    @Override
    public void takeDamage(double damage) {
        if (magicShieldActive) {
            System.out.println("🛡️ Magic Shield absorbed the attack!");
            magicShieldActive = false;
            return;
        }

        super.takeDamage(damage);
    }



    public int getManaPoints() {
        return manaPoints;
    }

    public int getMaxMana() {
        return MAX_MANA;
    }

    @Override
    public String toString() {
        return "🔮 Mage - Fast Glass Cannon | Mana: " + manaPoints + "/" + MAX_MANA;
    }

    public String getDetailedDescription() {
        return "🔮 MAGE\n" +
                "━━━━━━━━━━━━━━━━━━━━\n" +
                "Role: Hit & Run / Assassin\n" +
                "Health: " + MAX_HEALTH + " HP (Lowest)\n" +
                "Speed: " + SPEED + " (Fastest)\n" +
                "Size: " + SIZE + "px (Small)\n" +
                "Weapon: Laser\n" +
                "Mana: " + MAX_MANA + "\n" +
                "Special: Teleport\n" +
                "  - Cost: 30 mana\n" +
                "  - Distance: 150px\n" +
                "  - Cooldown: 5 seconds\n" +
                "━━━━━━━━━━━━━━━━━━━━\n" +
                "Strategy: Dodge attacks,\n" +
                "fast strikes, teleport away";
    }
}


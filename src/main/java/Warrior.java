public class Warrior extends Fighter {

    // ===== CONSTANTS =====
    private static final String COLOR = "RED";
    private static final String SHIELD_COLOR = "GOLD";
    private static final double SIZE = 45;
    private static final double MAX_HEALTH = 150;
    private static final double SPEED = 4.0;

    private static final long SHIELD_DURATION = 3000;   // ms
    private static final long SHIELD_COOLDOWN = 10000;  // ms

    // ===== STATE =====
    private boolean shieldActive = false;
    private long shieldActivationTime = 0;

    // ===== CONSTRUCTOR =====
    public Warrior(double x, double y) {
        super("Warrior", MAX_HEALTH, x, y, SPEED);
        this.currentWeapon = Weapon.createRifle();

        System.out.println("⚔ Warrior created at (" + x + ", " + y + ")");
    }


    @Override
    public String getColor() {
        return isShieldActive() ? SHIELD_COLOR : COLOR;
    }

    @Override
    public double getSize() {
        return SIZE;
    }


    @Override
    public void useSpecialAbility() {
        long now = System.currentTimeMillis();

        if (!isShieldOnCooldown(now)) {
            shieldActive = true;
            shieldActivationTime = now;
            System.out.println("🛡 " + name + " activated Shield!");
        } else {
            System.out.println("⏳ Shield cooldown: " +
                    getCooldownRemaining(now) / 1000 + "s");
        }
    }

    // ===== SHIELD LOGIC =====

    private boolean isShieldActive() {
        if (!shieldActive) return false;

        if (System.currentTimeMillis() - shieldActivationTime >= SHIELD_DURATION) {
            shieldActive = false;
            System.out.println("🛡 " + name + "'s shield expired");
            return false;
        }
        return true;
    }

    private boolean isShieldOnCooldown(long now) {
        return now - shieldActivationTime < SHIELD_COOLDOWN;
    }

    private long getCooldownRemaining(long now) {
        return SHIELD_COOLDOWN - (now - shieldActivationTime);
    }


    @Override
    public void takeDamage(double damage) {
        if (isShieldActive()) {
            damage *= 0.5;
            System.out.println("🛡 Shield reduced damage!");
        }
        super.takeDamage(damage);
    }



    public String getShieldStatus() {
        long now = System.currentTimeMillis();

        if (isShieldActive()) {
            return "🛡 ACTIVE (" +
                    (SHIELD_DURATION - (now - shieldActivationTime)) / 1000 + "s)";
        }

        if (!isShieldOnCooldown(now)) {
            return "✅ READY";
        }

        return "⏳ Cooldown (" + getCooldownRemaining(now) / 1000 + "s)";
    }

    @Override
    public String toString() {
        return "⚔ Warrior | Shield: " + getShieldStatus();
    }

    public String getDetailedDescription() {
        return """
                ⚔ WARRIOR
                ━━━━━━━━━━━━━━━━━━━━
                Role: Tank / Frontline
                Health: 150 HP
                Speed: 4.0
                Size: 45px
                Weapon: Rifle
                Special: Shield Bash
                 - 50% Damage Reduction
                 - Duration: 3s
                 - Cooldown: 10s
                ━━━━━━━━━━━━━━━━━━━━
                Strategy: Absorb damage and protect allies
                """;
    }
}
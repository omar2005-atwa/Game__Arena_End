public class Weapon {
    private String name;
    private double damage;
    private double projectileSpeed;
    private long cooldown;
    private String color;
    private double projectileSize;
    private String description;


    public Weapon(String name, double damage, double projectileSpeed,
                  long cooldown, String color, double projectileSize,
                  String description) {
        this.name = name;
        this.damage = damage;
        this.projectileSpeed = projectileSpeed;
        this.cooldown = cooldown;
        this.color = color;
        this.projectileSize = projectileSize;
        this.description = description;
    }


    public String getName() {
        return name;
    }


    public double getDamage() {
        return damage;
    }


    public double getProjectileSpeed() {
        return projectileSpeed;
    }


    public long getCooldown() {
        return cooldown;
    }


    public String getColor() {
        return color;
    }


    public double getProjectileSize() {
        return projectileSize;
    }


    public String getDescription() {
        return description;
    }


    public double getFireRate() {
        return 1000.0 / cooldown;
    }


    public double getDPS() {
        return damage * getFireRate();
    }



    public Projectile createProjectile(double startX, double startY) {
        return new Projectile(
                startX,
                startY,
                projectileSpeed,
                damage,
                color,
                projectileSize
        );
    }


    public static Weapon createPistol() {
        return new Weapon(
                "Pistol",
                10,
                8,
                400,
                "YELLOW",
                8,
                "Fast fire rate, low damage"
        );
    }


    public static Weapon createRifle() {
        return new Weapon(
                "Rifle",
                15,
                10,
                600,
                "ORANGE",
                10,
                "Balanced weapon, good accuracy"
        );
    }


    public static Weapon createCannon() {
        return new Weapon("Cannon", 35, 5, 1200, "RED", 15, "Slow but devastating damage"
        );
    }


    public static Weapon createLaser() {
        return new Weapon(
                "Laser",
                8,
                15,
                300,
                "CYAN",
                6,
                "Ultra fast, low damage"
        );
    }


    public static Weapon createMachineGun() {
        return new Weapon(
                "Machine Gun",
                6,
                12,
                200,
                "GOLD",
                7,
                "Rapid fire, spray and pray"
        );
    }


    public static Weapon createSniper() {
        return new Weapon(
                "Sniper",
                40,
                20,
                1500,
                "PURPLE",
                5,
                "One shot, one kill"
        );
    }


    public static Weapon createRocketLauncher() {
        return new Weapon("Rocket Launcher", 50, 7, 2000, "ORANGERED", 20, "Massive damage, slow reload"
        );
    }


    public static Weapon createPlasmaGun() {
        return new Weapon(
                "Plasma Gun",
                12,
                13,
                500,
                "LIME",
                12,
                "Energy-based weapon"
        );
    }

    public static Weapon createBow() {
        return new Weapon ("Bow",
                5,
                5,
                1500,
                "RED",
                15,
                "Slow damage");
    }


    public static Weapon[] getAllWeapons() {
        return new Weapon[] {
                createPistol(),
                createRifle(),
                createCannon(),
                createLaser(),
                createMachineGun(),
                createSniper(),
                createRocketLauncher(),
                createPlasmaGun(),
                createBow()
        };
    }


    public static String[] getAllWeaponNames() {
        Weapon[] weapons = getAllWeapons();
        String[] names = new String[weapons.length];
        for (int i = 0; i < weapons.length; i++) {
            names[i] = weapons[i].getName();
        }
        return names;
    }

    public static Weapon getWeaponByName(String name) {
        Weapon[] weapons = getAllWeapons();
        for (Weapon weapon : weapons) {
            if (weapon.getName().equalsIgnoreCase(name)) {
                return weapon;
            }
        }
        return createPistol();
    }

    public Weapon clone() {
        return new Weapon(
                this.name,
                this.damage,
                this.projectileSpeed,
                this.cooldown,
                this.color,
                this.projectileSize,
                this.description
        );
    }

    @Override
    public String toString() {
        return name + " (DMG: " + damage + ", CD: " + cooldown + "ms)";
    }
}
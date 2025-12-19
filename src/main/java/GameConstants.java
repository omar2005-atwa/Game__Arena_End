
public class GameConstants {

    public static final double SCREEN_WIDTH = 1000;
    public static final double SCREEN_HEIGHT = 600;
    public static final double MIDDLE_LINE = SCREEN_WIDTH / 2;
    public static final double TOP_BAR_HEIGHT = 100;

    // ====================================================================
    // GAME SETTINGS - إعدادات اللعبة
    // ====================================================================

    public static final double DEFAULT_PLAYER_SPEED = 5.0;
    public static final int FRAME_RATE = 60; // إطار في الثانية
    public static final double PROJECTILE_LIFETIME = 10.0; // ثواني

    // ====================================================================
    // PLAYER BOUNDARIES - حدود اللاعبين
    // ====================================================================

    // حدود اللاعب 1 (يسار)
    public static final double PLAYER1_MIN_X = 50;
    public static final double PLAYER1_MAX_X = MIDDLE_LINE - 50;

    // حدود اللاعب 2 (يمين)
    public static final double PLAYER2_MIN_X = MIDDLE_LINE + 50;
    public static final double PLAYER2_MAX_X = SCREEN_WIDTH - 50;

    // حدود رأسية مشتركة
    public static final double MIN_Y = 50;
    public static final double MAX_Y = SCREEN_HEIGHT - 50;

    // ====================================================================
    // COLORS - الألوان
    // ====================================================================

    public static final String COLOR_PLAYER1 = "#00ff88";
    public static final String COLOR_PLAYER2 = "#ff0088";
    public static final String COLOR_BACKGROUND = "#0f0c29";
    public static final String COLOR_GOLD = "#FFD700";
    public static final String COLOR_WHITE = "#FFFFFF";

    // ألوان الشخصيات
    public static final String COLOR_WARRIOR = "RED";
    public static final String COLOR_MAGE = "PURPLE";
    public static final String COLOR_ARCHER = "GREEN";

    // ألوان الأسلحة
    public static final String COLOR_PISTOL = "YELLOW";
    public static final String COLOR_RIFLE = "ORANGE";
    public static final String COLOR_CANNON = "RED";
    public static final String COLOR_LASER = "CYAN";

    // ====================================================================
    // CHARACTER STATS - إحصائيات الشخصيات
    // ====================================================================

    // Warrior
    public static final double WARRIOR_HEALTH = 150;
    public static final double WARRIOR_SPEED = 4.0;
    public static final double WARRIOR_SIZE = 45;

    // Mage
    public static final double MAGE_HEALTH = 80;
    public static final double MAGE_SPEED = 6.5;
    public static final double MAGE_SIZE = 35;
    public static final int MAGE_MAX_MANA = 100;

    // Archer
    public static final double ARCHER_HEALTH = 100;
    public static final double ARCHER_SPEED = 5.0;
    public static final double ARCHER_SIZE = 38;
    public static final int ARCHER_MAX_ARROWS = 3;

    // ====================================================================
    // WEAPON STATS - إحصائيات الأسلحة
    // ====================================================================

    // Pistol
    public static final double PISTOL_DAMAGE = 10;
    public static final double PISTOL_SPEED = 8;
    public static final long PISTOL_COOLDOWN = 400;

    // Rifle
    public static final double RIFLE_DAMAGE = 15;
    public static final double RIFLE_SPEED = 10;
    public static final long RIFLE_COOLDOWN = 600;

    // Cannon
    public static final double CANNON_DAMAGE = 35;
    public static final double CANNON_SPEED = 5;
    public static final long CANNON_COOLDOWN = 1200;

    // Laser
    public static final double LASER_DAMAGE = 8;
    public static final double LASER_SPEED = 15;
    public static final long LASER_COOLDOWN = 300;

    // ====================================================================
    // SPECIAL ABILITIES - القدرات الخاصة
    // ====================================================================

    // Warrior Shield
    public static final long WARRIOR_SHIELD_DURATION = 3000; // 3 ثواني
    public static final long WARRIOR_SHIELD_COOLDOWN = 10000; // 10 ثواني
    public static final double WARRIOR_SHIELD_REDUCTION = 0.5; // 50% تقليل ضرر

    // Mage Teleport
    public static final double MAGE_TELEPORT_DISTANCE = 150;
    public static final long MAGE_TELEPORT_COOLDOWN = 5000; // 5 ثواني
    public static final int MAGE_TELEPORT_COST = 30; // مانا

    // Archer Multi-Shot
    public static final long ARCHER_ARROW_REGEN = 3000; // 3 ثواني لكل سهم
    public static final double ARCHER_CRIT_CHANCE = 0.15; // 15%
    public static final double ARCHER_CRIT_MULTIPLIER = 2.0; // 2x ضرر

    // ====================================================================
    // CONTROL KEYS - أزرار التحكم
    // ====================================================================

    // Player 1
    public static final String P1_UP = "W";
    public static final String P1_DOWN = "S";
    public static final String P1_LEFT = "A";
    public static final String P1_RIGHT = "D";
    public static final String P1_SHOOT = "F";
    public static final String P1_SPECIAL = "G";

    // Player 2
    public static final String P2_UP = "UP";
    public static final String P2_DOWN = "DOWN";
    public static final String P2_LEFT = "LEFT";
    public static final String P2_RIGHT = "RIGHT";
    public static final String P2_SHOOT = "L";
    public static final String P2_SPECIAL = "K";

    // Weapon Switch Keys
    public static final String[] WEAPON_KEYS = {"DIGIT1", "DIGIT2", "DIGIT3", "DIGIT4"};

    // ====================================================================
    // UI SETTINGS - إعدادات الواجهة
    // ====================================================================

    public static final String FONT_TITLE = "Impact";
    public static final String FONT_MAIN = "Arial";
    public static final String FONT_DIGITAL = "Monospaced";

    public static final int FONT_SIZE_TITLE = 52;
    public static final int FONT_SIZE_LARGE = 28;
    public static final int FONT_SIZE_MEDIUM = 18;
    public static final int FONT_SIZE_SMALL = 14;

    // ====================================================================
    // GAME MESSAGES - رسائل اللعبة
    // ====================================================================

    public static final String MSG_GAME_TITLE = "⚔️ BATTLE ARENA ⚔️";
    public static final String MSG_CHOOSE_FIGHTER = "Choose Your Fighter";
    public static final String MSG_PLAYER1_WINS = "PLAYER 1 WINS! 🎉";
    public static final String MSG_PLAYER2_WINS = "PLAYER 2 WINS! 🎉";
    public static final String MSG_GAME_OVER = "🏆 GAME OVER 🏆";

    // ====================================================================
    // SOUND SETTINGS - إعدادات الصوت (للتطوير المستقبلي)
    // ====================================================================

    public static final boolean SOUND_ENABLED = true;
    public static final double SOUND_VOLUME = 0.7;

    // ====================================================================
    // DEBUG SETTINGS - إعدادات التصحيح
    // ====================================================================

    public static final boolean DEBUG_MODE = false;
    public static final boolean SHOW_HITBOXES = false;
    public static final boolean SHOW_FPS = false;

    // ====================================================================
    // DIFFICULTY SETTINGS - إعدادات الصعوبة
    // ====================================================================

    public static final double EASY_DAMAGE_MULTIPLIER = 0.7;
    public static final double NORMAL_DAMAGE_MULTIPLIER = 1.0;
    public static final double HARD_DAMAGE_MULTIPLIER = 1.5;

    // ====================================================================
    // UTILITY METHODS - دوال مساعدة
    // ====================================================================

    /**
     * منع إنشاء instance من هذا الكلاس
     */
    private GameConstants() {
        throw new AssertionError("Cannot instantiate constants class");
    }

    /**
     * طباعة جميع الثوابت (للتصحيح)
     */
    public static void printAllConstants() {
        System.out.println("═══════════════════════════════════════");
        System.out.println("        GAME CONSTANTS");
        System.out.println("═══════════════════════════════════════");
        System.out.println("Screen: " + SCREEN_WIDTH + "x" + SCREEN_HEIGHT);
        System.out.println("Warrior HP: " + WARRIOR_HEALTH);
        System.out.println("Mage HP: " + MAGE_HEALTH);
        System.out.println("Archer HP: " + ARCHER_HEALTH);
        System.out.println("═══════════════════════════════════════");
    }
}








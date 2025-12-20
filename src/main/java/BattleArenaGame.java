import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.animation.AnimationTimer;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
public class BattleArenaGame extends Application {

    private static final double SCREEN_WIDTH = 1000;
    private static final double SCREEN_HEIGHT = 600;
    private static final double TOP_BAR_HEIGHT = 100;

    private Fighter player1;
    private Fighter player2;
    private ArrayList<Projectile> projectiles = new ArrayList<>();
    private Set<String> pressedKeys = new HashSet<>();

    private Pane gamePane;
    private Circle player1Circle;
    private Circle player2Circle;
    private ProgressBar player1HealthBar;
    private ProgressBar player2HealthBar;
    private Label player1HealthLabel;
    private Label player2HealthLabel;
    private Label player1WeaponLabel;
    private Label player2WeaponLabel;
    private Label player1SpecialLabel;
    private Label player2SpecialLabel;
    private Label timerLabel;
    private AnimationTimer gameLoop;

    private ComboBox<String> player1Choice;
    private ComboBox<String> player2Choice;
    private ComboBox<String> player1WeaponChoice;
    private ComboBox<String> player2WeaponChoice;
    private ComboBox<String> gameModeChoice;

    private int player1Wins = 0;
    private int player2Wins = 0;
    private long gameStartTime;
    private Stage primaryStage;
    private boolean gameEnded = false;

    private boolean fullArenaMode = true;
    private Line dividerLine;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        this.primaryStage = stage;
        primaryStage.setTitle(" Battle Arena Game ");

        Scene selectionScene = createSelectionScene();
        primaryStage.setScene(selectionScene);
        primaryStage.setResizable(false);
        primaryStage.show();

        System.out.println(" Game started successfully!");
    }

    private Scene createSelectionScene() {
        VBox root = new VBox(15);
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-background-color: linear-gradient(to bottom, #0f0c29, #302b63, #24243e);");
        root.setPrefSize(SCREEN_WIDTH, SCREEN_HEIGHT + TOP_BAR_HEIGHT);

        Label titleLabel = new Label("⚔️ BATTLE ARENA ⚔️");
        titleLabel.setFont(Font.font("Impact", FontWeight.BOLD, 48));
        titleLabel.setStyle("-fx-text-fill: #FFD700; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.8), 10, 0.5, 0, 3);");

        Label subtitleLabel = new Label("Choose Your Fighter");
        subtitleLabel.setStyle("-fx-text-fill: #ffffff; -fx-font-size: 18px;");

        VBox gameModeBox = createGameModeBox();

        HBox selectionsBox = new HBox(60);
        selectionsBox.setAlignment(Pos.CENTER);

        VBox player1Box = createPlayerSelectionBox("PLAYER 1", true);

        Label vsLabel = new Label("VS");
        vsLabel.setFont(Font.font("Impact", FontWeight.BOLD, 40));
        vsLabel.setStyle("-fx-text-fill: #FFD700;");

        VBox player2Box = createPlayerSelectionBox("PLAYER 2", false);

        selectionsBox.getChildren().addAll(player1Box, vsLabel, player2Box);

        Button startButton = new Button("⚔️ START BATTLE ⚔️");
        startButton.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        startButton.setStyle(
                "-fx-background-color: linear-gradient(to bottom, #FFD700, #FFA500); " +
                        "-fx-text-fill: #000000; " +
                        "-fx-padding: 12 40 12 40; " +
                        "-fx-background-radius: 10; " +
                        "-fx-cursor: hand;"
        );

        startButton.setOnAction(e -> {
            fullArenaMode = gameModeChoice.getValue().contains("Full");

            Scene gameScene = createGameScene();
            primaryStage.setScene(gameScene);
            startGame();
        });

        VBox controlsBox = createControlsBox();

        root.getChildren().addAll(titleLabel, subtitleLabel, gameModeBox, selectionsBox, startButton, controlsBox);

        Scene scene = new Scene(root, SCREEN_WIDTH, SCREEN_HEIGHT + TOP_BAR_HEIGHT);

        try {
            scene.getStylesheets().add(getClass().getResource("/game-style.css").toExternalForm());
        } catch (Exception e) {
            System.err.println("❌ ERROR: Could not load game-style.css.");
        }

        return scene;
    }

    private VBox createGameModeBox() {
        VBox box = new VBox(8);
        box.setAlignment(Pos.CENTER);
        box.setStyle(
                "-fx-background-color: rgba(255, 215, 0, 0.15); " +
                        "-fx-background-radius: 12; " +
                        "-fx-padding: 15; " +
                        "-fx-border-color: #FFD700; " +
                        "-fx-border-width: 2; " +
                        "-fx-border-radius: 12;"
        );

        Label modeLabel = new Label("🎮 Game Mode");
        modeLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        modeLabel.setStyle("-fx-text-fill: #FFD700;");

        gameModeChoice = new ComboBox<>();
        gameModeChoice.getItems().addAll(
                "🌍 Full Arena",
                "⚡ Split Arena"
        );
        gameModeChoice.setValue("🌍 Full Arena");
        gameModeChoice.setPrefWidth(250);

        Label descLabel = new Label("Full Arena: Move anywhere | Split Arena: Each player in their half");
        descLabel.setStyle("-fx-text-fill: #CCCCCC; -fx-font-size: 10px;");
        descLabel.setWrapText(true);
        descLabel.setMaxWidth(300);
        descLabel.setAlignment(Pos.CENTER);

        box.getChildren().addAll(modeLabel, gameModeChoice, descLabel);

        return box;
    }

    private VBox createPlayerSelectionBox(String playerName, boolean isPlayer1) {
        VBox box = new VBox(12);
        box.setAlignment(Pos.CENTER);
        box.setStyle(
                "-fx-background-color: rgba(255, 255, 255, 0.1); " +
                        "-fx-background-radius: 15; " +
                        "-fx-padding: 20; " +
                        "-fx-border-color: " + (isPlayer1 ? "#00ff88" : "#ff0088") + "; " +
                        "-fx-border-width: 3; " +
                        "-fx-border-radius: 15;"
        );

        Label nameLabel = new Label(playerName);
        nameLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        nameLabel.setStyle("-fx-text-fill: " + (isPlayer1 ? "#00ff88" : "#ff0088") + ";");

        Label charLabel = new Label("Character:");
        charLabel.setStyle("-fx-text-fill: white; -fx-font-size: 14;");

        ComboBox<String> charChoice = new ComboBox<>();
        charChoice.getItems().addAll("Warrior ⚔️", "Mage 🔮", "Archer 🏹");
        charChoice.setValue(isPlayer1 ? "Warrior ⚔️" : "Archer 🏹");
        charChoice.setPrefWidth(180);

        Label weaponLabel = new Label("Starting Weapon:");
        weaponLabel.setStyle("-fx-text-fill: white; -fx-font-size: 14;");

        ComboBox<String> weaponChoice = new ComboBox<>();
        weaponChoice.getItems().addAll(Weapon.getAllWeaponNames());
        weaponChoice.setValue("Pistol");
        weaponChoice.setPrefWidth(180);

        if (isPlayer1) {
            player1Choice = charChoice;
            player1WeaponChoice = weaponChoice;
        } else {
            player2Choice = charChoice;
            player2WeaponChoice = weaponChoice;
        }

        box.getChildren().addAll(nameLabel, charLabel, charChoice, weaponLabel, weaponChoice);

        return box;
    }

    private VBox createControlsBox() {
        VBox box = new VBox(8);
        box.setAlignment(Pos.CENTER);
        box.setStyle("-fx-background-color: rgba(0, 0, 0, 0.5); -fx-padding: 20; -fx-background-radius: 15;");

        Label title = new Label("🎮 CONTROLS 🎮");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        title.setStyle("-fx-text-fill: #FFD700;");

        Label p1 = new Label("Player 1: WASD to move | F to shoot | G for special ability");
        p1.setStyle("-fx-text-fill: #00ff88; -fx-font-size: 14;");

        Label p2 = new Label("Player 2: Arrow Keys to move | L to shoot | K for special ability");
        p2.setStyle("-fx-text-fill: #ff0088; -fx-font-size: 14;");

        Label weapon = new Label("Switch Weapon: P1 (1-4) | P2 (7-0)");
        weapon.setStyle("-fx-text-fill: white; -fx-font-size: 12;");

        box.getChildren().addAll(title, p1, p2, weapon);

        return box;
    }

    private Scene createGameScene() {
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #0a0a0a;");

        gamePane = new Pane();
        gamePane.setPrefSize(SCREEN_WIDTH, SCREEN_HEIGHT);
        gamePane.setStyle("-fx-background-color: linear-gradient(to bottom, #1a1a2e, #16213e);");

        // شبكة الخلفية
        for (int i = 0; i < 20; i++) {
            Line gridLine = new Line(i * 50, 0, i * 50, SCREEN_HEIGHT);
            gridLine.setStroke(Color.rgb(255, 255, 255, 0.05));
            gamePane.getChildren().add(gridLine);
        }

        // إضافة خط الفاصل إذا كان الوضع نصف ملعب
        if (!fullArenaMode) {
            dividerLine = new Line(SCREEN_WIDTH / 2, 0, SCREEN_WIDTH / 2, SCREEN_HEIGHT);
            dividerLine.setStroke(Color.rgb(255, 215, 0, 0.6));
            dividerLine.setStrokeWidth(3);
            dividerLine.getStrokeDashArray().addAll(15d, 10d);
            gamePane.getChildren().add(dividerLine);
        }

        HBox topBar = createTopBar();

        root.setTop(topBar);
        root.setCenter(gamePane);

        createPlayers();

        Scene scene = new Scene(root, SCREEN_WIDTH, SCREEN_HEIGHT + TOP_BAR_HEIGHT);

        try {
            scene.getStylesheets().add(getClass().getResource("/game-style.css").toExternalForm());
        } catch (Exception e) {
            System.err.println(" ERROR: Could not load game-style.css.");
        }

        scene.setOnKeyPressed(e -> pressedKeys.add(e.getCode().toString()));
        scene.setOnKeyReleased(e -> pressedKeys.remove(e.getCode().toString()));

        return scene;
    }

    private HBox createTopBar() {
        HBox topBar = new HBox(20);
        topBar.setAlignment(Pos.CENTER);
        topBar.setStyle(
                "-fx-background-color: linear-gradient(to bottom, #0f0c29, #302b63); " +
                        "-fx-padding: 15; " +
                        "-fx-border-color: #FFD700; " +
                        "-fx-border-width: 0 0 3 0;"
        );
        topBar.setPrefHeight(TOP_BAR_HEIGHT);

        VBox p1Info = createPlayerInfoBox(true);

        VBox centerInfo = new VBox(5);
        centerInfo.setAlignment(Pos.CENTER);

        Label vsLabel = new Label("⚔️ VS ⚔️");
        vsLabel.setFont(Font.font("Impact", FontWeight.BOLD, 28));
        vsLabel.setStyle("-fx-text-fill: #FFD700;");

        timerLabel = new Label("00:00");
        timerLabel.setFont(Font.font("Monospaced", FontWeight.BOLD, 24));
        timerLabel.setStyle("-fx-text-fill: white;");

        Label scoreLabel = new Label(player1Wins + " - " + player2Wins);
        scoreLabel.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        scoreLabel.setStyle("-fx-text-fill: #FFD700;");

        centerInfo.getChildren().addAll(vsLabel, timerLabel, scoreLabel);

        VBox p2Info = createPlayerInfoBox(false);

        topBar.getChildren().addAll(p1Info, centerInfo, p2Info);

        return topBar;
    }

    private VBox createPlayerInfoBox(boolean isPlayer1) {
        VBox info = new VBox(5);
        info.setAlignment(Pos.CENTER);
        info.setPrefWidth(350);

        Label nameLabel = new Label(isPlayer1 ? "PLAYER 1" : "PLAYER 2");
        nameLabel.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        nameLabel.setStyle("-fx-text-fill: " + (isPlayer1 ? "#00ff88" : "#ff0088") + ";");

        ProgressBar healthBar = new ProgressBar(1.0);
        healthBar.setPrefWidth(300);
        healthBar.setPrefHeight(20);
        healthBar.setStyle("-fx-accent: #00ff88;");

        Label healthLabel = new Label("100 / 100 HP");
        healthLabel.setStyle("-fx-text-fill: white; -fx-font-size: 14;");

        Label weaponLabel = new Label("🔫 Weapon: Pistol");
        weaponLabel.setStyle("-fx-text-fill: #FFD700; -fx-font-size: 12;");

        Label specialLabel = new Label("✨ Special: Ready");
        specialLabel.setStyle("-fx-text-fill: #FFFF00; -fx-font-size: 11;");

        if (isPlayer1) {
            player1HealthBar = healthBar;
            player1HealthLabel = healthLabel;
            player1WeaponLabel = weaponLabel;
            player1SpecialLabel = specialLabel;
        } else {
            player2HealthBar = healthBar;
            player2HealthLabel = healthLabel;
            player2WeaponLabel = weaponLabel;
            player2SpecialLabel = specialLabel;
        }

        info.getChildren().addAll(nameLabel, healthBar, healthLabel, weaponLabel, specialLabel);

        return info;
    }

    private void createPlayers() {
        String p1Type = player1Choice.getValue().split(" ")[0];
        String p2Type = player2Choice.getValue().split(" ")[0];

        switch (p1Type) {
            case "Warrior":
                player1 = new Warrior(150, SCREEN_HEIGHT / 2);
                break;
            case "Mage":
                player1 = new Mage(150, SCREEN_HEIGHT / 2);
                break;
            case "Archer":
                player1 = new Archer(150, SCREEN_HEIGHT / 2);
                break;
            default:
                player1 = new Warrior(150, SCREEN_HEIGHT / 2);
        }

        player1.setCurrentWeapon(Weapon.getWeaponByName(player1WeaponChoice.getValue()));

        switch (p2Type) {
            case "Warrior":
                player2 = new Warrior(SCREEN_WIDTH - 150, SCREEN_HEIGHT / 2);
                break;
            case "Mage":
                player2 = new Mage(SCREEN_WIDTH - 150, SCREEN_HEIGHT / 2);
                break;
            case "Archer":
                player2 = new Archer(SCREEN_WIDTH - 150, SCREEN_HEIGHT / 2);
                break;
            default:
                player2 = new Archer(SCREEN_WIDTH - 150, SCREEN_HEIGHT / 2);
        }

        player2.setCurrentWeapon(Weapon.getWeaponByName(player2WeaponChoice.getValue()));

        player1Circle = new Circle(player1.getSize() / 2);
        player1Circle.setFill(Color.web(player1.getColor()));
        player1Circle.setStroke(Color.WHITE);
        player1Circle.setStrokeWidth(3);

        player2Circle = new Circle(player2.getSize() / 2);
        player2Circle.setFill(Color.web(player2.getColor()));
        player2Circle.setStroke(Color.WHITE);
        player2Circle.setStrokeWidth(3);

        gamePane.getChildren().addAll(player1Circle, player2Circle);

        System.out.println("✅ Players created - Mode: " + (fullArenaMode ? "Full Arena" : "Split Arena"));
    }

    private void startGame() {
        gameStartTime = System.currentTimeMillis();
        projectiles.clear();
        gameEnded = false;

        gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (!gameEnded) {
                    update();
                    render();
                    checkGameOver();
                }
            }
        };
        gameLoop.start();

        System.out.println("🎮 Game started! Good luck!");
    }

    private void update() {
        handlePlayer1Input();
        handlePlayer2Input();
        updateProjectiles();
        updateSpecialAbilities();
        updateTimer();
    }

    private void handlePlayer1Input() {
        double speed = player1.getSpeed();
        double minX = 50;
        double maxX = fullArenaMode ? SCREEN_WIDTH - 50 : (SCREEN_WIDTH / 2) - 50;
        double minY = 50;
        double maxY = SCREEN_HEIGHT - 50;

        if (pressedKeys.contains("W")) {
            player1.move(0, -speed, minX, maxX, minY, maxY);
        }
        if (pressedKeys.contains("S")) {
            player1.move(0, speed, minX, maxX, minY, maxY);
        }
        if (pressedKeys.contains("A")) {
            player1.move(-speed, 0, minX, maxX, minY, maxY);
        }
        if (pressedKeys.contains("D")) {
            player1.move(speed, 0, minX, maxX, minY, maxY);
        }

        if (pressedKeys.contains("F")) {
            try {
                Projectile p = player1.shoot();
                if (p != null) {
                    projectiles.add(p);
                }
            } catch (Exception e) {
                System.err.println("Error shooting: " + e.getMessage());
            }
        }

        if (pressedKeys.contains("G")) {
            player1.useSpecialAbility();
            pressedKeys.remove("G");
        }

        handleWeaponSwitch(player1, true);
    }

    private void handlePlayer2Input() {
        double speed = player2.getSpeed();
        double minX = fullArenaMode ? 50 : (SCREEN_WIDTH / 2) + 50;
        double maxX = SCREEN_WIDTH - 50;
        double minY = 50;
        double maxY = SCREEN_HEIGHT - 50;

        if (pressedKeys.contains("UP")) {
            player2.move(0, -speed, minX, maxX, minY, maxY);
        }
        if (pressedKeys.contains("DOWN")) {
            player2.move(0, speed, minX, maxX, minY, maxY);
        }
        if (pressedKeys.contains("LEFT")) {
            player2.move(-speed, 0, minX, maxX, minY, maxY);
        }
        if (pressedKeys.contains("RIGHT")) {
            player2.move(speed, 0, minX, maxX, minY, maxY);
        }

        if (pressedKeys.contains("L")) {
            try {
                Projectile p = player2.shoot();
                if (p != null) {
                    p.reverseDirection();
                    projectiles.add(p);
                }
            } catch (Exception e) {
                System.err.println("Error shooting: " + e.getMessage());
            }
        }

        if (pressedKeys.contains("K")) {
            player2.useSpecialAbility();
            pressedKeys.remove("K");
        }

        handleWeaponSwitch(player2, false);
    }

    private void handleWeaponSwitch(Fighter player, boolean isPlayer1) {
        if (isPlayer1) {
            if (pressedKeys.contains("DIGIT1")) {
                player.setCurrentWeapon(Weapon.createPistol());
                pressedKeys.remove("DIGIT1");
            } else if (pressedKeys.contains("DIGIT2")) {
                player.setCurrentWeapon(Weapon.createRifle());
                pressedKeys.remove("DIGIT2");
            } else if (pressedKeys.contains("DIGIT3")) {
                player.setCurrentWeapon(Weapon.createCannon());
                pressedKeys.remove("DIGIT3");
            } else if (pressedKeys.contains("DIGIT4")) {
                player.setCurrentWeapon(Weapon.createLaser());
                pressedKeys.remove("DIGIT4");
            }
        } else {
            if (pressedKeys.contains("DIGIT7")) {
                player.setCurrentWeapon(Weapon.createPistol());
                pressedKeys.remove("DIGIT7");
            } else if (pressedKeys.contains("DIGIT8")) {
                player.setCurrentWeapon(Weapon.createRifle());
                pressedKeys.remove("DIGIT8");
            } else if (pressedKeys.contains("DIGIT9")) {
                player.setCurrentWeapon(Weapon.createCannon());
                pressedKeys.remove("DIGIT9");
            } else if (pressedKeys.contains("DIGIT0")) {
                player.setCurrentWeapon(Weapon.createLaser());
                pressedKeys.remove("DIGIT0");
            }
        }
    }

    private void updateProjectiles() {
        synchronized (projectiles) {
            Iterator<Projectile> iterator = projectiles.iterator();
            while (iterator.hasNext()) {
                Projectile proj = iterator.next();

                try {
                    proj.move();

                    if (proj.getSpeedX() < 0 && proj.checkCollision(player1)) {
                        player1.takeDamage(proj.getDamage());
                        iterator.remove();
                        continue;
                    }

                    if (proj.getSpeedX() > 0 && proj.checkCollision(player2)) {
                        player2.takeDamage(proj.getDamage());
                        iterator.remove();
                        continue;
                    }

                    if (proj.isOutOfBounds(SCREEN_WIDTH, SCREEN_HEIGHT)) {
                        iterator.remove();
                    }
                } catch (Exception e) {
                    System.err.println("Error updating projectile: " + e.getMessage());
                    iterator.remove();
                }
            }
        }
    }

    private void updateSpecialAbilities() {
        if (player1 instanceof Mage) {
            ((Mage) player1).regenerateMana();
        }
        if (player2 instanceof Mage) {
            ((Mage) player2).regenerateMana();
        }

        if (player1 instanceof Archer) {
            ((Archer) player1).regenerateArrows();
        }
        if (player2 instanceof Archer) {
            ((Archer) player2).regenerateArrows();
        }
    }

    private void updateTimer() {
        long elapsed = (System.currentTimeMillis() - gameStartTime) / 1000;
        long minutes = elapsed / 60;
        long seconds = elapsed % 60;
        timerLabel.setText(String.format("%02d:%02d", minutes, seconds));
    }

    private void render() {
        player1Circle.setCenterX(player1.getX());
        player1Circle.setCenterY(player1.getY());
        player1Circle.setFill(Color.web(player1.getColor()));

        player2Circle.setCenterX(player2.getX());
        player2Circle.setCenterY(player2.getY());
        player2Circle.setFill(Color.web(player2.getColor()));

        gamePane.getChildren().removeIf(node ->
                node instanceof Circle && node != player1Circle && node != player2Circle
        );

        synchronized (projectiles) {
            for (Projectile proj : projectiles) {
                try {
                    Circle projCircle = new Circle(proj.getX(), proj.getY(), proj.getSize() / 2);
                    projCircle.setFill(Color.web(proj.getColor()));
                    projCircle.setStroke(Color.WHITE);
                    projCircle.setStrokeWidth(1);
                    gamePane.getChildren().add(projCircle);
                } catch (Exception e) {
                }
            }
        }

        updateUI();
    }

    private void updateUI() {
        player1HealthLabel.setText(String.format("%.0f / %.0f HP",
                player1.getHealth(), player1.getMaxHealth()));
        double p1Progress = player1.getHealthPercentage();
        player1HealthBar.setProgress(p1Progress);
        applyHealthBarColor(player1HealthBar, p1Progress);

        player1WeaponLabel.setText("🔫 " + player1.getCurrentWeapon().getName());

        if (player1 instanceof Mage) {
            Mage mage = (Mage) player1;
            player1SpecialLabel.setText(String.format("✨ Mana: %d/%d",
                    mage.getManaPoints(), mage.getMaxMana()));
        } else if (player1 instanceof Archer) {
            Archer archer = (Archer) player1;
            player1SpecialLabel.setText(archer.getArrowsStatus());
        } else if (player1 instanceof Warrior) {
            Warrior warrior = (Warrior) player1;
            player1SpecialLabel.setText("🛡️ " + warrior.getShieldStatus());
        }

        player2HealthLabel.setText(String.format("%.0f / %.0f HP",
                player2.getHealth(), player2.getMaxHealth()));
        double p2Progress = player2.getHealthPercentage();
        player2HealthBar.setProgress(p2Progress);
        applyHealthBarColor(player2HealthBar, p2Progress);

        player2WeaponLabel.setText("🔫 " + player2.getCurrentWeapon().getName());

        if (player2 instanceof Mage) {
            Mage mage = (Mage) player2;
            player2SpecialLabel.setText(String.format("✨ Mana: %d/%d",
                    mage.getManaPoints(), mage.getMaxMana()));
        } else if (player2 instanceof Archer) {
            Archer archer = (Archer) player2;
            player2SpecialLabel.setText(archer.getArrowsStatus());
        } else if (player2 instanceof Warrior) {
            Warrior warrior = (Warrior) player2;
            player2SpecialLabel.setText("🛡️ " + warrior.getShieldStatus());
        }
    }

    private void applyHealthBarColor(ProgressBar bar, double progress) {
        String color;

        if (progress > 0.6) {
            color = "#00FF88";
        } else if (progress > 0.3) {
            color = "#FFD700";
        } else {
            color = "#FF4B2B";
        }

        bar.setStyle("-fx-accent: " + color + ";");
    }

    private void checkGameOver() {
        if (gameEnded) return;

        if (!player1.isAlive() || !player2.isAlive()) {
            gameEnded = true;
            gameLoop.stop();

            String winner;

            if (player1.isAlive()) {
                winner = "PLAYER 1 WINS!";
                player1Wins++;
            } else if (player2.isAlive()) {
                winner = "PLAYER 2 WINS!";
                player2Wins++;
            } else {
                winner = "DRAW! 🤝";
            }

            System.out.println("🏆 " + winner);

            Platform.runLater(() -> showGameOverDialog(winner));
        }
    }

    private void showGameOverDialog(String winner) {

        Stage dialog = new Stage();
        dialog.initOwner(primaryStage);
        dialog.setTitle("🏆 GAME OVER 🏆");
        dialog.setResizable(false);

        VBox root = new VBox(20);
        root.setAlignment(Pos.CENTER);
        root.setStyle(
                "-fx-background-color: linear-gradient(to bottom, #0f0c29, #302b63); " +
                        "-fx-padding: 30;"
        );

        Label title = new Label("GAME OVER");
        title.setFont(Font.font("Impact", FontWeight.BOLD, 42));
        title.setTextFill(Color.GOLD);

        Label winnerLabel = new Label(winner);
        winnerLabel.setFont(Font.font("Arial", FontWeight.BOLD, 26));

        if (winner.contains("PLAYER 1")) {
            winnerLabel.setTextFill(Color.web("#00ff88"));
        } else if (winner.contains("PLAYER 2")) {
            winnerLabel.setTextFill(Color.web("#ff0088"));
        } else {
            winnerLabel.setTextFill(Color.WHITE);
        }

        long duration = (System.currentTimeMillis() - gameStartTime) / 1000;

        Label stats = new Label(
                String.format(
                        "⏱ Time: %d:%02d\n\n" +
                                "📊 SCORE\n" +
                                "Player 1: %d Wins\n" +
                                "Player 2: %d Wins",
                        duration / 60, duration % 60,
                        player1Wins, player2Wins
                )
        );

        stats.setFont(Font.font("Monospaced", 16));
        stats.setTextFill(Color.WHITE);
        stats.setAlignment(Pos.CENTER);

        Button playAgain = createGameOverButton("🔄 PLAY AGAIN", "#00ff88");
        Button menu = createGameOverButton("🏠 MAIN MENU", "#FFD700");
        Button exit = createGameOverButton("🚪 EXIT", "#ff4b2b");

        playAgain.setOnAction(e -> {
            dialog.close();
            startGame();
        });

        menu.setOnAction(e -> {
            dialog.close();
            primaryStage.setScene(createSelectionScene());
        });

        exit.setOnAction(e -> {
            Platform.exit();
            System.exit(0);
        });

        HBox buttons = new HBox(15, playAgain, menu, exit);
        buttons.setAlignment(Pos.CENTER);

        root.getChildren().addAll(title, winnerLabel, stats, buttons);

        Scene scene = new Scene(root, 420, 420);
        dialog.setScene(scene);
        dialog.show();
    }
    // ================= GAME OVER BUTTON =================
    private Button createGameOverButton(String text, String color) {
        Button btn = new Button(text);
        btn.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        btn.setStyle(
                "-fx-background-color: " + color + ";" +
                        "-fx-text-fill: black;" +
                        "-fx-padding: 10 18 10 18;" +
                        "-fx-background-radius: 8;" +
                        "-fx-cursor: hand;"
        );
        return btn;
    }


}
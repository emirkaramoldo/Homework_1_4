import java.util.Objects;
import java.util.Random;

public class Main {
    public static int bossHealth = 700;
    public static int bossDamage = 100;
    public static String bossDefence;
    public static int[] heroesHealth = {270, 260, 250, 700, 200, 300, 230, 400};
    public static int[] heroesDamage = {15, 20, 25, 5, 10, 15, 5, 0};
    public static String[] heroesAttackType = {"Physical", "Magical", "Kinetic", "Golem", "Lucky", "Medic", "Thor", "Witcher"};
    public static int roundNumber = 0;

    public static void main(String[] args) {
        printStatistics();

        while (!isGameFinished()) {
            playRound();
        }
    }

    public static void chooseBossDefence() {
        Random random = new Random();
        int randomIndex = random.nextInt(heroesAttackType.length);
        bossDefence = heroesAttackType[randomIndex];
    }

    public static void playRound() {
        roundNumber++;
        chooseBossDefence();
        printBossDamage();
        bossHits();
        heroesHit();
        missLucky();
        golemTakeDamage();
        medicHealing();
        printStatistics();
    }

    public static void bossHits() {
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[i] > 0) {
                heroesHealth[i] = Math.max(heroesHealth[i] - bossDamage, 0);
            }
        }
    }

    public static void heroesHit() {
        for (int i = 0; i < heroesDamage.length; i++) {
            if (heroesHealth[i] > 0 && bossHealth > 0 && heroesDamage[i] > 0) {
                int damage = heroesDamage[i];
                if (Objects.equals(heroesAttackType[i], bossDefence)) {
                    Random random = new Random();
                    int coeff = random.nextInt(9) + 2;
                    damage = heroesDamage[i] * coeff;
                    System.out.println("Critical damage: " + damage);
                }
                bossHealth = Math.max(bossHealth - damage, 0);
            }
        }
    }

    public static void medicHealing() {
        Random random = new Random();
        int randomHealth = random.nextInt(40 - 10) + 10;
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[i] > 0 && heroesHealth[i] < 100 && heroesHealth[5] > 0) {
                heroesHealth[i] += randomHealth;
                System.out.println("Медик вылечил героя на " + randomHealth);
                break;
            }
        }
    }

    public static void golemTakeDamage() {
        int takeDamage = bossDamage / 5;
        int aliveHeroes = 0;
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[i] > 0 && heroesHealth[3] > 0) {
                aliveHeroes++;
                heroesHealth[i] += takeDamage;
                heroesHealth[3] -= bossDamage - (takeDamage * aliveHeroes);
                if (heroesHealth[3] < 0) {
                    heroesHealth[3] = 0;
                }
            }
        }
        System.out.println("Голем поглотил " + (takeDamage * aliveHeroes));
    }

    public static void missLucky() {
        Random random = new Random();
        boolean luck = random.nextBoolean();
        if (luck && heroesHealth[4] > 0) {
            heroesHealth[4] += bossDamage;
            System.out.println("Везучий увернулся от атаки");
        }
    }



    public static boolean isGameFinished() {
        if (bossHealth <= 0) {
            System.out.println("Heroes won!!!");
            return true;
        }

        boolean allHeroesDead = true;
        for (int j : heroesHealth) {
            if (j > 0) {
                allHeroesDead = false;
                break;
            }
        }
        if (allHeroesDead) {
            System.out.println("Boss won!!!");
        }
        return allHeroesDead;
    }

    public static void printStatistics() {
        System.out.println("ROUND " + roundNumber + " -------------");
        System.out.println("Boss health: " + bossHealth + " damage: " +
                bossDamage + " defence: " +
                (bossDefence == null ? "No defence" : bossDefence));
        for (int i = 0; i < heroesHealth.length; i++) {
            System.out.println(heroesAttackType[i] +
                    " health: " + heroesHealth[i] + " damage: " + heroesDamage[i]);
        }
    }

    public static void printBossDamage() {
        System.out.println("Урон Босса " + bossDamage);
    }
}
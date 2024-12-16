import OSPRNG.TriangularRNG;
import OSPRNG.*;

public class Main {
    public static void main(String[] args) {
        priklad4C();
    }

    public static void priklad1() {
        final int pocetPokusov = 1000000;

        TriangularRNG genCenaNovin = new TriangularRNG(0.25, 0.6, 0.95);
        UniformContinuousRNG genDlzkaPredajaCas = new UniformContinuousRNG(250., 420.);

        double cenaNakupna = 0.15;
        double cenaVykupna = 0.15*0.65;

        //Hodnoty z generatora
        double cenaPredajna = 0;
        double casPredaja = 0;

        double najvacsiZisk = 0;
        int indexPocetBalikov = 0;



        for (int j = 1; j <= 20; j++) { //experimenty
            double pocetBalikov = j;
            double vysledokCelkovy = 0;

            for (int i = 0; i < pocetPokusov; i++) { //replikacie
                casPredaja = genDlzkaPredajaCas.sample();
                cenaPredajna = genCenaNovin.sample();
                double pocetPredanychKusov = Math.min(Math.floor(casPredaja / 2.7) + 1, pocetBalikov * 10);

                double vysledok = (pocetPredanychKusov * cenaPredajna)
                        + ((pocetBalikov * 10 - pocetPredanychKusov) * cenaVykupna)
                        - ((pocetBalikov * 10) * cenaNakupna);

                vysledokCelkovy += vysledok;
            }
            double priemerVysledku = vysledokCelkovy / pocetPokusov;
            if (priemerVysledku > najvacsiZisk) {
                najvacsiZisk = priemerVysledku;
            }
            System.out.println("Pocet predachych balikov: " + j + " zisk: " + priemerVysledku);
        }
        System.out.println("Najvacsi zisk: "+ najvacsiZisk);
    }

    public static void priklad2() {
        final int pocetPokusov = 1000000;

        //Veci jasne zo zadania
        int maxPocetA = 70;
        int maxPocetB = 90;
        TriangularRNG genNakladyA = new TriangularRNG(1., 1.75, 2.5);
        TriangularRNG genNakladyB = new TriangularRNG(0.7, 1.2, 1.7);
        UniformDiscreteRNG genDopytA = new UniformDiscreteRNG(40, 79);
        UniformDiscreteRNG genDopytB = new UniformDiscreteRNG(66, 154);

        int cenaPredajnaA = 3;
        int cenaPredajnaB = 2;
        //Premenne
        double nakladyA = 0;
        double nakladyB = 0;
        int dopytA = 0;
        int dopytB = 0;

        double ziskA = 0;
        double ziskB = 0;

        double celkovyVysledokA = 0;
        double celkovyVysledokB = 0;

        int predaneA, predaneB;

        for (int i = 0; i < pocetPokusov ; i++) {
            nakladyA = genNakladyA.sample();
            dopytA = genDopytA.sample();
            predaneA = Math.min(maxPocetA, dopytA);
            ziskA = (predaneA * cenaPredajnaA)
                    - (nakladyA * maxPocetA);
            celkovyVysledokA += ziskA;

            nakladyB = genNakladyB.sample();
            dopytB = genDopytB.sample();
            predaneB = Math.min(maxPocetB, dopytB);
            ziskB = (predaneB * cenaPredajnaB)
                    - (nakladyB * maxPocetB);
            celkovyVysledokB += ziskB;
        }
        double priemerA = celkovyVysledokA / pocetPokusov;
        double priemerB = celkovyVysledokB / pocetPokusov;

        System.out.println("Priemerný zisk pre typ A: " + priemerA);
        System.out.println("Priemerný zisk pre typ B: " + priemerB);

        System.out.println(priemerA > priemerB ? "Odporúčame vyrábať typ A." : "Odporúčame vyrábať typ B.");
    }

    public static void priklad2GPT() {
        final int pocetPokusov = 1_000_000;

        // Generátory pre náklady na výrobu
        TriangularRNG nakladyA = new TriangularRNG(1.0, 1.75, 2.5);  // min, modus, max
        TriangularRNG nakladyB = new TriangularRNG(0.7, 1.2, 1.7);

        // Generátory pre dopyt
        UniformDiscreteRNG dopytA = new UniformDiscreteRNG(40, 79);  // <40; 80)
        UniformDiscreteRNG dopytB = new UniformDiscreteRNG(66, 154); // <66; 155)

        // Predajné ceny
        double predajnaCenaA = 3.0;
        double predajnaCenaB = 2.0;

        // Počet vyrobených kusov
        int maxVyrobenychA = 70;
        int maxVyrobenychB = 90;

        // Akumulátory pre zisky
        double celkovyZiskA = 0;
        double celkovyZiskB = 0;

        for (int i = 0; i < pocetPokusov; i++) {
            // Generovanie náhodných hodnôt
            double nakladyNaVyrobuA = nakladyA.sample();
            double nakladyNaVyrobuB = nakladyB.sample();
            int dopytPreA = dopytA.sample();
            int dopytPreB = dopytB.sample();

            // Výpočet zisku pre typ A
            int predaneA = Math.min(dopytPreA, maxVyrobenychA);  // Predané množstvo
            double ziskA = (predaneA * predajnaCenaA)            // Príjmy z predaja
                    - (maxVyrobenychA * nakladyNaVyrobuA);       // Náklady na výrobu
            celkovyZiskA += ziskA;

            // Výpočet zisku pre typ B
            int predaneB = Math.min(dopytPreB, maxVyrobenychB);  // Predané množstvo
            double ziskB = (predaneB * predajnaCenaB)            // Príjmy z predaja
                    - (maxVyrobenychB * nakladyNaVyrobuB);       // Náklady na výrobu
            celkovyZiskB += ziskB;
        }

        // Priemerné zisky
        double priemernyZiskA = celkovyZiskA / pocetPokusov;
        double priemernyZiskB = celkovyZiskB / pocetPokusov;

        // Výsledky
        System.out.println("Priemerný zisk pre typ A: " + priemernyZiskA);
        System.out.println("Priemerný zisk pre typ B: " + priemernyZiskB);

        if (priemernyZiskA > priemernyZiskB) {
            System.out.println("Odporúčame vyrábať typ A.");
        } else {
            System.out.println("Odporúčame vyrábať typ B.");
        }
    }
    public static void priklad3() {
        UniformContinuousRNG genX = new UniformContinuousRNG(0.0, 1.0);
        UniformContinuousRNG genY = new UniformContinuousRNG(0.0, 1.0);

        double x0 = 0.5;    // Stred kruhu (x)
        double y0 = 0.5;    // Stred kruhu (y)
        double polomer = 0.5; // Polomer kruhu
        int pocetBodovVKruhu = 0;
        int pocetBodov = 0;
        double piOdhad = 0;

        // Požadovaná presnosť odhadu
        double chybaThreshold = 1e-6;

        // Monte Carlo simulácia
        while (Math.abs(piOdhad - Math.PI) >= chybaThreshold) {
            double x = genX.sample();
            double y = genY.sample();

            // Overenie, či bod leží v kruhu
            if (Math.pow((x - x0), 2) + Math.pow((y - y0), 2) <= Math.pow(polomer, 2)) {
                pocetBodovVKruhu++;
            }
            pocetBodov++;

            // Odhad hodnoty π
            piOdhad = 4.0 * ((double) pocetBodovVKruhu / pocetBodov);
        }

        System.out.println("Odhad pi je: " + piOdhad + ", potrebný počet bodov: " + pocetBodov);
    }


    public static void priklad3GPT() {
        // Inicializácia RNG pre rovnomerné rozdelenie na intervale [0, 1]
        UniformContinuousRNG rngX = new UniformContinuousRNG(0.0, 1.0);
        UniformContinuousRNG rngY = new UniformContinuousRNG(0.0, 1.0);

        double piSkutocne = Math.PI;  // Skutočná hodnota π
        double chybaThreshold = 1e-6; // Požadovaná presnosť
        int pocetBodov = 0;           // Počet generovaných bodov
        int pocetVBodov = 0;          // Počet bodov v kruhu
        double piOdhad = 0;

        do {
            // Generovanie náhodného bodu
            double x = rngX.sample(); // Rovnomerné [0, 1]
            double y = rngY.sample(); // Rovnomerné [0, 1]

            // Test, či bod patrí do kruhu
            if (Math.pow(x - 0.5, 2) + Math.pow(y - 0.5, 2) <= 0.25) {
                pocetVBodov++;
            }

            pocetBodov++;

            // Odhad hodnoty π
            piOdhad = 4.0 * pocetVBodov / pocetBodov;

        } while (Math.abs(piOdhad - piSkutocne) >= chybaThreshold);

        // Výsledky
        System.out.println("Odhad hodnoty π: " + piOdhad);
        System.out.println("Počet bodov potrebných na dosiahnutie presnosti: " + pocetBodov);
    }

    public static void priklad4A() {
        int pozicia = 0;
        double celkovyVysledok = 0;
        UniformDiscreteRNG genPohyb = new UniformDiscreteRNG(0, 1);

        for (int j = 0; j < 1000000; j++) {
            pozicia = 0;
            for (int i = 0; i < 1000; i++) {
                int nahodnyKrok = genPohyb.sample();
                if (nahodnyKrok == 0) {
                    nahodnyKrok = -1;
                }
                pozicia += nahodnyKrok;
            }
            celkovyVysledok += Math.abs(pozicia);
        }
        celkovyVysledok /= 1000000;
        double teoretickyVysledok = Math.sqrt((2 * 1000) / Math.PI);
        System.out.println("Priemerne skonci v bode: " + celkovyVysledok);
        System.out.println("Teoreticky vysledok: " + teoretickyVysledok);
    }

    public static void priklad4B() {
        final int pocetPokusov = 1_000_000;
        UniformDiscreteRNG genPohybXAleboY = new UniformDiscreteRNG(0, 1); //0 == x, 1 == y
        UniformDiscreteRNG genPohybPlusMinus = new UniformDiscreteRNG(0, 1); // -1 || +1

        double priemerX = 0;
        double priemerY = 0;

        for (int i = 0; i < pocetPokusov; i++) {
            int poziciaX = 0;
            int poziciaY = 0;
            for (int j = 0; j < 1000; j++) {
                int xAleboY = genPohybXAleboY.sample();
                int pohyb = genPohybPlusMinus.sample();
                if (pohyb == 0) {
                    pohyb = -1;
                }
                if (xAleboY == 0) {
                    poziciaX += pohyb;
                } else {
                    poziciaY += pohyb;
                }
            }
            priemerX += Math.abs(poziciaX);
            priemerY += Math.abs(poziciaY);
        }
        priemerX /= pocetPokusov;
        priemerY /= pocetPokusov;
        System.out.println("Priemer X: " + priemerX + ", Priemer Y: " + priemerY);
        System.out.println("Domov by sa musel vratit v priemere: " + (priemerX + priemerY));
        System.out.println("Teoreticky pocet: " + Math.sqrt((4 * 1000) / Math.PI));
    }

    public static void priklad4C() {
        final int pocetPokusov = 1_000_000;
        UniformDiscreteRNG genPohybXYZ = new UniformDiscreteRNG(0, 2); //0 == x, 1 == y, z == 2
        UniformDiscreteRNG genPohybPlusMinus = new UniformDiscreteRNG(0, 1); // -1 || +1

        double priemerX = 0;
        double priemerY = 0;
        double priemerZ = 0;

        for (int i = 0; i < pocetPokusov; i++) {
            int poziciaX = 0;
            int poziciaY = 0;
            int poziciaZ = 0;
            for (int j = 0; j < 1000; j++) {
                int XYZ = genPohybXYZ.sample();
                int pohyb = genPohybPlusMinus.sample();
                if (pohyb == 0) {
                    pohyb = -1;
                }
                if (XYZ == 0) {
                    poziciaX += pohyb;
                } else if(XYZ == 1) {
                    poziciaY += pohyb;
                } else {
                    poziciaZ += pohyb;
                }
            }
            priemerX += Math.abs(poziciaX);
            priemerY += Math.abs(poziciaY);
            priemerZ += Math.abs(poziciaZ);
        }
        priemerX /= pocetPokusov;
        priemerY /= pocetPokusov;
        priemerZ /= pocetPokusov;
        System.out.println("Priemer X: " + priemerX + ", Priemer Y: " + priemerY + ", Priemer Z: " + priemerZ);
        System.out.println("Domov by sa musel vratit v priemere: " + (priemerX + priemerY + priemerZ));
        System.out.println("Teoreticky pocet: " + Math.sqrt((6 * 1000) / Math.PI));
    }
}
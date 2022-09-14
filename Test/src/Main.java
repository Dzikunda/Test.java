public class Main {

    public static void main(String[] args) {
        Lekarz.readFile();
        Pacjent.readFile();
        Wizyta.readFile();

//        - znajdź lekarza ktory miał najwięcej wizyt
        System.out.println("Lekarz z najwieksza iloscia wizyt: " + Lekarz.lekarzZnajwiekszaLiczbaWizyt());
//        - znajdź pacjenta który miał najwięcej wizyt
        System.out.println("Pacjent z najwieksza iloscia wizyt: " + Pacjent.najwiecejWizytPacjent());
//        - która specalizacja cieszy się największym powodzeniem?`
        System.out.println("Specjalizacje z najwiekszym powodzeniem to: " + Lekarz.specjalizacjaZNajwiekszymPowodzeniem());
//        - którego roku było najwięcej wizyt?
        System.out.println("Najwiecej wizyt bylo roku: " + Wizyta.najwiecejWizytWRoku());
//        - wypisz top 5 najstarszych lekarzy
        System.out.println("5 Najstarszych Lekarzow = " + Lekarz.najstarszy5Lekarzy());
//        - czy sa pacjenci ktorzy mieli wizyty u wiecej niz 5 roznych lekarzy?
        System.out.println("Pacjenci z wizytami u ponad 5 Lekarzy: " + Pacjent.pacjenciWiecejNiz5Lekarzy());
//        - czy sa lekarze ktorzy przyjmowali tylko 1 pacjenta?
        System.out.println("Lekarze ktorzy przyjmowali wylacznie 1 pacjenta: " + Lekarz.lekarzeZ1Pacjentem());


    }

}

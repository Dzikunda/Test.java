import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

public class Lekarz {
    private int id;
    private String nazwisko;
    private String imie;
    private String spejcalnosc;
    private LocalDate dataUrodzenia;
    private Long NIP;
    private Long pesel;
    private Set<Wizyta> wizyty = new HashSet<>();
    static List<Lekarz> lekarze = new ArrayList<>();

    public Lekarz(int id, String nazwisko, String imie, String spejcalnosc, LocalDate dataUrodzenia, Long NIP, Long pesel) {
        this.id = id;
        this.nazwisko = nazwisko;
        this.imie = imie;
        this.spejcalnosc = spejcalnosc;
        this.dataUrodzenia = dataUrodzenia;
        this.NIP = NIP;
        this.pesel = pesel;
        lekarze.add(this);
    }


    public int getId() {
        return id;
    }

    public String getSpejcalnosc() {
        return spejcalnosc;
    }

    public LocalDate getDataUrodzenia() {
        return dataUrodzenia;
    }

    public int getiloscWizyt() {
        return wizyty.size();
    }

    @Override
    public String toString() {
        return "Lekarz{" + "id=" + id + ", nazwisko='" + nazwisko + '\'' + ", imie='" + imie + '\'' + ", spejcalnosc='" + spejcalnosc + '\'' + ", dataUrodzenia=" + dataUrodzenia + ", NIP=" + NIP + ", pesel=" + pesel + '}';
    }
    public void dodajWizyte(Wizyta wizyta) {
        wizyty.add(wizyta);
    }

    public static void readFile() {
        //try-with-resources
        try (FileReader fr = new FileReader("src/lekarze.txt")) {
            BufferedReader br = new BufferedReader(fr);
            String line1;
            int iteration = 0;
            while ((line1 = br.readLine()) != null) {
                if (iteration == 0) {
                    iteration++;
                    continue;
                }
                String[] a = line1.split("\t");
                int id = Integer.parseInt(a[0]);
                String nazwisko = a[1];
                String imie = a[2];
                String specjalnosc = a[3];
                LocalDate dataUrodzenia = LocalDate.parse(a[4]);
                Long NIP = Long.valueOf(a[5].replaceAll("-", ""));
                Long pesel = Long.valueOf(a[6]);
                new Lekarz(id, nazwisko, imie, specjalnosc, dataUrodzenia, NIP, pesel);
            }
            br.close();
        } catch (FileNotFoundException e) {
            System.out.println("Plik nie znaleziony. " + e);
        } catch (IOException e) {
            System.out.println("Problem z odczytem pliku. " + e);
        }
    }

    public static Lekarz findLekarz(int id) {
        for (Lekarz l : lekarze) {
            if (id == l.id) {
                return l;
            }
        }
        return null;
    }

    public static Lekarz lekarzZnajwiekszaLiczbaWizyt() {
        return lekarze.stream().max(Comparator.comparing(Lekarz::getiloscWizyt)).orElseThrow(NoSuchElementException::new);
    }

    public static List<Lekarz> lekarzeZ1Pacjentem() {
        List<Lekarz> lista = new ArrayList<>();
        for (Lekarz l : lekarze) {
            long l1 = l.wizyty.stream().map(Wizyta::getIdPacjenta).distinct().count();
            if (l1 == 1) {
                lista.add(l);
            }
        }
        return lista;
    }

    public static List<Lekarz> najstarszy5Lekarzy() {
        return lekarze.stream().sorted(Comparator.comparing(Lekarz::getDataUrodzenia)).limit(5).collect(Collectors.toList());
    }

    public static String specjalizacjaZNajwiekszymPowodzeniem() {

        Long max = lekarze.stream().filter(it -> Objects.nonNull(it.spejcalnosc)).collect(groupingBy(Lekarz::getSpejcalnosc, Collectors.counting())).entrySet().stream().max(Map.Entry.comparingByValue()).map(Map.Entry::getValue).get();
        return String.valueOf(lekarze.stream().filter(it -> Objects.nonNull(it.spejcalnosc)).collect(groupingBy(Lekarz::getSpejcalnosc, Collectors.counting())).entrySet().stream().filter(i -> i.getValue().equals(max)).collect(Collectors.toList()));
    }

}
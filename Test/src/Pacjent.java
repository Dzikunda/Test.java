import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;


public class Pacjent {
    private int id;
    private String nazwisko;
    private String imie;
    private Long pesel;
    private LocalDate dataUrodzenia;
    private Set<Wizyta> wizyty = new HashSet<>();
    static List<Pacjent> pacjenci = new ArrayList<>();


    public Pacjent(int id, String nazwisko, String imie, Long pesel, LocalDate dataUrodzenia) {
        this.id = id;
        this.nazwisko = nazwisko;
        this.imie = imie;
        this.pesel = pesel;
        this.dataUrodzenia = dataUrodzenia;
        pacjenci.add(this);

    }

    public int getId() {
        return id;
    }

    public int getiloscWizyt() {
        return wizyty.size();
    }


    @Override
    public String toString() {
        return "Pacjent{" + "id='" + id + '\'' + ", nazwisko='" + nazwisko + '\'' + ", imie='" + imie + '\'' + ", pesel='" + pesel + '\'' + ", dataUrodzenia='" + dataUrodzenia + '\'' + '}';
    }


    public void dodajWizyte(Wizyta wizyta) {
        wizyty.add(wizyta);
    }

    public static void readFile() {
        try (FileReader fr = new FileReader("src/pacjenci.txt")) {
            BufferedReader br = new BufferedReader(fr);
            String line1;
            int iteration = 0;
            while ((line1 = br.readLine()) != null) {
                if (iteration == 0) {
                    iteration++;
                    continue;
                }

                String[] a = line1.split("\t", 0);
                int id = Integer.parseInt(a[0]);
                String nazwisko = a[1];
                String imie = a[2];
                Long pesel = Long.valueOf(a[3]);
                String startDateString = a[4];
                DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("yyyy-M-d");
                LocalDate dataUrodzenia = LocalDate.parse(startDateString, formatter2);
                new Pacjent(id, nazwisko, imie, pesel, dataUrodzenia);

            }
            br.close();
        } catch (FileNotFoundException e) {
            System.out.println("Plik nie znaleziony. " + e);
        } catch (IOException e) {
            System.out.println("Problem z odczytem pliku. " + e);
        }
    }

    public static Pacjent findPacjent(int id) {
        for (Pacjent p : pacjenci) {
            if (p.id == id) {
                return p;
            }
        }
        return null;
    }

    public static Pacjent najwiecejWizytPacjent() {
        return pacjenci.stream().max(Comparator.comparing(Pacjent::getiloscWizyt)).orElseThrow(NoSuchElementException::new);
    }

    public static List<Pacjent> pacjenciWiecejNiz5Lekarzy() {
        List<Pacjent> list = new ArrayList<>();
        for (Pacjent p : pacjenci) {
            long l2 = p.wizyty.stream().map(Wizyta::getIdLekarza).distinct().count();
            if (l2 > 5) {
                list.add(p);
            }
        }
        return list;
    }

}
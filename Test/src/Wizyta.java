import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Wizyta {
    private Lekarz lekarz;
    private Pacjent pacjent;
    private LocalDate data;
    static List<Wizyta> wizyty = new ArrayList<>();

    public Wizyta(int idLekarza, int idPacjenta, LocalDate data) {
        this.lekarz = Lekarz.findLekarz(idLekarza);
        this.pacjent = Pacjent.findPacjent(idPacjenta);
        this.data = data;
        wizyty.add(this);
        lekarz.dodajWizyte(this);
        pacjent.dodajWizyte(this);
    }

    public int getIdLekarza() {
        return lekarz.getId();
    }

    public int getIdPacjenta() {
        return pacjent.getId();
    }

    public int getYear() {
        return data.getYear();
    }

    @Override
    public String toString() {
        return "Wizyta{" + "IdLekarza=" + getIdLekarza() + ", IdPacjenta=" + getIdPacjenta() + ", data=" + data + '}';
    }

    public Wizyta() {

    }

    public static void readFile() {
        try (FileReader fr = new FileReader("src/wizyty.txt")) {
            BufferedReader br = new BufferedReader(fr);
            String line1;

            int iteration = 0;
            while ((line1 = br.readLine()) != null) {
                if (iteration == 0) {
                    iteration++;
                    continue;
                }

                String[] a = line1.split("\t", 0);
                int idLekarza = Integer.parseInt(a[0]);
                int idPacjenta = Integer.parseInt(a[1]);
                String startDateString = a[2];
                DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("yyyy-M-d");
                LocalDate data = LocalDate.parse(startDateString, formatter2);
                new Wizyta(idLekarza, idPacjenta, data);
            }
            br.close();

        } catch (FileNotFoundException e) {
            System.out.println("Plik nie znaleziony. " + e);
        } catch (IOException e) {
            System.out.println("Problem z odczytem pliku. " + e);
        }
    }

    public static List<Integer> najwiecejWizytWRoku() {
        return wizyty.stream().collect(Collectors.groupingBy(Wizyta::getYear, Collectors.counting())).entrySet().stream().max(Map.Entry.comparingByValue()).stream().map(Map.Entry::getKey).collect(Collectors.toList());
    }

}

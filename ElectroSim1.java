import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JOptionPane;

public class ElectroSim1 {
    
    private static final int HARIBULAN = 30;
    private static final int HARITAHUN = 365;

    public static void main(String[] args) {
        int jmlalat = getIntegerInput("Masukkan jumlah alat listrik yang ingin dihitung:");
        
        String[] namaAlat = new String[jmlalat];
        double[] daya = new double[jmlalat];
        double[] durasi = new double[jmlalat];
        double[] tarifharian = new double[jmlalat];
        double tarifperkwh = getDoubleInput("Silahkan masukan tarif listrik per kWh (dalam rupiah):");
        double gajiperbulan = getDoubleInput("Silahkan masukan pendapatan bulanan sebagai pembanding:");

        double totalTarifBulanan = 0;

       for (int i = 0; i < jmlalat; i++) {
            namaAlat[i] = JOptionPane.showInputDialog("Masukkan nama alat listrik untuk alat ke-" + (i + 1) + ":");
            daya[i] = getDoubleInput("Masukkan daya alat listrik (dalam watt) untuk alat ke-" + (i + 1) + ":");
            durasi[i] = getDoubleInput("Masukkan durasi pemakaian harian (dalam jam) untuk alat ke-" + (i + 1) + ":");
 
            tarifharian[i] = biaya(daya[i], durasi[i], tarifperkwh);
            totalTarifBulanan += tarifharian[i] * HARIBULAN;
        }
        saveOutputToFile(namaAlat, tarifharian, totalTarifBulanan, gajiperbulan);
    }

    private static int getIntegerInput(String message) {
        while (true) {
            try {
                String input = JOptionPane.showInputDialog(message);
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Input tidak valid, silakan masukkan angka.");
            }
        }
    }

    private static double getDoubleInput(String message) {
        while (true) {
            try {
                String input = JOptionPane.showInputDialog(message);
                return Double.parseDouble(input);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Input tidak valid, silakan masukkan angka.");
            }
        }
    }

    private static void saveOutputToFile(String[] namaAlat, double[] tarifharian, double totalTarifBulanan, double gajiperbulan) {
        String outputFileName = "Estimasi_Biaya_Listrik.txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFileName))) {
            writer.write("=== Estimasi Biaya Listrik ===\n");
            for (int i = 0; i < namaAlat.length; i++) {
                double tarifbulanan = tarifharian[i] * HARIBULAN;
                double tariftahunan = tarifharian[i] * HARITAHUN;
                writer.write(String.format("\n",totalTarifBulanan));
                writer.write(String.format("==========================================\n",tarifbulanan));

                writer.write(String.format("Alat ke-%d (%s):\n", i + 1, namaAlat[i]));
                writer.write(String.format("Biaya harian: Rp %.2f\n", tarifharian[i]));
                writer.write(String.format("Biaya bulanan: Rp %.2f\n", tarifbulanan));
                writer.write(String.format("Biaya tahunan: Rp %.2f\n", tariftahunan));

                writer.write(String.format("==========================================\n",tarifbulanan));
              
            }
            writer.write(String.format("==========================================\n",totalTarifBulanan));
            writer.write(String.format("Total biaya bulanan untuk semua alat: Rp %.2f\n", totalTarifBulanan));
            writer.write(String.format("==========================================\n",totalTarifBulanan));
            writer.write(String.format("\n",totalTarifBulanan));
            
            if (gajiperbulan > totalTarifBulanan) {
                writer.write("Kamu cukup hemat listrik ya\n");
            } else if (totalTarifBulanan > gajiperbulan) {
                writer.write("Kamu perlu menghemat listrik ya\n");
            } else {
                writer.write("Kamu cukup hemat listrik ya\n");
            }

            JOptionPane.showMessageDialog(null, "Hasilnya telah disimpan ke file: " + outputFileName);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Terjadi kesalahan saat menulis ke file: " + e.getMessage());
        }
    }

    public static double biaya(double daya, double durasi, double tarifperkwh) {
        double kwh = daya / 1000.0;
        double penggunaanperhari = kwh * durasi;
        return penggunaanperhari * tarifperkwh;
    }
}
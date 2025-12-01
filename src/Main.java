import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import java.io.*;

// Kelas abstrak MenuItem sebagai kelas dasar
abstract class MenuItem {
    protected String nama;
    protected double harga;
    protected String kategori;
    
    public MenuItem(String nama, double harga, String kategori) {
        this.nama = nama;
        this.harga = harga;
        this.kategori = kategori;
    }
    
    // Getter dan Setter (Encapsulation)
    public String getNama() {
        return nama;
    }
    
    public void setNama(String nama) {
        this.nama = nama;
    }
    
    public double getHarga() {
        return harga;
    }
    
    public void setHarga(double harga) {
        this.harga = harga;
    }
    
    public String getKategori() {
        return kategori;
    }
    
    public void setKategori(String kategori) {
        this.kategori = kategori;
    }
    
    // Metode abstrak
    public abstract void tampilMenu();
}

// Kelas Makanan sebagai turunan dari MenuItem
class Makanan extends MenuItem {
    private String jenisMakanan;
    
    public Makanan(String nama, double harga, String kategori, String jenisMakanan) {
        super(nama, harga, kategori);
        this.jenisMakanan = jenisMakanan;
    }
    
    public String getJenisMakanan() {
        return jenisMakanan;
    }
    
    public void setJenisMakanan(String jenisMakanan) {
        this.jenisMakanan = jenisMakanan;
    }
    
    @Override
    public void tampilMenu() {
        System.out.println(nama + " - Rp " + harga + " (" + kategori + " - " + jenisMakanan + ")");
    }
}

// Kelas Minuman sebagai turunan dari MenuItem
class Minuman extends MenuItem {
    private String jenisMinuman;
    
    public Minuman(String nama, double harga, String kategori, String jenisMinuman) {
        super(nama, harga, kategori);
        this.jenisMinuman = jenisMinuman;
    }
    
    public String getJenisMinuman() {
        return jenisMinuman;
    }
    
    public void setJenisMinuman(String jenisMinuman) {
        this.jenisMinuman = jenisMinuman;
    }
    
    @Override
    public void tampilMenu() {
        System.out.println(nama + " - Rp " + harga + " (" + kategori + " - " + jenisMinuman + ")");
    }
}

// Kelas Diskon sebagai turunan dari MenuItem
class Diskon extends MenuItem {
    private double persentaseDiskon;
    
    public Diskon(String nama, double harga, String kategori, double persentaseDiskon) {
        super(nama, harga, kategori);
        this.persentaseDiskon = persentaseDiskon;
    }
    
    public double getPersentaseDiskon() {
        return persentaseDiskon;
    }
    
    public void setPersentaseDiskon(double persentaseDiskon) {
        this.persentaseDiskon = persentaseDiskon;
    }
    
    @Override
    public void tampilMenu() {
        System.out.println(nama + " - Diskon " + persentaseDiskon + "% (Min. Belanja Rp " + harga + ")");
    }
}

// Kelas Pesanan untuk mencatat pesanan pelanggan
class Pesanan {
    private List<MenuItem> itemPesanan;
    private List<Integer> jumlahPesanan;
    
    public Pesanan() {
        itemPesanan = new ArrayList<>();
        jumlahPesanan = new ArrayList<>();
    }
    
    public void tambahItem(MenuItem item, int jumlah) {
        itemPesanan.add(item);
        jumlahPesanan.add(jumlah);
    }
    
    public List<MenuItem> getItemPesanan() {
        return itemPesanan;
    }
    
    public List<Integer> getJumlahPesanan() {
        return jumlahPesanan;
    }
    
    public double hitungTotal() {
        double total = 0;
        for (int i = 0; i < itemPesanan.size(); i++) {
            total += itemPesanan.get(i).getHarga() * jumlahPesanan.get(i);
        }
        return total;
    }
}

// Kelas Menu untuk mengelola semua item menu
class MenuManager {
    private List<MenuItem> daftarMenu;
    
    public MenuManager() {
        daftarMenu = new ArrayList<>();
    }
    
    public void tambahItem(MenuItem item) {
        daftarMenu.add(item);
    }
    
    public List<MenuItem> getDaftarMenu() {
        return daftarMenu;
    }
    
    public MenuItem getItem(int index) throws IndexOutOfBoundsException {
        if (index < 0 || index >= daftarMenu.size()) {
            throw new IndexOutOfBoundsException("Item menu tidak ditemukan!");
        }
        return daftarMenu.get(index);
    }
    
    public void hapusItem(int index) throws IndexOutOfBoundsException {
        if (index < 0 || index >= daftarMenu.size()) {
            throw new IndexOutOfBoundsException("Item menu tidak ditemukan!");
        }
        daftarMenu.remove(index);
    }
    
    public void tampilkanMenu() {
        if (daftarMenu.isEmpty()) {
            System.out.println("Menu kosong.");
            return;
        }
        
        System.out.println("\n=== DAFTAR MENU ===");
        for (int i = 0; i < daftarMenu.size(); i++) {
            System.out.print((i + 1) + ". ");
            daftarMenu.get(i).tampilMenu();
        }
    }
    
    // Simpan menu ke file
    public void simpanMenuKeFile(String namaFile) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(namaFile))) {
            for (MenuItem item : daftarMenu) {
                if (item instanceof Makanan) {
                    Makanan m = (Makanan) item;
                    writer.println("MAKANAN|" + m.getNama() + "|" + m.getHarga() + "|" + m.getKategori() + "|" + m.getJenisMakanan());
                } else if (item instanceof Minuman) {
                    Minuman m = (Minuman) item;
                    writer.println("MINUMAN|" + m.getNama() + "|" + m.getHarga() + "|" + m.getKategori() + "|" + m.getJenisMinuman());
                } else if (item instanceof Diskon) {
                    Diskon d = (Diskon) item;
                    writer.println("DISKON|" + d.getNama() + "|" + d.getHarga() + "|" + d.getKategori() + "|" + d.getPersentaseDiskon());
                }
            }
            System.out.println("Menu berhasil disimpan ke file " + namaFile);
        } catch (IOException e) {
            System.out.println("Error menyimpan menu ke file: " + e.getMessage());
        }
    }
    
    // Muat menu dari file
    public void muatMenuDariFile(String namaFile) {
        try (BufferedReader reader = new BufferedReader(new FileReader(namaFile))) {
            String line;
            daftarMenu.clear(); // Kosongkan menu yang ada
            
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length >= 5) {
                    String tipe = parts[0];
                    String nama = parts[1];
                    double harga = Double.parseDouble(parts[2]);
                    String kategori = parts[3];
                    
                    switch (tipe) {
                        case "MAKANAN":
                            tambahItem(new Makanan(nama, harga, kategori, parts[4]));
                            break;
                        case "MINUMAN":
                            tambahItem(new Minuman(nama, harga, kategori, parts[4]));
                            break;
                        case "DISKON":
                            tambahItem(new Diskon(nama, harga, kategori, Double.parseDouble(parts[4])));
                            break;
                    }
                }
            }
            System.out.println("Menu berhasil dimuat dari file " + namaFile);
        } catch (FileNotFoundException e) {
            System.out.println("File tidak ditemukan: " + namaFile);
        } catch (IOException e) {
            System.out.println("Error membaca file: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Error format angka dalam file: " + e.getMessage());
        }
    }
}

// Kelas utama
public class Main {
    private static MenuManager menuManager = new MenuManager();
    private static final String FILE_MENU = "menu.txt";
    
    public static void main(String[] args) {
        // Muat menu dari file jika ada
        menuManager.muatMenuDariFile(FILE_MENU);
        
        // Jika file tidak ada atau kosong, tambahkan menu default
        if (menuManager.getDaftarMenu().isEmpty()) {
            inisialisasiMenuDefault();
        }
        
        boolean running = true;
        try (Scanner input = new Scanner(System.in)) {
            while (running) {
                tampilkanMenuUtama();
                try {
                    int pilihan = input.nextInt();
                    input.nextLine(); // Bersihkan buffer
                    
                    switch (pilihan) {
                        case 1:
                            tambahItemMenu(input);
                            break;
                        case 2:
                            menuManager.tampilkanMenu();
                            System.out.println("\nTekan Enter untuk kembali...");
                            input.nextLine();
                            break;
                        case 3:
                            prosesPesanan(input);
                            break;
                        case 4:
                            menuManager.simpanMenuKeFile(FILE_MENU);
                            break;
                        case 5:
                            menuManager.muatMenuDariFile(FILE_MENU);
                            break;
                        case 6:
                            running = false;
                            System.out.println("Terima kasih!");
                            break;
                        default:
                            System.out.println("Pilihan tidak valid!");
                            break;
                    }
                } catch (Exception e) {
                    System.out.println("Input tidak valid! Masukkan angka.");
                    input.nextLine(); // Bersihkan buffer
                }
            }
        }
    }
    
    private static void tampilkanMenuUtama() {
        System.out.println("\n=== SISTEM MANAJEMEN RESTORAN ===");
        System.out.println("1. Tambah Item Menu");
        System.out.println("2. Tampilkan Menu");
        System.out.println("3. Proses Pesanan");
        System.out.println("4. Simpan Menu ke File");
        System.out.println("5. Muat Menu dari File");
        System.out.println("6. Keluar");
        System.out.print("Pilih menu: ");
    }
    
    private static void inisialisasiMenuDefault() {
        menuManager.tambahItem(new Makanan("Nasi Goreng", 15000, "Makanan", "Berat"));
        menuManager.tambahItem(new Makanan("Mie Goreng", 10000, "Makanan", "Berat"));
        menuManager.tambahItem(new Makanan("Gulai Ayam", 12000, "Makanan", "Berat"));
        menuManager.tambahItem(new Makanan("Ayam Bakar", 12000, "Makanan", "Berat"));
        menuManager.tambahItem(new Minuman("Es Kelapa", 7000, "Minuman", "Dingin"));
        menuManager.tambahItem(new Minuman("Jus Jeruk", 6000, "Minuman", "Dingin"));
        menuManager.tambahItem(new Minuman("Es Teh", 3000, "Minuman", "Dingin"));
        menuManager.tambahItem(new Minuman("Kopi", 5000, "Minuman", "Panas"));
        menuManager.tambahItem(new Diskon("Diskon Spesial", 50000, "Promo", 10.0));
    }
    
    private static void tambahItemMenu(Scanner input) {
        System.out.println("\n=== TAMBAH ITEM MENU ===");
        System.out.println("1. Makanan");
        System.out.println("2. Minuman");
        System.out.println("3. Diskon");
        System.out.print("Pilih jenis item: ");
        
        try {
            int jenis = input.nextInt();
            input.nextLine(); // Bersihkan buffer
            
            switch (jenis) {
                case 1:
                    tambahMakanan(input);
                    break;
                case 2:
                    tambahMinuman(input);
                    break;
                case 3:
                    tambahDiskon(input);
                    break;
                default:
                    System.out.println("Jenis item tidak valid!");
                    break;
            }
        } catch (Exception e) {
            System.out.println("Input tidak valid!");
            input.nextLine(); // Bersihkan buffer
        }
    }
    
    private static void tambahMakanan(Scanner input) {
        try {
            System.out.print("Masukkan nama makanan: ");
            String nama = input.nextLine();
            
            System.out.print("Masukkan harga: ");
            double harga = input.nextDouble();
            
            System.out.print("Masukkan kategori: ");
            input.nextLine(); // Bersihkan buffer
            String kategori = input.nextLine();
            
            System.out.print("Masukkan jenis makanan: ");
            String jenisMakanan = input.nextLine();
            
            menuManager.tambahItem(new Makanan(nama, harga, kategori, jenisMakanan));
            System.out.println("Makanan berhasil ditambahkan!");
        } catch (Exception e) {
            System.out.println("Error input makanan!");
            input.nextLine(); // Bersihkan buffer
        }
    }
    
    private static void tambahMinuman(Scanner input) {
        try {
            System.out.print("Masukkan nama minuman: ");
            String nama = input.nextLine();
            
            System.out.print("Masukkan harga: ");
            double harga = input.nextDouble();
            
            System.out.print("Masukkan kategori: ");
            input.nextLine(); // Bersihkan buffer
            String kategori = input.nextLine();
            
            System.out.print("Masukkan jenis minuman: ");
            String jenisMinuman = input.nextLine();
            
            menuManager.tambahItem(new Minuman(nama, harga, kategori, jenisMinuman));
            System.out.println("Minuman berhasil ditambahkan!");
        } catch (Exception e) {
            System.out.println("Error input minuman!");
            input.nextLine(); // Bersihkan buffer
        }
    }
    
    private static void tambahDiskon(Scanner input) {
        try {
            System.out.print("Masukkan nama diskon: ");
            String nama = input.nextLine();
            
            System.out.print("Masukkan minimal belanja: ");
            double minBelanja = input.nextDouble();
            
            System.out.print("Masukkan persentase diskon: ");
            double persentase = input.nextDouble();
            
            menuManager.tambahItem(new Diskon(nama, minBelanja, "Promo", persentase));
            System.out.println("Diskon berhasil ditambahkan!");
        } catch (Exception e) {
            System.out.println("Error input diskon!");
            input.nextLine(); // Bersihkan buffer
        }
    }
    
    private static void prosesPesanan(Scanner input) {
        try {
            menuManager.tampilkanMenu();
            
            Pesanan pesanan = new Pesanan();
            boolean tambahItem = true;
            
            while (tambahItem) {
                try {
                    System.out.print("\nMasukkan nomor menu (0 untuk selesai): ");
                    int nomor = input.nextInt();
                    
                    if (nomor == 0) {
                        tambahItem = false;
                        break;
                    }
                    
                    MenuItem item = menuManager.getItem(nomor - 1);
                    
                    // Cek jika item adalah diskon, lewati
                    if (item instanceof Diskon) {
                        System.out.println("Item diskon tidak bisa dipesan!");
                        continue;
                    }
                    
                    System.out.print("Masukkan jumlah: ");
                    int jumlah = input.nextInt();
                    
                    pesanan.tambahItem(item, jumlah);
                    System.out.println("Item ditambahkan ke pesanan!");
                    
                } catch (IndexOutOfBoundsException e) {
                    System.out.println("Nomor menu tidak valid!");
                }
            }
            
            if (pesanan.getItemPesanan().isEmpty()) {
                System.out.println("Tidak ada pesanan yang diproses.");
                return;
            }
            
            // Hitung total dan terapkan diskon
            double total = pesanan.hitungTotal();
            double diskon = hitungDiskon(total);
            double totalAkhir = total - diskon;
            
            // Tampilkan struk
            tampilkanStruk(pesanan, total, diskon, totalAkhir);
            
            // Simpan struk ke file
            simpanStrukKeFile(pesanan, total, diskon, totalAkhir);
            
        } catch (Exception e) {
            System.out.println("Error dalam proses pesanan: " + e.getMessage());
        }
    }
    
    private static double hitungDiskon(double total) {
        double diskon = 0;
        
        // Cek diskon yang tersedia
        for (MenuItem item : menuManager.getDaftarMenu()) {
            if (item instanceof Diskon) {
                Diskon d = (Diskon) item;
                if (total >= d.getHarga()) {
                    diskon = total * (d.getPersentaseDiskon() / 100);
                    break;
                }
            }
        }
        
        return diskon;
    }
    
    private static void tampilkanStruk(Pesanan pesanan, double total, double diskon, double totalAkhir) {
        System.out.println("\n=== STRUK PEMESANAN ===");
        System.out.println("Tanggal: " + java.time.LocalDateTime.now());
        System.out.println("------------------------");
        
        List<MenuItem> items = pesanan.getItemPesanan();
        List<Integer> jumlahs = pesanan.getJumlahPesanan();
        
        for (int i = 0; i < items.size(); i++) {
            MenuItem item = items.get(i);
            int jumlah = jumlahs.get(i);
            double subtotal = item.getHarga() * jumlah;
            System.out.println(item.getNama() + " x" + jumlah + " = Rp " + subtotal);
        }
        
        System.out.println("------------------------");
        System.out.println("Total: Rp " + total);
        if (diskon > 0) {
            System.out.println("Diskon: Rp " + diskon);
        }
        System.out.println("Total Akhir: Rp " + totalAkhir);
        System.out.println("========================");
        System.out.println("TERIMA KASIH!");
    }
    
    private static void simpanStrukKeFile(Pesanan pesanan, double total, double diskon, double totalAkhir) {
        try {
            String namaFile = "struk_" + System.currentTimeMillis() + ".txt";
            try (PrintWriter writer = new PrintWriter(new FileWriter(namaFile))) {
                writer.println("=== STRUK PEMESANAN ===");
                writer.println("Tanggal: " + java.time.LocalDateTime.now());
                writer.println("------------------------");
                
                List<MenuItem> items = pesanan.getItemPesanan();
                List<Integer> jumlahs = pesanan.getJumlahPesanan();
                
                for (int i = 0; i < items.size(); i++) {
                    MenuItem item = items.get(i);
                    int jumlah = jumlahs.get(i);
                    double subtotal = item.getHarga() * jumlah;
                    writer.println(item.getNama() + " x" + jumlah + " = Rp " + subtotal);
                }
                
                writer.println("------------------------");
                writer.println("Total: Rp " + total);
                if (diskon > 0) {
                    writer.println("Diskon: Rp " + diskon);
                }
                writer.println("Total Akhir: Rp " + totalAkhir);
                writer.println("========================");
                writer.println("TERIMA KASIH!");
            }
            System.out.println("Struk berhasil disimpan ke file " + namaFile);
        } catch (IOException e) {
            System.out.println("Error menyimpan struk: " + e.getMessage());
        }
    }
}

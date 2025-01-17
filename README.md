# Aplikasi Social Media Java

Aplikasi social media sederhana yang dibangun menggunakan Java dengan antarmuka grafis (GUI) menggunakan JFrame Form. Aplikasi ini mengintegrasikan AWS DynamoDB sebagai database untuk menyimpan data pengguna dan konten social media.

## Fitur Utama

### 1. Manajemen Akun
- **Registrasi**: Pengguna baru dapat membuat akun dengan mengisi informasi yang diperlukan
- **Login**: Sistem autentikasi untuk pengguna yang sudah terdaftar

### 2. Beranda
- Melihat postingan dari semua pengguna
- Membuat postingan baru
- Berinteraksi dengan postingan:


### 3. Sistem Pesan
- Mengirim pesan pribadi ke pengguna lain
- Melihat riwayat percakapan

## Teknologi yang Digunakan

- **Backend & Frontend**: Java
- **IDE**: NetBeans
- **GUI Framework**: Java Swing (JFrame Form)
- **Database**: AWS DynamoDB
- **Komponen**: Java Class dan JFrame Form

## Struktur Database

AWS DynamoDB digunakan untuk menyimpan:
- Data pengguna
- Postingan
- Interaksi postingan (like, komentar)
- Pesan antar pengguna

## Persyaratan Sistem

- JDK 8 atau lebih tinggi
- NetBeans IDE
- Koneksi internet untuk akses AWS DynamoDB
- AWS SDK untuk Java
- Konfigurasi AWS (Access Key dan Secret Key)

## Cara Menjalankan Aplikasi

1. Clone repository ini
2. Buka project menggunakan NetBeans IDE
3. Pastikan semua dependencies terinstall
4. Konfigurasi AWS credentials di file konfigurasi
5. Build dan jalankan aplikasi

## Konfigurasi AWS DynamoDB

```java
// Contoh konfigurasi AWS DynamoDB
AWSCredentials credentials = new BasicAWSCredentials(
    "YOUR_ACCESS_KEY",
    "YOUR_SECRET_KEY"
);

AmazonDynamoDB client = AmazonDynamoDBClientBuilder
    .standard()
    .withCredentials(new AWSStaticCredentialsProvider(credentials))
    .withRegion(Regions.AP_SOUTHEAST_1)
    .build();
```

## Kontribusi

Jika Anda ingin berkontribusi pada project ini, silakan:
1. Fork repository
2. Buat branch baru untuk fitur Anda
3. Commit perubahan
4. Push ke branch
5. Buat Pull Request



## Kontak

Jika Anda memiliki pertanyaan atau saran, silakan buka issue baru di repository ini.

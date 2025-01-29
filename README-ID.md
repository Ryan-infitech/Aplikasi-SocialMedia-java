<div align="right">

<a href="README.md"><img src="https://flagcdn.com/w40/gb.png" width="25"></a> | <a href="README-ID.md"><img src="https://flagcdn.com/w40/id.png" width="20"></a>


</div>

# Aplikasi Social Media Java

Aplikasi social media sederhana yang dibangun menggunakan Java dengan antarmuka grafis (GUI) menggunakan JFrame Form. Aplikasi ini mengintegrasikan AWS DynamoDB sebagai database untuk menyimpan data pengguna dan konten social media.

![ss](./readme%20media/RunSocialMedia.gif)

## Fitur Utama

### 1. Manajemen Akun
- **Registrasi**: Pengguna baru dapat membuat akun dengan mengisi informasi yang diperlukan
- **Login**: Sistem autentikasi untuk pengguna yang sudah terdaftar

  ![](./readme%20media/register%20page.png)

### 2. Beranda
- Melihat postingan dari semua pengguna
- Membuat postingan baru
- Berinteraksi dengan postingan:

  ![](./readme%20media/beranda.png)


### 3. Sistem Pesan
- Mengirim pesan pribadi ke pengguna lain
- Melihat riwayat percakapan

  ![](./readme%20media/pesan.png)
## Teknologi yang Digunakan

![](https://cdn.hashnode.com/res/hashnode/image/upload/v1690034956546/101c1694-7e87-458e-afd5-ab65c48c468e.gif?w=1600&h=840&fit=crop&crop=entropy&auto=format,compress&gif-q=60&format=webm)

- **Backend & Frontend**: Java
- **IDE**: NetBeans
- **GUI Framework**: Java Swing (JFrame Form)
- **Database**: AWS DynamoDB
- **Komponen**: Java Class dan JFrame Form

## Database
![](https://media.licdn.com/dms/image/v2/D5612AQHmXgaaTvUgUQ/article-cover_image-shrink_720_1280/article-cover_image-shrink_720_1280/0/1683409691265?e=2147483647&v=beta&t=MI0BsgNjRzCSGldmtEY7txk0WvBcMeEEm0jx8Cn5vYU)

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

[![WhatsApp](https://img.shields.io/badge/WhatsApp-25D366?style=for-the-badge&logo=whatsapp&logoColor=white)](https://wa.me/6285157517798)
[![Instagram](https://img.shields.io/badge/Instagram-E4405F?style=for-the-badge&logo=instagram&logoColor=white)](https://www.instagram.com/ryan.septiawan__/)



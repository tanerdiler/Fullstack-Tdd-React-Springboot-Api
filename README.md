# KATA-1
CONTRACT DRIVEN DESIGN
## AMAÇ
- Rest servislerimizin her zaman aynı isteği alıp aynı cevabı döneceğini garantisini vermek
- Frontend ekibine veya third-party bir entegrasyon için veritabanı gerektirmeyen çalışabilir bir api uygulaması vermek 
## KAZANILACAK DENEYİM
- Spring Cloud Contract kullanılarak aşağıdaki isterlerin Contract Test'lerinin yazılması
- Contract'ları çalıştıran Spring Stub Runner paketinin hazırlanması
- Servis methodlarının mocklanması
## GÖREVLER
[MISSION-1.tr](https://github.com/tanerdiler/tdd-kata-ddd-transactions-api/blob/mission-1/MISSION-1_tr.md) dokümanı içerisinde bulunmaktadır.
## İSTERLER
#### 1. Satın Alım Uzmanı olarak, satın alım işlemlerini sisteme kayıt edebilmeliyim
##### 1.1 Şirketin CFO'su olarak, sisteme girilen satın alım işleminin limite bakılarak sistem tarafından onaylanmasını istiyorum
##### 1.2 Şirketin CFO'su olarak, sisteme girilen satın alım işleminin limite bakılarak sistem tarafından onaylanmamasını istiyorum
##### 1.3 Şirketin CFO'su olarak, sisteme girilen tüm satın alım işlemlerini listeleyebilmek istiyorum
##### 1.4 Şirketin CFO'su olarak, satın alım uzmanına ait tüm işlemleri listeleyebilmek istiyorum
#### 2. Backend developer olarak, frontend ekibine dummy verilerle çalıştırılabilir bir api uygulaması vermeliyim
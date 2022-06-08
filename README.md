# Homework-ZP
Cybersecurity homework - PGP

OK baki, naša grupa je 1. 
  
Алгоритми за асиметричне кључеве које апликација треба да подржи су:  
Група 1: RSA за потписивање и енкрипцију са кључевима величине 1024, 2048 или 4096 бита  
Алгоритми за симетричне кључеве које апликација треба да подржи су:  
Група 1: 3DES са EDE конфигурацијум и три кључа и CAST5 са кључем величине 128 бита.    
  
   
~~@gaventee OK, listen baki, nova konvencija:~~
* ~~Sve promene koje imaš radi na nekom branchu, pa otvori pull request. (Blokirao sam direkan komit na main, da me ne zajebeš negde)~~
* ~~Ako PR nema veze sa mnom, samo ga zatvori sam.~~
* ~~Ako treba nešto da znam šta si radio u PRu, dodaj me, i samo iskomentariši šta misliš da je bitno za mene, i ako nema ništa da ja testiram, slobodno zatvori PR.~~
* ~~Ako imaš neki stub za koji čekaš mene, stavi `throw new NotImplementedException();`. I ja isto stavljam tamo gde čekam tebe.~~
* Radi šta hoćeš, ja sam završio.

Promena plana:
* kad se napravi privatni/javni par samo se u privatne stavlja, ima dugme da exportuje javni iz privatnog a tvoj javni ti nigde drugo ni ne treba, ako baš oćeš moš da ga eksportuješ pa importuješ kao samo javni
* import i export dugmići se odnose na klučeve za trenutno otvoren panel i import baca grešku ako ne odgovara fajl.
* šifra se traži samo za potpis/šifrovanje, za import/eksport ne mora pošto je svejedno ključ beskoristan bez šifre

INSERT INTO `role` (`id_role`, `name`) VALUES ('1', 'ROLE_USER'), ('2', 'ROLE_WORKER'), ('3', 'ROLE_ADMIN');
INSERT INTO `role_uzytkownikow` (`uzytkownicy_id`, `role_id`) VALUES ('1', '3'), ('2', '1');
INSERT INTO `uzytkownicy_informacje` (`id_uzytkownicy_info`, `miasto`, `nazwisko`, `imie`, `telefon`, `kod_pocztowy`, `ulica`, `uzytkownik_id`) VALUES (NULL, 'Lublin', 'Jan', 'Kowalski', '+48123456789', '20-218', 'Organowa', '1'), (NULL, 'Lublin', 'Tomasz', 'Nowak', '123456789', '20-111', 'Lubomelska', '2');
INSERT INTO `uzytkownicy` (`id_uzytkownicy`, `email`, `login`, `haslo`) VALUES ('1', 'jan.kowalski@gmail.com', 'jan', '$2a$10$/fajymyED6f0syj0CWdm8eqCaFCmDEBb7vPi4KYdgfWyFewwCPp0e'), ('2', 'tomasz.nowak@gmail.com', 'tomasz', '$2a$10$jIkp3wsonyBxGdH.uYjF.uXKHg2TW/xtJ39Kn4j6NB3gpJi6wnLzW');
INSERT INTO `status_paczki` (`id_status`, `opis`, `nazwa`) VALUES ('1', 'W dostawie', 'W dostawie'), ('2', 'W magazynie', 'W magazynie');
INSERT INTO `paczki` (`id_paczki`, `uwagi`, `data_nadania`, `wysokosc`, `dlugosc`, `numer_paczki`, `szerokosc`, `samochody_id`, `kody_id`, `zawartosc_id`, `odbiorcy_id`, `nadawcy_id`, `status_id`) VALUES (NULL, 'uwagi do paczki', '2019-08-08 07:22:08', '120', '120', '001', '120', '1', '3', '1', '1', '1', '1'), (NULL, 'uwagi do paczki2', '2019-08-09 00:00:00', '100', '100', '002', '100', '2', '4', '2', '2', '2', '2');
INSERT INTO `nadawcy` (`id_nadawcy`, `miasto`, `nazwa_firmy`, `email`, `nazwisko`, `imie`, `telefon`, `kod_pocztowy`, `ulica`) VALUES (NULL, 'Lublin', 'brak', 'jan.kowalski@gmail.com', 'Kowalski', 'Jan', '123456789', '20218', 'Organowa'), (NULL, 'Lublin', '', 'tomasz.nowak@gmail.com', 'Nowak', 'Tomasz', '123456789', '20882', 'Lubomelska');
INSERT INTO `odbiorcy` (`id_odbiorcy`, `miasto`, `nazwa_firmy`, `email`, `nazwisko`, `imie`, `telefon`, `kod_pocztowy`, `ulica`) VALUES (NULL, 'Wrocław', 'brak', 'krzysztof.kuna@gmail.com', 'Kuna', 'Krzysztof', '123123123', '20558', 'Sokola'), (NULL, 'Wrocław', 'firma1', 'maciej.kowal@gmail.com', 'Kowal', 'Maciej', '123412345', '20991', 'Szkolna');
INSERT INTO `status_samochodu` (`id_status`, `opis`, `nazwa`) VALUES (NULL, 'W terenie', 'Teren'), (NULL, 'Samochód stoi w pobliżu firmy', 'Wolny');
INSERT INTO `samochody` (`id_samochody`, `marka`, `pojemnosc`, `kolor`, `uwagi`, `typ_silnika`, `numer_rejestracyjny`, `ladownosc`, `model`, `moc`, `rodzaj`, `status_samochodu_id`, `kody_id`) VALUES (NULL, 'Audi', '1.9', 'srebrny', 'brak', 'Diesel', 'LU123AB', '1250', 'A4 B5', '115', 'Sedan', '1', '1'), (NULL, 'Volvo', '2.0', 'Czarny', 'powypadkowy', 'Diesel', 'LU98745', '1000', 'S80', '136', 'Sedan', '2', '2');
INSERT INTO `magazyny` (`id_magazyny`, `miasto`, `opis_magazynu`, `telefon`, `kod_pocztowy`, `ulica`, `kody_id`) VALUES (NULL, 'Lublin', 'Magazyn główny', '123456789', '20222', 'Budowlana', '5'), (NULL, 'Wrocław', 'Magazyn główny', '987654321', '20991', 'Szkolna', '6');
INSERT INTO `kody` (`id_kody`, `sciezka_do_pliku`) VALUES (NULL, 'C:\\Users\\Samochod1'), (NULL, 'C:\\Users\\Samochod2'),  (NULL, 'C:\\Users\\Paczka1'), (NULL, 'C:\\Users\\Paczka2'),  (NULL, 'C:\\Users\\Magazyn1'), (NULL, 'C:\\Users\\Magazyn2'), (NULL, 'C:\\Users\\Towar1'), (NULL, 'C:\\Users\\Towar2');
INSERT INTO `kategorie` (`id_kategorii`, `opis`, `nazwa`) VALUES (NULL, 'UWAGA SZKŁO', 'Szkło'), (NULL, 'sprzęt agd', 'AGD');
INSERT INTO `zawartosc` (`id_zawartosc`, `opis_zawartosci`, `czas_kompletacji`) VALUES (NULL, 'W paczce znajduje się sprzęt RTV i AGD', '2019-08-13 00:00:00'), (NULL, 'W paczce znajdują się meble.', '2019-08-08 00:00:00');
INSERT INTO `towary` (`id_towary`, `uwagi`, `nazwa`, `waga`, `kategoria_id`, `kody_id`, `zawartosc_id`) VALUES (NULL, 'Delikatny sprzęt', 'Telewizor', '3.2', '2', '7', '1'), (NULL, 'Części do złożenia biurka', 'Biurko', '11.20', '1', '8', '2');
INSERT INTO `paczki_magazyny` (`paczki_id`, `magazyny_id`) VALUES ('1', '1'), ('2', '2');
INSERT INTO `paczki_uzytkownikow` (`uzytkownicy_id`, `paczki_id`) VALUES ('1', '1'), ('2', '2');

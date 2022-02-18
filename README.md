# XML-Veb-Servisi - Информациони систем за вакцинацију грађана
Projekat iz predmeta XML i veb servisi.


## Asistent
- [Veljko Maksimović]

## Članovi tima
- [Marko Bjelica SW 04/2018]
- [Darko Tica SW 22/2018]
- [Bojan Baškalo SW 49/2018]
- [Veljko Tošić SW 55/2018]

## Upustvo za pokretanje sistema aplikacija

- Potrebno je pokrenuti ImunizacijaApp backend aplikaciju kao Spring boot aplikaciju 
  -  (Intelij - Maven project | Eclipse - Maven project )
- Potrebno je pokrenuti SluzbenikApp backend aplikaciju kao Spring boot aplikaciju 
  -  (Intelij - Maven project | Eclipse - Maven project )
- Potrebno je pokrenuti ImunizacijaAppFront frontend aplikaciju kao Angular aplikaciju 
  - (potreban Node: 16.13.0, npm 8.1.0  i Angular CLI: 13.1.4)
- Potrebno je pokrenuti SluzbeniкAppFront frontend aplikaciju kao Angular aplikaciju 
  - (potreban Node: 16.13.0, npm 8.1.0  i Angular CLI: 13.1.4)

- Potrebno je preko Docker Dekstopa kreirati 4 docker image-a 
  - ( 2 existDB ( sluzbenik:8081 i imunizacija:8082 ) i 2 fuseki (imunizacija:8083 i sluzbenik:8084) 
  - za svaki fuseki image je kreiran po jedan busybox kontejner )

[Veljko Maksimović]: https://github.com/VeljkoMaksimovic
[Marko Bjelica SW 04/2018]: https://github.com/bjelicamarko
[Darko Tica SW 22/2018]: https://github.com/darkotica
[Veljko Tošić SW 55/2018]: https://github.com/tosic-sw
[Bojan Baškalo SW 49/2018]: https://github.com/BoJaN77799

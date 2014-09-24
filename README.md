<h1> 1. Opis problema </h1>
Aplikacija je izdrađena u okviru predmeta [Inteligentni sistemi](http://is.fon.rs/) koji se realizuje na Fakultetu Organizacionih nauka u Beogradu. Aplikacijom su preuzet podaci o receptima sa sledećih izvora na Web-u: [Yummly.com](http://www.yummly.com/) i [AllRecipes.com](http://allrecipes.com/). Za preuzimanje podataka sa sajta Yummly.com korišćen je [Yummly API](https://developer.yummly.com/). Strukturirani podaci (predstavljeni korišćenjem Microdata formata) sa sajta AllRecipes.com su umetnuti u same HTML stranice i za njihovu ekstrakciju korišćen je [Microdata to RDF Distiler](http://www.w3.org/2012/pyMicrodata/). Po preuzimanju, podaci su predstavljeni korišćenjem RDF vokabulara [schema:Recipe](http://schema.org/Recipe), a potom su smešteni u lokalni RDF repozitorijum.	      

<h1> 2. Domenski model </h1>
U skladu sa RDF vokabularom [schema:Recipe](http://schema.org/Recipe), kreiran je domenski model, predstavljen sledećim dijagramom:

<img src="https://github.com/jelicatus/InteligetniPretraga/blob/master/domenskiModelIntFinalni.png" />

<h1> 3. Rešenje </h1>
Na sajtu AllRecipes.com podaci o receptima su umetnuti u HTML i predstavljenim preko Microdata standarda. Strukturirani podaci su preuzimani korišćenjem alata [Microdata to RDF Distiler](http://www.w3.org/2012/pyMicrodata/) koji omogućava ekstrakciju podataka predstavljenih u Microdata formatu u željeni format odgovara. Korišćeni format odogovora je JSON format. Podaci dalje parsirani i konvertovani u odgovarajuce objekte domenskog modela.

Za preuzimanje podataka sa sajta Yummly.com korišćen je API koji vraća podatke u JSON formatu. Najpre je izvršen GET poziv za pretragu recepata kako bi se dobili njihovi id-jevi, a potom GET poziv koji u sebi sadrži id recepta, gde je odgovor u JSON format i sadrži podatke o receptu. Podaci o receptu su parsirani i konvertovani u odgovarajuce objekte domenskog modela.

Dalje, instance domenskog modela su transformisani u RDF triplete i sačuvani u lokalnu RDF bazu.

<h1> 4. Tehnička realizacija </h1>

Aplikacija je rađena u programskom jeziku Java.
Za analiziranje web stranica korišćena je [Jsoup biblioteka](http://jsoup.org/). U pitanju je biblioteka koja omogućava parsiranje HTML stranica pomoću pogodnog API-a za ekstrakciju podataka, kao i pretragu i manipulaciju podacima. Ona obezbeđuje pristup željenim DOM elementima. 

Za preuzimanje Microdata podataka direktno iy HTML-a korišćen je alat [Microdata to RDF Distiler](http://www.w3.org/2012/pyMicrodata/) koji omogućava ekstrakciju podataka predstavljenih u Microdata formatu u željeni format odgovara.

Za manipulaciju sa podacima u JSON formatu korišćena je biblioteka [JSON.simple](https://code.google.com/p/json-simple/).

U aplikaciji je korišćena i [Jenabean biblioteka](https://code.google.com/p/jenabean/), pomoću se vrši mapiranje Java objekata u RDF putem anotacija. [Jena TDB](http://jena.apache.org/documentation/tdb/) je komponeneta [Jena framework](http://jena.apache.org/index.html)-a koja se koristi se za skladištenje RDF podataka i omogućava izvršavanje SPARQL upita nad sačuvanim podacima.

<h1> 1. Opis problema </h1>
Aplikacija je izdrađena u okviru predmeta Inteligentni sistemi koji se realizuje na Fakultetu Organizacionih nauka u Beogradu. Aplikacijom su preuzet podaci o receptima sa sledećih izvora na Web-u: Yummly.com i AllRecipes.com. Za preuzimanje podataka sa sajta Yummly.com korišćen je API (https://developer.yummly.com/), a struktuirani podaci (predstavljeni korišćenjem Microdata formata) sa sajta AllRecipes.com su umetnuti u same HTLM stranice i za njihovu ekstrakciju korišćen je Microdata to RDF Distiler (http://www.w3.org/2012/pyMicrodata/). Po preuzimanju, podaci su predstavljeni korišćenjem RDF vokabulara schema:Recipe, a potom su smešteni u lokalni RDF repozitorijum Jena TDB.	      

<h1> 2. Domenski model </h1>
U skladu sa RDF vokabularom Recipe, kreiran je domenski model, predstavljen sledećim dijagramom:

<img src="https://github.com/jelicatus/InteligetniPretraga/blob/master/domenskiModelIntFinalni.png" />

<h1> 3. Rešenje </h1>
Kreirana je aplikacija koja prikuplja meta podatke o receptima predstavljenim preko Microdata standarda sa web sajta AllRecipes.com I podatke o receptima sa sajta Yummly.com korišćenjem njihovog API-ja. Na osnovu tih podataka ona kreira odgovarajuće objekte domenskog modela, a zatim te objekte čuva u lokalnu RDF bazu. 
<h1> 4. Tehnička realizacija </h1>
Aplikacija je rađena u programskom jeziku Java.
AllRecipe.com - Za analiziranje web stranica korišćena je Jsoup biblioteka. U pitanju je biblioteka koja omogućava parsiranje HTML stranica pomoću pogodnog API-a za ekstrakciju podataka, kao i pretragu i manipulaciju podacima. Ona obezbeđuje pristup željenim DOM elementima. Za prikupljanje podataka korišćen je je Microdata to RDF Distiler (http://www.w3.org/2012/pyMicrodata/) koji omogućava ekstrakciju podataka predstavljenih u microdata formatu u željeni format odgovara. Ovde je korišćen odogovor u .json formatu, koji je potom parsiran korišćenjem biblioteke JSON.simple (https://code.google.com/p/json-simple/). 
Yummly.com – Za preuzimanje podataka sa ovog sajta korišćen je API koji vraća podatke u .json formatu. Najpre je izvršen GET poziv za pretragu recepata kako bi se dobili njihovi id-jevi, a potom GET poziv koji u sebi sadrži id recepta, gde je odgovor u .json format I sadrži podatke o receptu. Podaci o receptu su parsirani korišćenjem JSON simple biblioteke.
Podaci preuzeti sa oba sajta su korišćeni za kreiranje objekata domenskog modela.
U aplikaciji je korišćena i Jenabean biblioteka, pomoću se vrši mapiranje Java objekata u RDF putem anotacija. Jena TDB je komponeneta Jena framework-a koja se koristi se za skladištenje  RDF podataka I omogućava izvršavanje SPARQL upita nad sačuvanim podacima.

package com.springmvc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;



/**
 * 
 * Raportoi kuinka Spring MVC toimii ja mitkä ovat sovelluksen eri tiedostojen roolit? 
 * -----------------------------------------------------------------------------------
 * 
 * Tässä esimerkissä luotu Spring MVC -applikaatio toimii MVC -mallin mukaisesti. Kun 
 * käyttäjä tulee sovelluksen kotisivulle ("localhost:8080/widgets"). Näkee Spring
 * -annotaatiolla lisätty @Controller -luokka (WidgetController) kyseinen URL:n ja 
 * kutsuu sitä edustavaa funktiota - getWidgets(). Kyseinen funktio (ja luokka) käyttää
 * @AutoWired -automaattisesti annotoitua injektiota - WidgetRepository, jonka avulla 
 * voimme tehdä SQL-kyselyitä meidän JPA-luokan taululle, missä se on luotu (@Entity).
 * Löydetyt widgetit lisätään Springin omaan UI-modeliin (Model-objekti), jota voidaan
 * myös käyttää Thymeleaf -templaatissa. Tämän jälkeen kontrolleri palauttaa "widgets" 
 * -tekstiarvon, jonka perusteella Thymeleaf näyttää käyttäjälle vastaavan nimisen templaatin
 * (resources/templates/widgets.html). Tässä templaatissa voimme käyttää Thymeleaf -kirjaston
 * omaa annotaatiota tulostaa kaikki löydetyt widgetit for -loopin avulla tauluun.
 * 
 * Esimerkiksi: yhden widgetin muokkaus widgets -listalta. 
 *  -> Käyttäjä painaa "Edit" -nappia johon on yhdistetty URL-osoite "/widget/edit/{{id}}"
 *  -> Kontrolleri (WidgetController) vastaanottaa kyseinen URL-osoitteen ja suorittaa
 *    sitä vastaavan funktion (editWidget()). 
 *  -> Muokattavan widgetin ID tulee mukana parametrina, ja kontrolleri kutsuu 
 *    WidgetRepository -rajanpintaa, jolla on mahdollista tehdä ORM-SQL -kyselyitä tietokantaan,
 *    joka on määritelty Widget.java -JPA-luokassa.
 *  -> Jos widget löydetään, se lisätään Modelin attribuuttiin (jossei löydy luodaan tyhjä olio)
 *  -> Seuraavaksi kontrollerin funktio palauttaa string -arvon "widgetform", jota Thymeleaf 
 *    käyttää tietääkseen minkä templaatin käyttäjälle ladataan seuraavaksi. 
 *  -> widgetform.html -templaatissa vastaanotetaan Model -objekti, jonka avulla renderöidään
 *    widgetin arvot lomakkeeseen (nimi ja selite).
 *  -> Kun käyttäjä on muokannut arvoja ja painaa "Submit" -nappia, viedään nämä arvot vastaavaan
 *    URL-osoitteeseen kontrollerin funktiossa (updateWidget()). 
 *  -> Päivitetty widget tallennetaan tietokantaan WidgetRepository -rajapinnan avulla.
 *  -> Lopuksi kontrollerin funktio palauttaa arvon "redirect:/widget/{{id}}", joka vie 
 *    käyttäjän widget.html -templaattiin, jossa juuri päivitetty widget näytetään ID:n
 *    perusteella
 * 
 * 
 * Kuinka Spring-konteksti toimii Spring Boot -sovelluksessa?
 * ----------------------------------------------------------
 * 
 * Spring -konteksti on rekisteri kaikista vapaista Spring -beaneista.
 * Luokat voidaan luokitella Spring -beaneiksi, jos niissä käytetään
 * Springin omia annoaatioita kuten @Service, @Controller ja @Entity.
 * 
 * Kun beanet on merkitty, ne pitää rekisteröidä Spring -kontekstiin; tämän
 * Spring Boot tekee automaattisesti suorittamalla projektin kaikkien 
 * luokkien pakettiskannauksen. Kun Spring -konteksti on nyt rakenteilla, 
 * toteuttaa se IoC (Inversion of Control) -suunnittelumallin riippuvuusinjektioin
 * avulla. Eli kun esim. Spring bean tarvitsee riippuvuutta, kuten palvelua tai 
 * repoa, papu voi joko määrittää konstruktorin, joka hyväksyy riippuvaisen beanin 
 * tai se voi hyödyntää @AutoWired -annotaatiota kertoakseen Springille, että se 
 * tarvitsee kyseisen riippuvuudeen. Spring ratkaisee kaikki riippuvuuudet ja 
 * "vahvistaa" (autowires) sovelluksen yhteen.
 * 
 * Toisinsanoen luokkasi kertoo containerille mitä riippuvuuksia se tarvitsee
 * suorittamiseen, johon container tarjoaa sopivat riippuvuudet luokalle ajon
 * aikana.
 * 
 * 
 * Mitä Application-tiedoston @SpringBootApplication -annotaatio tekee?
 * --------------------------------------------------------------------
 * 
 * @SpringBootApplication -annotaatio pitää sisällään seuraavanlaisia 
 * annotaatioita:
 *
 *  - @Configuration -annotaatio kertoo Spring -rajapinnalle, että alla
 *    oleva luokka sisältää konfiguraatio-tietoja. (Tätä annotaatiota voidaan
 *    käyttää beanien luomiseen, jotka halutaan rekisteröidä Springin kontekstiin.
 *  
 *  - @EnableAutoConfiguration -annotaatio kehottaa Springia määrittämään
 *    resurssit automaattisesti, joita se löytyää CLASSPATH -määritelmästä (kuten
 *    H2 ja TomCat).
 * 
 *  - @ComponentScan -annotaatio kehottaa Springia skannaamaan CLASSPATH -paketit
 *    nykyisen paketin (tässä tapauksessa com.springmvc -paketti) alla toimivista 
 *    Spring -annotaatioista kuten @Service ja @Controller
 * 
 */
@SpringBootApplication
public class SpringBootMvcApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootMvcApplication.class, args);
	}

}

package com.springmvc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.springmvc.model.Widget;
import com.springmvc.repository.WidgetRepository;

/**
 * Mistä komponenteista muodostuvat model, view ja controller? 
 * -----------------------------------------------------------
 * = KONTROLLERI
 * --------------------------------
 * 
 * Kontrolleri on näkymän (view) ja modelin (model) välissä, joka tutkii
 * pyyntöjä näkymän kautta ja muuntaa ne model -luokalle valmiiksi ja
 * toisinpäin. Eli se voi hyväksyä näkymän tulevia pyyntöjä, päivittää
 * modelia ja lähettää näkymälle päivitetyn model takaisin, joka tulostaa
 * sen käyttäjälle.
 * 
 * Kun Spring -framework suorittaa luokkien paketti-skannaukset, se löytää
 * tästä luokasta @Controller -annotaation omaavan luokan, tekee siitä 
 * instanssin ja lisää sen Springin kontekstiin, joka on konfiguroitu 
 * käsittelemään web-pyyntöjä. 
 * 
 * Kontrolleri käyttää WidgetRepository -instanssia itse feikkidatan hakuun.
 * Modelina toimii "Widget" -niminen objekti. ORM -tietokantakyselyt toimivat 
 * H2 -kirjaston kanssa tulleella rajapinnalla.
 * 
 * Koska viewissä (näkymässä) käytetään Thymeleaf -templaatti-moottoria, palauttaa
 * tämän kontrollerin jokainen funktio tekstinpätkän, joka toimii URL:na, jonka 
 * view lataa seuraavaksi kun kontrollerin funktio on suoritettu ja tarvittavat 
 * toimet ollaan tehty.
 * 
 * Funktiot, jotka palauttavat suoran templaatin nimen (esim. "widget"/"widgetform" tmv.),
 * palauttavat myös Model -objektin. Model -objekti toteuttaa Springin UI-käyttöliittymää
 * ja tarjoaa menetelmiä määritteiden lisäämiseksi Modeliin. Nämä attribuutit asetetaan 
 * käytettäviksi templaatille, joka tuodaan käyttäjälle.
 * 
 * 
 * @Controller -annotaatio kertoo Springille, että kyseinen luokka
 * toimii kontrollerina tässä Spring MVC -applikaatiossa. 
 * 
 * @GetMapping - annotaatiolla voidaan kartoittaa HTTP-GET -pyyntö
 *  tiettyihin käsittelymenetelmiin
 * @PostMapping - annotaatiolla voidaan kartoittee HTTP-POST -pyyntö
 *  tiettyihin käsittelymenetelmiin
 */
@Controller
public class WidgetController {

  /**
   * @Autowired -annotaation ansiosta meidän ei itse tarvitse määrittää injektion 
   * määritystä. Kun Spring näkee tämän annotaation, se etsii WidgetRepository 
   * -implementaation ja automaattisesti injektoi sen määritysten mukaan tähän luokkaan.
   */
  @Autowired
  private WidgetRepository widgetRepository;
  
  /**
   * Funktio, joka lisää modeliin uuden attribuutin, joka on 
   * tyhjä Widget -olio. Tämän jälkeen funktio palauttaa 
   * tekstiarvon, joka lataa "/widgetform" -sivun. Tätä funktiota
   * käytetään kun halutaan ladata templaatti, jossa luodaan
   * uusi widgetti.
   * 
   * @param model - Springin UI -model
   * @return Tekstinpätkän, joka lataa "/widgetform" -sivun
   */
  @GetMapping("/widget/new")
  public String newWidget(Model model) {
    model.addAttribute("widget", new Widget());
    return "widgetform";
  }
  
  /**
   * Funktio, joka luo uuden widgetin. Uuden tallennetun widgetin
   * arvot tulevat parametrina funktioon ja käyttäen injektoitua
   * WidgetRepository -rajapintaa, tallennetaan uusi widgetin arvo
   * tietokantaan. Tämän jälkeen käyttäjä ohjataan juuri luodun widgetin
   * yksityiskohta -sivulle, joka renderöidään sen ID:n avulla.
   * 
   * @param widget - uuden widgetin arvot
   * @param model - Springin UI -model
   * @return Tekstinpätkän, joka lataa "/widget" -sivun
   */
  @PostMapping("/widget")
  public String createWidget(Widget widget, Model model) {
    widgetRepository.save(widget);
    return "redirect:/widget/" + widget.getId();
  }
  
  /**
   * Funktio, joka etsii widgetin ID:n perusteella. ID tulee
   * parametrina. Käyttäen hyväksi injektoitua WidgetRepository -rajapintaa,
   * koitetaan etsiä tätä widgettiä ID:n perusteella. Jos widgettiä ei löydy,
   * palautetaan uusi tyhjä widget -olio.
   * 
   * @param id - ID, jolla etsitään widgettiä
   * @param model - Springin UI-model
   * @return Tekstinpätkän, joka lataa "/widget" -sivun
   */
  @GetMapping("/widget/{id}")
  public String getWidgetById(@PathVariable Long id, Model model) {
    model.addAttribute("widget", widgetRepository.findById(id)
      .orElse(new Widget()));
    return "widget";
  }
  
 /**
  * Funktio, joka hakee kaikki widgetit käyttäen injektoitua
  * WidgetRepository -rajapintaa. Tätä funktiota kutsutaan kun
  * käyttäjä saapuu "localhost:8080/widgets" -sivulle. Kun kaikki
  * widgetit ollaan haettu ja laitettu model -objektiin, palauttaa
  * funktio "widgets" -arvon, jota Thymeleaf käyttää viedäkseen 
  * käyttäjät oikeaan osoitteeseen.
  * 
  * @param model - Springin UI -model
  * @return Tekstinpätkän, joka lataa "/widgets" -sivun
  */
  @GetMapping("/widgets")
  public String getWidgets(Model model) {
    model.addAttribute("widgets", widgetRepository.findAll());
    return "widgets";
  }
  
  /**
   * Funktio, jonka avulla voidaan muokata olemassaolevaa widgettiä.
   * Funktioon tulee parametrina muokattavat widgetin ID. Käyttäen 
   * hyväksi injektoitua WidgetRepository -rajapintaa, koitetaan
   * etsiä tämä widget ID:n perusteella, ja josse löytyy, laitetaan
   * se modelin attribuuttiin (muussa tapauksessa tyhjä olio). 
   * Käyttäjä sen jälkeen viedään "/widgetform" -osoitteeseen.
   * 
   * @param id - Widgetin ID, jota muokataan
   * @param model - Springin UI -model
   * @return Tekstinpätkän, joka lataa "/widgetform" -sivun
   */
  @GetMapping("/widget/edit/{id}")
  public String editWidget(@PathVariable Long id, Model model) {
    model.addAttribute("widget", widgetRepository.findById(id)
      .orElse(new Widget()));
    return "widgetform";
  }
  
  /**
   * Funktio, joka päivittää muokatun widgetin tietokantaan. Päivitetty
   * widgetti tulee paramterina. Kun se on tallennettu tietokantaan, viedään
   * käyttäjä tämän päivitetyn widgetin yksityiskohta -sivulle ("/widget/{{id}}”).
   * 
   * @param widget - widget, jota päivitetään.
   * @return Tekstinpätkän, joka lataa tietyn widgetin sivun (ID:n perusteella)
   */
  @PostMapping("/widget/{id}")
  public String updateWidget(Widget widget) {
    widgetRepository.save(widget);
    return "redirect:/widget/" + widget.getId();
  }
  
  /**
   * Funktio, jonka avulla poistetaan widgetti ID:n perusteella käyttäen hyväksi
   * injektoitua WidgetRepository -rajapintaa. Kun se on poistettu, uudelleenohjataan
   * käyttäjä takaisin etusivulle ("/widgets").
   * 
   * @param id - Widgetin ID, joka poistetaan
   * @return Tekstinpätkän, joka lataa pääsivun ("/widgets") uudestaan
   */
  @GetMapping("/widget/delete/{id}")
  public String deleteWidget(@PathVariable Long id) {
    widgetRepository.deleteById(id);
    return "redirect:/widgets";
  }
  
}

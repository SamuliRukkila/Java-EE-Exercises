package com.springmvc.repository;

import org.springframework.data.repository.CrudRepository;
import com.springmvc.model.Widget;

/**
 * WidgetRepository -rajapinta tarjoaa valmiita tietokanta -operaatioita 
 * widgeteille, kuten findById(), findAll(), deleteById() jne.
 * 
 * Kyseinen rajapinta toimii modelin (Widget JPA -luokka) kanssa yhteistyössä,
 * jotta se hakee tarvittavat tiedot taulusta.
 * 
 * Kahdesta alla olevasta luokan parameterista:
 *  1. on Widget, mikä kuvastaa entiteetin (JPA luokka: Widget) 
 *    tyyppiä, jota tämä rajapinta hallitsee 
 *  2. on Widget -rajapinnan ensisijaisen avaimen (id) tyyppi
 *  
 * Käyttäen hyväksi H2 -kirjaston moottoria, meidän ei itse tarvitse luoda tai
 * yhdistää tietokantaan, vaan H2 luo sen meille automaattisesti. Tämä helpottaa
 * kehitysvaiheessa CRUD + MVC -toimintojen testausta ja varmuutta.
 */
public interface WidgetRepository extends CrudRepository<Widget, Long> {}
